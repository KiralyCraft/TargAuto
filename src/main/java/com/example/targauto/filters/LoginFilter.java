package com.example.targauto.filters;

import com.example.targauto.utils.AuthHelper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "LoginFilter", urlPatterns = "/*")
public class LoginFilter implements Filter {
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    Filter.super.init(filterConfig);
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    var httpRequest = (HttpServletRequest) request;
    var httResponse = (HttpServletResponse) response;
    var httpSession = httpRequest.getSession();
    var user = AuthHelper.loggedInOrNull(httpSession);
    var requestPath = httpRequest.getServletPath();

    if (user.isPresent()
        || requestPath.equals("/Home.jsf")
        || requestPath.matches("/Login.jsf")
        || requestPath.matches("/Cars.jsf")
        || requestPath.startsWith("/SelectedCar.jsf")
        || requestPath.matches("/Signin.jsf")
        || requestPath.matches("/SavedCars.jsf")
    	|| requestPath.matches("/Tests.jsf"))
    {
      chain.doFilter(httpRequest, httResponse);
      return;
    }
    httResponse.sendRedirect(httpRequest.getContextPath() + "/Home.jsf");
  }
}
