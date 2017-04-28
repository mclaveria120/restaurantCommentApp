package finalproject.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finalproject.database.dao.CommentDAO;
import finalproject.database.dao.ReviewDAO;
import finalproject.exception.EntityNotFoundException;
import finalproject.exception.InvalidInputData;
import finalproject.model.Comment;
import finalproject.model.Restaurant;
import finalproject.model.Review;
import finalproject.model.Role;
import finalproject.model.User;
import finalproject.util.Util;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewDAO reviewDAO;
	
	@Autowired
	private Util util;
	
	
	public List<Review> getReviewsByRestaurant(Integer id) throws InvalidInputData, EntityNotFoundException{
		if(id==null){
			throw new InvalidInputData();
		}else{
			List<Review> reviews = this.reviewDAO.getReviewsByRestaurant(id);
			return reviews;
		}
	}
	
	public Review findById(Integer id) throws InvalidInputData,EntityNotFoundException  {
		if(id==null){
				throw new InvalidInputData();
		}else{
			return reviewDAO.findById(id);
		}
	}

	public List<Review> getReviewByUserId(Integer id) throws InvalidInputData {
		if(id==null){
			throw new InvalidInputData();
		}else{
			return  reviewDAO.getReviewByUser(id);
		}
	}
	
	public void addCommentToaReview(Comment comment,Integer reviewId) throws EntityNotFoundException, InvalidInputData{
		Review review = reviewDAO.findById(reviewId);
		review.setComment(comment);
		this.reviewDAO.update(review);
	}

	public void save(User user, Restaurant restaurant, Review review) throws InvalidInputData {
		review.setRestaurant_id(restaurant.getId());
		review.setUser_id(user.getId());
		review.setDate(util.getDate());
		if(!validReview(review)){
			throw new InvalidInputData();
		}
		reviewDAO.persist(review);
	}
	
	public void save(Restaurant restaurant, Review review) throws InvalidInputData {
		review.setRestaurant_id(restaurant.getId());
		review.setDate(util.getDate());
		if(!validReview(review)){
			throw new InvalidInputData();
		}
		reviewDAO.persist(review);
	}
	
	private boolean validReview(Review review) {
		 	String date = review.getDate();
		    double stars = review.getStars();
		    String description = review.getDescription();
		 
		return util.isParameterValid(date) 
				&& util.isParameterValid(description)
				&& (stars>=1 || stars<=5) ;
				
	}

	public void checkReviewExistance(Integer reviewId) throws InvalidInputData, EntityNotFoundException {
		if(reviewId==null){
			throw new InvalidInputData();
		}else{
			try{
				  reviewDAO.findById(reviewId);
				  
			}catch(NoResultException e){
				
				throw new InvalidInputData();
			}
			
		}
		
	}

}