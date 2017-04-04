package com.shootr.web.admin;

import com.shootr.web.admin.service.UserService;
import com.shootr.web.admin.view.login.AfterLoginView;
import com.shootr.web.admin.view.login.LoginView;
import com.shootr.web.admin.view.menu.MainMenuBar;
import com.shootr.web.admin.view.menu.UserBar;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ui.Transport;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import javax.inject.Inject;

@Theme("valo")
@SpringUI(path = "/admin")
@Push(transport = Transport.LONG_POLLING)
public class MainUI extends UI {

    @Inject
    private SpringViewProvider viewProvider;

    @Inject
    private MainMenuBar mainMenuBar;

    @Inject
    private UserBar userBar;

    @Inject
    private UserService userService;

    @Override
    protected void init(VaadinRequest request) {
        injectCustomStyles();

        final VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        root.setMargin(false);
        root.setSpacing(false);
        setContent(root);

        HorizontalLayout bar = new HorizontalLayout(mainMenuBar, userBar);
        bar.setWidth(100, Unit.PERCENTAGE);
        bar.setComponentAlignment(userBar, Alignment.MIDDLE_RIGHT);
        root.addComponent(bar);

        final Panel viewContainer = new Panel();
        viewContainer.setSizeFull();
        root.addComponent(viewContainer);
        root.setExpandRatio(viewContainer, 1.0f);

        Navigator navigator = new Navigator(this, viewContainer);
        navigator.addProvider(viewProvider);
        navigator.setErrorView(new ErrorView());

        //Check if user is logged
        getNavigator().addViewChangeListener(new ViewChangeListener() {
            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {
                boolean result = true;

                String userName = (String) VaadinSession.getCurrent().getAttribute(UserService.SESSION_USERNAME);
                boolean isLoggedIn = userName != null;

                boolean isLoginView = event.getNewView() instanceof LoginView;

                if (!isLoggedIn && !isLoginView) {
                    //redirect to login view
                    getNavigator().navigateTo(LoginView.VIEW_NAME);
                    result = false;

                } else if (isLoggedIn && isLoginView) {
                    // If someone tries to access to login view while logged in, then cancel
                    result = false;
                }

                if (isLoggedIn) {
                    userBar.refreshVisibleByLoggedIn(true);
                    if (userName.equals("user")) {
                        mainMenuBar.refreshVisibleByUser();
                    } else {
                        mainMenuBar.refreshVisibleByLoggedIn(true);
                    }

                } else {
                    mainMenuBar.refreshVisibleByLoggedIn(false);
                    userBar.refreshVisibleByLoggedIn(false);
                }

                return result;
            }

            @Override
            public void afterViewChange(ViewChangeEvent event) {

            }
        });


        //try to log in the user by remember me
        if (userService.loginRememberedUser()) {
            if (getUI().getNavigator().getCurrentView() instanceof LoginView) {
                getUI().getNavigator().navigateTo(AfterLoginView.VIEW_NAME);
            }
        }
    }

    private void injectCustomStyles() {
        Page.Styles styles = Page.getCurrent().getStyles();
        styles.add(".v-grid-body { cursor: pointer ; }");
        styles.add(".v-menubar {\n" +
                           "   border: none !important;\n" +
                           "   background-image: none !important;\n" +
                           "   box-shadow: none !important;\n" +
                           "}");
        styles.add(".v-menubar-menuitem { border-right-width: 0px !important; }");
    }


    private class ErrorView extends VerticalLayout implements View {

        private Label message;

        ErrorView() {
            setMargin(true);
            addComponent(message = new Label("Please click one of the buttons at the top of the screen."));
            message.addStyleName(ValoTheme.LABEL_COLORED);
        }

        @Override
        public void enter(ViewChangeListener.ViewChangeEvent event) {
        }
    }


}
