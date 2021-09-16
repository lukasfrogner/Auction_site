package com.projektgik2h9.auctionsite.service;

import java.util.List;

import com.projektgik2h9.auctionsite.models.Category;
import com.projektgik2h9.auctionsite.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    
    @Autowired
    CategoryRepository categoryRepository;

    public void saveAllCategories(List<Category> categories){
        for(Category c : categories){
            categoryRepository.save(c);
        }
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
    
    public Category getCategoryById(Integer id){
        return categoryRepository.findById(id).get();
    }

    public Category getCategoryByType(String type){
        return categoryRepository.findByType(type);
    }
}
