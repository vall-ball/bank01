package ru.vallball.bank01.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@ComponentScan("ru.vallball.bank01")
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService clientService;
	
	@Bean
	 public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	  };
	  
	  @Override
	  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    auth.userDetailsService(clientService).passwordEncoder(passwordEncoder());
	  }
	  
	  protected void configure(HttpSecurity http) throws Exception {
		  http.authorizeRequests().antMatchers("/accounts").hasAnyRole("CLIENT","BANKIR").
			and().authorizeRequests().antMatchers("/clients/**","/accounts/**").hasRole("BANKIR").
			//and().authorizeRequests().antMatchers("/","/registration").permitAll().
			and().formLogin()
			//and().exceptionHandling().accessDeniedPage("/403");
			//and().exceptionHandling().accessDeniedHandler(new MyAccessDeniedHandler())
			;
	  }

}



