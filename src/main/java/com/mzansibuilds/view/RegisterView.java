package com.mzansibuilds.view;

import com.mzansibuilds.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("register")
@AnonymousAllowed
public class RegisterView extends VerticalLayout {

    public RegisterView(UserService userService) {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        getStyle().set("background-color", "#ffffff");

        H2 title = new H2("Create Your Account");
        title.getStyle().set("color", "#2e7d32");

        TextField usernameField = new TextField("Username");
        usernameField.setWidth("300px");

        TextField emailField = new TextField("Email");
        emailField.setWidth("300px");

        PasswordField passwordField = new PasswordField("Password");
        passwordField.setWidth("300px");

        PasswordField confirmField = new PasswordField("Confirm Password");
        confirmField.setWidth("300px");

        Button registerBtn = new Button("Create Account");
        registerBtn.setWidth("300px");
        registerBtn.getStyle()
                .set("background-color", "#2e7d32")
                .set("color", "white")
                .set("border-radius", "4px");

        Anchor loginLink = new Anchor("/login", "Already have an account? Login here");
        loginLink.getStyle().set("color", "#2e7d32");

        registerBtn.addClickListener(e -> {
            String username = usernameField.getValue().trim();
            String email = emailField.getValue().trim();
            String password = passwordField.getValue();
            String confirm = confirmField.getValue();

            // Basic validation before touching the database
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Notification.show("All fields are required");
                return;
            }
            if (!password.equals(confirm)) {
                Notification.show("Passwords do not match");
                return;
            }
            if (password.length() < 6) {
                Notification.show("Password must be at least 6 characters");
                return;
            }

            try {
                userService.register(username, email, password);
                Notification.show("Account created! Please log in.");
                getUI().ifPresent(ui -> ui.navigate("login"));
            } catch (IllegalArgumentException ex) {
                Notification.show(ex.getMessage());
            }
        });

        VerticalLayout card = new VerticalLayout(
                title, usernameField, emailField,
                passwordField, confirmField,
                registerBtn, loginLink
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