package ru.vallball.bank01.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.vallball.bank01.dao.AccountRepository;
import ru.vallball.bank01.dao.ClientRepository;
import ru.vallball.bank01.model.Account;
import ru.vallball.bank01.model.Client;

@Service
@Transactional
public class AccountServiceImpl implements AccountService{

	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Override
	public void save(Account account) {
		accountRepository.save(account);
	}


	@Override
	public void delete(Long id) {
		accountRepository.deleteById(id);		
	}

	@Override
	public void update(Account account) {
		accountRepository.save(account);		
	}

	@Override
	public Account findAccountById(Long id) {
		if (!accountRepository.findById(id).isPresent()) return null;
		else return accountRepository.findById(id).get();
	}
	
	
	
	@Override
	public List<Account> findAllByClient(Client client) {
		List<Account> accounts = accountRepository.findAll();
		List<Account> accountsForUser = new ArrayList<>();
		for (Account account : accounts) {
			if (account.getClient().getUsername().equals(client.getUsername())) accountsForUser.add(account);
		}
		return accountsForUser;
	}


	@Override
	public Page<Account> findByClient(Pageable pageable, Client client) {
		return accountRepository.findByClient(pageable, client);
	}
	

}
