package org.silly.rats.shop.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository
	extends JpaRepository<Item, Integer> {
	List<Item> findByCategoryId(Integer category);
}
