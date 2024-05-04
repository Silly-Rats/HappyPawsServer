package org.silly.rats.shop.order;

import lombok.RequiredArgsConstructor;
import org.silly.rats.config.JwtService;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;

@RestController
@RequestMapping(path = "api/order")
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderService;
	private final JwtService jwtService;

	@GetMapping(path ="/not_completed")
	public List<Order> getNotCompletedOrders(@RequestHeader("Authorization") String token)
			throws AuthenticationException {
		token = token.substring(7);
		if (!jwtService.extractClaim(token, (c) -> c.get("type")).equals("shop worker")) {
			throw new AuthenticationException("User is not a shop worker");
		}

		return orderService.getNotCompletedOrders();
	}

	@GetMapping(path = "/user")
	public List<UserOrder> getUserItems(@RequestHeader(name = "Authorization") String token) {
		token = token.substring(7);
		Integer id = (Integer) jwtService.extractClaim(token, (c) -> c.get("id"));
		return orderService.getUserOrders(id);
	}

	@PatchMapping(path = "/proceed")
	public Order proceedOrder(@RequestHeader(name = "Authorization") String token,
							  @RequestBody Integer id)
			throws AuthenticationException {
		token = token.substring(7);
		if (!jwtService.extractClaim(token, (c) -> c.get("type")).equals("shop worker")) {
			throw new AuthenticationException("User is not a shop worker");
		}

		return orderService.proceedOrder(id);
	}

	@PatchMapping(path = "/cancel")
	public Order cancelOrder(@RequestHeader(name = "Authorization") String token,
			@RequestBody Integer id) {
		token = token.substring(7);
		String type = jwtService.extractClaim(token, (c) -> c.get("type")).toString();

		return orderService.cancelOrder(id, type);
	}
}
