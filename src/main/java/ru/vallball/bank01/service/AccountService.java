package ru.vallball.bank01.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ru.vallball.bank01.model.Account;
import ru.vallball.bank01.model.Client;

public interface AccountService {
	void save(Account account);
	void delete(Long id);
	void update(Account account);
	Account findAccountById(Long id);
	List<Account> findAllByClient(Client client);
	public Page<Account> findByClient(Pageable pageable, Client client);

}
