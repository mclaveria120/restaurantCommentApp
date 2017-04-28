package finalproject.database;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finalproject.database.queries.Queries;
import finalproject.exception.EntityNotFoundException;
import finalproject.exception.InvalidInputData;

public  abstract class AbstractDAO<E> implements DAO<E>{

	protected Class<E> entityClass;

	@Autowired
	protected SessionFactory sessionFactory;
	
	
	public AbstractDAO() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		entityClass = (Class) pt.getActualTypeArguments()[0];
	}

	@Override
	public void persist(E entity) {
		Queries.save(entity, sessionFactory);
	}

	@Override
	public void remove(E entity) {
		Queries.delete(entity, sessionFactory);
	}

	@Override
	public void update(E entity) {
		Queries.update(entity, sessionFactory);
	}
	
	@Override
	public E findById(Integer id)  throws EntityNotFoundException, InvalidInputData{
		E entity = Queries.findById(entityClass, sessionFactory, id);
		if(id==null){
			throw new InvalidInputData();
		}else if(entity==null){
			throw new EntityNotFoundException(entityClass.getName());
		}else{
			return entity;
		}
	}

	@Override
	public List<E> findAll() {
		return Queries.findAll(entityClass, sessionFactory);
	}
	
	
	@Override
	public long count() {
		 return Queries.count(entityClass, sessionFactory);
	}

	@Override
	public boolean exists(Integer id) {
		return Queries.findById(entityClass, sessionFactory, id)!=null;
	}
	
}
