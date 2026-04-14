package com.mzansibuilds.view;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class NavbarLayout extends HorizontalLayout {

    public NavbarLayout(String username) {
        setWidthFull();
        setAlignItems(Alignment.CENTER);
        getStyle()
                .set("background-color", "#1b5e20")
                .set("padding", "0.75rem 1.5rem")
                .set("margin-bottom", "1rem");

        H3 brand = new H3("MzansiBuilds");
        brand.getStyle()
                .set("color", "white")
                .set("margin", "0")
                .set("margin-right", "2rem");

        Anchor dashboard = styledLink("My Projects", "/dashboard");
        Anchor feed = styledLink("Project Feed", "/feed");
        Anchor celebration = styledLink("🏆 Celebration Wall", "/celebration");

        Span spacer = new Span();
        spacer.getStyle().set("flex-grow", "1");

        Span welcome = new Span("👋 " + username);
        welcome.getStyle()
                .set("color", "#a5d6a7")
                .set("margin-right", "1rem");

        Anchor logout = styledLink("Logout", "/logout");

        add(brand, dashboard, feed, celebration, spacer, welcome, logout);
    }

    private Anchor styledLink(String text, String href) {
        Anchor link = new Anchor(href, text);
        link.getStyle()
                .set("color", "white")
                .set("margin-right", "1rem")
                .set("text-decoration", "none");
        return link;
    }
}