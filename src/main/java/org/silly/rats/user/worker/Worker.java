package org.silly.rats.user.worker;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.user.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "worker_details")
public class Worker {
	@Id
	@Column(name = "worker_id")
	@JsonIgnore
	private Integer workerId;

	@Column(name = "rating_sum")
	private Integer ratingSum = 0;

	@Column(name = "reviews_num")
	private Integer reviewsNum = 0;
	private String description;

	@OneToOne
	@JoinColumn(name = "worker_id", referencedColumnName = "user_id")
	@JsonBackReference
	private User user;
}