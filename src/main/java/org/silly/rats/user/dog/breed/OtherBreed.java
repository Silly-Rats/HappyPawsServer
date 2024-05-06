package org.silly.rats.user.dog.breed;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.user.dog.Dog;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "other_breed")
public class OtherBreed {
	@Id
	@Column(name = "dog_id")
	@JsonIgnore
	private Integer id;

	private String name;
	private String size;

	@OneToOne
	@JoinColumn(name = "dog_id")
	@JsonBackReference
	private Dog dog;
}
