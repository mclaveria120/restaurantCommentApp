package finalproject.database.dao;


import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import finalproject.database.AbstractDAO;
import finalproject.database.queries.AbstractQuery;
import finalproject.database.queries.ComplexQuery;
import finalproject.model.Restaurant;
import finalproject.model.User;

@Repository
public class UserDAO extends AbstractDAO<User>{
	
	public User loginUser(String email, String password) {
		return AbstractQuery.query(sessionFactory, new ComplexQuery<User>() {
			
			@Override
			public User query(Session session) {
				TypedQuery<User> q=session.createQuery("FROM User  where email=:email and password=:password", User.class)
						.setParameter("email", email)
						.setParameter("password", password);
				return q.getSingleResult();
			}
		});
	}

	public User getUserByEmail(String email) {
		return AbstractQuery.query(sessionFactory, new ComplexQuery<User>() {
				
				@Override
				public User query(Session session) {
					TypedQuery<User> q = session.createQuery("SELECT o FROM User o where email=:email", entityClass)
							.setParameter("email", email);
					return q.getSingleResult();
				}
			});
	}
	
	public boolean isUserRegister(String email) {
		boolean answer=true;
		try{
			AbstractQuery.query(sessionFactory, new ComplexQuery<User>() {
				
				@Override
				public User query(Session session) {
					TypedQuery<User> q = session.createQuery("SELECT o FROM User o where email=:email", entityClass)
							.setParameter("email", email);
					return q.getSingleResult();
				}
			});
		}catch(NoResultException e){
			answer=false;
		}
		return answer;
	}

	public List<User> getUserByRole(String role) {
		return AbstractQuery.query(sessionFactory, new ComplexQuery<List<User>>() {
			@Override
			public List<User> query(Session session) {
				TypedQuery<User> q = session.createQuery("SELECT o FROM User o where role=:role", entityClass);
				q.setParameter("role", role);
				return q.getResultList();
			}
		});
	}
}
