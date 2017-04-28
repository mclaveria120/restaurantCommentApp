package finalproject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import finalproject.security.handlers.JsonAccesDeniedHandler;
import finalproject.security.handlers.JsonLoginFailureHandler;
import finalproject.security.handlers.JsonLogoutSuccessHandler;
import finalproject.security.handlers.JsonLoginSuccessHandler;
import finalproject.security.handlers.JsonUnauthorizedHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfigAdapter extends WebSecurityConfigurerAdapter {

	
	@Autowired
    private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JsonUnauthorizedHandler jsonUnauthorizedHandler;
	
	@Autowired
	private JsonLoginSuccessHandler jsonLoginSuccessHandler;
	
	@Autowired
    private JsonLogoutSuccessHandler jsonLogoutSuccessHandler;
	
	@Autowired
	private JsonLoginFailureHandler  jsonLoginFailureHandler;
	
	@Autowired
	private JsonAccesDeniedHandler jsonAccesDeniedHandler;
	
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(getPsswordEncoder());
    }
    
       
    @Override
    protected void configure(HttpSecurity http) throws Exception { 
        http
        .csrf().disable()
        .exceptionHandling()
        	.authenticationEntryPoint(jsonUnauthorizedHandler).accessDeniedHandler(jsonAccesDeniedHandler)
        .and()
        	.authorizeRequests()
        	.antMatchers("/registration").permitAll()
        	.antMatchers("/api/*").authenticated()
        	//.antMatchers("/rest/*").authenticated()//aca van los roles.
        .and()
        	.formLogin().successHandler(jsonLoginSuccessHandler).failureHandler(jsonLoginFailureHandler)
        .and()
        	.logout().deleteCookies("JSESSIONID").logoutSuccessHandler(jsonLogoutSuccessHandler)
        .and()
        	.sessionManagement().maximumSessions(1);
    }
     
    @Bean
    public JsonLoginSuccessHandler mySuccessHandler(){
        return new JsonLoginSuccessHandler();
    }
    @Bean
    public SimpleUrlAuthenticationFailureHandler myFailureHandler(){
        return new JsonLoginFailureHandler();
    }
    @Bean
	public PasswordEncoder getPsswordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
}
