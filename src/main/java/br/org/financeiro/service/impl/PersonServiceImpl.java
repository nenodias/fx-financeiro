package br.org.financeiro.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import br.org.financeiro.model.Person;
import br.org.financeiro.persistence.model.PersonEntity;
import br.org.financeiro.service.PersonService;

@Singleton
public class PersonServiceImpl implements PersonService {

	@Inject
	private EntityManager entityManager;
	
	@Override
	public void save(Person person) {
		final EntityTransaction t = entityManager.getTransaction();
		t.begin();
		try {
			PersonEntity entity = PersonEntity.from(person);
			if(entity.getId() != null) {
				entityManager.merge(entity);
			}else {
				entityManager.persist(entity);
			}
		}catch(Exception ex) {
			t.rollback();
			ex.printStackTrace();
		}finally {
			t.commit();
		}
	}
	
	@Override
	public List<Person> list(){
		Query createQuery = entityManager.createQuery("FROM PersonEntity");
    	@SuppressWarnings("unchecked")
		List<PersonEntity> resultList = (List<PersonEntity>) createQuery.getResultList();
    	List<Person> personData = new ArrayList<>();
    	resultList.forEach((person) -> personData.add( PersonEntity.to(person) ));
    	return personData;
	}
	
	
	public PersonEntity findById(Long id) {
		PersonEntity entity = entityManager.find(PersonEntity.class, id);
		return entity;
	}
	
	@Override
	public void delete(Long id) {
		final EntityTransaction t = entityManager.getTransaction();
		t.begin();
		try {
			PersonEntity entity = findById(id);
			entityManager.remove(entity);
		}catch(Exception ex) {
			t.rollback();
			ex.printStackTrace();
		}finally {
			t.commit();
		}
	}
}
