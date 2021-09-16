package com.projektgik2h9.auctionsite.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    private List<Auction> auctions = new ArrayList<>();

    @OneToMany(
        mappedBy = "auction",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    private List<Bid> bids = new ArrayList<>();

    private String username;
    private String email;
    private String password;
    private String role;
    private Integer enabled;


    public User() {
    }

    public User(Integer id, String username, String email, String password, String role, Integer enabled, List<Auction> auctions, List<Bid> bids) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
        this.auctions = auctions;
        this.bids = bids;
    }
    public User(Integer id, String username, String email, String password, String role, Integer enabled) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public List<Auction> getAuctions() {
        return this.auctions;
    }

    public void setAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }

    public void addAuction(Auction auction) {
        this.auctions.add(auction);
        auction.setUser(this);
    }

    public void addBid(Bid bid) {
        this.bids.add(bid);
        bid.setUser(this);
    }

    public void removeAuction(Auction auction) {
        this.auctions.remove(auction);
        auction.setUser(null);
    }

    public List<Bid> getBids() {
        return this.bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public Integer getNrOfAuctions(){
        return this.auctions.size();
    }

    public Integer getNrOfBids(){
        return this.bids.size();
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", username='" + getUsername() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", role='" + getRole() + "'" +
            "}";
    }
    
}
