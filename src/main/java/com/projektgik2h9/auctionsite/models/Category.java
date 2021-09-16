package com.projektgik2h9.auctionsite.models;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.List;

@Entity
@Table(name="categories")
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String type;

    @OneToMany(
        mappedBy = "category",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    private List<Auction> auctions = new ArrayList<>();

    

    public Category() {
    }


    public Category(Integer id, String type, List<Auction> auctions) {
        this.id = id;
        this.type = type;
        this.auctions = auctions;
    }
    

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public List<Auction> getAuctions() {
        return this.auctions;
    }

    public void setAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }

    public void addAuction(Auction auction){
        this.auctions.add(auction);
        auction.setCategory(this);
    }

}
