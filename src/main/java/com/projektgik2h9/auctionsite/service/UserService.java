package com.projektgik2h9.auctionsite.service;

import java.util.List;

import com.projektgik2h9.auctionsite.models.User;
import com.projektgik2h9.auctionsite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void saveAllUsers(List<User> allUsers){
        for(User u : allUsers){
            userRepository.save(u);
        } 
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Integer id){
        return userRepository.findById(id).get();
    }
    public void updateUserById(Integer id, String username, String email, String role){
        User user = userRepository.findById(id).get();
        user.setUsername(username);
        user.setEmail(email);
        user.setRole(role);
        userRepository.save(user);
    }
}
