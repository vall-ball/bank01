package ru.vallball.bank01.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.vallball.bank01.model.Account;
import ru.vallball.bank01.model.Operation;

public interface OperationRepository extends JpaRepository<Operation, Long>{
//	public Page<Operation> findByClient(Pageable pageable, Client client);
	public List<Operation> findBySource(Account account);
	public List<Operation> findByDestination(Account account);
	
}
