package org.silly.rats.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "worker_details")
public class Worker {
	@Id
	@Column(name = "worker_id")
	@JsonBackReference
	private Integer workerId;

	@Column(name = "rating_sum")
	private Integer ratingSum;

	@Column(name = "reviews_num")
	private Integer reviewsNum;
	private String description;

	@OneToOne
	@JoinColumn(name = "worker_id", referencedColumnName = "user_id")
	@JsonBackReference
	private User user;
}