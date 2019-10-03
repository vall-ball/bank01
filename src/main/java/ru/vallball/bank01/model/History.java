package ru.vallball.bank01.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "histories")
public class History {
	@Id
	private Long id;
	@OneToOne
	@MapsId
	@JoinColumn(name = "id")
	private Client client;
	@ManyToMany
	@JoinTable(name="histories_operations", joinColumns=@JoinColumn(name="history_id"), inverseJoinColumns=@JoinColumn(name="operation_id"))  
	private List<Operation> operations;
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public List<Operation> getOperations() {
		return operations;
	}
	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}
	public Long getId() {
		return id;
	}
	
	
	
}
