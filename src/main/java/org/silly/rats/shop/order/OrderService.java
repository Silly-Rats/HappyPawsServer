package org.silly.rats.shop.order;

import lombok.RequiredArgsConstructor;
import org.silly.rats.shop.order.details.OrderStatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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


}
