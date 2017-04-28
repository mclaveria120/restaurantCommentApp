package finalproject.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import finalproject.model.User;

public class CustomeUserDetail extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;

	private User user;
	
	public CustomeUserDetail(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		// TODO Auto-generated constructor stub
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
//	 @Override
//	    public boolean equals(Object o) {
//		 System.out.println(o.getClass().getName());
//	        if (o instanceof CustomeUserDetail) {
//	        	System.out.println(getUser().getEmail().equals(((CustomeUserDetail) o).getUser().getEmail()));
//	            return getUser().getEmail().equals(((CustomeUserDetail) o).getUser().getEmail());
//	        }
//	        return false;
//	    }
//
//	    @Override
//	    public int hashCode() {
//	        return user.getEmail().hashCode();
//	    }

}
