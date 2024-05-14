package org.silly.rats.user;

import lombok.Data;

@Data
public class OrderUser {
	private String name;
	private String email;
	private String phoneNum;

	public OrderUser(User user) {
		this.name = user.getFirstName() + " " + user.getLastName();
		this.email = user.getEmail();
		this.phoneNum = user.getPhoneNum();
	}
}
