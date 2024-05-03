package org.silly.rats.shop.order;

import lombok.RequiredArgsConstructor;
import org.silly.rats.config.JwtService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.util.List;

@RestController
@RequestMapping(path = "api/order")
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderService;
	private final JwtService jwtService;

	@GetMapping("/not_completed")
	public List<Order> getNotCompletedOrders(@RequestHeader("Authorization") String token)
			throws AuthenticationException {
		if (token == null || !token.startsWith("Bearer "))  {
			throw new AuthenticationException("Authentication required");
		}

		token = token.substring(7);
		if (!jwtService.extractClaim(token, (c) -> c.get("type")).equals("shop worker")) {
			throw new AuthenticationException("User is not a shop worker");
		}

		return orderService.getNotCompletedOrders();
	}
}
