package org.silly.rats.auth;

import lombok.RequiredArgsConstructor;
import org.silly.rats.config.JwtService;
import org.silly.rats.user.User;
import org.silly.rats.user.UserRepository;
import org.silly.rats.user.type.AccountType;
import org.silly.rats.user.type.AccountTypeRepository;
import org.silly.rats.user.worker.Worker;
import org.silly.rats.user.worker.WorkerRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final UserRepository userRepository;
	private final WorkerRepository workerRepository;
	private final AccountTypeRepository accountTypeRepository;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;

	public AuthenticationResponse register(RegisterRequest request) {
		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new AuthenticationServiceException("Email already used");
		}

		if (userRepository.findByPhoneNum(request.getPhoneNum()).isPresent()) {
			throw new AuthenticationServiceException("Phone number already used");
		}

		AccountType accountType = accountTypeRepository.findByName(request.getType());
		if (accountType == null) {
			throw new AuthenticationServiceException("There is no such account type");
		}
		User user = new User();
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setEmail(request.getEmail());
		user.setPhoneNum(request.getPhoneNum());
		user.setDob(request.getDob());
		user.setType(accountType);
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		User saved = userRepository.save(user);
		if (!user.getType().equals("user")) {
			Worker worker = new Worker();
			worker.setWorkerId(saved.getId());
			worker.setDescription(request.getDescription());
			workerRepository.save(worker);
		}

		String jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(), request.getPassword()));

		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new UsernameNotFoundException("Email is not found"));
		user.setModifyDate(LocalDate.now());
		String jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}
}
