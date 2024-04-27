package org.silly.rats.user.dog.breed;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.user.dog.Dog;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "breed")
public class Breed {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "breed_id")
	private Byte id;

	private String name;
	private String size;

	@OneToMany(mappedBy = "breed")
	@JsonIgnore
	private List<Dog> dogs;
}
