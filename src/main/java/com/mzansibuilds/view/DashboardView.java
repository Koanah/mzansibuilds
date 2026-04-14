package com.mzansibuilds.view;

import com.mzansibuilds.entity.Project;
import com.mzansibuilds.entity.User;
import com.mzansibuilds.security.SecurityUtils;
import com.mzansibuilds.service.ProjectService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;
import java.util.List;

@Route("dashboard")
@PermitAll
public class DashboardView extends VerticalLayout {

    public DashboardView(ProjectService projectService, SecurityUtils securityUtils) {
        setPadding(false);
        setSpacing(false);

        User currentUser = securityUtils.getCurrentUser();

        add(new NavbarLayout(currentUser.getUsername()));

        VerticalLayout content = new VerticalLayout();
        content.setPadding(true);

        H2 heading = new H2("My Projects");
        heading.getStyle().set("color", "#2e7d32");

        Button newProjectBtn = new Button("+ New Project",
                e -> getUI().ifPresent(ui -> ui.navigate("project/new")));
        newProjectBtn.getStyle()
                .set("background-color", "#2e7d32")
                .set("color", "white")
                .set("margin-bottom", "1rem");

        List<Project> myProjects = projectService.getProjectsByUser(currentUser);

        Grid<Project> grid = new Grid<>(Project.class, false);
        grid.addColumn(Project::getTitle).setHeader("Title").setAutoWidth(true);
        grid.addColumn(Project::getStage).setHeader("Stage").setAutoWidth(true);
        grid.addColumn(Project::getStatus).setHeader("Status").setAutoWidth(true);
        grid.addColumn(Project::getSupportRequired).setHeader("Support Needed").setAutoWidth(true);
        grid.addComponentColumn(project -> {
            Button manageBtn = new Button("Manage",
                    e -> getUI().ifPresent(ui -> ui.navigate("project/" + project.getId())));
            manageBtn.getStyle()
                    .set("background-color", "#2e7d32")
                    .set("color", "white");
            return manageBtn;
        }).setHeader("Actions");

        grid.setItems(myProjects);
        grid.setWidthFull();

        content.add(heading, newProjectBtn, grid);

        if (myProjects.isEmpty()) {
            Paragraph empty = new Paragraph(
                    "You have no projects yet. Click '+ New Project' to get started."
            );
            empty.getStyle().set("color", "#888");
            content.add(empty);
        }

        add(content);
    }
}