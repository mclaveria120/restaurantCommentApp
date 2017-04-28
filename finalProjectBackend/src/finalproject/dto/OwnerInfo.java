package finalproject.dto;

public class OwnerInfo {

	private String name;
	private String numberOfRestaurants;
	private Integer id;
	
	public String getNumberOfRestaurants() {
		return numberOfRestaurants;
	}
	public void setNumberOfRestaurants(String numberOfRestaurants) {
		this.numberOfRestaurants = numberOfRestaurants;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
