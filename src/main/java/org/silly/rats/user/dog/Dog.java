package org.silly.rats.user.dog;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.reserve.Reserve;
import org.silly.rats.user.User;
import org.silly.rats.user.dog.breed.Breed;
import org.silly.rats.user.dog.breed.OtherBreed;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dog")
public class Dog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dog_id")
	private Integer id;

	private String name;
	private LocalDate dob;

	@ManyToOne
	@JoinColumn(name = "breed")
	private Breed breed;

	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "dog_id")
	@JsonIgnore
	private OtherBreed otherBreed;
	private String comment;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private User user;

	@OneToMany(mappedBy = "dog", cascade = CascadeType.REMOVE)
	@JsonBackReference
	private List<Reserve> reserves;

	public Breed getBreed() {
		if (breed == null) {
			return Breed.builder()
					.name(otherBreed.getName())
					.size(otherBreed.getSize())
					.build();
		}
		return breed;
	}
}
