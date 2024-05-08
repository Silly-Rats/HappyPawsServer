package org.silly.rats.shop.order;

import lombok.RequiredArgsConstructor;
import org.silly.rats.shop.item.ItemRepository;
import org.silly.rats.shop.item.details.ItemTypeRepository;
import org.silly.rats.shop.order.details.OrderDetailRepository;
import org.silly.rats.shop.order.details.OrderItemDetails;
import org.silly.rats.shop.order.details.OrderStatusRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final OrderStatusRepository orderStatusRepository;

	public List<Order> getNotCompletedOrders() {
		Integer completed = orderStatusRepository.getIdByName("Completed");
		Integer cancelled = orderStatusRepository.getIdByName("Cancelled");

		return orderRepository.findNotCompleted(completed, cancelled);
	}

	public List<UserOrder> getUserOrders(Integer id) {
		return orderRepository.findByUserId(id)
				.stream()
				.map(UserOrder::new)
				.toList();
	}

	public Order proceedOrder(Integer id) {
		Optional<Order> optional = orderRepository.findById(id);
		if (optional.isEmpty()) {
			return null;
		}
		Order order = optional.get();

		if (order.getStatus().equals("Processing") || order.getStatus().equals("Awaiting delivery")) {
			if (availableInShop(order)) {
				reserveItems(order);
				order.setStatus(orderStatusRepository.findByName("Awaiting in shop"));
			} else {
				order.setStatus(orderStatusRepository.findByName("Awaiting delivery"));
			}
		} else if (order.getStatus().equals("Awaiting in shop")) {
			order.setStatus(orderStatusRepository.findByName("Completed"));
		}

		order.setChangeDate(LocalDateTime.now());
		return orderRepository.save(order);
	}

	public Order cancelOrder(Integer id, String type) {
		Optional<Order> optional = orderRepository.findById(id);
		if (optional.isEmpty()) {
			return null;
		}
		Order order = optional.get();

		if (type.equals("shop worker")) {
			if (!order.getStatus().equals("User Cancelled")) {
				System.out.println(order.getStatus());
				freeItems(order);
			}
			order.setStatus(orderStatusRepository.findByName("Cancelled"));
		} else {
			freeItems(order);
			order.setStatus(orderStatusRepository.findByName("User Cancelled"));
		}

		order.setChangeDate(LocalDateTime.now());
		return orderRepository.save(order);
	}

	private boolean availableInShop(Order order) {
		for (OrderItemDetails detail : order.getDetails()) {
			if (detail.getQty() > detail.getItem().getQty()) {
				return false;
			}
		}

		return true;
	}

	private void reserveItems(Order order) {
		for (OrderItemDetails detail : order.getDetails()) {
			detail.getItem().setQty(detail.getItem().getQty() - detail.getQty());
		}
	}

	private void freeItems(Order order) {
		for (OrderItemDetails detail : order.getDetails()) {
			detail.getItem().setQty(detail.getItem().getQty() + detail.getQty());
		}
	}
}