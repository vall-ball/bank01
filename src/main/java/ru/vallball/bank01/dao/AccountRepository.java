package ru.vallball.bank01.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.vallball.bank01.model.Account;
import ru.vallball.bank01.model.Client;

public interface AccountRepository extends JpaRepository<Account, Long>{
	
	public Page<Account> findByClient(Pageable pageable, Client client);
	public List<Account> findByClient(Client client);
}
