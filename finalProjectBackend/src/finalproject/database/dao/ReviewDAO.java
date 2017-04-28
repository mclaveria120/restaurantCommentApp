package finalproject.database.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import finalproject.database.AbstractDAO;
import finalproject.database.queries.AbstractQuery;
import finalproject.database.queries.ComplexQuery;
import finalproject.model.Review;

@Repository
public class ReviewDAO extends AbstractDAO<Review>{

	public List<Review> getReviewByUser(Integer userId){
		return AbstractQuery.query(sessionFactory, new ComplexQuery<List<Review>>() {
			
			@Override
			public List<Review> query(Session session) {
				TypedQuery<Review> q = session.createQuery("FROM Review WHERE user_id = :user_id", entityClass);
				q.setParameter("user_id", userId);
				return q.getResultList();
			}
		});
	}
	
	public List<Review> getReviewsByRestaurant(Integer restaurantId){
		return AbstractQuery.query(sessionFactory, new ComplexQuery<List<Review>>() {
			
			@Override
			public List<Review> query(Session session) {
				TypedQuery<Review> q = session.createQuery("FROM Review  WHERE restaurant_id = :restaurant_id", entityClass);
				q.setParameter("restaurant_id", restaurantId);
				return q.getResultList();
			}
		});
	}
	
}
