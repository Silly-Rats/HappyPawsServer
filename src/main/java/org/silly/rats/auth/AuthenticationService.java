package org.silly.rats.auth;

import lombok.RequiredArgsConstructor;
import org.silly.rats.config.JwtService;
import org.silly.rats.user.User;
import org.silly.rats.user.UserRepository;
import org.silly.rats.user.Worker;
import org.silly.rats.user.type.AccountType;
import org.silly.rats.user.type.AccountTypeRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final UserRepository userRepository;
	private final AccountTypeRepository accountTypeRepository;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;

	public AuthenticationResponse register(RegisterRequest request) {
		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new AuthenticationServiceException("Email already used");
		}

		AccountType accountType = accountTypeRepository.findByName(request.getType());
		User user = User.builder()
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.email(request.getEmail())
				.phoneNum(request.getPhoneNum())
				.dob(request.getDob())
				.type(accountType)
				.workerDetails(accountType.getId().equals((byte) 1) ? null : new Worker())
				.password(passwordEncoder.encode(request.getPassword()))
				.build();

		userRepository.save(user);
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
		String jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}
}
