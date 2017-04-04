package com.shootr.web.admin.view.menu;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.MenuBar;

import javax.annotation.PostConstruct;

@SpringComponent
@UIScope
public class MainMenuBar extends MenuBar {

    @PostConstruct
    public void init() {

        //menu for admin user
        MenuItem entitiesMenuItem = addParentItem(this, MainMenu.Entities);
        addChildrenItem(entitiesMenuItem, MainMenu.Entities_Test);

        addParentItem(this, MainMenu.Batch);

        addParentItem(this, MainMenu.User);

        //default menu for normal user
        addParentItem(this, MainMenu.Entities_Test);
    }

    private MenuItem addParentItem(MainMenuBar mainMenuBar, MainMenu mainMenu) {
        return mainMenuBar.addItem(mainMenu.text(), getMenuBarCommand(mainMenu.viewName()));
    }

    private void addChildrenItem(MenuItem parent, MainMenu mainMenu) {
        parent.addItem(mainMenu.text(), getMenuBarCommand(mainMenu.viewName()));
    }

    private MenuBar.Command getMenuBarCommand(final String viewName) {
        if (viewName == null) {
            return null;
        }
        return selectedItem -> getUI().getNavigator().navigateTo(viewName);
    }

    public void refreshVisibleByLoggedIn(boolean isLoggedIn) {
        for (MenuItem item : this.getItems()) {
            if (item.getText().equals(MainMenu.Entities_Test.text())) {
                item.setVisible(false);
            } else {
                item.setVisible(isLoggedIn);
            }
        }
    }

    public void refreshVisibleByUser() {
        for (MenuItem item : this.getItems()) {
            if (item.getText().equals(MainMenu.Entities_Test.text())) {
                item.setVisible(true);
            } else {
                item.setVisible(false);
            }
        }
    }
}
