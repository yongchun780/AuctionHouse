package com.mercury.AuctionHouse.service;

import com.mercury.AuctionHouse.dao.UserDao;
import com.mercury.AuctionHouse.http.AuthenticationSuccessResponse;
import com.mercury.AuctionHouse.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

public class AuthService {
    @Autowired
    private UserDao userDao;

    public Response checklogin(Authentication authentication) {
        if (authentication != null) {
            Response response = new AuthenticationSuccessResponse(true, 200, "Logged In!", userDao.findByUsername(authentication.getName()));
            return response;
        } else {
            return new Response(false);
        }
    }
}
