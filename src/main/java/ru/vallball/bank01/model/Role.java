package ru.vallball.bank01.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority{
	
	ROLE_CLIENT, ROLE_BANKIR;

	@Override
	public String getAuthority() {
		return name();
	}
}
