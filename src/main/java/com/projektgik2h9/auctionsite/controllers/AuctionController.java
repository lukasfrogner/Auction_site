package com.projektgik2h9.auctionsite.controllers;
import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import com.projektgik2h9.auctionsite.models.Auction;
import com.projektgik2h9.auctionsite.models.User;
import com.projektgik2h9.auctionsite.repository.AuctionRepository;
import com.projektgik2h9.auctionsite.service.AuctionService;
import com.projektgik2h9.auctionsite.service.BidService;
import com.projektgik2h9.auctionsite.service.CategoryService;
import com.projektgik2h9.auctionsite.service.UserService;
import com.projektgik2h9.auctionsite.test.Round;
import com.projektgik2h9.auctionsite.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuctionController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @Autowired
    AuctionService auctionService;

    @Autowired
    BidService bidService;

    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    EmailService emailService;


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


    @GetMapping("/auction/{id}")
    public String viewAuction(Model model, Principal principal, @PathVariable Integer id){
        User currentUser = setImportantUserData(principal, model);
        Auction currentAuction = auctionService.getAuctionById(id);
        Boolean isAllowed = true;
        Boolean hasBids = false;
        model.addAttribute("auction", currentAuction);
        if(currentAuction.getBids().size() > 0){
            model.addAttribute("topThreeBids", bidService.getThreeHighestBids(id));
            model.addAttribute("highestBid", currentAuction.getHighestBid());
            hasBids = true;
            if(currentUser != null){//inloggad användare får inte buda över sitt egna bud
                if(currentUser.getId() == currentAuction.getHighestBid().getUser().getId()){ //kollar om inloggade användaren också är den användare som äger det högsta budet
                    isAllowed = false;
                }
            }     
        }  
        if(currentUser != null){
            if(currentUser.getId() == currentAuction.getUser().getId()){//inloggad användare får inte buda på sin egen auktion
                isAllowed = false;
            }
        }
        Double increaseBid = currentAuction.getHighestBid().getAmount() == null ? currentAuction.getCost()*1.1d : currentAuction.getHighestBid().getAmount()*1.1d;
        model.addAttribute("isActive", currentAuction.isActive() == true ? true : false); 
        model.addAttribute("hasBids", hasBids);
        model.addAttribute("isAllowed", isAllowed);
        model.addAttribute("increaseBid", Round.round(increaseBid, 2));
        model.addAttribute("bids", bidService.getAllBidsDescAmount(id));
        model.addAttribute("currentCategory", currentAuction.getCategory());
        return "auctionitem";
    }

    @GetMapping("/auction/post")
    public String postAuction(Model model, Principal principal){
        setImportantUserData(principal, model);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "addAuctionForm";
    }

    @GetMapping("/auction/post/done")
    public String postAuctionDone(Model model, Principal principal, @RequestParam Map<String, String> formData){
        Auction auction = new Auction();
        auction.setCost(Double.parseDouble(formData.get("cost")));
        auction.setName(formData.get("name"));
        auction.setDescription(formData.get("description"));
        auction.setUser(userService.getUserByUsername(principal.getName()));
        auction.setCategory(categoryService.getCategoryById(Integer.parseInt(formData.get("category"))));
        auction.setImgUrl(formData.get("imgUrl"));
        auction.setActive(true);
        long week = 604800000L;
        auction.setStartDate(new Timestamp(Timestamp.from(Instant.now()).getTime()));
        auction.setEndDate(new Timestamp(Timestamp.from(Instant.now()).getTime() + week));
        auctionService.saveAuction(auction);
        User user = userService.getUserByUsername(principal.getName());
        emailService.sendEmail(user.getEmail(), "Du har lagt upp en auktion med titeln: " + auction.getName(), "Auktion upplagd!");
        return "redirect:/auction/" + auctionService.getLatestAuction().getId();
    }

    @GetMapping("/auction/category/{id}/{pageno}")
    public String viewAuctionsByCategory(Model model, Principal principal, @PathVariable Integer id, @PathVariable Integer pageno){
        setImportantUserData(principal, model);
        final Integer PAGESIZE=3;
        Pageable pageable = PageRequest.of(pageno, PAGESIZE, Sort.by("cost").descending());
        Page<Auction> pagedResult = auctionRepository.findAllByCategory_id(id, pageable);      
        List<Auction> listAuctions;
        listAuctions = pagedResult.getContent();
        model.addAttribute("auctions", listAuctions);
        model.addAttribute("currentPageNumber", pagedResult.getNumber()+1);
        model.addAttribute("displayableCurrentPageNumber", pagedResult.getNumber()+1);
        model.addAttribute("nextPageNumber", pageno+1);
        model.addAttribute("previousPageNumber", pageno-1);
        model.addAttribute("totalPages", pagedResult.getTotalPages());
        model.addAttribute("totalItems", pagedResult.getTotalElements());
        model.addAttribute("onePage", pagedResult.getTotalPages() == 1 ? true : false);
        model.addAttribute("hasNext", pagedResult.hasNext());
        model.addAttribute("hasPrevious", pagedResult.hasPrevious());
        model.addAttribute("currentCategoryId", id);
        model.addAttribute("currentCategory", categoryService.getCategoryById(id).getType());
        return "category";
    }

}