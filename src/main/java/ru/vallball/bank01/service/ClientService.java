package ru.vallball.bank01.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import ru.vallball.bank01.model.Client;

public interface ClientService extends UserDetailsService{
	void save(Client client);
	List<Client> list();
	void delete(Long id);
	void update(Client client);
	Client findClientById(Long id);
	Page<Client> findAll(Pageable pageable);

}
