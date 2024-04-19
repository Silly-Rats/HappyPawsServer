package org.silly.rats.user.type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountTypeService {
	private final AccountTypeRepository accountTypeRepository;
	private final List<AccountType> accountTypes;

	@Autowired
	public AccountTypeService(AccountTypeRepository accountTypeRepository) {
		this.accountTypeRepository = accountTypeRepository;
		this.accountTypes =accountTypeRepository.findAll();
	}

	public Byte getAccountTypeByName(String name) {
		for (AccountType accountType : accountTypes) {
			if (accountType.getName().equals(name)) {
				return accountType.getId();
			}
		}
		return null;
	}
}
