package com.revature.dto;

import java.util.Objects;

public class AddOrUpdateBankAccountDTO {
	
	//private int clientId;
	private String bankAccountNo;
	private String bankAccountType;
	private String amount;
	
	public AddOrUpdateBankAccountDTO() {
		super();
	}

	public AddOrUpdateBankAccountDTO(String bankAccountNo, String bankAccountType, String amount) {
		super();
//		this.clientId = clientId;
		this.bankAccountNo = bankAccountNo;
		this.bankAccountType = bankAccountType;
		this.amount = amount;
	}

//	public int getClientId() {
//		return clientId;
//	}
//
//	public void setClientId(int clientId) {
//		this.clientId = clientId;
//	}

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
		return Objects.hash(amount, bankAccountNo, bankAccountType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddOrUpdateBankAccountDTO other = (AddOrUpdateBankAccountDTO) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(bankAccountNo, other.bankAccountNo)
				&& Objects.equals(bankAccountType, other.bankAccountType);
	}

	@Override
	public String toString() {
		return "AddOrUpdateBankAccountDTO [bankAccountNo=" + bankAccountNo + ", bankAccountType=" + bankAccountType
				+ ", amount=" + amount + "]";
	}
	

// WITH clientsId variable
//	@Override
//	public int hashCode() {
//		return Objects.hash(amount, bankAccountNo, bankAccountType, clientId);
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		AddOrUpdateBankAccountDTO other = (AddOrUpdateBankAccountDTO) obj;
//		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount)
//				&& Objects.equals(bankAccountNo, other.bankAccountNo)
//				&& Objects.equals(bankAccountType, other.bankAccountType) && clientId == other.clientId;
//	}

//	@Override
//	public String toString() {
//		return "AddOrUpdateBankAccountDTO [clientId=" + clientId + ", bankAccountNo=" + bankAccountNo
//				+ ", bankAccountType=" + bankAccountType + ", amount=" + amount + "]";
//	}
//	
	
}
