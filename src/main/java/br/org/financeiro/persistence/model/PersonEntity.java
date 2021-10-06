package br.org.financeiro.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.org.financeiro.model.Person;
import br.org.financeiro.util.DateUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "person")
public class PersonEntity implements Serializable{

	private static final long serialVersionUID = 1784474719001330267L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="street")
	private String street;
	
	@Column(name="postal_code")
	private Integer postalCode;
	
	@Column(name="city")
	private String city;
	
	@Column(name="birthday")
	private Date birthday;
	
	public static PersonEntity from(Person person) {
		PersonEntity entity = new PersonEntity();
		entity.setId(person.getId() != 0L ? person.getId() : null);
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setStreet(person.getStreet());
		entity.setPostalCode(person.getPostalCode());
		entity.setCity(person.getCity());
		entity.setBirthday(DateUtil.toDate(person.getBirthday()));
		return entity;
	}
	
	public static Person to(PersonEntity person) {
		Person entity = new Person();
		entity.setId(person.getId());
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setStreet(person.getStreet());
		entity.setPostalCode(person.getPostalCode());
		entity.setCity(person.getCity());
		entity.setBirthday(DateUtil.toLocalDate(person.getBirthday()));
		return entity;
	}
}
