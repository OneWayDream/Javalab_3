package ru.itis.controllers;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import ru.itis.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SuccessRegistrationController implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        if (httpServletRequest.getMethod().equals("GET")){
            ModelAndView modelAndView = new ModelAndView();
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
            modelAndView.setViewName("success");
            return modelAndView;
        }
        return null;
    }
}
