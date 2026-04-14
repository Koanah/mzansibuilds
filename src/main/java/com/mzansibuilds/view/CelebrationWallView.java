package com.mzansibuilds.view;

import com.mzansibuilds.entity.Project;
import com.mzansibuilds.security.SecurityUtils;
import com.mzansibuilds.service.ProjectService;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;
import java.util.List;

@Route("celebration")
@PermitAll
public class CelebrationWallView extends VerticalLayout {

    public CelebrationWallView(ProjectService projectService, SecurityUtils securityUtils) {
        setPadding(false);
        setSpacing(false);

        String username = securityUtils.getCurrentUser().getUsername();
        add(new NavbarLayout(username));

        VerticalLayout content = new VerticalLayout();
        content.setPadding(true);
        content.setAlignItems(Alignment.CENTER);

        H1 heading = new H1("🏆 Celebration Wall");
        heading.getStyle().set("color", "#2e7d32");

        Paragraph subtitle = new Paragraph(
                "Completed projects by the MzansiBuilds community."
        );
        subtitle.getStyle().set("color", "#666");

        content.add(heading, subtitle);

        List<Project> completed = projectService.getCompletedProjects();

        if (completed.isEmpty()) {
            Paragraph empty = new Paragraph(
                    "No completed projects yet — be the first to finish one!"
            );
            empty.getStyle().set("color", "#888");
            content.add(empty);
        } else {
            for (Project project : completed) {
                VerticalLayout card = new VerticalLayout();
                card.getStyle()
                        .set("border", "2px solid #2e7d32")
                        .set("border-radius", "8px")
                        .set("padding", "1.5rem")
                        .set("margin", "0.5rem")
                        .set("background-color", "#f1f8e9")
                        .set("width", "500px")
                        .set("box-shadow", "0 2px 6px rgba(0,0,0,0.08)");

                H3 title = new H3("🎉 " + project.getTitle());
                title.getStyle()
                        .set("color", "#1b5e20")
                        .set("margin", "0 0 0.5rem 0");

                Paragraph creator = new Paragraph("Built by: " + project.getUser().getUsername());
                creator.getStyle().set("color", "#555").set("margin", "0");

                Paragraph desc = new Paragraph(project.getDescription());
                desc.getStyle().set("color", "#444");

                card.add(title, creator, desc);
                content.add(card);
            }
        }

        add(content);
    }
}