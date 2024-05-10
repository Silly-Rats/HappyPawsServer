package org.silly.rats.shop.order.details;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderStatusRepository
	extends JpaRepository<OrderStatus, Byte> {
	OrderStatus findByName(String name);

	@Query("SELECT s.id FROM OrderStatus s WHERE s.name = ?1")
	Byte getIdByName(String name);
}
