package com.projektgik2h9.auctionsite.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import javax.transaction.Transactional;
import com.projektgik2h9.auctionsite.models.Auction;
import com.projektgik2h9.auctionsite.models.User;
import com.projektgik2h9.auctionsite.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class AuctionService {

    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    BidService bidService;

    @Autowired
    EmailService emailService;


    public List<Auction> getAllAuctions(){
        return auctionRepository.findAll();
    }

    public void saveAllAuctions(List<Auction> auctions){
        for(Auction a : auctions){
        auctionRepository.save(a);
        }
    }
    
    public Auction getAuctionById(Integer id){
        return auctionRepository.findById(id).get();
    }
    public List<Auction> getAuctionByUserId(Integer id){
        return auctionRepository.findByUser_id(id);
    }
    
    public void updateAuctionById(Integer id, String description, String name){
        Auction auction = auctionRepository.findById(id).get();
        auction.setDescription(description);
        auction.setName(name);
        auctionRepository.save(auction);
    }

    public void saveAuction(Auction auction){
        auctionRepository.save(auction);
    }

    public Auction getLatestAuction(){
        return auctionRepository.findTopByOrderByIdDesc();
    }

    public List<Auction> getAuctionsByCategoryId(Integer id){
        return auctionRepository.findByCategory_id(id);
    } 

    public List<Auction> getAllFinishedAuctions(){
        return auctionRepository.findAllByActive(false);
    }

    public void deleteAuction(Integer id){
        auctionRepository.deleteById(id);
    }

    public void myTask(){
        System.out.println("Metod anropad");  
        System.out.println("Data kanske hämtad");
    }

    
    @Scheduled(fixedRate = 60000)//körs varje minut
    @Transactional()
    public void notifyWinner(){
        Long currentTime = new Timestamp(Timestamp.from(Instant.now()).getTime()).getTime();
        List<Auction> auctions = auctionRepository.findAllByActive(true);
        for (Auction a : auctions){
            if (a.getEndDate().getTime() < currentTime){//har auktionens tid tagit slut?
                if(a.getBids().size() == 0){//finns det bud på auktionen? Om inga skicka mail till säljare
                    emailService.sendEmail(a.getUser().getEmail(), "Inga bud lades på din auktion " + a.getName(), "Auktion");
                }
                else{//om en vinnare, skicka mail till vinnare
                    User winner = a.getHighestBid().getUser();
                    System.out.println(winner.getEmail());
                    emailService.sendEmail(winner.getEmail(), 
                                            "Du har vunnit auktionen på " + a.getName() + " med bud " + a.getHighestBid().getAmount() + "kr.", 
                                            "Du vann " + a.getName() + "!");           
                }   
                a.setActive(false);
                auctionRepository.save(a);     
            }
        }
    }


}
