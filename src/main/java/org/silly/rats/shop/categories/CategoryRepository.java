package org.silly.rats.shop.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository
	extends JpaRepository<Category, Integer> {
	@Query("SELECT c FROM Category c WHERE c.parent.id = ?1 OR ?1 IS NULL AND c.parent IS NULL")
	List<Category> findAllSubCategories(Integer parent);
}
