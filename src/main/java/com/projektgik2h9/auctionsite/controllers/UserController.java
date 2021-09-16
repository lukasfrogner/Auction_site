package com.projektgik2h9.auctionsite.controllers;

import java.security.Principal;
import java.util.Map;

import com.projektgik2h9.auctionsite.models.Auction;
import com.projektgik2h9.auctionsite.models.User;
import com.projektgik2h9.auctionsite.service.AuctionService;
import com.projektgik2h9.auctionsite.service.CategoryService;
import com.projektgik2h9.auctionsite.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    
    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    AuctionService auctionService;

    private User setImportantUserData(Principal p, Model m){
        m.addAttribute("categories", categoryService.getAllCategories());
        if(p != null){
            User loggedIn = userService.getUserByUsername(p.getName());
            boolean admin = loggedIn.getRole().equalsIgnoreCase("ROLE_ADMIN") ? true : false; //kolla roll för inloggad användare
            boolean user = loggedIn.getRole().equalsIgnoreCase("ROLE_USER") ? true : false;  //kolla roll för inloggad användare
            m.addAttribute("user", user);
            m.addAttribute("admin", admin);
            m.addAttribute("loggedIn", loggedIn);
            return loggedIn;
        }
        return null;
    }

    @GetMapping("/error")
    public String errorPage(Model model, Principal principal){
        setImportantUserData(principal, model);
        model.addAttribute("errorText", "något gick fel");
        return "errorview";
    }


    @GetMapping("/profile/{id}")
    public String showProfile(Model model, Principal principal, @PathVariable Integer id){   
        User loggedIn = setImportantUserData(principal, model);
        model.addAttribute("user", userService.getUserById(id));
        boolean isAllowed = false;
        if(loggedIn != null){
            isAllowed = loggedIn.getId() == id ? true : false;
        }
        model.addAttribute("isAllowed", isAllowed);
        model.addAttribute("loggedId", loggedIn);
        return "profile";
    }

    @GetMapping("/profile/{id}/auctions")
    public String showProfileAuctions(Model model, Principal principal, @PathVariable Integer id){
        setImportantUserData(principal, model);
        model.addAttribute("auctions", auctionService.getAuctionByUserId(id));
        return "profileAuctions";
    }

    @GetMapping("/profile/{userId}/auctions/delete/{auctionId}")
    public String deleteAuction(Model model, Principal principal, @PathVariable Integer userId, @PathVariable Integer auctionId){
        User loggedIn = setImportantUserData(principal, model);
        Auction currentAuction = auctionService.getAuctionById(auctionId);
        if(loggedIn != null && loggedIn.getId() == userId && currentAuction.getBids().size() == 0){
            auctionService.deleteAuction(auctionId);
        }
        return "redirect:/profile/" + userId;
    }
    
    @GetMapping("/profile/{userId}/auctions/edit/{auctionId}")
    public String editAuction(Model model, Principal principal, @RequestParam Map<String, String> formData, @PathVariable Integer userId, @PathVariable Integer auctionId){
        User loggedIn = setImportantUserData(principal, model);
        model.addAttribute("loggedIn", loggedIn);
        model.addAttribute("auction", auctionService.getAuctionById(auctionId));
        return "editAuctionForm";
    } 

    @GetMapping("/profile/{userId}/auctions/edit/{auctionId}/done")
    public String editAuctionDone(Model model, Principal principal, @RequestParam Map<String, String> formData, @PathVariable Integer userId, @PathVariable Integer auctionId){
        User loggedIn = setImportantUserData(principal, model);
        if(loggedIn != null && loggedIn.getId() == userId){
            auctionService.updateAuctionById(auctionId, formData.get("description"), formData.get("name"));  
        }  
        return "redirect:/profile/" + userId;
    } 
    
}
