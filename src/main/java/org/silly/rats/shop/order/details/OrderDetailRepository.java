package org.silly.rats.shop.order.details;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository
	extends JpaRepository<OrderItemDetails, OrderDetailId> {
}
