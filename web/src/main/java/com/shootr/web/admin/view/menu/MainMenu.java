package com.shootr.web.admin.view.menu;


import com.shootr.web.admin.view.entity.test.TestView;
import com.shootr.web.admin.view.job.BatchJobsView;
import com.shootr.web.admin.view.login.AfterLoginView;

public enum MainMenu {
    Entities("Entities", null),
    Entities_Test("Test", TestView.VIEW_NAME),

    Batch("Jobs", BatchJobsView.VIEW_NAME),

    User("UserMain", AfterLoginView.VIEW_NAME);

    private String text;
    private String viewName;

    MainMenu(String text, String viewName) {
        this.text = text;
        this.viewName = viewName;
    }

    public String text() {
        return text;
    }

    public String viewName() {
        return viewName;
    }

}
