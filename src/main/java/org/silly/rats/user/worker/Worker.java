package org.silly.rats.user.worker;

import jakarta.persistence.*;
import org.silly.rats.user.User;

@Entity
@Table(name = "worker_details")
public class Worker {
	@Id
	@Column(name = "worker_id")
	private Integer workerId;

	@Column(name = "rating_sum")
	private Integer ratingSum;

	@Column(name = "reviews_num")
	private Integer reviewsNum;
	private String description;

	@OneToOne
	@JoinColumn(name = "worker_id", referencedColumnName = "user_id")
	private User user;

	public Worker() {}

	public Worker(Integer workerId, Integer ratingSum, Integer reviewsNum, String description, User user) {
		this.workerId = workerId;
		this.ratingSum = ratingSum;
		this.reviewsNum = reviewsNum;
		this.description = description;
		this.user = user;
	}

	public Worker(Integer ratingSum, Integer reviewsNum, String description, User user) {
		this.ratingSum = ratingSum;
		this.reviewsNum = reviewsNum;
		this.description = description;
		this.user = user;
	}

	public Integer getWorkerId() {
		return workerId;
	}

	public Integer getRatingSum() {
		return ratingSum;
	}

	public Integer getReviewsNum() {
		return reviewsNum;
	}

	public String getDescription() {
		return description;
	}

	public User getUser() {
		return user;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}