package ru.vallball.bank01.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "operations")
public class Operation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name="source_id")
	private Account source;
	@ManyToOne
	@JoinColumn(name="destination_id")
	private Account destination;
	private LocalDate date;
	private LocalTime time;
	private BigDecimal sum;
	
	public Account getSource() {
		return source;
	}
	public void setSource(Account source) {
		this.source = source;
	}
	public Account getDestination() {
		return destination;
	}
	public void setDestination(Account destination) {
		this.destination = destination;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public LocalTime getTime() {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
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
		return "Операция № " + this.id + " от " + this.date + " перевела сумму " + this.sum + " со счёта " 
				+ this.source.getId() + " клиента " + this.source.getClient()+ " на счёт " 
				+ this.destination.getId() + " клиента " + this.destination.getClient(); 
	}
}
