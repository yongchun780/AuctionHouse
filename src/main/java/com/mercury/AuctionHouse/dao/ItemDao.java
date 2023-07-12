package com.mercury.AuctionHouse.dao;

import com.mercury.AuctionHouse.bean.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemDao extends JpaRepository<Item, Integer> {
    public Item findById(int id);

}
