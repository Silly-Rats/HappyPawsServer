package org.silly.rats.shop.attribute.value;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeValueRepository
	extends JpaRepository<AttributeValue, Integer> {
}
