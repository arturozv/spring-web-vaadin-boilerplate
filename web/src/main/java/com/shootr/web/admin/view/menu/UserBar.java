package com.shootr.web.admin.view.menu;

import com.shootr.web.admin.service.UserService;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@UIScope
@SpringComponent
public class UserBar extends HorizontalLayout {

    private MenuBar userBar;
    //private Embedded logo;

    @Inject
    private UserService userService;

    @PostConstruct
    public void init() {
        userBar = new MenuBar();
        userBar.addItem("Logout", getLogoutBarCommand());
        addComponent(userBar);

        //logo = new Embedded();
        //logo.setSource(new ClassResource("/images/logo.png"));
        //addComponent(logo);
        //setComponentAlignment(logo, Alignment.BOTTOM_CENTER);

        setSpacing(true);
        setMargin(new MarginInfo(false, true, false, false));
    }

    public void refreshVisibleByLoggedIn(boolean isLoggedIn) {
        for (MenuItem item : this.userBar.getItems()) {
            item.setVisible(isLoggedIn);
        }
    }

    private MenuBar.Command getLogoutBarCommand() {
        return selectedItem -> userService.logout(getUI().getNavigator());
    }
}
