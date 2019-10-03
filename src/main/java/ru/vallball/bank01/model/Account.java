package ru.vallball.bank01.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

@Entity
@Table(name = "accounts")
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name="client_id")
	private Client client;
	@Digits(integer=10, fraction=2, message = "Не более 10 миллиардов")
	private BigDecimal sum;
	
	
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public BigDecimal getSum() {
		return sum;
	}
	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}
	public Long getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "Счёт № " + this.getId() + " суммой " + this.getSum() + " клиента " + this.getClient().getUsername();
	}

	

}
