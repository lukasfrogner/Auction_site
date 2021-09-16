package com.projektgik2h9.auctionsite.service;

import java.util.List;
import com.projektgik2h9.auctionsite.models.Bid;
import com.projektgik2h9.auctionsite.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BidService {
    
    @Autowired
    BidRepository bidRepository;

    public void saveAllBids(List<Bid> bids){
        for(Bid b : bids){
            bidRepository.save(b);
        }
    }

    public List<Bid> getAllBids(){
        return bidRepository.findAll();
    }
    public void saveBid(Bid bid){
        bidRepository.save(bid);
    }
   
    public List<Bid> getThreeHighestBids(Integer auction_id){
        return bidRepository.findTop3ByAuction_idOrderByAmountDesc(auction_id);
    }

    public List<Bid> getAllBidsDescAmount(Integer auction_id){
        return bidRepository.findAllByAuction_idOrderByAmountDesc(auction_id);
    }
    
}
