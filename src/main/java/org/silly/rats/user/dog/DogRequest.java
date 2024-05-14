package org.silly.rats.user.dog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DogRequest {
	private Integer id;
	private String name;
	private LocalDate birthday;
	private Integer breed;
	private String breedName;
	private String size;
	private String comment;
}
