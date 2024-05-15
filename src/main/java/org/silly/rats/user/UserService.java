package org.silly.rats.user;

import lombok.RequiredArgsConstructor;
import org.silly.rats.user.type.AccountTypeRepository;
import org.silly.rats.user.worker.WorkerInfo;
import org.silly.rats.util.ImageUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final AccountTypeRepository accountTypeRepository;
	private final PasswordEncoder passwordEncoder;

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

	public User getUserById(Integer id) {
		return userRepository.findById(id).orElse(null);
	}

	public String saveImage(Integer id, String image) {
		User user = userRepository.findById(id).orElse(null);
		if (user == null) {
			return null;
		}

		String savedImage = ImageUtil.saveImage(image, "img/user", id.toString());
		user.setImageName(id + ".png");
		userRepository.save(user);
		return savedImage;
	}

	public String getImage(Integer id) {
		User user = userRepository.findById(id).orElse(null);
		if (user == null) {
			return null;
		}

		return user.getImage();
	}

	public void patchUser(PatchUserRequest request, Integer id) {
		User user = userRepository.findById(id).orElseThrow(() ->
				new IllegalArgumentException("User with id " + id + " not found"));

		if (request.getNewPassword() != null) {
			if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
				throw new IllegalArgumentException("Wrong password");
			}
			user.setPassword(passwordEncoder.encode(request.getNewPassword()));
		}

		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setDob(request.getDob());
		user.setPhoneNum(request.getPhoneNum());
		userRepository.save(user);
	}

	public void deleteUser(Integer id, String pass)
			throws AuthenticationException {
		User user = userRepository.findById(id).orElseThrow(() ->
				new IllegalArgumentException("There is no user with id: " + id));
		if (passwordEncoder.matches(pass, user.getPassword())) {
			userRepository.deleteById(id);
		} else {
			throw new AuthenticationException("Wrong password");
		}
	}
}
