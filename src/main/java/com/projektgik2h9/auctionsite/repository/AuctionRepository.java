package com.projektgik2h9.auctionsite.repository;

import java.util.List;

import com.projektgik2h9.auctionsite.models.Auction;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Integer>{

    public Auction findTopByOrderByIdDesc();
    public List<Auction> findByCategory_id(Integer id);
    public List<Auction> findByUser_id(Integer id);
    public Page<Auction> findAllByCategory_id(Integer id, Pageable pageable);
    public Page<Auction> findAllByCategory_idAndActive(Integer id, boolean active, Pageable pageable);
    public List<Auction> findAllByActive(boolean active);


}
