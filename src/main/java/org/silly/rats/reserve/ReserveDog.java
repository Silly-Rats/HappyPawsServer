package org.silly.rats.reserve;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.user.dog.Dog;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReserveDog {
	private Integer id;
	private String name;

	public ReserveDog(Dog dog) {
		this.id = dog.getId();
		this.name = dog.getName();
	}
}
