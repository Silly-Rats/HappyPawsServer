package org.silly.rats.user;

import lombok.RequiredArgsConstructor;
import org.silly.rats.config.JwtService;
import org.silly.rats.user.type.AccountTypeRepository;
import org.silly.rats.user.worker.WorkerInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final AccountTypeRepository accountTypeRepository;
	private final JwtService jwtService;

	public List<WorkerInfo> getAllWorkersByType(String type) {
		try {
			return accountTypeRepository.findByName(type).getUsers()
					.stream()
					.map((e) -> new WorkerInfo(e.getWorkerDetails()))
					.toList();
		} catch (NullPointerException e) {
			return null;
		}
	}

	public User getUserByToken(String token) {
		if (token.startsWith("Bearer ")) {
			token = token.substring(7);
			Integer id = (Integer) jwtService.extractClaim(token, (c) -> c.get("id"));
			return userRepository.findById(id).orElse(null);
		}
		return null;
	}
}
