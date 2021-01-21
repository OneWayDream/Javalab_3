package ru.itis.controllers;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import ru.itis.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionController implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        if (httpServletRequest.getMethod().equals("GET")){
            ModelAndView modelAndView = new ModelAndView();
            String cookieErrorMessage = (String) httpServletRequest.getSession().getAttribute("cookieErrorMessage");
            httpServletRequest.getSession().setAttribute("cookieErrorMessage", null);
            String unexpectedExceptionMessage = (String) httpServletRequest.getSession().getAttribute("unexpectedExceptionMessage");
            httpServletRequest.getSession().setAttribute("unexpectedExceptionMessage", null);
            if (cookieErrorMessage!=null){
                modelAndView.addObject("message", cookieErrorMessage);
            }
            if (unexpectedExceptionMessage!=null){
                modelAndView.addObject("message", unexpectedExceptionMessage);
            }
            if (httpServletRequest.getSession().getAttribute("user")!=null){
                User user = (User) httpServletRequest.getSession().getAttribute("user");
                modelAndView.addObject("login", user.getLogin());
                modelAndView.addObject("is_signed", "true");
            } else {
                modelAndView.addObject("is_signed", "false");
            }
            String userBackground = (String) httpServletRequest.getSession().getAttribute("background");
            if (userBackground!=null){
                modelAndView.addObject("user_background", userBackground);
            }
            modelAndView.setViewName("exception");
            return modelAndView;
        }
        return null;
    }
}
