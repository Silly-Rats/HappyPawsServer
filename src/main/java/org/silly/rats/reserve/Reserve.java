package org.silly.rats.reserve;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.reserve.service.ServiceType;
import org.silly.rats.user.dog.Dog;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "reserve")
public class Reserve {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reserve_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "dog_id")
	@JsonIgnore
	private Dog dog;

	@ManyToOne
	@JoinColumn(name = "service")
	private ServiceType service;

	@Column(name = "reserve_time")
	private LocalDateTime reserveTime;
	private Double price;

	public ReserveDog getDogInfo() {
		return new ReserveDog(dog);
	}

	@JsonIgnore
	public boolean isCompleted() {
		return LocalDateTime.now().isAfter(reserveTime);
	}
}
