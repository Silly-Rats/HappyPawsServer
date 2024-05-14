package org.silly.rats.shop.order;

import lombok.RequiredArgsConstructor;
import org.silly.rats.config.JwtService;
import org.silly.rats.user.OrderUser;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;

@RestController
@RequestMapping(path = "api/order")
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderService;
	private final JwtService jwtService;

	@GetMapping(path = "/all")
	public List<Order> getOrderUser(@RequestHeader("Authorization") String token,
									@RequestParam String orderId,
									@RequestParam String status,
									@RequestParam String sortBy,
									@RequestParam Boolean asc)
			throws AuthenticationException {
		if (!isShopWorker(token)) {
			throw new AuthenticationException("User is not a shop worker");
		}

		return orderService.getOrders(orderId, status, sortBy, asc);
	}

	@GetMapping(path = "/{id}/user")
	public OrderUser getOrderUser(@RequestHeader("Authorization") String token,
								  @PathVariable int id)
			throws AuthenticationException {
		if (!isShopWorker(token)) {
			throw new AuthenticationException("User is not a shop worker");
		}

		return orderService.getOrderUser(id);
	}

	@GetMapping(path = "/user")
	public List<UserOrder> getUserItems(@RequestHeader(name = "Authorization") String token,
										@RequestParam String status,
										@RequestParam String sortBy,
										@RequestParam Boolean asc) {
		Integer id = extractId(token);
		return orderService.getUserOrders(id, status, sortBy, asc);
	}

	@PostMapping(path = "/user")
	public void addUserOrder(@RequestHeader(name = "Authorization") String token,
							 @RequestBody List<OrderRequest> request) {
		Integer id = extractId(token);
		orderService.addUserOrder(request, id);
	}

	@PatchMapping(path = "/proceed")
	public Order proceedOrder(@RequestHeader(name = "Authorization") String token,
							  @RequestBody Integer id)
			throws AuthenticationException {
		if (!isShopWorker(token)) {
			throw new AuthenticationException("User is not a shop worker");
		}

		return orderService.proceedOrder(id);
	}

	@PatchMapping(path = "/cancel")
	public Order cancelOrder(@RequestHeader(name = "Authorization") String token,
							 @RequestBody Integer id) {
		String type = extractType(token);
		return orderService.cancelOrder(id, type);
	}

	private Integer extractId(String token) {
		token = token.substring(7);
		return (Integer) jwtService.extractClaim(token, (c) -> c.get("id"));
	}

	private String extractType(String token) {
		token = token.substring(7);
		return (String) jwtService.extractClaim(token, (c) -> c.get("type"));
	}

	private boolean isShopWorker(String token) {
		return extractType(token).equals("shop worker");
	}
}
