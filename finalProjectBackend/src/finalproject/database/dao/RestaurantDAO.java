package finalproject.database.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import finalproject.database.AbstractDAO;
import finalproject.database.queries.AbstractQuery;
import finalproject.database.queries.ComplexQuery;
import finalproject.model.Restaurant;

@Repository
public class RestaurantDAO extends AbstractDAO<Restaurant> {
	
	
	public List<Restaurant> getEnabledRestaurantsByOwner(Integer id){
			return AbstractQuery.query(sessionFactory, new ComplexQuery<List<Restaurant>>() {
				@Override
				public List<Restaurant> query(Session session) {
					TypedQuery<Restaurant> q = session.createQuery("FROM Restaurant WHERE user_id = :user_id and enabled=1", entityClass);
					q.setParameter("user_id", id);
					return q.getResultList();
				}
			});
	}
	public List<Restaurant> getRestaurantByQuery(String query){
		return AbstractQuery.query(sessionFactory, new ComplexQuery<List<Restaurant>>() {
			
			@Override
			public List<Restaurant> query(Session session) {
				TypedQuery<Restaurant> q = session.createQuery("SELECT o FROM Restaurant o WHERE"
						+ " enabled=1 and user_id!=null and name=:query order by id DESC", entityClass)
						.setParameter("query", query);
				return q.getResultList();
			}
		});
	}
	public List<Restaurant> getLastInsertedRestaurant(){
		return AbstractQuery.query(sessionFactory, new ComplexQuery<List<Restaurant>>() {
			
			@Override
			public List<Restaurant> query(Session session) {
				TypedQuery<Restaurant> q = session.createQuery("FROM Restaurant WHERE enabled=1 and user_id!=null order by id DESC ", entityClass);
				q.setMaxResults(5);
				return q.getResultList();
			}
		});
	}
	
	public Restaurant getUserRestaurant(Integer restaurantId, Integer userId) {
		return AbstractQuery.query(sessionFactory, new ComplexQuery<Restaurant>() {
			
			@Override
			public Restaurant query(Session session) {
				TypedQuery<Restaurant> q = session.createQuery("FROM Restaurant WHERE user_id = :user_id and enabled=1 and id=:restaurant_id", entityClass)
						.setParameter("user_id", userId)
						.setParameter("restaurant_id", restaurantId);
				return q.getSingleResult();
			}
		});
	}
	public List<Restaurant> getEnableRestaurants() {
		return AbstractQuery.query(sessionFactory, new ComplexQuery<List<Restaurant>>() {
			@Override
			public List<Restaurant> query(Session session) {
				TypedQuery<Restaurant> q = session.createQuery("FROM Restaurant WHERE  enabled=1 and user_id!=null", entityClass);
				return q.getResultList();
			}
		});
	}
	
	public Restaurant getEnableRestaurantById(Integer restaurantId) {
		return AbstractQuery.query(sessionFactory, new ComplexQuery<Restaurant>() {
			@Override
			public Restaurant query(Session session) {
				TypedQuery<Restaurant> q = session.createQuery("FROM Restaurant WHERE  enabled=1 and user_id!=null and id=:restaurant_id", entityClass)
				.setParameter("restaurant_id", restaurantId);
				return q.getSingleResult();
			}
		});
	}
}