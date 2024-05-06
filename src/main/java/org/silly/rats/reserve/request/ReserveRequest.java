package org.silly.rats.reserve.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveRequest {
	private Integer reserveId;

	private String firstName;
	private String lastName;
	private String email;
	private String phone;

	private Integer dogId;
	private String dogName;
	private String breed;
	private String dogSize;

	private LocalDateTime reserveTime;
}
