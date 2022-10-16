package net.javaguides.springboot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "person")
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "persion_id")
	private int persondId;

	@Column(name = "persion_name")
	private String personName;

	@Column(name = "persion_contact_no")
	private String personContactNo;

	@Column(name = "persion_address")
	private String personAddress;


	public Person() {

	}

	public Person(int persondId, String personName, String personContactNo, String personAddress) {
		this.persondId = persondId;
		this.personName = personName;
		this.personContactNo = personContactNo;
		this.personAddress = personAddress;
	}

	public int getPersondId() {
		return persondId;
	}

	public void setPersondId(int persondId) {
		this.persondId = persondId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonContactNo() {
		return personContactNo;
	}

	public void setPersonContactNo(String personContactNo) {
		this.personContactNo = personContactNo;
	}

	public String getPersonAddress() {
		return personAddress;
	}

	public void setPersonAddress(String personAddress) {
		this.personAddress = personAddress;
	}

	@Override
	public String toString() {
		return "Person [persondId=" + persondId + ", personName=" + personName + ", personContactNo=" + personContactNo
				+ ", personAddress=" + personAddress + "]";
	}

}
