package com.shootr.web.admin.service;

import com.shootr.web.admin.view.login.LoginView;
import com.shootr.web.core.dao.domain.RememberUser;
import com.shootr.web.core.dao.repository.RememberUserRepository;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.servlet.http.Cookie;

@Component
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Inject
    private RememberUserRepository rememberUserRepository;

    @Override
    public boolean authenticate(String username, String password, boolean rememberMe) {
        boolean isAdmin = username.equals("admin") && password.equals("admin");
        boolean isUser = username.equals("user") && password.equals("user");

        if (rememberMe) {
            rememberUser(username);
        }

        return isAdmin || isUser;
    }

    public void rememberUser(String username) {
        String uuid = UUID.randomUUID().toString();

        RememberUser rememberUser = RememberUser.builder().userName(username).cookie(uuid).build();
        rememberUserRepository.insert(rememberUser);

        Cookie cookie = new Cookie(COOKIE_NAME, uuid);
        cookie.setPath("/");
        cookie.setMaxAge((int) TimeUnit.DAYS.toSeconds(15));
        VaadinService.getCurrentResponse().addCookie(cookie);
    }

    @Override
    public void logout(Navigator navigator) {
        Cookie cookie = new Cookie(COOKIE_NAME, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        VaadinService.getCurrentResponse().addCookie(cookie);

        VaadinSession.getCurrent().setAttribute(SESSION_USERNAME, null);

        navigator.navigateTo(LoginView.VIEW_NAME);
    }


    @Override
    public boolean loginRememberedUser() {
        Optional<Cookie> rememberMeCookie = getRememberMeCookie();

        logger.info("Remember me cookie found: {}", rememberMeCookie.isPresent());

        if (rememberMeCookie.isPresent()) {
            String id = rememberMeCookie.get().getValue();
            RememberUser rememberUser = rememberUserRepository.findByCookie(id);
            logger.info("RememberUser found: {}", rememberUser);

            if (rememberUser != null && rememberUser.getUserName() != null) {
                VaadinSession.getCurrent().setAttribute(SESSION_USERNAME, rememberUser.getUserName());
                return true;
            }
        }
        return false;
    }

    Optional<Cookie> getRememberMeCookie() {
        Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
        return Arrays.stream(cookies).filter(c -> c.getName().equals(COOKIE_NAME)).findFirst();
    }
}
