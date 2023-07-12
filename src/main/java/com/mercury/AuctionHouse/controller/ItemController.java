package com.mercury.AuctionHouse.controller;

import com.mercury.AuctionHouse.bean.Item;
import com.mercury.AuctionHouse.http.Response;
import com.mercury.AuctionHouse.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItemService itemService;

    @PreAuthorize("permitAll()")
    @GetMapping
    public List<Item> getAll() {
        return itemService.getAll();
    }

    @PreAuthorize("hasAnyAuthority('SELLER', 'ADMIN')")
    @PostMapping
    public Response save(@RequestBody Item item, Authentication authentication) {
        return itemService.save(item, authentication);
    }

    @PreAuthorize("hasAnyAuthority('SELLER', 'ADMIN')")
    @DeleteMapping("/{id}")
    public Response delete(@PathVariable int id, Authentication authentication) {
        return itemService.delete(id, authentication);
    }

    @PreAuthorize("hasAuthority('BUYER')")
    @PutMapping("/bid/{id}/{price}")
    public Response update(@PathVariable int id, @PathVariable int price, Authentication authentication) {
        return itemService.edit(id, price, authentication);
    }

    @PreAuthorize("hasAuthority('BUYER')")
    @PutMapping("/payoff/{id}")
    public Response payOff(@PathVariable int id, Authentication authentication) {
        return itemService.payOff(id, authentication);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/search/{keyword}")
    public List<Item> searchByKeyword(@PathVariable String keyword) {
        return itemService.searchByKeyword(keyword);
    }


}
