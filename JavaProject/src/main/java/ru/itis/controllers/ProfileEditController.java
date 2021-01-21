package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import ru.itis.exceptions.UsersRepositoryException;
import ru.itis.models.User;
import ru.itis.services.SecurityService;
import ru.itis.services.UsersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileEditController implements Controller {

    @Autowired
    private UsersService usersService;
    @Autowired
    private SecurityService securityService;

    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        if (httpServletRequest.getMethod().equals("GET")){
            ModelAndView modelAndView = new ModelAndView();
            setProfileEditAttributes(modelAndView, httpServletRequest);
            String userBackground = (String) httpServletRequest.getSession().getAttribute("background");
            if (userBackground!=null){
                modelAndView.addObject("user_background", userBackground);
            }
            modelAndView.setViewName("profile-edit");
            return modelAndView;
        } else if (httpServletRequest.getMethod().equals("POST")){
            ModelAndView modelAndView = new ModelAndView();
            String form = httpServletRequest.getParameter("form");
            if (form.equals("user_data")){
                String login = httpServletRequest.getParameter("new_login");
                String firstName = httpServletRequest.getParameter("new_first_name");
                String nickname = httpServletRequest.getParameter("new_nickname");
                String email = httpServletRequest.getParameter("new_email");
                String gender = httpServletRequest.getParameter("new_gender");
                String country = httpServletRequest.getParameter("new_country");
                String vk = httpServletRequest.getParameter("new_vk");
                String facebook = httpServletRequest.getParameter("new_facebook");
                String userBackground = httpServletRequest.getParameter("user_background");
                httpServletRequest.getSession().setAttribute("background", userBackground);
                User user = User.builder()
                        .id(((User) httpServletRequest.getSession().getAttribute("user")).getId())
                        .login(login)
                        .firstName(firstName)
                        .minecraftNickname(nickname)
                        .email(email)
                        .country(country)
                        .vk(vk)
                        .facebook(facebook)
                        .build();
                int checkResult = securityService.checkUserChangeData(user, gender);
                switch (checkResult){
                    case 1: modelAndView.addObject("message", "User with such login already exists.");
                        break;
                    case 2: modelAndView.addObject("message", "User with such email already exists.");
                        break;
                    case 3: modelAndView.addObject("message", "Your login must be more than 2 characters long");
                        break;
                    case 4: modelAndView.addObject("message", "Your login must be less than 30 characters long");
                        break;
                    case 5: modelAndView.addObject("message", "Your first name must be more than 1 characters long");
                        break;
                    case 6: modelAndView.addObject("message", "Your first name must be less than 30 characters long");
                        break;
                    case 7: modelAndView.addObject("message", "Your minecraft nickname must be more than 3 characters long");
                        break;
                    case 8: modelAndView.addObject("message", "Your minecraft nickname must be less than 30 characters long");
                        break;
                    case 9: modelAndView.addObject("message", "You have to enter E-MAIL in the e-mail field.");
                        break;
                    case 10: modelAndView.addObject("message", "Your email must be less than 30 characters long");
                        break;
                    case 11: modelAndView.addObject("message", "Your country must be less than 30 characters long");
                        break;
                    case 12: modelAndView.addObject("message", "You have to enter VK-link in the VK field.");
                        break;
                    case 13: modelAndView.addObject("message", "Your vk-link must be less than 30 characters long");
                        break;
                    case 14: modelAndView.addObject("message", "You have to enter Facebook-link in the Facebook field.");
                        break;
                    case 15: modelAndView.addObject("message", "Your facebook-link must be less than 30 characters long");
                        break;
                    case 16: try{
                        usersService.updateUser(user, httpServletRequest);
                        httpServletRequest.getSession().setAttribute("message", "Your data has been successfully changed!");
                        httpServletResponse.sendRedirect(httpServletRequest.getServletContext().getContextPath()+"/profile");
                    } catch (UsersRepositoryException ex){
                        modelAndView.addObject("message", "Something went wrong.");
                    }
                        break;
                }
                modelAndView.addObject("login", user.getLogin());
                modelAndView.addObject("email", user.getEmail());
                modelAndView.addObject("first_name", firstName);
                modelAndView.addObject("nickname", nickname);
                modelAndView.addObject("gender", gender);
                modelAndView.addObject("country", country);
                modelAndView.addObject("vk", vk);
                modelAndView.addObject("facebook", facebook);
                modelAndView.addObject("is_signed", "true");
                modelAndView.addObject("image", "Default");
                modelAndView.addObject("user_background", userBackground);
                modelAndView.setViewName("profile-edit");
                return modelAndView;
            } else if (form.equals("change_password")){
                User user = (User) httpServletRequest.getSession().getAttribute("user");
                String password = httpServletRequest.getParameter("current_password");
                String newPassword = httpServletRequest.getParameter("new_password");
                String reNewPassword = httpServletRequest.getParameter("re_new_password");
                String userBackground = httpServletRequest.getParameter("user_background");
                httpServletRequest.getSession().setAttribute("background", userBackground);
                boolean isCorrect = securityService.matches(password, user.getPassword());
                if (isCorrect){
                    if (newPassword.equals(reNewPassword)){
                        user.setPassword(securityService.hashUserPassword(newPassword));
                        try{
                            usersService.updateUserPassword(user, httpServletRequest);
                            httpServletRequest.getSession().setAttribute("message", "Your password has been successfully changed!");
                            httpServletResponse.sendRedirect(httpServletRequest.getServletContext().getContextPath()+"/profile");
                        } catch (UsersRepositoryException ex){
                            modelAndView.addObject("password_message", "Something went wrong.");
                        }
                    } else {
                        modelAndView.addObject("password_message", "You must enter two identical new passwords");
                    }
                } else {
                    modelAndView.addObject("password_message", "An incorrect account password was entered");
                }
                setProfileEditAttributes(modelAndView, httpServletRequest);
                modelAndView.addObject("user_background", userBackground);
                modelAndView.setViewName("profile-edit");
                return modelAndView;
            } else if (form.equals("delete_account")){
                User user = (User) httpServletRequest.getSession().getAttribute("user");
                String password = httpServletRequest.getParameter("delete_password");
                String userAccess = httpServletRequest.getParameter("delete_access");
                String userBackground = httpServletRequest.getParameter("user_background");
                httpServletRequest.getSession().setAttribute("background", userBackground);
                boolean isCorrect = securityService.matches(password, user.getPassword());
                if (isCorrect){
                    if (!userAccess.equals("")){
                        try{
                            securityService.deleteCookieForUser(user, httpServletRequest, httpServletResponse);
                            usersService.deleteUser(user, httpServletRequest);
                            httpServletResponse.sendRedirect(httpServletRequest.getServletContext().getContextPath()+"/main");
                        } catch (UsersRepositoryException ex){
                            modelAndView.addObject("delete_message", "Something went wrong.");
                        }
                    } else {
                        modelAndView.addObject("delete_message", "You must confirm that you want to delete your account");
                    }
                } else {
                    modelAndView.addObject("delete_message", "An incorrect account password was entered");
                }
                setProfileEditAttributes(modelAndView, httpServletRequest);
                modelAndView.addObject("user_background", userBackground);
                modelAndView.setViewName("profile-edit");
                return modelAndView;
            } else {
                httpServletResponse.sendRedirect(httpServletRequest.getServletContext().getContextPath()+"/main");
            }
        }
        return null;
    }

    protected void setProfileEditAttributes(ModelAndView modelAndView, HttpServletRequest httpServletRequest){
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        modelAndView.addObject("login", user.getLogin());
        modelAndView.addObject("email", user.getEmail());
        modelAndView.addObject("is_signed", "true");
        String role = (user.getRole().equals(""))? "" : user.getRole();
        String firstName = (user.getFirstName().equals(""))? "" : user.getFirstName();
        String nickname = (user.getMinecraftNickname().equals(""))? "" : user.getMinecraftNickname();
        Integer genderData = user.getGender();
        String gender;
        switch (genderData){
            case 1: gender = "Male"; break;
            case 2: gender = "Female"; break;
            default: gender = "Attack Helicopter";
        }
        String country = (user.getCountry().equals(""))? "" : user.getCountry();
        String vk = (user.getVk().equals(""))? "" : user.getVk();
        String facebook = (user.getFacebook().equals(""))? "" : user.getFacebook();
        modelAndView.addObject("role", role);
        modelAndView.addObject("first_name", firstName);
        modelAndView.addObject("nickname", nickname);
        modelAndView.addObject("gender", gender);
        modelAndView.addObject("country", country);
        modelAndView.addObject("vk", vk);
        modelAndView.addObject("facebook", facebook);

        modelAndView.addObject("image", "Default");
    }
}
