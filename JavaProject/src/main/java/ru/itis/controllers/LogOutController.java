package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import ru.itis.services.SecurityService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogOutController implements Controller {

    @Autowired
    protected SecurityService securityService;

    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        if (httpServletRequest.getMethod().equals("GET")){
            securityService.logOut(httpServletRequest, httpServletResponse);
            httpServletResponse.sendRedirect(httpServletRequest.getServletContext().getContextPath() + "/main");
        }
        return null;
    }
}
