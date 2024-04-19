package org.silly.rats.user.type;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTypeRepository
	extends JpaRepository<AccountType, Byte> {
	@Query("SELECT t.id FROM AccountType t WHERE t.name = ?1")
	Byte findByName(String name);
}
