package br.org.financeiro.service;

import java.util.List;

import br.org.financeiro.model.Person;

public interface PersonService {

	void save(Person person);

	List<Person> list();

	void delete(Long id);

}
