package ru.vallball.bank01.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Proxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
	
@Entity
@Proxy(lazy=false)
@Table(name = "clients")
public class Client implements UserDetails{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Size(min=2, max=30)
	private String username;
	@NotNull
	@Size(min=2, max=300)
	private String password;
	@NotNull
	@Enumerated(EnumType.STRING)
	private Role role;
	@OneToMany(
	        mappedBy = "client",
	        cascade = CascadeType.ALL,
	        orphanRemoval = true
	    )
	private List<Account> accounts;
	
	public Client() {
		
	}
	
	public Client(String username, String password,String role) {
		this.username = username;
		this.password = password;
		this.role = Role.valueOf("ROLE_" + role.toUpperCase());
	}
	
	public Client toClient(PasswordEncoder passwordEncoder) {
		this.setPassword(passwordEncoder.encode(password));
		return this;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = Role.valueOf("ROLE_" + role.toUpperCase());
	}
	public List<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	public Long getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return this.username + " " + this.role.toString();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority(role.getAuthority()));
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	

}
