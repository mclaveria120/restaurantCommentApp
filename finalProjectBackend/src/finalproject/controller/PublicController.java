package finalproject.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import finalproject.model.User;
import finalproject.services.RestaurantService;
import finalproject.services.ReviewService;
import finalproject.util.Util;

@Controller
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/guest")
public class PublicController {

		@Autowired
		private RestaurantService restaurantService;
		
		@Autowired
		private Util util;
		
		@Autowired
		private ReviewService reviewService;
		
		@GET
		@Path("/restaurants/{query}")
		public Response getRestaurants(@PathParam("query") String query) {
			try {
					List<Restaurant> restaurants = restaurantService.getRestaurantByQuery(query);
					return Response.ok().entity(restaurants).build();
				} catch (InvalidInputData error) {
					return util.buildErrorResponse(400, error);
				}
		}
		
		
		@GET
		@Path("/restaurants/last")
		public Response getLastInsertedRestaurants() {
				List<Restaurant> restaurants = restaurantService.getLastInsertedRestaurant();
				return Response.ok().entity(restaurants).build();
		}
		
		@POST
		@Path("/restaurants/{id_restaurant}/review")
		public Response addComment(@PathParam("id_restaurant") Integer idRestaurant, Review review) {
			
			try {
				Restaurant restaurant = restaurantService.getRestaurantById(idRestaurant);
				reviewService.save(restaurant, review);
				return Response.ok().build();
				
			} catch (EntityNotFoundException | InvalidInputData e) {
				return util.buildErrorResponse(400, e);	
			}
			
		}
}

