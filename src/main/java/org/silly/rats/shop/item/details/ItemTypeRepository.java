package org.silly.rats.shop.item.details;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemTypeRepository
	extends JpaRepository<ItemType, Integer> {
}
