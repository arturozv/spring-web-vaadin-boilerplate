package com.shootr.web.admin.util;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;


public class NotificationBuilder {

    public static void aNotification(String title) {
        aNotification(title, null);
    }

    public static void aNotification(String title, String subTitle) {
        Notification notification = new Notification(title, subTitle, Notification.Type.HUMANIZED_MESSAGE);
        notification.setDelayMsec(2000);
        notification.show(Page.getCurrent());
    }

    public static void aErrorNotification(String title) {
        aErrorNotification(title, null);
    }

    public static void aErrorNotification(String title, String subTitle) {
        Notification notification = new Notification(title, subTitle, Notification.Type.ERROR_MESSAGE);
        notification.setDelayMsec(3000);
        notification.show(Page.getCurrent());
    }
}
