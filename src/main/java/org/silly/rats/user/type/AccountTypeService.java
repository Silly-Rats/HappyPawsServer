package org.silly.rats.user.type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountTypeService {
	private final AccountTypeRepository accountTypeRepository;

	@Autowired
	public AccountTypeService(AccountTypeRepository accountTypeRepository) {
		this.accountTypeRepository = accountTypeRepository;
	}

	public Byte getAccountTypeByName(String name) {
		return accountTypeRepository.findByName(name);
	}
}
