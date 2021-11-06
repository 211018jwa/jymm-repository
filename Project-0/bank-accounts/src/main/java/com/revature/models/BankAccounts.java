package com.revature.models;

import java.util.Objects;

public class BankAccounts {
	
	private int bankId;
	private String bankAccountNo;
	private String bankAccountType;
	private String amount;
	private int clientId;
	
	public BankAccounts() {
		super();
	}

	public BankAccounts(int bankId, int clientId, String bankAccountNo, String bankAccountType, String amount) {
		super();
		this.bankId = bankId;
		this.bankAccountNo = bankAccountNo;
		this.bankAccountType = bankAccountType;
		this.amount = amount;
		this.clientId = clientId;
	}

	public int getBankId() {
		return bankId;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
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

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, bankAccountNo, bankAccountType, bankId, clientId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BankAccounts other = (BankAccounts) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(bankAccountNo, other.bankAccountNo)
				&& Objects.equals(bankAccountType, other.bankAccountType) && bankId == other.bankId
				&& clientId == other.clientId;
	}

	@Override
	public String toString() {
		return "BankAccounts [bankId=" + bankId + ", bankAccountNo=" + bankAccountNo + ", bankAccountType="
				+ bankAccountType + ", amount=" + amount + ", clientId=" + clientId + "]";
	}
	
	

}
