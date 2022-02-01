package com.example.targauto.utils;

import com.example.targauto.models.User;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class SessionUtils
{

	public static HttpSession getSession()
	{
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public static HttpServletRequest getRequest()
	{
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public static Optional<User> getUser()
	{
		User user;
		HttpSession session = getSession();
		user = (User) session.getAttribute("user");
		if (user != null)
			return Optional.of(user);
		else
			return Optional.empty();
	}

	public static void storeUserInSession(User user)
	{
		HttpSession session = SessionUtils.getSession();
		session.setAttribute("user", user);
	}
	/*
	 * Stores a message in the current user's session to be displayed.
	 * If one argument has been specified, it is assumed to store the feedback in the user's session. Else, store it for the name denoted by the first parameter.
	 */
	@SafeVarargs
	public static boolean queueUserFeedback(String... theFeedback)
	{
		if (theFeedback.length==1)
		{
			HttpSession theSession = getSession();
			if (theSession != null)
			{
				theSession.setAttribute("userFeedback", theFeedback[0]);
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			throw new UnsupportedOperationException("Nu-i gata inca");
		}
		//return false;
	}
	/*
	 * Returns the feedback for a user, if applicable. Otherwise, returns null.
	 */
	public static String getUserFeedback()
	{
		HttpSession theSession = getSession();
		if (theSession != null)
		{
			try
			{
				return (String) theSession.getAttribute("userFeedback");
			}
			catch(ClassCastException cce)
			{
				System.err.println("Feedback parsing failed: "+cce.getMessage());
				return "ERR";
			}
		}
		else
		{
			return null;
		}
	}
}
