package com.projektgik2h9.auctionsite.controllers;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;
import com.projektgik2h9.auctionsite.test.Round;
import com.projektgik2h9.auctionsite.models.Auction;
import com.projektgik2h9.auctionsite.models.Bid;
import com.projektgik2h9.auctionsite.models.User;
import com.projektgik2h9.auctionsite.service.AuctionService;
import com.projektgik2h9.auctionsite.service.BidService;
import com.projektgik2h9.auctionsite.service.CategoryService;
import com.projektgik2h9.auctionsite.service.EmailService;
import com.projektgik2h9.auctionsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BidController {

    @Autowired
    BidService bidService;

    @Autowired
    UserService userService;

    @Autowired
    AuctionService auctionService;

    @Autowired
    EmailService emailService;

    @Autowired
    CategoryService categoryService;


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
    
    @GetMapping("/bid/{id}")
    public String bidOnAuction(Model model, Principal principal, @PathVariable Integer id, @RequestParam Map<String, String> requestParams){
        User currentUser = setImportantUserData(principal, model);
        Bid bid = new Bid();
        Auction auction = auctionService.getAuctionById(id);
        Double highestBid = auction.getHighestBid().getAmount() == null ? auction.getCost() : auction.getHighestBid().getAmount();//kolla om det finns ett högsta bud, om inte sätt auktionens kostnad som "högsta bud"
        bid.setUser(currentUser);
        bid.setAmount(Round.round((highestBid * 1.1d), 2));//öka bud med 10%, till 2 decimaler
        bid.setAuction(auction);
        bid.setTimestamp(new Timestamp(Timestamp.from(Instant.now()).getTime()));
        bidService.saveBid(bid);
        emailService.sendEmail(currentUser.getEmail(), "Bud på "  + bid.getAmount() + "kr lagt på " + auction.getName(), "Bud lagt!");
        
        return "redirect:/auction/" + id;
    }
}