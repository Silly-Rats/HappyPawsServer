package org.silly.rats.shop.attribute;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeRepository
	extends JpaRepository<Attribute, Integer> {
	@Query("SELECT a FROM Attribute a WHERE a.category.id = ?1")
	List<Attribute> findByCategory(Integer category);
}
