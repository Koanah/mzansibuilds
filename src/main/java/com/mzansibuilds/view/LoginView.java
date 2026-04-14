package com.mzansibuilds.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@AnonymousAllowed
public class LoginView extends VerticalLayout {

    public LoginView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        getStyle().set("background-color", "#ffffff");

        // Brand header
        H1 brand = new H1("MzansiBuilds");
        brand.getStyle()
                .set("color", "#2e7d32")
                .set("margin-bottom", "0");

        Paragraph tagline = new Paragraph("Where South African developers build together");
        tagline.getStyle()
                .set("color", "#555")
                .set("margin-top", "0");

        H3 subtitle = new H3("Sign in to your account");
        subtitle.getStyle().set("color", "#333");

        TextField usernameField = new TextField("Username");
        usernameField.setWidth("300px");

        PasswordField passwordField = new PasswordField("Password");
        passwordField.setWidth("300px");

        Button loginBtn = new Button("Login");
        loginBtn.setWidth("300px");
        loginBtn.getStyle()
                .set("background-color", "#2e7d32")
                .set("color", "white")
                .set("border-radius", "4px");

        Anchor registerLink = new Anchor("/register", "Don't have an account? Register here");
        registerLink.getStyle().set("color", "#2e7d32");

        // Spring Security expects a standard HTML form POST to /login
        // with fields named exactly "username" and "password".
        // We use hidden inputs + JavaScript to submit it when the button is clicked.
        com.vaadin.flow.component.Html form = new com.vaadin.flow.component.Html(
                "<form id='login-form' method='post' action='/login'>" +
                        "<input type='hidden' id='username-hidden' name='username'/>" +
                        "<input type='hidden' id='password-hidden' name='password'/>" +
                        "</form>"
        );

        loginBtn.addClickListener(e -> {
            String u = usernameField.getValue().trim();
            String p = passwordField.getValue();
            if (u.isEmpty() || p.isEmpty()) {
                Notification.show("Please enter your username and password");
                return;
            }
            getUI().ifPresent(ui -> ui.getPage().executeJs(
                    "document.getElementById('username-hidden').value = $0;" +
                            "document.getElementById('password-hidden').value = $1;" +
                            "document.getElementById('login-form').submit();",
                    u, p
            ));
        });

        VerticalLayout card = new VerticalLayout(
                brand, tagline, subtitle,
                usernameField, passwordField,
                loginBtn, registerLink, form
        );
        card.setAlignItems(Alignment.CENTER);
        card.getStyle()
                .set("border", "1px solid #ddd")
                .set("border-radius", "8px")
                .set("padding", "2rem")
                .set("box-shadow", "0 2px 8px rgba(0,0,0,0.1)")
                .set("max-width", "400px");

        add(card);
    }
}