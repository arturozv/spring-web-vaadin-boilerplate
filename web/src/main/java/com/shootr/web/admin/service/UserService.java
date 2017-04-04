package com.shootr.web.admin.service;

import com.vaadin.navigator.Navigator;

public interface UserService {

    String COOKIE_NAME = "remember-me-123";
    String SESSION_USERNAME = "username";

    boolean authenticate(String username, String password, boolean rememberMe);

    boolean loginRememberedUser();

    void logout(Navigator navigator);
}
