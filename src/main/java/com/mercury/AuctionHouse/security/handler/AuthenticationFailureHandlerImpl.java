package com.mercury.AuctionHouse.security.handler;

import com.mercury.AuctionHouse.security.SecurityUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
										AuthenticationException exception) throws IOException, ServletException {
		SecurityUtils.sendResponse(response, HttpServletResponse.SC_OK, "Login failed", exception);
	}

}
