package ru.itis.controllers;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import ru.itis.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileController implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        if (httpServletRequest.getMethod().equals("GET")){
            ModelAndView modelAndView = new ModelAndView();
            User user = (User) httpServletRequest.getSession().getAttribute("user");
            modelAndView.addObject("login", user.getLogin());
            modelAndView.addObject("email", user.getEmail());
            modelAndView.addObject("is_signed", "true");
            String role = (user.getRole().equals(""))? "Undefined" : user.getRole();
            String firstName = (user.getFirstName().equals(""))? "Undefined" : user.getFirstName();
            String nickname = (user.getMinecraftNickname().equals(""))? "Undefined" : user.getMinecraftNickname();
            Integer genderData = user.getGender();
            String gender;
            switch (genderData){
                case 1: gender = "Male"; break;
                case 2: gender = "Female"; break;
                default: gender = "Attack Helicopter";
            }
            String country = (user.getCountry().equals(""))? "Undefined" : user.getCountry();
            String vk = (user.getVk().equals(""))? "Undefined" : user.getVk();
            String facebook = (user.getFacebook().equals(""))? "Undefined" : user.getFacebook();
            modelAndView.addObject("role", role);
            if (role.equals("User")){
                modelAndView.addObject("text_color", "text-success");
            } else if (role.equals("Admin")){
                modelAndView.addObject("text_color", "text-danger");
            }
            modelAndView.addObject("first_name", firstName);
            modelAndView.addObject("nickname", nickname);
            modelAndView.addObject("gender", gender);
            modelAndView.addObject("country", country);
            modelAndView.addObject("vk", vk);
            modelAndView.addObject("facebook", facebook);

            modelAndView.addObject("image", "Default");

            String userBackground = (String) httpServletRequest.getSession().getAttribute("background");
            if (userBackground!=null){
                modelAndView.addObject("user_background", userBackground);
            }

            String message = (String) httpServletRequest.getSession().getAttribute("message");
            if (message!=null){
                httpServletRequest.getSession().setAttribute("message", null);
                modelAndView.addObject("message", message);
            }
            modelAndView.setViewName("profile");
            return modelAndView;
        }
        return null;
    }
}
