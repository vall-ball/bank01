package ru.vallball.bank01.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.SortDefinition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.vallball.bank01.dao.AccountRepository;
import ru.vallball.bank01.dao.OperationRepository;
import ru.vallball.bank01.model.Account;
import ru.vallball.bank01.model.Client;
import ru.vallball.bank01.model.Operation;

@Service
@Transactional
public class OperationServiceImpl implements OperationService{
	
	@Autowired
	OperationRepository operationRepository;
	
	@Autowired
	AccountRepository accountRepository;

	@Override
	public void save(Operation operation) {
		Account source = operation.getSource();
		source.setSum(source.getSum().subtract(operation.getSum()));
		accountRepository.save(source);
		Account destination = operation.getDestination();
		destination.setSum(destination.getSum().add(operation.getSum()));
		accountRepository.save(destination);
		operationRepository.save(operation);
		
	}

	@Override
	public void delete(Long id) {
		operationRepository.deleteById(id);
		
	}

	@Override
	public void update(Operation operation) {
		operationRepository.save(operation);
		
	}

	@Override
	public Operation findOperationById(Long id) {
		if (operationRepository.findById(id) == null) return null;
		else return operationRepository.findById(id).get();
	}

	@Override
	public List<Operation> findAllByAccount(Account account) {
		// TODO Auto-generated method stub
		return null;
	}
/*
	@Override
	public Page<Operation> findByAccount(Pageable pageable, Account account) {
		// TODO Auto-generated method stub
		return null;
	}
*/
	@Override
	public PagedListHolder<Operation> findByClient(Pageable pageable, Client client) {
		List<Account> accounts = accountRepository.findByClient(client);
		List<Operation> operations = new ArrayList<>();
		for (Account account : accounts) {
			operations.addAll(operationRepository.findByDestination(account));
			operations.addAll(operationRepository.findBySource(account));
		}
		List<Operation> resultList= operations.stream().distinct().collect(Collectors.toList());
		SortDefinition sort = new MutableSortDefinition("id",true,true);
		PagedListHolder<Operation> page = new PagedListHolder<Operation>(resultList,sort);
		page.resort();
		page.setPageSize(pageable.getPageSize());
		page.setPage(pageable.getPageNumber());
		return page;
	}
}
