package org.silly.rats.user;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;
	private LocalDate dob;

	@Column(name = "phone_num")
	private String phoneNum;
	private String email;
	private Byte type;

	@Column(name = "image_name")
	private String imageName;
	private String password;

	public User() {
	}

	public User(String firstName, String lastName, LocalDate dob, String phoneNum, String email, Byte type, String imageName, String password) {
		this(-1, firstName, lastName, dob, phoneNum, email, type, imageName, password);
	}

	public User(Integer id, String firstName, String lastName, LocalDate dob, String phoneNum, String email, Byte type, String imageName, String password) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.phoneNum = phoneNum;
		this.email = email;
		this.type = type;
		this.imageName = imageName;
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String first_name) {
		this.firstName = first_name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String last_name) {
		this.lastName = last_name;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phone_num) {
		this.phoneNum = phone_num;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String image_name) {
		this.imageName = image_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
