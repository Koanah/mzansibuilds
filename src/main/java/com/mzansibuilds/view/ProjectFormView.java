package com.mzansibuilds.view;

import com.mzansibuilds.entity.User;
import com.mzansibuilds.security.SecurityUtils;
import com.mzansibuilds.service.ProjectService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;

@Route("project/new")
@PermitAll
public class ProjectFormView extends VerticalLayout {

    public ProjectFormView(ProjectService projectService, SecurityUtils securityUtils) {
        setPadding(false);
        setSpacing(false);

        User currentUser = securityUtils.getCurrentUser();
        add(new NavbarLayout(currentUser.getUsername()));

        VerticalLayout content = new VerticalLayout();
        content.setAlignItems(Alignment.CENTER);
        content.setPadding(true);

        H2 title = new H2("Create New Project");
        title.getStyle().set("color", "#2e7d32");

        TextField titleField = new TextField("Project Title");
        titleField.setWidth("400px");
        titleField.setRequired(true);
        titleField.setPlaceholder("e.g. Township Connect App");

        TextArea descField = new TextArea("Description");
        descField.setWidth("400px");
        descField.setMinHeight("120px");
        descField.setPlaceholder("What are you building and why?");

        ComboBox<String> stageBox = new ComboBox<>("Project Stage");
        stageBox.setItems("Idea", "Planning", "In Progress", "Testing", "Launching");
        stageBox.setWidth("400px");
        stageBox.setRequired(true);

        TextField supportField = new TextField("Support Required");
        supportField.setWidth("400px");
        supportField.setPlaceholder("e.g. Frontend Dev, UI Designer, Data Engineer");

        Button submitBtn = new Button("Create Project");
        submitBtn.setWidth("400px");
        submitBtn.getStyle()
                .set("background-color", "#2e7d32")
                .set("color", "white");

        Button cancelBtn = new Button("Cancel",
                e -> getUI().ifPresent(ui -> ui.navigate("dashboard")));
        cancelBtn.setWidth("400px");
        cancelBtn.getStyle()
                .set("color", "#2e7d32")
                .set("background-color", "white")
                .set("border", "1px solid #2e7d32");

        submitBtn.addClickListener(e -> {
            if (titleField.isEmpty() || stageBox.isEmpty()) {
                Notification.show("Project title and stage are required");
                return;
            }
            projectService.createProject(
                    titleField.getValue(),
                    descField.getValue(),
                    stageBox.getValue(),
                    supportField.getValue(),
                    currentUser
            );
            Notification.show("Project created successfully!");
            getUI().ifPresent(ui -> ui.navigate("dashboard"));
        });

        VerticalLayout form = new VerticalLayout(
                title, titleField, descField,
                stageBox, supportField,
                submitBtn, cancelBtn
        );
        form.setAlignItems(Alignment.CENTER);
        form.getStyle()
                .set("border", "1px solid #ddd")
                .set("border-radius", "8px")
                .set("padding", "2rem")
                .set("max-width", "500px")
                .set("box-shadow", "0 2px 8px rgba(0,0,0,0.08)");

        content.add(form);
        add(content);
    }
}