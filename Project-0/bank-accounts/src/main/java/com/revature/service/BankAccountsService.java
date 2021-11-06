package com.revature.service;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.revature.dao.BankAccountsDAO;
import com.revature.dto.AddOrUpdateBankAccountDTO;
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

}
