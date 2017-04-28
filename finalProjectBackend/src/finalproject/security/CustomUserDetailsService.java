package finalproject.security;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import finalproject.model.User;
import finalproject.services.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user ;
		try{
			 user = userService.getUserByName(userName);
		}catch(NoResultException e){
			throw new UsernameNotFoundException("User not found");
		}
		CustomeUserDetail userDetail= new CustomeUserDetail(user.getEmail(), user.getPassword(), 
                 true, true, true, true, getGrantedAuthorities(user));
		userDetail.setUser(user);
		return userDetail;
	}

	 private List<GrantedAuthority> getGrantedAuthorities(User user){
	        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	        authorities.add(new SimpleGrantedAuthority("ROLE_"+user.getRole()));
	        return authorities;
	 }
}

