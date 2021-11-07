package com.revature.service;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.revature.dao.BankAccountsDAO;
import com.revature.dto.AddOrUpdateBankAccountDTO;
import com.revature.dto.JoinTableForClientAndBankAccountDTO;
import com.revature.exceptions.InvalidInputException;
import com.revature.models.BankAccounts;

public class BankAccountsService {

	private BankAccountsDAO bankAccountsDao;

	public BankAccountsService() {
		this.bankAccountsDao = new BankAccountsDAO();
	}

	public BankAccounts addBankAccount(String id, AddOrUpdateBankAccountDTO bankDto)
			throws SQLException, InvalidInputException {

		if (bankDto.getBankAccountNo().trim().equals("") || bankDto.getBankAccountType().trim().equals("")) {
			throw new InvalidInputException("Bank Account No/Bank Account Type cannot be empty!");
		}

		Set<String> validBankAccountTypes = new HashSet<>();
		validBankAccountTypes.add("Checkings");
		validBankAccountTypes.add("Savings");

		if (!validBankAccountTypes.contains(bankDto.getBankAccountType())) {
			throw new InvalidInputException("Bank Account Type must be either 'Checkings' OR 'Savings'!");
		}

		if (Integer.parseInt(bankDto.getAmount()) <= 0) {
			throw new InvalidInputException("Amount must greater than 0 only!");
		}

		try {
			int clientsId = Integer.parseInt(id);

			BankAccounts bankAccounts = this.bankAccountsDao.insertBankAccount(clientsId, bankDto);

			return bankAccounts;

		} catch (NumberFormatException e) {
			throw new InvalidInputException("Amount can only be of int or double value!");
		} 

	}

	public List<JoinTableForClientAndBankAccountDTO> getAccountsById(String clientId) throws SQLException {

		int clientsId = Integer.parseInt(clientId);

		return this.bankAccountsDao.selectAccountsById(clientsId);
	}

	public List<JoinTableForClientAndBankAccountDTO> getAccountsWithSpecificAmount(String clientId,
			String amountGreaterThan, String amountLessThan) throws SQLException {
		
		int clientsId = Integer.parseInt(clientId);
		int amountGreater = Integer.parseInt(amountGreaterThan);
		int amountLess = Integer.parseInt(amountLessThan);
		
		if (amountGreaterThan != null && amountLessThan != null) {
			return this.bankAccountsDao.selectAccountsWithSpecificAmount(clientsId, amountGreater, amountLess);
		} else {
			return this.bankAccountsDao.selectAccountsById(clientsId);
		}
	
	}

}
