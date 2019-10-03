package ru.vallball.bank01.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.vallball.bank01.dao.ClientRepository;
import ru.vallball.bank01.model.Client;

@Service
@Transactional
public class ClientServiceImpl implements ClientService{
	
	@Autowired
	ClientRepository clientRepository;

	@Override
	public void save(Client client) {
		clientRepository.save(client);
		
	}

	@Override
	public List<Client> list() {
		return (List<Client>) clientRepository.findAll();
	}

	@Override
	public void delete(Long id) {
		clientRepository.deleteById(id);
		
	}

	@Override
	public void update(Client client) {
		clientRepository.save(client);
		
	}

	@Override
	public Client findClientById(Long id) {
		return clientRepository.findById(id).get();
	}

	@Override
	public Page<Client> findAll(Pageable pageable) {
		return clientRepository.findAll(pageable);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Client client = clientRepository.findUserByUsername(username);
		if (client != null) return client;
		throw new UsernameNotFoundException("Пользователь " + username + " не найден");
	}
	

}
