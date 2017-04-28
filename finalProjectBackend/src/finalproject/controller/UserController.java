package finalproject.controller;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import finalproject.database.dao.CommentDAO;
import finalproject.database.dao.RestaurantDAO;
import finalproject.database.dao.ReviewDAO;
import finalproject.database.dao.UserDAO;
import finalproject.exception.EntityNotFoundException;
import finalproject.exception.InvalidInputData;
import finalproject.exception.InvalidUser;
import finalproject.model.Comment;
import finalproject.model.Restaurant;
import finalproject.model.Review;
import finalproject.model.User;
import finalproject.services.CommentService;
import finalproject.services.RestaurantService;
import finalproject.services.ReviewService;
import finalproject.services.UserService;
import finalproject.util.Util;

@Path("/api/users")
@Controller
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private Util util;
	
	
	////OWNER
	@GET
	@PreAuthorize("hasRole('ROLE_OWNER')")
	@Path("/{id}/restaurants")
	public Response getUsersRestaurants(@PathParam("id") Integer id) {
		try {
			
			util.isTheRightUser(id);
			List<Restaurant> restaurants = restaurantService.getEnableRestaurantsByUserId(id);
			return util.buildOkResponse(restaurants);
			
		} catch (InvalidInputData | InvalidUser e) {
			return util.buildErrorResponse(400, e);	
		}
		
	}
	
	@POST
	@PreAuthorize("hasRole('ROLE_OWNER')")
	@Path("/{id_user}/restaurants/")
	public Response addUserRestaurant(@PathParam("id_user") Integer userId, Restaurant restaurant) {
		try {
			restaurant.setEnabled(true);
			util.isTheRightUser(userId);
			
			restaurantService.save(restaurant, userId);
			return Response.ok().build();
		} catch (EntityNotFoundException | InvalidInputData | InvalidUser e) {
			return util.buildErrorResponse(400, e);	
		}
		
	}
	
	@GET
	@PreAuthorize("hasRole('ROLE_OWNER')")
	@Path("/{id_user}/restaurants/{id_restaurant}")
	public Response getUserRestaurant(@PathParam("id_user") Integer userId, @PathParam("id_restaurant") Integer restaurantId) {
		try {
			util.isTheRightUser(userId);
			return Response.ok(restaurantService.getUserRestaurant(restaurantId,userId)).build();
		} catch ( InvalidInputData | InvalidUser e) {
			return util.buildErrorResponse(400, e);	
		}
		
	}
	
	@PUT
	@PreAuthorize("hasRole('ROLE_OWNER')")
	@Path("/{id_user}/restaurants/")
	public Response updateUserRestaurant(@PathParam("id_user") Integer userId, Restaurant restaurant) {
		try {
			util.isTheRightUser(userId);
			restaurantService.updateRestaurant(restaurant, userId);
			return Response.ok().build();
		} catch (EntityNotFoundException | InvalidInputData | InvalidUser e) {
			return util.buildErrorResponse(400, e);	
		}
		
	}
	
	@POST
	@PreAuthorize("hasRole('ROLE_OWNER')")
	@Path("/{id_user}/reviews/{id_review}/comments")
	public Response addComment(@PathParam("id_user") Integer userId,@PathParam("id_review") Integer reviewId, Comment comment) {
		try {

			util.isTheRightUser(userId);
			reviewService.checkReviewExistance(reviewId);
			commentService.saveComment(comment, userId);
			reviewService.addCommentToaReview(comment, reviewId);
			return Response.ok().build();
			
		} catch (EntityNotFoundException | InvalidInputData | InvalidUser e) {
			return util.buildErrorResponse(400, e);	
		}
		
	}
	///ADMIN
	@GET
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Response getAllUsers() {
			 List<User> users = userService.getUsers();
			 for (User user : users) {
				user.setPassword(null);
			}
			return util.buildOkResponse(users);
	}
	
	//CUSTOMER
	@POST
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	@Path("/{id_user}/restaurants/{id_restaurant}/review")
	public Response addComment(@PathParam("id_user") Integer userId,@PathParam("id_restaurant") Integer idRestaurant, Review review) {
		
		try {
			
			User user = userService.getUserById(userId);
			util.isTheRightUser(userId);
			
			Restaurant restaurant = restaurantService.getEnableRestaurantById(idRestaurant);
			reviewService.save(user, restaurant, review);
			
			return Response.ok().build();
			
		} catch (EntityNotFoundException | InvalidInputData | InvalidUser e) {
			return util.buildErrorResponse(400, e);	
		}
		
	}
}
