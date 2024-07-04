package org.silly.rats.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNum;
	private LocalDate dob;
	private String type;
	private String password;
	private String description;
}
