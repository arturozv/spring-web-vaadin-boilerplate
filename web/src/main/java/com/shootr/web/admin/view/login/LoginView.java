package com.shootr.web.admin.view.login;

import com.shootr.web.admin.service.UserService;
import com.shootr.web.admin.util.NotificationBuilder;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

@SpringView(name = LoginView.VIEW_NAME)
public class LoginView extends CustomComponent implements View {
    public static final String VIEW_NAME = "LoginView";
    private static final Logger logger = LoggerFactory.getLogger(LoginView.class);
    private final TextField userTextField;
    private final PasswordField password;
    private final Button loginButton;

    @Inject
    private UserService userService;

    public LoginView() {
        setSizeFull();

        // Create the user input field
        userTextField = new TextField("User");
        userTextField.setWidth("300px");

        // Create the password input field
        password = new PasswordField("Password");
        password.setWidth("300px");
        password.setValue("");

        CheckBox rememberMe = new CheckBox("Remember me");

        // Create login button
        loginButton = new Button("Login", e -> buttonClick(userTextField.getValue(), password.getValue(), rememberMe.getValue()));
        loginButton.setClickShortcut(KeyCode.ENTER);

        // Add both to a panel
        VerticalLayout fields = new VerticalLayout(userTextField, password, loginButton, rememberMe);
        fields.setSpacing(true);
        fields.setMargin(new MarginInfo(true, true, true, false));
        fields.setSizeUndefined();

        // The view root layout
        VerticalLayout viewLayout = new VerticalLayout(fields);
        viewLayout.setSizeFull();
        viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
        setCompositionRoot(viewLayout);
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // focus the username field when user arrives to the login view
        userTextField.focus();
    }

    public void buttonClick(String userName, String password, boolean rememberMe) {
        boolean isLogged = userService.authenticate(userName, password, rememberMe);

        logger.info("attempting to log user: {}, result: {}", userName, isLogged);

        if (isLogged) {
            VaadinSession.getCurrent().setAttribute(UserService.SESSION_USERNAME, userName);
            getUI().getNavigator().navigateTo(AfterLoginView.VIEW_NAME);
        } else {
            this.password.setValue(null);
            this.password.focus();
            NotificationBuilder.aErrorNotification("Incorrect user/password");
        }
    }
}