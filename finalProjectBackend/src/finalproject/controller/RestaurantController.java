package finalproject.controller;

import java.util.List;

import javax.annotation.security.PermitAll;
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
import org.springframework.stereotype.Controller;

import finalproject.exception.EntityNotFoundException;
import finalproject.exception.InvalidInputData;
import finalproject.model.Restaurant;
import finalproject.model.Review;
import finalproject.model.Role;
import finalproject.model.User;
import finalproject.services.RestaurantService;
import finalproject.services.ReviewService;
import finalproject.services.UserService;
import finalproject.util.Util;

@Path("/api/restaurants")
@Controller
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestaurantController {
	
	@Autowired
	private Util util;
	
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReviewService reviewService;
	
	
	///ADMIN
	
	@GET
	@Path("/all")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Response getRestaurants() {
		List<Restaurant> restaurants = restaurantService.getRestaurants();
		return Response.ok().entity(restaurants).build();
	}
	
	@POST
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Path("/{id}/enable")
	public Response enableRestaurant(@PathParam("id") Integer restaurantId) {
		 try {
				restaurantService.enableRestaurant(restaurantId, true);
				return Response.ok().build();
			} catch (InvalidInputData e) {
				return util.buildErrorResponse(400, e);
			}
	}
	
	@POST
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Path("/{id}/disable")
	public Response disableRestaurant(@PathParam("id") Integer restaurantId) {
		 try {
			restaurantService.enableRestaurant(restaurantId, false);
			return Response.ok().build();
		} catch (InvalidInputData e) {
			return util.buildErrorResponse(400, e);
		}
	}
	
	
	@POST()
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Response addRestaurant(Restaurant restaurant) {
		try {
			restaurantService.save(restaurant);
			return Response.ok().build();
		} catch (InvalidInputData e) {
			return util.buildErrorResponse(400, e);	
		}
	}
	
	
	@GET
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Path("/{id_restaurant}/reviews")
	public Response getReviewsFromRestaurant(@PathParam("id_restaurant") Integer id) {
			try {
				List<Review> reviews = reviewService.getReviewsByRestaurant(id);
				return util.buildOkResponse(reviews);
			} catch (InvalidInputData | EntityNotFoundException e) {
				return util.buildErrorResponse(400, e);	
			}
	}
	
	@PUT
	@Path("/{restaurantId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Response addOwnerToRestaurant(@PathParam("restaurantId") Integer restaurantId,User user) {
		try {
			user.setRole(Role.OWNER.toString());
			this.userService.saveUser(user);
			this.restaurantService.addUserToRestaurant(restaurantId, user);
			return Response.ok().build();
		} catch (InvalidInputData e) {
			return util.buildErrorResponse(400, e);	
		}
	}
	
	//Customer
	
	@GET
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	public Response getEnableRestaurants() {
		List<Restaurant> restaurants = restaurantService.getEnableRestaurants();
		return Response.ok().entity(restaurants).build();
	}
	
	@GET
	@Path("/{query}")
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	public Response getRestaurants(@PathParam("query") String query) {
		try {
				List<Restaurant> restaurants = restaurantService.getRestaurantAndReviewsByQuery(query);
				return Response.ok().entity(restaurants).build();
			} catch (InvalidInputData error) {
				return util.buildErrorResponse(400, error);
			}
	}

}
