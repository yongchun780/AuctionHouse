package com.mercury.AuctionHouse.service;

import com.mercury.AuctionHouse.bean.Item;
import com.mercury.AuctionHouse.bean.User;
import com.mercury.AuctionHouse.dao.ItemDao;
import com.mercury.AuctionHouse.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private UserService userService;
    public List<Item> getAll() {
        return itemDao.findAll();
    }
    public Response save(Item item, Authentication authentication) {
        try {
            User authenticatedUser = (User) authentication.getPrincipal();
            item.setSeller(authenticatedUser);
            // buyer is the poster initially.
            item.setBuyer(authenticatedUser);
            itemDao.save(item);
            return new Response(true);
        } catch (Exception e) {
            System.out.println(e);
            return new Response(false);
        }
    }

    public Response delete(int id, Authentication authentication) {
        try {
            User authenticatedUser = (User) authentication.getPrincipal();
            Item i = itemDao.findById(id);
            if ((i.getSeller() == authenticatedUser) || userService.isAdmin(authentication.getAuthorities())) {
                itemDao.delete(i);
                return new Response(true, "Item " + id + " was deleted successfully!" );
            } else {
                return new Response(false, "You don't have right!");
            }
        } catch (Exception e) {
            System.out.println(e);
            return new Response(false);
        }
    }

    public Response edit(int id, int price, Authentication authentication) {
        try {
            Item i = (Item) itemDao.findById(id);
            if (price >= i.getPayOffPrice()) {
                return payOff(id, authentication);
            }
            if (price <= i.getCurrentPrice() || price <= i.getStartingPrice()) {
                return new Response(false, "Your bidding price must be higher than current price and starting price!");
            }
            i.setBuyer((User) authentication.getPrincipal());
            i.setCurrentPrice(price);
            itemDao.save(i);
            return new Response(true, "Nice bid!");
        } catch (Exception e) {
            System.out.println(e);
            return new Response(false);
        }
    }
    public Response payOff(int id, Authentication authentication) {
        try {
            Item i = (Item) itemDao.findById(id);
            i.setSold(true);
            i.setCurrentPrice(i.getPayOffPrice());
            i.setBuyer((User) authentication.getPrincipal());
            itemDao.save(i);
            return new Response(true);
        } catch (Exception e) {
            System.out.println(e);
            return new Response(false);
        }
    }

    public void updateAsSold(Item item) {
        item.setSold(true);
        itemDao.save(item);
    }

    public List<Item> searchByKeyword(String keyword) {
        keyword = keyword.toLowerCase();
        List<Item> itemsWithKeyword = new ArrayList<>();
        for (Item i : getAll()) {
            if (i.getName().toLowerCase().contains(keyword) || i.getInfo().toLowerCase().contains(keyword)) {
                itemsWithKeyword.add(i);
            }
        }
        return itemsWithKeyword;
    }
}
