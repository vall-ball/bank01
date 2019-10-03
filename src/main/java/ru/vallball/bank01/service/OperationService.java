package ru.vallball.bank01.service;

import java.util.List;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Pageable;

import ru.vallball.bank01.model.Account;
import ru.vallball.bank01.model.Client;
import ru.vallball.bank01.model.Operation;

public interface OperationService {
	void save(Operation operation);
	void delete(Long id);
	void update(Operation operation);
	Operation findOperationById(Long id);
	List<Operation> findAllByAccount(Account account);
//	public Page<Operation> findByAccount(Pageable pageable, Account account);
public PagedListHolder<Operation> findByClient(Pageable pageable, Client client);
}
