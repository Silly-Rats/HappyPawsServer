package org.silly.rats.user;

import lombok.RequiredArgsConstructor;
import org.silly.rats.config.JwtService;
import org.silly.rats.user.type.AccountTypeRepository;
import org.silly.rats.user.worker.WorkerInfo;
import org.silly.rats.util.ImageUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final AccountTypeRepository accountTypeRepository;

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
		user.setImageName(id + ".jpg");
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
}
