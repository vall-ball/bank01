//��������������� �����
package ru.vallball.bank01.model;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;

public class Sum {
	@Digits(integer=10, fraction=2, message = "�� ����� 10 ����������")
	private BigDecimal sum;

	public BigDecimal getSum() {
		return sum;
	}

	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}
	
	
}
