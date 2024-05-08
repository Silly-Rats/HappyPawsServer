package org.silly.rats.reserve.hotel;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.reserve.Reserve;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "hotel")
public class HotelDetails {
	@Id
	private Long id;

	@OneToOne
	@JoinColumn(name = "id")
	private Reserve reserve;

	@Column(name = "end_date")
	private LocalDateTime endDate;

	@Column(name = "with_food")
	private Byte withFood;
}
