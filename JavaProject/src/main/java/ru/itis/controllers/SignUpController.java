package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import ru.itis.exceptions.CookiesRepositoryException;
import ru.itis.exceptions.DuplicateUsersException;
import ru.itis.exceptions.UsersRepositoryException;
import ru.itis.models.User;
import ru.itis.services.SecurityService;
import ru.itis.services.UsersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpController implements Controller {

    @Autowired
    private UsersService usersService;
    @Autowired
    private SecurityService securityService;

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
            modelAndView.setViewName("sign-up");
            return modelAndView;
        } else if (httpServletRequest.getMethod().equals("POST")){
            ModelAndView modelAndView = new ModelAndView();
            String login = httpServletRequest.getParameter("login");
            String email = httpServletRequest.getParameter("email");
            String password = httpServletRequest.getParameter("pass");
            String repassword = httpServletRequest.getParameter("re_pass");
            String userAccess = httpServletRequest.getParameter("agree-term");
            String userBackground = httpServletRequest.getParameter("user_background");
            httpServletRequest.getSession().setAttribute("background", userBackground);
            if (httpServletRequest.getSession().getAttribute("user")!=null){
                User user = (User) httpServletRequest.getSession().getAttribute("user");
                modelAndView.addObject("login", user.getLogin());
                modelAndView.addObject("is_signed", "true");
            } else {
                modelAndView.addObject("is_signed", "false");
            }
            User user = User.builder()
                    .login(login)
                    .email(email)
                    .password(password)
                    .vk("")
                    .role("User")
                    .minecraftNickname("")
                    .firstName("")
                    .country("")
                    .facebook("")
                    .gender(0)
                    .build();
            int checkResult = securityService.verifyUserData(user, repassword, userAccess);
            switch (checkResult){
                case 1: modelAndView.addObject("message", "You have to fill all form fields.");
                    break;
                case 2: modelAndView.addObject("message", "You have to enter two equals passwords.");
                    break;
                case 3: modelAndView.addObject("message", "You have to access with our rules.");
                    break;
                case 4: modelAndView.addObject("message", "You have to enter E-MAIL in the e-mail field.");
                    break;
                case 5: modelAndView.addObject("message", "Your login must be more than 2 characters long");
                    break;
                case 6: modelAndView.addObject("message", "Your login must be less than 30 characters long");
                    break;
                case 7: modelAndView.addObject("message", "Your email must be less than 30 characters long");
                    break;
                case 8: modelAndView.addObject("message", "Your password must be more than 7 characters long");
                    break;
                case 9: modelAndView.addObject("message", "Your password must be less than 40 characters long");
                    break;
                case 10: try{
                    user.setPassword(securityService.hashUserPassword(user.getPassword()));
                    Long newUserId = usersService.saveUser(user);
                    securityService.addRecord(newUserId);
                    httpServletRequest.getSession().setAttribute("/success", true);
                    httpServletResponse.sendRedirect(httpServletRequest.getServletContext().getContextPath()+"/success");
                } catch (DuplicateUsersException ex){
                    modelAndView.addObject("message", "User with such " +  ex.getMessage() + " already exists.");
                } catch (UsersRepositoryException | CookiesRepositoryException ex){
                    modelAndView.addObject("message", "Something went wrong.");
                }
                    break;
            }
            modelAndView.addObject("user_login", login);
            modelAndView.addObject("user_email", email);
            modelAndView.addObject("user_background", userBackground);
            modelAndView.setViewName("sign-up");
            return modelAndView;
        }
        return null;
    }
}
