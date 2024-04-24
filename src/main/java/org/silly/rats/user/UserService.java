package org.silly.rats.user;

import lombok.RequiredArgsConstructor;
import org.silly.rats.user.type.AccountTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final AccountTypeRepository accountTypeRepository;

	public List<User> getAllUsersByType(String type) {
		return accountTypeRepository.findByName(type).getUsers();
	}

	public List<User> getUsers() {
		return userRepository.findAll();
	}
}
