package finalproject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import finalproject.database.dao.UserDAO;
import finalproject.exception.EntityNotFoundException;
import finalproject.exception.InvalidInputData;
import finalproject.model.Role;
import finalproject.model.User;
import finalproject.util.Util;

@Service
public class UserService {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private Util util;
	
	
	public User getUserById(Integer id) throws InvalidInputData, EntityNotFoundException{
		if(id==null){
			throw new InvalidInputData();
		}else{
			return userDAO.findById(id);
		}
	}

	public List<User> getUsers(){
		return userDAO.findAll();
	}

	public void saveUser(User user) throws InvalidInputData {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		if(validUser(user) && !isUserAlreadyResiter(user.getEmail())){
			this.userDAO.persist(user);
		}else{
			throw new InvalidInputData();
		}
	}

	public User getUserByName(String name){
		User user = this.userDAO.getUserByEmail(name);
		return user;
	}
	
	private boolean validUser(User user) {
		 String email = user.getEmail();
		 String name = user.getName();	
		 String password = user.getPassword();
		 String phone = user.getPhone();
		 String role = user.getRole();
		 String surname = user.getSurname();
		 
		return util.isParameterValid(email) 
				&& util.isParameterValid(name)
				&& util.isParameterValid(password)
				&& util.isParameterValid(phone)
				&& util.isParameterValid(role)
				&& (Role.GUEST.toString().equals(role) || Role.OWNER.toString().equals(role))
				&& util.isParameterValid(surname);
	}
	

	public boolean isUserAlreadyResiter(String email) {
		return  userDAO.isUserRegister(email);
	}

	public List<User> getOwners() {
		return this.userDAO.getUserByRole("OWNER");
	}
	public List<User> getCustomers() {
		return this.userDAO.getUserByRole("CUSTOMER");
	}
}
