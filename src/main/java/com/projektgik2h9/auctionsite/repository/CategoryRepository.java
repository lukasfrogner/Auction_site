package com.projektgik2h9.auctionsite.repository;

import com.projektgik2h9.auctionsite.models.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByType(String type);
}
