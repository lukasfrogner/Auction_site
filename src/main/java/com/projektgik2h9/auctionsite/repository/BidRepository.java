package com.projektgik2h9.auctionsite.repository;

import java.util.List;

import com.projektgik2h9.auctionsite.models.Bid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends JpaRepository<Bid, Integer>{
    
    public List<Bid> findTop3ByAuction_idOrderByAmountDesc(Integer auction_id);
    public List<Bid> findAllByAuction_idOrderByAmountDesc(Integer auction_id);
}
