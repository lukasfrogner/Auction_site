package com.projektgik2h9.auctionsite.controllers;
import java.security.Principal;
import java.util.Map;

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
public class AdminController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @Autowired
    AuctionService auctionService;

    private User setImportantUserData(Principal p, Model m){
        m.addAttribute("categories", categoryService.getAllCategories());
        if(p != null){
            User loggedIn = userService.getUserByUsername(p.getName());
            boolean admin = loggedIn.getRole().equalsIgnoreCase("ROLE_ADMIN") ? true : false;   
            boolean user = loggedIn.getRole().equalsIgnoreCase("ROLE_USER") ? true : false;    
            m.addAttribute("user", user);
            m.addAttribute("admin", admin);
            m.addAttribute("loggedIn", loggedIn);
            return loggedIn;
        }     
        return null;
    }

    @GetMapping("/admin/tools/users")
    public String adminToolsUsers(Model model, Principal principal){
        setImportantUserData(principal, model);
        model.addAttribute("users", userService.getAllUsers());
        return "adminToolsUsers";
    }

    @GetMapping("/admin/tools/users/{id}")
    public String adminManageUser(Model model, Principal principal, @PathVariable Integer id){
        setImportantUserData(principal, model);
        model.addAttribute("user", userService.getUserById(id));
        User user = userService.getUserById(id);
        System.out.println(user.toString());
        return "adminToolsManageUser";
    }

    @GetMapping("/admin/tools/users/update/{id}")
    public String adminEditUser(Model model, Principal principal, @PathVariable Integer id, @RequestParam Map<String, String> formData){
        userService.updateUserById(id, formData.get("username"), formData.get("email"), formData.get("role"));
        if(formData.get("old_username").equalsIgnoreCase(principal.getName())){
            return "redirect:/logout";
        }
        return "redirect:/admin/tools/users";
    }

    @GetMapping("/admin/tools/auctions")
    public String adminToolsAuctions(Model model, Principal principal){
        setImportantUserData(principal, model);
        model.addAttribute("auctions", auctionService.getAllAuctions());
        return "adminToolsAuctions";
    }

    @GetMapping("/admin/tools/auctions/{id}")
    public String adminToolsAuctions(Model model, Principal principal, @PathVariable Integer id){
        setImportantUserData(principal, model);
        model.addAttribute("auction", auctionService.getAuctionById(id));
        return "adminToolsManageAuction";
    }
    @GetMapping("/admin/tools/auction/update/{id}")
    public String adminEditAuction(Model model, Principal principal, @PathVariable Integer id, @RequestParam Map<String, String> formData){
        setImportantUserData(principal, model);
        auctionService.updateAuctionById(id, formData.get("description"), formData.get("name"));
        return "redirect:/admin/tools/auctions";
    }

    @GetMapping("/admin/tools/auction/delete/{id}")
    public String deleteAuction(Model model, Principal principal, @PathVariable Integer id){
        setImportantUserData(principal, model);
        auctionService.deleteAuction(id);
        return "redirect:/admin/tools/auctions";
    }
}