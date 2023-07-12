package com.mercury.AuctionHouse.service;

import com.mercury.AuctionHouse.bean.Item;
import com.mercury.AuctionHouse.bean.Profile;
import com.mercury.AuctionHouse.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.mercury.AuctionHouse.http.Response;
import com.mercury.AuctionHouse.bean.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Response register(User user, boolean isBuyer) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Profile profile = new Profile(isBuyer ? 1 : 2); // 1: buyer, 2: seller
        user.setProfile(profile);
        userDao.save(user);
        return new Response(true);
    }

    public Response changePassword(User user, Authentication authentication) {
        if(user.getUsername().equals(authentication.getName()) || isAdmin(authentication.getAuthorities())) {
            User u = userDao.findByUsername(user.getUsername());
            u.setPassword(passwordEncoder.encode(user.getPassword()));
            userDao.save(u);
        }else {
            //TODO: Not authorize to update password if not current loggedin user or admin.
            return new Response(false);
        }
        return new Response(true);
    }

    public Response banUser(int id, Authentication authentication) {
        User u = userDao.findById(id).get();
        if (u != null && u.getProfile().getId() != 3 && isAdmin(authentication.getAuthorities())) {
            u.setProfile(new Profile(4, "BANNED"));
            userDao.save(u);
            return new Response(true, "User " + id + " was banned successfully! ");
        } else {
            return new Response(false);
        }
    }

    public boolean isAdmin(Collection<? extends GrantedAuthority> profiles) {
        boolean isAdmin = false;
        for(GrantedAuthority profile: profiles) {
            if(profile.getAuthority().equals("ADMIN")) {
                isAdmin = true;
            }
        }
        return isAdmin;
    }

    public List<Item> getPurchase(Authentication authentication) {
        User u = userDao.findByUsername(authentication.getName());
        List<Item> purchases = new ArrayList<>();
        for (Item i : u.getPurchasedItems()) {
            if (i.isSold()) {
                purchases.add(i);
            }
        }
        return purchases;
    }

    public List<Item> getOwnedItems(Authentication authentication) {
        User u = userDao.findByUsername(authentication.getName());
        return u.getOwnedItems();
    }
}
