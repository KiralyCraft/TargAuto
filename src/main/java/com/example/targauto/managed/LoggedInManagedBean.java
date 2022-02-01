package com.example.targauto.managed;

import com.example.targauto.interfaces.services.AuthenticationService;
import com.example.targauto.models.User;
import com.example.targauto.utils.SessionUtils;
import jakarta.annotation.ManagedBean;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;

import java.io.Serializable;
import java.util.Optional;

@Named("loggedInManagedBean")
@ManagedBean
@SessionScoped
public class LoggedInManagedBean implements Serializable {
    @EJB
    AuthenticationService authenticationService;
    private String username;
    private String password;
    private String message;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Deprecated
    public String getMessage() {
        return message;
    }
    @Deprecated
    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        if (username == null) return "";
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // validate login
    public String validateUsernamePassword() {
        Optional<User> userFromDatabase = authenticationService.getUserByUsername(username);
        var user = authenticationService.verifyUser(userFromDatabase, username, password);
        if (user.isPresent()) {
            SessionUtils.storeUserInSession(user.get());
            return "Home";
        } else {
        	SessionUtils.queueUserFeedback("Login failed, please try again.");
            return "Login";
        }
    }

    public boolean userLoggedIn() {
        return SessionUtils.getUser().isPresent();
    }

    public Optional<User> getUserOptional() {
        return SessionUtils.getUser();
    }

    // logout event, invalidate session
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "Login";
    }
    
    /*
     * Called by JSF - May return NULL, so parse it properly
     */
    public String getUserFeedback()
    {
    	return SessionUtils.getUserFeedback(); //Session-specific
    }
    
    /*
     * Called by JSF
     */
    public boolean queueUserFeedback(String theFeedback)
    {
    	return SessionUtils.queueUserFeedback(theFeedback);
    }
}
