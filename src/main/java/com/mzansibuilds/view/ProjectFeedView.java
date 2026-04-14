package com.mzansibuilds.view;

import com.mzansibuilds.entity.Project;
import com.mzansibuilds.entity.User;
import com.mzansibuilds.security.SecurityUtils;
import com.mzansibuilds.service.ProjectService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;
import java.util.List;
import java.util.stream.Collectors;

@Route("feed")
@PermitAll
public class ProjectFeedView extends VerticalLayout {

    public ProjectFeedView(ProjectService projectService, SecurityUtils securityUtils) {
        setPadding(false);
        setSpacing(false);

        User currentUser = securityUtils.getCurrentUser();
        add(new NavbarLayout(currentUser.getUsername()));

        VerticalLayout content = new VerticalLayout();
        content.setPadding(true);

        H2 heading = new H2("Project Feed");
        heading.getStyle().set("color", "#2e7d32");

        Paragraph sub = new Paragraph("Discover what other developers are building.");
        sub.getStyle().set("color", "#666");

        // Search bar
        TextField searchField = new TextField();
        searchField.setPlaceholder("Search by project title...");
        searchField.setWidth("300px");

        // Stage filter
        ComboBox<String> stageFilter = new ComboBox<>();
        stageFilter.setPlaceholder("Filter by stage");
        stageFilter.setItems("All", "Idea", "Planning", "In Progress", "Testing", "Launching");
        stageFilter.setValue("All");
        stageFilter.setWidth("200px");

        HorizontalLayout filters = new HorizontalLayout(searchField, stageFilter);
        filters.setAlignItems(Alignment.END);

        List<Project> feedProjects = projectService.getFeedForUser(currentUser);

        Grid<Project> grid = new Grid<>(Project.class, false);
        grid.addColumn(Project::getTitle).setHeader("Title").setAutoWidth(true);
        grid.addColumn(p -> p.getUser().getUsername()).setHeader("Creator").setAutoWidth(true);
        grid.addColumn(Project::getStage).setHeader("Stage").setAutoWidth(true);
        grid.addColumn(Project::getSupportRequired).setHeader("Support Needed").setAutoWidth(true);
        grid.addComponentColumn(project -> {
            Button viewBtn = new Button("View Project",
                    e -> getUI().ifPresent(ui -> ui.navigate("project/" + project.getId())));
            viewBtn.getStyle()
                    .set("background-color", "#2e7d32")
                    .set("color", "white");
            return viewBtn;
        }).setHeader("Actions");

        grid.setItems(feedProjects);
        grid.setWidthFull();

        // Filter logic — runs entirely on the already-loaded list, no extra DB calls
        Runnable applyFilters = () -> {
            String query = searchField.getValue().toLowerCase();
            String stage = stageFilter.getValue();

            List<Project> filtered = feedProjects.stream()
                    .filter(p -> p.getTitle().toLowerCase().contains(query))
                    .filter(p -> "All".equals(stage) || stage.equals(p.getStage()))
                    .collect(Collectors.toList());

            grid.setItems(filtered);
        };

        searchField.addValueChangeListener(e -> applyFilters.run());
        stageFilter.addValueChangeListener(e -> applyFilters.run());

        content.add(heading, sub, filters, grid);

        if (feedProjects.isEmpty()) {
            Paragraph empty = new Paragraph("No other projects yet. Invite your teammates!");
            empty.getStyle().set("color", "#888");
            content.add(empty);
        }

        add(content);
    }
}