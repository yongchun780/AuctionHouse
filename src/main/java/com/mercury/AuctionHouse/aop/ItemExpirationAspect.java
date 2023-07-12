package com.mercury.AuctionHouse.aop;

import com.mercury.AuctionHouse.bean.Item;
import com.mercury.AuctionHouse.service.ItemService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;



@Aspect
@Component
public class ItemExpirationAspect {
    @Autowired
    private ItemService itemService;
    @Around("execution (* com.mercury.AuctionHouse.service.ItemService.getAll(..))")
    private Object checkExpired(ProceedingJoinPoint pjp) {
        Object o = null;
        try {
            o = pjp.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        List<Item> allItems = (List) o;
        List<Item> allAvailableItems = new ArrayList<>();
        for (Item i : allItems) {
            if (i.getExpireTime().before(currentTime)) {
                itemService.updateAsSold(i);
            }
            allAvailableItems.add(i);
        }
        return allAvailableItems;
    }
}
