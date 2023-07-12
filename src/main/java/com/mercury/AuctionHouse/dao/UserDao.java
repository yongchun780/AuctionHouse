package com.mercury.AuctionHouse.dao;

import com.mercury.AuctionHouse.bean.Item;
import com.mercury.AuctionHouse.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {
    public User findByUsername(String username);

}
