package com.mercury.AuctionHouse.controller;

import com.mercury.AuctionHouse.bean.Item;
import com.mercury.AuctionHouse.bean.User;
import com.mercury.AuctionHouse.http.Response;
import com.mercury.AuctionHouse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("permitAll()")
    @PostMapping("/{isBuyer}")
    public Response addUser(@RequestBody User user, @PathVariable boolean isBuyer) {
        return userService.register(user, isBuyer);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('BUYER', 'SELLER', 'ADMIN')")
    public Response changeUser(@RequestBody User user, Authentication authentication) {
        return userService.changePassword(user, authentication);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public Response banUser(@PathVariable int id, Authentication authentication) {
        return userService.banUser(id, authentication);
    }

    @PreAuthorize("hasAuthority('BUYER')")
    @GetMapping("/purchases")
    public List<Item> getPurchase(Authentication authentication) {
        return userService.getPurchase(authentication);
    }

    @PreAuthorize("hasAnyAuthority('SELLER', 'ADMIN')")
    @GetMapping("/items")
    public List<Item> getOwnedItems(Authentication authentication) {
        return userService.getOwnedItems(authentication);
    }
}
