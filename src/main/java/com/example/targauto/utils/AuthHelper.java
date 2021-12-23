package com.example.targauto.utils;

import com.example.targauto.models.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class AuthHelper {
    public static Optional<User> loggedInOrNull(HttpSession httpSession) {
        var user = httpSession.getAttribute("user");
        if (user == null) return Optional.empty();
        return Optional.of((User) user);
    }

    public static User getUser(HttpServletRequest httpServletRequest) {
        var httpSession = httpServletRequest.getSession();
        var user = httpSession.getAttribute("user");
        return (User) user;
    }

    public static void setUser(HttpServletRequest httpServletRequest, User user) {
        var httpSession = httpServletRequest.getSession();
        httpSession.setAttribute("user", user);
    }


}
