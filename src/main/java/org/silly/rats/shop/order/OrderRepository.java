package org.silly.rats.shop.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository
	extends JpaRepository<Order, Integer> {
	@Query("SELECT o FROM Order o WHERE o.status.id != ?1 AND o.status.id != ?2")
	List<Order> findNotCompleted(Integer completed, Integer cancelled);
}
