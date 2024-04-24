package org.silly.rats.user.type;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTypeRepository
	extends JpaRepository<AccountType, Byte> {
	AccountType findByName(String name);
}
