package finalproject.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import finalproject.dto.CustomerInfo;
import finalproject.dto.OwnerInfo;
import finalproject.dto.RestaurantInfo;
import finalproject.exception.InvalidInputData;
import finalproject.model.Restaurant;
import finalproject.model.User;
import finalproject.services.RestaurantService;
import finalproject.services.ReviewService;
import finalproject.services.UserService;
import finalproject.util.Util;

@Controller
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/kpi")
public class KPIController {

	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReviewService reviewService;
	
	
	@Autowired
	private Util util;
	
	@GET
	@Path("/restaurants")
	public Response getRestaunrantInfo() {
		try {
				List<RestaurantInfo> restaurantsInfo = new ArrayList<RestaurantInfo>();
				
				List<Restaurant> restaurants = restaurantService.getEnableRestaurants();
				for (Restaurant restaurant : restaurants) {
					RestaurantInfo restaurantInfo= new RestaurantInfo();
					restaurantInfo.setName(restaurant.getName());
					restaurantInfo.setId(restaurant.getId());
					restaurantInfo.setNumberOfRewviews(restaurant.getReviews().size()+"");
					restaurantsInfo.add(restaurantInfo);
				}
				return Response.ok().entity(restaurantsInfo).build();
			} catch (Exception e) {
				return util.buildErrorResponse(400, "Error");
			}
	}
	
	@GET
	@Path("/owners")
	public Response getOwnersInfo() {
		try {
				List<OwnerInfo> ownersInfo = new ArrayList<OwnerInfo>();
				
				List<User> users = userService.getOwners();
				for (User user : users) {
					OwnerInfo ownerInfo= new OwnerInfo();
					ownerInfo.setName(user.getName());
					ownerInfo.setId(user.getId());
					ownerInfo.setNumberOfRestaurants(this.restaurantService.getEnableRestaurantsByUserId(user.getId()).size()+"");
					ownersInfo.add(ownerInfo);
				}
				return Response.ok().entity(ownersInfo).build();
			} catch (Exception e) {
				return util.buildErrorResponse(400, "Error");
			}
	}
	
	@GET
	@Path("/customers")
	public Response getCustomerInfo() {
		try {
				List<CustomerInfo> customersInfo = new ArrayList<CustomerInfo>();
				
				List<User> users = userService.getOwners();
				for (User user : users) {
					CustomerInfo customerInfo= new CustomerInfo();
					customerInfo.setName(user.getName());
					customerInfo.setId(user.getId());
					customerInfo.setNumberOfReviews(this.reviewService.getReviewByUserId(user.getId()).size()+"");
					customersInfo.add(customerInfo);
				}
				return Response.ok().entity(customersInfo).build();
			} catch (Exception e) {
				return util.buildErrorResponse(400, "Error");
			}
	}
}
