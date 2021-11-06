package com.revature.dto;

import java.util.Objects;

public class JoinTableForClientAndBankAccountDTO {
	
	private int clientId;
	private String firstName;
	private String lastName;
	private String bankAccountNo;
	private String bankAccountType;
	private String amount;
	
	public JoinTableForClientAndBankAccountDTO() {
		super();
	}

	public JoinTableForClientAndBankAccountDTO(int clientId, String firstName, String lastName, String bankAccountNo,
			String bankAccountType, String amount) {
		super();
		this.clientId = clientId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.bankAccountNo = bankAccountNo;
		this.bankAccountType = bankAccountType;
		this.amount = amount;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public String getBankAccountType() {
		return bankAccountType;
	}

	public void setBankAccountType(String bankAccountType) {
		this.bankAccountType = bankAccountType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, bankAccountNo, bankAccountType, clientId, firstName, lastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JoinTableForClientAndBankAccountDTO other = (JoinTableForClientAndBankAccountDTO) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(bankAccountNo, other.bankAccountNo)
				&& Objects.equals(bankAccountType, other.bankAccountType) && clientId == other.clientId
				&& Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName);
	}

	@Override
	public String toString() {
		return "JoinTableForClientAndBankAccountDTO [clientId=" + clientId + ", firstName=" + firstName + ", lastName="
				+ lastName + ", bankAccountNo=" + bankAccountNo + ", bankAccountType=" + bankAccountType + ", amount="
				+ amount + "]";
	}	

}
