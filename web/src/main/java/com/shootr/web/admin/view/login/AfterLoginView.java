package com.shootr.web.admin.view.login;

import com.shootr.web.admin.service.UserService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.inject.Inject;

@SpringView(name = AfterLoginView.VIEW_NAME)
public class AfterLoginView extends CustomComponent implements View {
    public static final String VIEW_NAME = "AfterLoginMainView";

    Label text = new Label();

    @Inject
    private UserService userService;

    Button logout = new Button("Logout", event -> {
        userService.logout(getUI().getNavigator());
    });

    public AfterLoginView() {
        setSizeFull();
        setCompositionRoot(new CssLayout(text, logout));

        VerticalLayout fields = new VerticalLayout(text, logout);
        fields.setSpacing(true);
        fields.setMargin(new MarginInfo(true, true, true, false));
        fields.setSizeUndefined();

        VerticalLayout viewLayout = new VerticalLayout(fields);
        viewLayout.setSizeFull();
        viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
        setCompositionRoot(viewLayout);
    }

    @Override
    public void enter(ViewChangeEvent event) {
        String userName = (String) VaadinSession.getCurrent().getAttribute(UserService.SESSION_USERNAME);
        text.setValue("Logged as " + userName);
    }

}