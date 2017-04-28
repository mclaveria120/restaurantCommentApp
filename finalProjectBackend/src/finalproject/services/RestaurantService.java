package finalproject.services;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finalproject.database.dao.RestaurantDAO;
import finalproject.database.dao.ReviewDAO;
import finalproject.database.dao.UserDAO;
import finalproject.exception.EntityNotFoundException;
import finalproject.exception.InvalidInputData;
import finalproject.model.Restaurant;
import finalproject.model.User;
import finalproject.util.Util;

@Service
public class RestaurantService {

	
	@Autowired
	private RestaurantDAO restaurantDAO;
	
	@Autowired
	private ReviewDAO reviewDAO;
	
	@Autowired
	private Util util;
	
	@Autowired
	private UserDAO userDAO;
	
	
	public List<Restaurant> getRestaurantByQuery(String query) throws InvalidInputData{
		if(!util.isParameterValid(query)){
			throw new InvalidInputData();
		}else{
			List<Restaurant> restaurants = restaurantDAO.getRestaurantByQuery(query);
			 for (Restaurant restaurant : restaurants) {
					restaurant.setReviews(null);
					restaurant.setNumberOfReviews(this.reviewDAO.getReviewsByRestaurant(restaurant.getId()).size());
			 }		
			return restaurants;
		}
	}
	
	public List<Restaurant> getRestaurantAndReviewsByQuery(String query) throws InvalidInputData{
		if(!util.isParameterValid(query)){
			throw new InvalidInputData();
		}else{
			List<Restaurant> restaurants = restaurantDAO.getRestaurantByQuery(query);
			 for (Restaurant restaurant : restaurants) {
					restaurant.setReviews(this.reviewDAO.getReviewsByRestaurant(restaurant.getId()));
			 }		
			return restaurants;
		}
	}

	public List<Restaurant> getRestaurants() {
		List<Restaurant> restaurants = restaurantDAO.findAll();
		for (Restaurant restaurant : restaurants) {
			restaurant.setReviews(null);
			restaurant.setNumberOfReviews(this.reviewDAO.getReviewsByRestaurant(restaurant.getId()).size());
		}
		return restaurants;
	}


	public Restaurant getRestaurantById(Integer id) throws InvalidInputData,EntityNotFoundException  {
			return restaurantDAO.findById(id);
	}

	public void save(Restaurant restaurant, Integer userId) throws InvalidInputData, EntityNotFoundException {
		if(!this.validateRestaurant(restaurant)){
			throw new InvalidInputData();
		}else{
			User user = userDAO.findById(userId);
			restaurant.setUser_id(user.getId());
			restaurantDAO.persist(restaurant);
		}
		
	}
	public Restaurant getEnableRestaurantById(Integer id) throws InvalidInputData{
		try{
			return this.restaurantDAO.getEnableRestaurantById(id);	
		}catch(NoResultException e){
			 throw new InvalidInputData();
		}
		
	}
	
	
	public void updateRestaurant(Restaurant restaurant, Integer userId) throws InvalidInputData, EntityNotFoundException{
		if(!this.validateRestaurant(restaurant)){
			throw new InvalidInputData();
		}else{
			Restaurant oldRestaurant ;
			
			try{
				 oldRestaurant = restaurantDAO.findById(restaurant.getId());	
			}catch(NoResultException e){
				 throw new InvalidInputData();
			}
			
			if(oldRestaurant.getUser_id().equals(userId)){
				oldRestaurant.setAddress(restaurant.getAddress());
				oldRestaurant.setDescription(restaurant.getDescription());
				oldRestaurant.setName(restaurant.getName());
				oldRestaurant.setMaxCapacity(restaurant.getMaxCapacity());
				restaurantDAO.update(oldRestaurant);
			}else{
				throw new InvalidInputData();
			}
		}
	}
	
	public void addUserToRestaurant(Integer restaurantId,User user) throws InvalidInputData{
		try {
			Restaurant restaurant = this.getRestaurantById(restaurantId);
			
			if(restaurant.getUser_id()!=null){
				throw new InvalidInputData();
			}
			
			restaurant.setUser_id(user.getId());
			this.restaurantDAO.update(restaurant);
		} catch (EntityNotFoundException e) {
			throw new InvalidInputData();
		}
	}
	
	public boolean validateRestaurant(Restaurant restaurant){
		String address = restaurant.getAddress();
		String description = restaurant.getDescription();
		double maxCapacity = restaurant.getMaxCapacity();
		String name = restaurant.getName();
		return util.isParameterValid(address) 
				&& util.isParameterValid(description) 
				&& maxCapacity!=0 
				&& util.isParameterValid(name);
	}

	public List<Restaurant> getEnableRestaurantsByUserId(Integer userId) throws InvalidInputData {
		if(userId==null){
			throw new InvalidInputData();
		}else{
			List<Restaurant> restaurants = restaurantDAO.getEnabledRestaurantsByOwner(userId);
			for (Restaurant restaurant : restaurants) {
				restaurant.setReviews(this.reviewDAO.getReviewsByRestaurant(restaurant.getId()));
			}
			return restaurants;
		}
		
	}

	public void save(Restaurant restaurant) throws InvalidInputData {
		if(!this.validateRestaurant(restaurant)){
			throw new InvalidInputData();
		}else{
			restaurantDAO.persist(restaurant);
		}
		
	}

	public void enableRestaurant(Integer restaurantId, boolean enable) throws InvalidInputData {
		 Restaurant restaurant;
			try {
				restaurant = this.getRestaurantById(restaurantId);
			    restaurant.setEnabled(enable);
			} catch (EntityNotFoundException e) {
				throw  new InvalidInputData();
			}
		 restaurantDAO.update(restaurant);
		
	}

	public Restaurant getUserRestaurant(Integer restaurantId, Integer userId) throws InvalidInputData {
		if(restaurantId==null || userId==null){
			throw new InvalidInputData();
		}else{
			Restaurant restaurant;
			try{
				restaurant = restaurantDAO.getUserRestaurant(restaurantId,userId);
				restaurant.setReviews(this.reviewDAO.getReviewsByRestaurant(restaurant.getId()));
			}catch (NoResultException e) {
				throw new InvalidInputData();
			}
			
			return restaurant;
		}
	}

	public List<Restaurant> getEnableRestaurants() {
		List<Restaurant> enableRestaurants = restaurantDAO.getEnableRestaurants();
		for (Restaurant restaurant : enableRestaurants) {
			restaurant.setReviews(this.reviewDAO.getReviewsByRestaurant(restaurant.getId()));
		}
		return enableRestaurants;
		
	}
	public List<Restaurant> getLastInsertedRestaurant() {
		 List<Restaurant> restaurants = restaurantDAO.getLastInsertedRestaurant();
		 for (Restaurant restaurant : restaurants) {
			restaurant.setReviews(null);
			restaurant.setNumberOfReviews(this.reviewDAO.getReviewsByRestaurant(restaurant.getId()).size());
		 }
		return  restaurants;
		 
	}
}
