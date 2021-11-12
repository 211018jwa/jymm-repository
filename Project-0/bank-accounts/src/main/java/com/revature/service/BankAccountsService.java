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

	public BankAccountsService(BankAccountsDAO bankAccountsDao) {
		this.bankAccountsDao = bankAccountsDao;
	}

	public boolean validateFields(AddOrUpdateBankAccountDTO bankDto) throws InvalidInputException {

		boolean success = true;

		if (bankDto.getBankAccountNo().trim().equals("") || bankDto.getBankAccountType().trim().equals("")
				|| bankDto.getAmount().trim().equals("")) {
			success = false;
			throw new InvalidInputException("Bank Account No/Bank Account Type/Amount cannot be empty!");
		}

		Set<String> validBankAccountTypes = new HashSet<>();
		validBankAccountTypes.add("Checkings");
		validBankAccountTypes.add("Savings");

		if (!validBankAccountTypes.contains(bankDto.getBankAccountType())) {
			success = false;
			throw new InvalidInputException("Bank Account Type must be either 'Checkings' OR 'Savings'!");
		}
		if (bankDto.getAmount().matches("[a-zA-Z]+")) {
			success = false;
			throw new InvalidInputException("Amount can only be of int or double value!");
		}
		if (Double.parseDouble(bankDto.getAmount()) <= 0) {
			success = false;
			throw new InvalidInputException("Amount must be greater than 0 only!");
		}

		return success;
	}

	public BankAccounts addBankAccount(String id, AddOrUpdateBankAccountDTO bankDto)
			throws SQLException, InvalidInputException {

		if (validateFields(bankDto)) {

			int clientsId = Integer.parseInt(id);
			BankAccounts bankAccounts = this.bankAccountsDao.insertBankAccount(clientsId, bankDto);
			
			return bankAccounts;
		}
		return null;
		
	}

	public BankAccounts getBankAccount(String cid, String accId)
			throws SQLException, BankAccountNotFoundException, InvalidInputException {

		try {
			
			int bankId = Integer.parseInt(accId);
			int clientId = Integer.parseInt(cid);

			BankAccounts getBankAccountByIds = this.bankAccountsDao.selectBankAccountsById(clientId, bankId);

			if (getBankAccountByIds == null) {
				throw new BankAccountNotFoundException(
						"Client " + clientId + " doesn't have any existing bank account id of " + bankId);
			}

			return getBankAccountByIds;

		} catch (NumberFormatException e) {
			throw new InvalidInputException("Entered id cannot be converted to int value! ");
		}

	}

//	public List<JoinTableForClientAndBankAccountDTO> getAccountsById(String clientId) throws SQLException {
//
//		int clientsId = Integer.parseInt(clientId);
//
//		return this.bankAccountsDao.selectAccountsById(clientsId);
//	}

	public List<JoinTableForClientAndBankAccountDTO> getAccountsWithSpecificAmount(String clientId,
			String amountGreaterThan, String amountLessThan) throws SQLException, InvalidInputException {

		try {

			int clientsId = Integer.parseInt(clientId);
	
			List<JoinTableForClientAndBankAccountDTO> bankAccount;

			if (amountGreaterThan != null && amountLessThan != null) {
				int amountGreater = Integer.parseInt(amountGreaterThan);
				int amountLess = Integer.parseInt(amountLessThan);
				bankAccount = this.bankAccountsDao.selectAccountsWithSpecificAmount(clientsId, amountGreater, amountLess);
			} else {
				bankAccount = this.bankAccountsDao.selectAccountsWithSpecificAmount(clientsId, 0, 1000000);
			}
			return bankAccount;
		} catch (NumberFormatException e) {
			throw new InvalidInputException("Amount Greater Than or Amount Less Than must be a convertable int type!");
		}
	}

	public BankAccounts editBankAccount(String cId, String accId, AddOrUpdateBankAccountDTO bankDto)
			throws InvalidInputException, SQLException {

		if (validateFields(bankDto)) {

			int clientId = Integer.parseInt(cId);
			int accountId = Integer.parseInt(accId);

			BankAccounts bankAccounts = this.bankAccountsDao.updateBankAccount(clientId, accountId, bankDto);

			return bankAccounts;
		} else {
			return null;
		}
	}

	public void removeBankAccount(String cId, String accId) throws SQLException, InvalidInputException {

		try {

			int clientId = Integer.parseInt(cId);
			int accountId = Integer.parseInt(accId);

			this.bankAccountsDao.deleteBankAccount(clientId, accountId);

		} catch (NumberFormatException e) {
			throw new InvalidInputException("Amount can only be of int or double value!");
		}
	}

}
