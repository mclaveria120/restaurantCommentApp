package finalproject.database;

import java.util.List;

import finalproject.exception.EntityNotFoundException;
import finalproject.exception.InvalidInputData;


public interface DAO<E> {

	public void persist(E entity);

	public void remove(E entity);
	
	public void update(E entity);

	public E findById(Integer id) throws EntityNotFoundException,InvalidInputData;

	public long count();

	public boolean exists(Integer id);
	
	public List<E> findAll();

}
