package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.exceptions.MinionsDataRepositoryException;
import ru.itis.exceptions.NoSuchEntityException;
import ru.itis.models.User;
import ru.itis.services.MinionsDataService;
import ru.itis.utils.ProfitCalculator;
import ru.itis.utils.StringRefactor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

@Controller
public class MainPageController {

    @Autowired
    protected MinionsDataService minionsDataService;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView getMainPage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        ModelAndView modelAndView = new ModelAndView();
        setMainAttributes(modelAndView, httpServletRequest);
        String userBackground = (String) httpServletRequest.getSession().getAttribute("background");
        if (userBackground!=null){
            modelAndView.addObject("user_background", userBackground);
        }
        modelAndView.setViewName("main-page");
        return modelAndView;
    }

    @RequestMapping(value = "/main",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String calculateData(HttpServletRequest httpServletRequest){
        String minionName = (httpServletRequest.getParameter("minion")==null) ? "" : httpServletRequest.getParameter("minion");
        int tier = Integer.parseInt(httpServletRequest.getParameter("tier"));
        String fuelName = (httpServletRequest.getParameter("fuel")==null) ? "" : httpServletRequest.getParameter("fuel");
        if (fuelName.equals("SELECT")){
            fuelName="";
        }
        String upgrade1Name = (httpServletRequest.getParameter("upgrade1")==null) ? "" : httpServletRequest.getParameter("upgrade1");
        if (upgrade1Name.equals("SELECT")){
            upgrade1Name="";
        }
        String upgrade2Name = (httpServletRequest.getParameter("upgrade2")==null) ? "" : httpServletRequest.getParameter("upgrade2");
        if (upgrade2Name.equals("SELECT")){
            upgrade2Name="";
        }

        StringBuilder result = new StringBuilder("{");
        try{
            int checkResult = ProfitCalculator.checkFormData(minionsDataService, minionName, upgrade1Name, upgrade2Name);
            switch (checkResult){
                case 1:
                    result.append("\"ex\":\"You have to choose a minion\"");
                    break;
                case 2:
                    result.append("\"ex\":\"Upgrade '").append(StringRefactor.remakeString(upgrade1Name))
                            .append("' cannot be set on '").append(StringRefactor.remakeString(minionName)).append("'.\"");
                    break;
                case 3:
                    result.append("\"ex\":\"Upgrade '").append(StringRefactor.remakeString(upgrade2Name))
                            .append("' cannot be set on '").append(StringRefactor.remakeString(minionName)).append("'.\"");
                    break;
                case 4:
                    result.append("\"wm\":\"Its useless to set two '").append(StringRefactor.remakeString(upgrade1Name))
                            .append("', one of them will be used in the calculations.\",");
                case 5: double profit = ProfitCalculator.calculateProfit(minionsDataService, minionName, tier, fuelName,
                        upgrade1Name, upgrade2Name);
                    result.append("\"result\":").append((int) profit);
                    break;
            }
        } catch (MinionsDataRepositoryException ex){
            result = new StringBuilder("{\"ex\":\"Something went wrong.\"");
        } catch (NoSuchEntityException ex){
            result = new StringBuilder("{\"ex\":\"Incorrect input data.\"");
        }
        result.append("}");
        return result.toString();
    }

    protected void setMainAttributes(ModelAndView modelAndView, HttpServletRequest httpServletRequest){
        modelAndView.addObject("minionsList", minionsDataService.getAllMinions().stream().map(minion -> StringRefactor.remakeString(minion.getName())).toArray());
        modelAndView.addObject("minionsLogosList", minionsDataService.getAllMinions().stream().map(minion -> minion.getName().trim()).toArray());
        modelAndView.addObject("fuelsList", minionsDataService.getAllFuels().stream().map(fuel->StringRefactor.remakeString(fuel.getName())).collect(Collectors.toList()));
        modelAndView.addObject("fuelsLogosList", minionsDataService.getAllFuels().stream().map(fuel->fuel.getName().trim()).collect(Collectors.toList()));
        modelAndView.addObject("upgradesList", minionsDataService.getAllUpgrades().stream().map(upgrade -> StringRefactor.remakeString(upgrade.getName())).collect(Collectors.toList()));
        modelAndView.addObject("upgradesLogosList", minionsDataService.getAllUpgrades().stream().map(upgrade->upgrade.getName().trim()).collect(Collectors.toList()));
        if (httpServletRequest.getServletContext().getAttribute("dataDownloadError")!=null) {
            modelAndView.addObject("dataDownloadError", true);
        }
        if (httpServletRequest.getSession().getAttribute("user")!=null){
            User user = (User) httpServletRequest.getSession().getAttribute("user");
            modelAndView.addObject("login", user.getLogin());
            modelAndView.addObject("is_signed", "true");
        } else {
            modelAndView.addObject("is_signed", "false");
        }
    }
}
