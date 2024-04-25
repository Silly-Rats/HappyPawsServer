package org.silly.rats.user.worker;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.user.User;

import java.util.Date;

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
	@JsonIgnore
	private Integer ratingSum = 0;

	@Column(name = "reviews_num")
	@JsonIgnore
	private Integer reviewsNum = 0;
	private String description;

	@Column(name = "start_working")
	private Date startWorking;

	@OneToOne
	@JoinColumn(name = "worker_id", referencedColumnName = "user_id")
	@JsonBackReference
	private User user;

	public Double getRating() {
		return reviewsNum == 0 ? 0 : (double) ratingSum / reviewsNum;
	}
}