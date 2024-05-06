package org.silly.rats.shop.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository
	extends JpaRepository<Category, Integer> {
	@Query("SELECT c FROM Category c WHERE c.parent IS NULL")
	List<Category> findAllBasic();

	@Query("SELECT c FROM Category c WHERE c.parent.id = ?1")
	List<Category> findSubCategories(Integer id);
}
