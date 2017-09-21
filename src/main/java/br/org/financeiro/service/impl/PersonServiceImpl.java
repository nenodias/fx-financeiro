package br.org.financeiro.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import br.org.financeiro.model.Person;
import br.org.financeiro.persistence.model.PersonEntity;
import br.org.financeiro.service.PersonService;

@Singleton
public class PersonServiceImpl implements PersonService {

	@Inject
	private Session session;
	
	@Override
	public void save(Person person) {
		try {
			PersonEntity entity = PersonEntity.from(person);
			session.saveOrUpdate(entity);
			session.flush();
			session.evict(entity);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public List<Person> list(){
		Query createQuery = session.createQuery("FROM PersonEntity");
    	@SuppressWarnings("unchecked")
		List<PersonEntity> resultList = (List<PersonEntity>) createQuery.list();
    	List<Person> personData = new ArrayList<>();
    	resultList.forEach((person) -> {
    		personData.add( PersonEntity.to(person) );
    		session.evict(person);
		});
    	return personData;
	}
	
	
	public PersonEntity findById(Long id) {
		PersonEntity entity = (PersonEntity) session.get(PersonEntity.class, id);
		session.evict(entity);
		return entity;
	}
	
	@Override
	public void delete(Long id) {
		try {
			PersonEntity entity = findById(id);
			session.delete(entity);
			session.evict(entity);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
