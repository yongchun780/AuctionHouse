package com.mercury.AuctionHouse.security.handler;

import com.mercury.AuctionHouse.security.SecurityUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationSuccessHandlerImpl extends SimpleUrlAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
										Authentication authentication) throws IOException, ServletException {
		SecurityUtils.sendResponse(response, HttpServletResponse.SC_OK, "Login successfully.", null);
	}
	
}
