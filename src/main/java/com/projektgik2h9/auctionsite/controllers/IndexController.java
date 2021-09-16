package com.projektgik2h9.auctionsite.controllers;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.projektgik2h9.auctionsite.models.Auction;
import com.projektgik2h9.auctionsite.models.Bid;
import com.projektgik2h9.auctionsite.models.User;
import com.projektgik2h9.auctionsite.models.Category;
import com.projektgik2h9.auctionsite.service.AuctionService;
import com.projektgik2h9.auctionsite.service.BidService;
import com.projektgik2h9.auctionsite.service.UserService;
import com.projektgik2h9.auctionsite.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    
    @Autowired
    UserService userService;

    @Autowired
    AuctionService auctionService;

    @Autowired
    BidService bidService;

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


    @GetMapping("")
    public String index(Model model, Principal principal){
        User loggedIn = setImportantUserData(principal, model);
        model.addAttribute("auctions", auctionService.getAllAuctions());
        model.addAttribute("loggedIn", loggedIn);
        return "index";
    }

    @GetMapping("/initdb")//initierar databasen med data
    public String adminView(Model model){
        User user1 = new User(null, "jonken", "jonken@email.com", "$2y$12$2jS2E/2CB9NCHLKHMgx26uMo3mGEW6lrp1s03OR5vuBm4knyk0l3O", "ROLE_ADMIN", 1);
        User user2 = new User(null, "basklord", "basklord@email.com", "$2y$12$2jS2E/2CB9NCHLKHMgx26uMo3mGEW6lrp1s03OR5vuBm4knyk0l3O", "ROLE_ADMIN", 1);
        User user3 = new User(null, "maria", "h19mkarl@gmail.com", "$2y$12$2jS2E/2CB9NCHLKHMgx26uMo3mGEW6lrp1s03OR5vuBm4knyk0l3O", "ROLE_ADMIN", 1);
        User user4 = new User(null, "frogge", "frogge@email.com", "$2y$12$2jS2E/2CB9NCHLKHMgx26uMo3mGEW6lrp1s03OR5vuBm4knyk0l3O", "ROLE_ADMIN", 1);
        User user5 = new User(null, "plebben", "plebben@email.com", "$2y$12$2jS2E/2CB9NCHLKHMgx26uMo3mGEW6lrp1s03OR5vuBm4knyk0l3O", "ROLE_USER", 1);
        User user6 = new User(null, "grodan", "h19mkarl@gmail.com", "$2y$12$2jS2E/2CB9NCHLKHMgx26uMo3mGEW6lrp1s03OR5vuBm4knyk0l3O", "ROLE_USER", 1);
        User user7 = new User(null, "shrek", "karlsson.jonas.skola@gmail.com", "$2y$12$2jS2E/2CB9NCHLKHMgx26uMo3mGEW6lrp1s03OR5vuBm4knyk0l3O", "ROLE_USER", 1);
        User user8 = new User(null, "långben", "h19lukfr@du.se", "$2y$12$2jS2E/2CB9NCHLKHMgx26uMo3mGEW6lrp1s03OR5vuBm4knyk0l3O", "ROLE_USER", 1);
        List<User> allUsers = new ArrayList<>();

        Category category1 = new Category();
        category1.setType("Instrument");
        Category category2 = new Category();
        category2.setType("Kläder");
        Category category3 = new Category();
        category3.setType("Inredning");
        Category category4 = new Category();
        category4.setType("Skor");
        Category category5 = new Category();
        category5.setType("Teknologi");
        List<Category> allCategories = new ArrayList<>();
        
        long week = 604800000L;
        long day = 1000 * 60 * 60 * 24;
        long sixHours = 1000 * 60 * 60 * 6;
        long oneHour = 1000 * 60 * 60;
        long tenMinutes = 1000 * 60 * 10;
        long oneMinute = 1000 * 60;

        Auction auction1 = new Auction();
        auction1.setCost(200d);
        auction1.setDescription("En frän grej.");
        auction1.setName("Kazoo");    
        auction1.setStartDate(new Timestamp(Timestamp.from(Instant.now()).getTime() + day));
        auction1.setEndDate(new Timestamp(Timestamp.from(Instant.now()).getTime() + week + day));
        auction1.setUser(user5);
        auction1.setCategory(category1);
        auction1.setActive(true);
        auction1.setImgUrl("https://lilmoco.com/images/products/9805.jpg");


        Auction auction2 = new Auction();
        auction2.setCost(300d);
        auction2.setDescription("Flannelskjorta.");
        auction2.setName("Röd och svart flannelskjorta i storlek L");
        auction2.setStartDate(new Timestamp(Timestamp.from(Instant.now()).getTime() + oneHour));
        auction2.setEndDate(new Timestamp(Timestamp.from(Instant.now()).getTime() + week + oneHour));
        auction2.setUser(user5);
        auction2.setCategory(category2);
        auction2.setActive(true);
        auction2.setImgUrl("https://images.unsplash.com/photo-1589992896387-140e940257d0?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=676&q=80");

        Auction auction3 = new Auction();
        auction3.setCost(8000d);
        auction3.setDescription("Autentisk bongotrumma.");
        auction3.setName("Bongotrumma");
        auction3.setStartDate(new Timestamp(Timestamp.from(Instant.now()).getTime() + oneMinute + oneMinute + tenMinutes));
        auction3.setEndDate(new Timestamp(Timestamp.from(Instant.now()).getTime() + week + oneMinute + oneMinute + tenMinutes));
        auction3.setUser(user6);
        auction3.setCategory(category1);
        auction3.setActive(true);
        auction3.setImgUrl("https://images.unsplash.com/photo-1595069906974-f8ae7ffc3e7a?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1267&q=80");

        Auction auction4 = new Auction();
        auction4.setCost(3500d);
        auction4.setDescription("Arne Jacobsen");
        auction4.setName("Äkta Arne Jacobsen Y-stol");
        auction4.setStartDate(new Timestamp(Timestamp.from(Instant.now()).getTime() + sixHours));
        auction4.setEndDate(new Timestamp(Timestamp.from(Instant.now()).getTime() + week + sixHours));
        auction4.setUser(user7);
        auction4.setCategory(category3);
        auction4.setActive(true);
        auction4.setImgUrl("http://www.malerklein-online.de/images/malerkleinonlinede/864-ch24-y-stol-h-j-wegner-fabrikengbg-se-8540.jpg");

        Auction auction5 = new Auction();
        auction5.setCost(2000d);
        auction5.setDescription("En begagnad rävpäls i nyskick");
        auction5.setName("Äkta rävpäls");
        auction5.setStartDate(new Timestamp(Timestamp.from(Instant.now()).getTime() + (oneHour * 3)));
        auction5.setEndDate(new Timestamp(Timestamp.from(Instant.now()).getTime() + week + (oneHour * 3)));
        auction5.setUser(user7);
        auction5.setCategory(category2);
        auction5.setActive(true);
        auction5.setImgUrl("https://d2mpxrrcad19ou.cloudfront.net/item_images/872157/10411916_fullsize.jpg");

        Auction auction6 = new Auction();
        auction6.setCost(1500d);
        auction6.setDescription("Ett Model M från 1985 i väldigt bra skick.");
        auction6.setName("Model M tangentbord");
        auction6.setStartDate(new Timestamp(Timestamp.from(Instant.now()).getTime() + day + day + sixHours));
        auction6.setEndDate(new Timestamp(Timestamp.from(Instant.now()).getTime() + week + day + day + sixHours));
        auction6.setUser(user8);
        auction6.setCategory(category5);
        auction6.setActive(true);
        auction6.setImgUrl("https://m.media-amazon.com/images/I/81LjfmVjvzL.jpg");

        Auction auction7 = new Auction();
        auction7.setCost(200d);
        auction7.setDescription("Riktigt slitna Converse som jag inte använder längre.");
        auction7.setName("Converse stl 47");
        auction7.setStartDate(new Timestamp(Timestamp.from(Instant.now()).getTime() + oneHour - tenMinutes));
        auction7.setEndDate(new Timestamp(Timestamp.from(Instant.now()).getTime() + week + oneHour - tenMinutes));
        auction7.setUser(user8);
        auction7.setCategory(category4);
        auction7.setActive(true);
        auction7.setImgUrl("https://images.unsplash.com/photo-1494496195158-c3becb4f2475?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80");
        
        Auction auction8 = new Auction();
        auction8.setCost(11500d);
        auction8.setDescription("Keltisk harpa handgjort i Tyskland.");
        auction8.setName("Celtic Harp - 36 strängar");
        auction8.setStartDate(new Timestamp(Timestamp.from(Instant.now()).getTime()));
        auction8.setEndDate(new Timestamp(Timestamp.from(Instant.now()).getTime() + week));
        auction8.setUser(user8);
        auction8.setCategory(category1);
        auction8.setActive(true);
        auction8.setImgUrl("https://images.unsplash.com/photo-1601902186937-b6c743ae2cd3?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80");

        Auction auction9 = new Auction();
        auction9.setCost(25d);
        auction9.setDescription("Badtofflor från Intersport stl 47, inköpta någon gång sent 90-tel. Knappt använda.");
        auction9.setName("Badtofflor stl 47");
        auction9.setStartDate(new Timestamp(Timestamp.from(Instant.now()).getTime() + (tenMinutes * 2)));
        auction9.setEndDate(new Timestamp(Timestamp.from(Instant.now()).getTime() + week + (tenMinutes * 2)));
        auction9.setUser(user8);
        auction9.setCategory(category4);
        auction9.setActive(true);
        auction9.setImgUrl("https://cdn.intersport.se/productimages/690x600/140117602000_40.jpg");

        Auction auction10 = new Auction();
        auction10.setCost(2000d);
        auction10.setDescription("Original GameBoy, funkar felfritt. Spel ingår.");
        auction10.setName("GameBoy");
        auction10.setStartDate(new Timestamp(Timestamp.from(Instant.now()).getTime() + 12924L));
        auction10.setEndDate(new Timestamp(Timestamp.from(Instant.now()).getTime() + week));
        auction10.setUser(user5);
        auction10.setCategory(category5);
        auction10.setActive(true);
        auction10.setImgUrl("https://images.unsplash.com/photo-1555864326-5cf22ef123cf?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1347&q=80");
        
        Auction auction11 = new Auction();
        auction11.setCost(3500d);
        auction11.setDescription("Bb-Trumpet av märket Startone");
        auction11.setName("Trumpet");
        auction11.setStartDate(new Timestamp(Timestamp.from(Instant.now()).getTime() - 111923L));
        auction11.setEndDate(new Timestamp(Timestamp.from(Instant.now()).getTime() + week));
        auction11.setUser(user5);
        auction11.setCategory(category1);
        auction11.setActive(true);
        auction11.setImgUrl("https://images.unsplash.com/photo-1573871666457-7c7329118cf9?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80");
        
        Auction auction12 = new Auction();
        auction12.setCost(25000000d);
        auction12.setDescription("Tvättäkta fiol byggd av Antonio Stradivari.");
        auction12.setName("Fiol");
        auction12.setStartDate(new Timestamp(Timestamp.from(Instant.now()).getTime() - 10230520L));
        auction12.setEndDate(new Timestamp(Timestamp.from(Instant.now()).getTime() + week));
        auction12.setUser(user6);
        auction12.setCategory(category1);
        auction12.setActive(true);
        auction12.setImgUrl("https://images.unsplash.com/photo-1612225330812-01a9c6b355ec?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80");

        List<Auction> allAuctions = new ArrayList<>();


        Bid bid1 = new Bid();
        bid1.setAmount(210d);
        bid1.setTimestamp(new Timestamp(Timestamp.from(Instant.now()).getTime()));
        bid1.setAuction(auction1);
        bid1.setUser(user7);

        Bid bid2 = new Bid();
        bid2.setAmount(220d);
        bid2.setAuction(auction1);
        bid2.setTimestamp(new Timestamp(Timestamp.from(Instant.now()).getTime()));
        bid2.setUser(user8);

        Bid bid3 = new Bid();
        bid3.setAmount(230d);
        bid3.setAuction(auction1);
        bid3.setTimestamp(new Timestamp(Timestamp.from(Instant.now()).getTime()));
        bid3.setUser(user7);

        Bid bid4 = new Bid();
        bid4.setAmount(240d);
        bid4.setAuction(auction1);
        bid4.setTimestamp(new Timestamp(Timestamp.from(Instant.now()).getTime()));
        bid4.setUser(user8);

        Bid bid5 = new Bid();
        bid5.setAmount(250d);
        bid5.setAuction(auction1);
        bid5.setTimestamp(new Timestamp(Timestamp.from(Instant.now()).getTime()));
        bid5.setUser(user7);

        Bid bid6 = new Bid();
        bid6.setAmount(250d);
        bid6.setAuction(auction1);
        bid6.setTimestamp(new Timestamp(Timestamp.from(Instant.now()).getTime()));
        bid6.setUser(user7);

        Bid bid7 = new Bid();
        bid7.setAmount(310d);
        bid7.setAuction(auction2);
        bid7.setTimestamp(new Timestamp(Timestamp.from(Instant.now()).getTime()));
        bid7.setUser(user6);

        Bid bid8 = new Bid();
        bid8.setAmount(8500d);
        bid8.setAuction(auction3);
        bid8.setTimestamp(new Timestamp(Timestamp.from(Instant.now()).getTime()));
        bid8.setUser(user5);

        Bid bid9 = new Bid();
        bid9.setAmount(4000d);
        bid9.setAuction(auction4);
        bid9.setTimestamp(new Timestamp(Timestamp.from(Instant.now()).getTime()));
        bid9.setUser(user8);

        Bid bid10 = new Bid();
        bid10.setAmount(4500d);
        bid10.setAuction(auction4);
        bid10.setTimestamp(new Timestamp(Timestamp.from(Instant.now()).getTime()));
        bid10.setUser(user5);

        Bid bid11 = new Bid();
        bid11.setAmount(2300d);
        bid11.setAuction(auction5);
        bid11.setTimestamp(new Timestamp(Timestamp.from(Instant.now()).getTime()));
        bid11.setUser(user5);

        Bid bid12 = new Bid();
        bid12.setAmount(2600d);
        bid12.setAuction(auction5);
        bid12.setTimestamp(new Timestamp(Timestamp.from(Instant.now()).getTime()));
        bid12.setUser(user6);

        Bid bid13 = new Bid();
        bid13.setAmount(1650d);
        bid13.setAuction(auction6);
        bid13.setTimestamp(new Timestamp(Timestamp.from(Instant.now()).getTime()));
        bid13.setUser(user7);

        Bid bid14 = new Bid();
        bid14.setAmount(1800d);
        bid14.setAuction(auction6);
        bid14.setTimestamp(new Timestamp(Timestamp.from(Instant.now()).getTime()));
        bid14.setUser(user5);

        Bid bid15 = new Bid();
        bid15.setAmount(210d);
        bid15.setAuction(auction7);
        bid15.setTimestamp(new Timestamp(Timestamp.from(Instant.now()).getTime()));
        bid15.setUser(user6);

        Bid bid16 = new Bid();
        bid16.setAmount(27.5d);
        bid16.setAuction(auction9);
        bid16.setTimestamp(new Timestamp(Timestamp.from(Instant.now()).getTime()));
        bid16.setUser(user6);

        List<Bid> allBids = new ArrayList<>();

        allCategories.add(category1);
        allCategories.add(category2);
        allCategories.add(category3);
        allCategories.add(category4);
        allCategories.add(category5);

        allUsers.add(user1);
        allUsers.add(user2);
        allUsers.add(user3);
        allUsers.add(user4);
        allUsers.add(user5);
        allUsers.add(user6);
        allUsers.add(user7);
        allUsers.add(user8);

        allAuctions.add(auction1);
        allAuctions.add(auction2);
        allAuctions.add(auction3);
        allAuctions.add(auction4);
        allAuctions.add(auction5);
        allAuctions.add(auction6);
        allAuctions.add(auction7);
        allAuctions.add(auction8);
        allAuctions.add(auction9);
        allAuctions.add(auction10);
        allAuctions.add(auction11);
        allAuctions.add(auction12);

        allBids.add(bid1);
        allBids.add(bid2);
        allBids.add(bid3);
        allBids.add(bid4);
        allBids.add(bid5);
        allBids.add(bid6);
        allBids.add(bid7);
        allBids.add(bid8);
        allBids.add(bid9);
        allBids.add(bid10);
        allBids.add(bid11);
        allBids.add(bid12);
        allBids.add(bid13);
        allBids.add(bid14);
        allBids.add(bid15);
        allBids.add(bid16);

        categoryService.saveAllCategories(allCategories);
        userService.saveAllUsers(allUsers);
        auctionService.saveAllAuctions(allAuctions);
        bidService.saveAllBids(allBids);
        return "redirect:/";
    }

}
