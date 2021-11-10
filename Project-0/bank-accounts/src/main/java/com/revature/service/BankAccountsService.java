package com.revature.service;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.revature.dao.BankAccountsDAO;
import com.revature.dto.AddOrUpdateBankAccountDTO;
import com.revature.dto.JoinTableForClientAndBankAccountDTO;
import com.revature.exceptions.BankAccountNotFoundException;
import com.revature.exceptions.InvalidInputException;
import com.revature.models.BankAccounts;

public class BankAccountsService {

	private BankAccountsDAO bankAccountsDao;

	public BankAccountsService() {
		this.bankAccountsDao = new BankAccountsDAO();
	}
	
	public void validateFields(AddOrUpdateBankAccountDTO bankDto) throws InvalidInputException {
		
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
		
	}

	public BankAccounts addBankAccount(String id, AddOrUpdateBankAccountDTO bankDto)
			throws SQLException, InvalidInputException {

		validateFields(bankDto);

		try {
			int clientsId = Integer.parseInt(id);

			BankAccounts bankAccounts = this.bankAccountsDao.insertBankAccount(clientsId, bankDto);

			return bankAccounts;

		} catch (NumberFormatException e) {
			throw new InvalidInputException("Amount can only be of int or double value!");
		} 

	}

	public BankAccounts getBankAccount(String cid, String accId) throws SQLException, BankAccountNotFoundException, InvalidInputException {
		
		try {
		
		int bankId = Integer.parseInt(accId);
		int clientId = Integer.parseInt(cid);
		
		BankAccounts getBankAccountByIds = this.bankAccountsDao.selectBankAccountsById(clientId, bankId);
		
		if (getBankAccountByIds == null) {
			throw new BankAccountNotFoundException ("Client " +clientId+ " doesn't have any existing bank account id of " +bankId);
		}
				
		return getBankAccountByIds;
		
		} catch (NumberFormatException e) {
			throw new InvalidInputException("Entered id cannot be converted to int value! ");
		}
			
	}
	

	public List<JoinTableForClientAndBankAccountDTO> getAccountsById(String clientId) throws SQLException {

		int clientsId = Integer.parseInt(clientId);

		return this.bankAccountsDao.selectAccountsById(clientsId);
	}

	
	public List<JoinTableForClientAndBankAccountDTO> getAccountsWithSpecificAmount(String clientId,
			String amountGreaterThan, String amountLessThan) throws SQLException, InvalidInputException {
		
//		try {
			
		int clientsId = Integer.parseInt(clientId);
		int amountGreater = Integer.parseInt(amountGreaterThan);
		int amountLess = Integer.parseInt(amountLessThan);
	
		if (amountGreaterThan != null && amountLessThan != null) {
			return this.bankAccountsDao.selectAccountsWithSpecificAmount(clientsId, amountGreater, amountLess);
		} else {
			return this.bankAccountsDao.selectAccountsById(clientsId);
		}
		
//		} catch (NumberFormatException e) {
//			throw new InvalidInputException ("Amount Greater Than or Amount Less Than must be a convertable int type!");
//		}
	}

	
	
	public BankAccounts editBankAccount(String cId, String accId, AddOrUpdateBankAccountDTO bankDto) throws InvalidInputException, SQLException {
		
		int clientId = Integer.parseInt(cId);
		int accountId = Integer.parseInt(accId);
		
		validateFields(bankDto);
		
		BankAccounts bankAccounts = this.bankAccountsDao.updateBankAccount(clientId, accountId, bankDto);
		
		return bankAccounts;
	}

	public void removeBankAccount(String cId, String accId) {
		int clientId = Integer.parseInt(cId);
		int accountId = Integer.parseInt(accId);
		
		
		
	}

}
