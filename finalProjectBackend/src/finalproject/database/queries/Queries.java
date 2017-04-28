package finalproject.database.queries;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Queries {
	
	public static  <E> void update(E entity,SessionFactory sessionFactory) {
		AbstractQuery.query(sessionFactory, new SimpleQuery() {

			@Override
			public void query(Session session) {
				session.update(entity);
			}

		});
	}

	public static  <E> void delete(E entity,SessionFactory sessionFactory) {
		AbstractQuery.query(sessionFactory, new SimpleQuery() {

			@Override
			public void query(Session session) {
				session.delete(entity);
			}
		});
	}
	
	public static  <E> void save(E entity,SessionFactory sessionFactory) {
		AbstractQuery.query(sessionFactory, new SimpleQuery() {

			@Override
			public void query(Session session) {
				session.save(entity);
			}
		});
	}
	
	public static <E>  E  findById(Class<E> entityClass,SessionFactory sessionFactory,Integer id ){
		
		return AbstractQuery.query(sessionFactory, new ComplexQuery<E>() {
			
			@Override
			public  E query(Session session) {
				return session.find(entityClass, id);
			}
		});
	}
	
	
	public static <E>  List<E>  findAll(Class<E> entityClass,SessionFactory sessionFactory){
		return AbstractQuery.query(sessionFactory, new ComplexQuery<List<E>>() {
			
			@Override
			public  List<E> query(Session session) {
				String query = String.format("SELECT o FROM %s o", entityClass.getSimpleName());
				return session.createQuery(query, entityClass).getResultList();
			}
		});
	}
	public static <E,Long>  Long  count(Class<E> entityClass,SessionFactory sessionFactory){
		return AbstractQuery.query(sessionFactory, new ComplexQuery<Long>() {
			
			@Override
			public Long query(Session session) {
				String query = String.format("SELECT count(o) FROM %s o", entityClass.getSimpleName());
				return (Long) session.createQuery(query, entityClass).getSingleResult();
			}
		});
	}
	
	
}
