package org.silly.rats.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatchUserRequest {
	private String firstName;
	private String lastName;
	private LocalDate dob;
	private String phoneNum;
	private String newPassword;
	private String oldPassword;
}
