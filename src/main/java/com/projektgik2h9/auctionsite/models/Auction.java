package com.projektgik2h9.auctionsite.models;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name = "auctions")
public class Auction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, 
                mappedBy = "auction", 
                cascade = CascadeType.ALL, 
                orphanRemoval = true)
    private List<Bid> bids = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    private String description;
    private String name;
    private Timestamp startDate;
    private Timestamp endDate;
    private Double cost;
    private boolean active;
    private String imgUrl;

    public Auction() {
    }

    public Auction(Integer id, User user, String description, String name, Timestamp startDate, Timestamp endDate, Double cost, List<Bid> bids, Category category, boolean active, String imgUrl) {
        this.id = id;
        this.user = user;
        this.description = description;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cost = cost;
        this.bids = bids;
        this.category = category;
        this.active = active;
        this.imgUrl = imgUrl; 
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getActive() {
        return this.active;
    }


    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return this.endDate;
    }

    public String getEndDateFormatted(){
        return new SimpleDateFormat("yyyy/MM/dd HH:mm").format(this.endDate);
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Double getCost() {
        return this.cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public void addBid(Bid bid) {
        this.bids.add(bid);
        bid.setAuction(this);
    }

    public List<Bid> getBids() {
        return this.bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public Integer getNrOfBids(){
        return this.bids.size();
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    

    public Bid getHighestBid(){
        Double highestBid = 0d;
        Bid bid = new Bid();
        for(Bid aBid : this.bids){
            if(aBid.getAmount() > highestBid){
                bid = aBid; 
            }
        }
        return bid;
    }

    public Timestamp getLatestBid(){
        Double highestBid = 0d;
        Timestamp latestBid = new Timestamp(0L);
        for(Bid aBid : this.bids){
            if(aBid.getAmount() > highestBid){
                latestBid = aBid.getTimestamp(); 
            }
        }
        return latestBid;
    }

    public boolean hasBids(){
        if(this.bids.size() == 0){
            return false;
        }
        else{
            return true;
        }
    }
}
