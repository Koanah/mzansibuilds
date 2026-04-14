package com.mzansibuilds.view;

import com.mzansibuilds.entity.*;
import com.mzansibuilds.security.SecurityUtils;
import com.mzansibuilds.service.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;
import java.time.LocalDate;
import java.util.Optional;

@Route("project/:projectId")
@PermitAll
public class MilestoneView extends VerticalLayout implements BeforeEnterObserver {

    private final ProjectService projectService;
    private final MilestoneService milestoneService;
    private final CommentService commentService;
    private final CollaborationRequestService collabService;
    private final SecurityUtils securityUtils;

    private Project project;
    private Grid<Milestone> milestoneGrid;
    private Grid<Comment> commentGrid;

    public MilestoneView(ProjectService projectService,
                         MilestoneService milestoneService,
                         CommentService commentService,
                         CollaborationRequestService collabService,
                         SecurityUtils securityUtils) {
        this.projectService = projectService;
        this.milestoneService = milestoneService;
        this.commentService = commentService;
        this.collabService = collabService;
        this.securityUtils = securityUtils;
        setPadding(false);
        setSpacing(false);
    }

    // BeforeEnterObserver fires before the view renders,
    // giving us a chance to read the URL parameter (:projectId)
    // and load the correct project from the database.
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String idStr = event.getRouteParameters().get("projectId").orElse("0");
        Long projectId = Long.parseLong(idStr);

        Optional<Project> found = projectService.getAllProjects().stream()
                .filter(p -> p.getId().equals(projectId))
                .findFirst();

        if (found.isEmpty()) {
            event.forwardTo("dashboard");
            return;
        }

        project = found.get();
        buildView();
    }

    private void buildView() {
        removeAll();
        User currentUser = securityUtils.getCurrentUser();
        boolean isOwner = project.getUser().getId().equals(currentUser.getId());

        add(new NavbarLayout(currentUser.getUsername()));

        VerticalLayout content = new VerticalLayout();
        content.setPadding(true);

        // Back button — goes to feed or dashboard depending on who's viewing
        Button backBtn = new Button(isOwner ? "← Back to Dashboard" : "← Back to Feed",
                e -> getUI().ifPresent(ui -> ui.navigate(isOwner ? "dashboard" : "feed")));
        backBtn.getStyle().set("color", "#2e7d32");

        H2 projectTitle = new H2(project.getTitle());
        projectTitle.getStyle().set("color", "#2e7d32");

        Paragraph desc = new Paragraph(project.getDescription());
        Paragraph stage = new Paragraph("Stage: " + project.getStage());
        stage.getStyle().set("font-weight", "bold");
        Paragraph support = new Paragraph("Support needed: " + project.getSupportRequired());
        Paragraph statusP = new Paragraph("Status: " + project.getStatus());

        content.add(backBtn, projectTitle, desc, stage, support, statusP);

        // Mark as completed — only the owner can do this
        if (isOwner && "ACTIVE".equals(project.getStatus())) {
            Button completeBtn = new Button("Mark as Completed 🎉", e -> {
                projectService.markCompleted(project);
                Notification.show("Congratulations! Project marked as completed.");
                getUI().ifPresent(ui -> ui.navigate("dashboard"));
            });
            completeBtn.getStyle()
                    .set("background-color", "#1b5e20")
                    .set("color", "white");
            content.add(completeBtn);
        }

        // MILESTONES
        content.add(new H3("Milestones"));

        milestoneGrid = new Grid<>(Milestone.class, false);
        milestoneGrid.addColumn(Milestone::getTitle).setHeader("Title").setAutoWidth(true);
        milestoneGrid.addColumn(Milestone::getDescription).setHeader("Description").setAutoWidth(true);
        milestoneGrid.addColumn(m -> m.getDate() != null ? m.getDate().toString() : "")
                .setHeader("Date").setAutoWidth(true);

        if (isOwner) {
            milestoneGrid.addComponentColumn(m -> {
                Button del = new Button("Delete", e -> {
                    milestoneService.deleteMilestone(m.getId());
                    refreshMilestones();
                    Notification.show("Milestone deleted");
                });
                del.getStyle().set("color", "red");
                return del;
            }).setHeader("Actions");
        }

        milestoneGrid.setWidthFull();
        refreshMilestones();
        content.add(milestoneGrid);

        // Add milestone form — only for the project owner
        if (isOwner) {
            TextField mTitle = new TextField("Milestone Title");
            mTitle.setWidth("250px");

            TextArea mDesc = new TextArea("Description");
            mDesc.setWidth("250px");

            DatePicker mDate = new DatePicker("Date");
            mDate.setValue(LocalDate.now());

            Button addBtn = new Button("Add Milestone", e -> {
                if (mTitle.isEmpty()) {
                    Notification.show("Milestone title is required");
                    return;
                }
                milestoneService.addMilestone(
                        mTitle.getValue(), mDesc.getValue(), mDate.getValue(), project
                );
                mTitle.clear();
                mDesc.clear();
                refreshMilestones();
                Notification.show("Milestone added");
            });
            addBtn.getStyle()
                    .set("background-color", "#2e7d32")
                    .set("color", "white");

            HorizontalLayout milestoneForm = new HorizontalLayout(mTitle, mDesc, mDate, addBtn);
            milestoneForm.setAlignItems(Alignment.END);
            content.add(milestoneForm);
        }

        // COLLABORATION REQUEST
        // Only show to non-owners viewing other people's projects
        if (!isOwner) {
            content.add(new H3("Interested in this project?"));
            Button collabBtn = new Button("Request to Collaborate", e -> {
                try {
                    collabService.sendRequest(currentUser, project);
                    Notification.show("Collaboration request sent to the project owner!");
                } catch (IllegalStateException ex) {
                    Notification.show(ex.getMessage());
                }
            });
            collabBtn.getStyle()
                    .set("background-color", "#212121")
                    .set("color", "white");
            content.add(collabBtn);
        }

        // COMMENTS
        content.add(new H3("Comments"));

        commentGrid = new Grid<>(Comment.class, false);
        commentGrid.addColumn(c -> c.getUser().getUsername()).setHeader("User").setAutoWidth(true);
        commentGrid.addColumn(Comment::getMessage).setHeader("Comment").setAutoWidth(true);
        commentGrid.setWidthFull();
        refreshComments();
        content.add(commentGrid);

        TextArea commentInput = new TextArea("Leave a comment");
        commentInput.setWidth("500px");
        commentInput.setPlaceholder("Share your thoughts or feedback...");

        Button postBtn = new Button("Post Comment", e -> {
            if (commentInput.isEmpty()) return;
            commentService.addComment(commentInput.getValue(), currentUser, project);
            commentInput.clear();
            refreshComments();
        });
        postBtn.getStyle()
                .set("background-color", "#2e7d32")
                .set("color", "white");

        content.add(commentInput, postBtn);
        add(content);
    }

    private void refreshMilestones() {
        milestoneGrid.setItems(milestoneService.getMilestonesForProject(project));
    }

    private void refreshComments() {
        commentGrid.setItems(commentService.getCommentsForProject(project));
    }
}