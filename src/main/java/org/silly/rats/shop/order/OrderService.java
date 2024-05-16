package org.silly.rats.shop.order;

import lombok.RequiredArgsConstructor;
import org.silly.rats.shop.item.details.ItemType;
import org.silly.rats.shop.item.details.ItemTypeRepository;
import org.silly.rats.shop.order.details.OrderDetailId;
import org.silly.rats.shop.order.details.OrderDetailRepository;
import org.silly.rats.shop.order.details.OrderItemDetails;
import org.silly.rats.shop.order.details.OrderStatusRepository;
import org.silly.rats.user.OrderUser;
import org.silly.rats.user.User;
import org.silly.rats.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final OrderDetailRepository orderDetailRepository;
	private final OrderStatusRepository orderStatusRepository;
	private final UserRepository userRepository;
	private final ItemTypeRepository itemTypeRepository;

	public List<Order> getOrders(String orderId, String status, String sortBy, Boolean asc) {
		Comparator<Order> comparator = orderComparator(sortBy, asc);

		Stream<Order> stream;
		if (status.equals("Not completed")) {
			Byte completed = orderStatusRepository.getIdByName("Completed");
			Byte cancelled = orderStatusRepository.getIdByName("Cancelled");
			stream = orderRepository
					.findNotCompleted(completed, cancelled)
					.stream();
		} else if (status.equals("All")) {
			stream = orderRepository.findAll().stream();
		} else {
			Byte statusId = orderStatusRepository.getIdByName(status);
			stream = orderRepository.findByStatusId(statusId).stream();
		}
		stream = stream.filter(e -> e.getId().toString().contains(orderId));
		stream = stream.sorted(comparator);

		return stream.toList();
	}

	private Comparator<Order> orderComparator(String sortBy, Boolean asc) {
		Comparator<Order> comparator = Comparator.comparing(Order::getId);

		if (sortBy.equals("status")) {
			comparator = Comparator.comparing(o -> o.getStatus().getId());
		} else if (sortBy.equals("order date")) {
			comparator = Comparator.comparing(Order::getOrderDate);
		} else if (sortBy.equals("change date")) {
			comparator = Comparator.comparing(Order::getChangeDate);
		} else if (sortBy.equals("price")) {
			comparator = Comparator.comparing(Order::getTotalPrice);
		}

		if (!asc) {
			comparator = comparator.reversed();
		}

		return comparator;
	}

	public OrderUser getOrderUser(Integer id) {
		Order order = orderRepository.findById(id).orElseThrow(() ->
				new IllegalArgumentException("There is no order with id: " + id));
		return new OrderUser(order.getUser());
	}

	public List<UserOrder> getUserOrders(Integer id, String status, String sortBy, Boolean asc) {
		Comparator<Order> comparator = orderComparator(sortBy, asc);

		Stream<Order> stream = orderRepository.findByUserId(id)
				.stream();
		if (status.equals("Not completed")) {
			Byte completed = orderStatusRepository.getIdByName("Completed");
			Byte cancelled = orderStatusRepository.getIdByName("Cancelled");
			Byte userCancelled = orderStatusRepository.getIdByName("User cancelled");
			stream = stream.filter(o -> !o.getStatus().getId().equals(completed) &&
					!o.getStatus().getId().equals(cancelled) &&
					!o.getStatus().getId().equals(userCancelled));
		} else if (!status.equals("All")) {
			Byte statusId = orderStatusRepository.getIdByName(status);
			stream = stream.filter(o -> o.getStatus().getId().equals(statusId));
		}

		return stream.sorted(comparator)
				.map(UserOrder::new)
				.toList();
	}

	public void addUserOrder(List<OrderRequest> request, Integer userId) {
		User user = userRepository.findById(userId).orElseThrow(() ->
				new IllegalArgumentException("There is no user with id: " + userId));
		Order order = new Order(null, orderStatusRepository.findByName("Processing"),
				user, LocalDateTime.now(), LocalDateTime.now(), null);
		order = orderRepository.save(order);

		for (OrderRequest item : request) {
			ItemType itemType = itemTypeRepository.getReferenceById(item.getItem());
			OrderItemDetails orderItemDetails = new OrderItemDetails(
					new OrderDetailId(itemType, order), item.getQty());
			orderDetailRepository.save(orderItemDetails);
		}
	}

	public Order proceedOrder(Integer id) {
		Optional<Order> optional = orderRepository.findById(id);
		if (optional.isEmpty()) {
			return null;
		}
		Order order = optional.get();

		if (order.getStatusName().equals("Processing") || order.getStatusName().equals("Awaiting delivery")) {
			if (availableInShop(order)) {
				reserveItems(order);
				order.setStatus(orderStatusRepository.findByName("Awaiting in shop"));
			} else {
				order.setStatus(orderStatusRepository.findByName("Awaiting delivery"));
			}
		} else if (order.getStatusName().equals("Awaiting in shop")) {
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
			if (!order.getStatusName().equals("User Cancelled")) {
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
