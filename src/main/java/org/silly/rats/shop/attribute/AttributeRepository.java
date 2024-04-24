package org.silly.rats.shop.attribute;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeRepository
	extends JpaRepository<Attribute, Integer> {
	List<Attribute> findByCategoryId(Integer category);
}
