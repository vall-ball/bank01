package ru.vallball.bank01.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vallball.bank01.model.Client;


public interface ClientRepository extends JpaRepository<Client, Long>{
	
	public Client findUserByUsername(String username);
		
	

}
