package com.revature.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.revature.dao.BankAccountsDAO;
import com.revature.dto.AddOrUpdateBankAccountDTO;
import com.revature.exceptions.BankAccountNotFoundException;
import com.revature.exceptions.InvalidInputException;
import com.revature.models.BankAccounts;

public class BankAccountsServiceTest {

	private BankAccountsService sut;

	/*
	 * BankAccountsService's validateFields() test
	 */

	// Happy Path
	@Test
	public void testValidateFieldsPositive() throws InvalidInputException {

		// ARRANGE
		BankAccountsService bankAccountsService = mock(BankAccountsService.class);

		AddOrUpdateBankAccountDTO addDto = new AddOrUpdateBankAccountDTO("1234567890", "Savings", "20000");

		when(bankAccountsService.validateFields(eq(addDto))).thenReturn(true);

		// ACT
		AddOrUpdateBankAccountDTO validateDto = new AddOrUpdateBankAccountDTO("1234567890", "Savings", "20000");

		boolean actual = bankAccountsService.validateFields(validateDto);
		boolean expected = bankAccountsService
				.validateFields(new AddOrUpdateBankAccountDTO("1234567890", "Savings", "20000"));

		// ASSERT
		Assertions.assertEquals(expected, actual);
	}

	// Sad Path
	@Test
	public void testValidateFieldsBankAccountNumberEmptyAllFieldsPositive() throws InvalidInputException {

		// ARRANGE
		BankAccountsService bankAccountsService = new BankAccountsService();

		// ACT AND ASSERT
		AddOrUpdateBankAccountDTO addDto = new AddOrUpdateBankAccountDTO(" ", "Savings", "20000");
		Assertions.assertThrows(InvalidInputException.class, () -> {
			bankAccountsService.validateFields(addDto);
		});
	}

	// Sad Path
	@Test
	public void testValidateFieldsBankAccountTypeEmptyAllFieldsPositive() throws InvalidInputException {

		// ARRANGE
		BankAccountsService bankAccountsService = new BankAccountsService();
		// ACT AND ASSERT
		AddOrUpdateBankAccountDTO addDto = new AddOrUpdateBankAccountDTO("1234567890", " ", "20000");
		Assertions.assertThrows(InvalidInputException.class, () -> {
			bankAccountsService.validateFields(addDto);
		});
	}

	// Sad Path
	@Test
	public void testValidateFieldsAmountEmptyAllFieldsPositive() throws InvalidInputException {

		// ARRANGE
		BankAccountsService bankAccountsService = new BankAccountsService();
		// ACT AND ASSERT
		AddOrUpdateBankAccountDTO addDto = new AddOrUpdateBankAccountDTO("1234567890", "Checkings", "0");
		Assertions.assertThrows(InvalidInputException.class, () -> {
			bankAccountsService.validateFields(addDto);
		});
	}

	// Sad Path
	@Test
	public void testValidateFieldsBankAccountTypeNotValid() throws InvalidInputException {

		// ARRANGE
		BankAccountsService bankAccountsService = new BankAccountsService();

		// ACT AND ASSERT
		AddOrUpdateBankAccountDTO addDto = new AddOrUpdateBankAccountDTO("1234567890", "Checkin", "1000");
		Assertions.assertThrows(InvalidInputException.class, () -> {
			bankAccountsService.validateFields(addDto);
		});

		// ACT AND ASSERT
		AddOrUpdateBankAccountDTO addDto1 = new AddOrUpdateBankAccountDTO("1234567890", "Savin", "2000");
		Assertions.assertThrows(InvalidInputException.class, () -> {
			bankAccountsService.validateFields(addDto1);

		});
	}

	// Sad Path
	@Test
	public void testValidateFieldsAmountLessThanOrEqualsZero() throws InvalidInputException {

		// ARRANGE
		BankAccountsService bankAccountsService = new BankAccountsService();

		// ACT AND ASSERT
		AddOrUpdateBankAccountDTO addDto = new AddOrUpdateBankAccountDTO("1234567890", "Checkings", "0");
		Assertions.assertThrows(InvalidInputException.class, () -> {
			bankAccountsService.validateFields(addDto);
		});
	}

	// Sad Path
	@Test
	public void testValidateFieldsAmountContainsAlphabet() throws InvalidInputException {

		// ARRANGE
		BankAccountsService bankAccountsService = new BankAccountsService();

		// ACT AND ASSERT
		AddOrUpdateBankAccountDTO addDto = new AddOrUpdateBankAccountDTO("1234567890", "Checkings", "abc");
		Assertions.assertThrows(InvalidInputException.class, () -> {
			bankAccountsService.validateFields(addDto);
		});
	}

	/*
	 * BankAccountsService's addBankAccount() test
	 */

	// Happy Path
	@Test
	public void testAddBankAccountPositive() throws SQLException, InvalidInputException {
		// ARRANGE
		BankAccountsDAO mockBankAccountsDao = mock(BankAccountsDAO.class);

		AddOrUpdateBankAccountDTO addDto = new AddOrUpdateBankAccountDTO("1234567890", "Checkings", "2000");

		when(mockBankAccountsDao.insertBankAccount(eq(1), eq(addDto)))
				.thenReturn(new BankAccounts(1, 1, "1234567890", "Checkings", "2000"));

		BankAccountsService bankAccountsService = new BankAccountsService(mockBankAccountsDao);

		// ACT
		AddOrUpdateBankAccountDTO addToDto = new AddOrUpdateBankAccountDTO("1234567890", "Checkings", "2000");
		BankAccounts actual = bankAccountsService.addBankAccount("1", addToDto);

		// ASSERT
		BankAccounts expected = (new BankAccounts(1, 1, "1234567890", "Checkings", "2000"));
		Assertions.assertEquals(expected, actual);

	}

	// Sad Path
	@Test
	public void testAddBankAccountValidateFieldsReturnsNull() throws NullPointerException {

		// ARRANGE
		BankAccountsDAO mockBankAccountsDao = mock(BankAccountsDAO.class);
		BankAccountsService bankAccountsService = new BankAccountsService(mockBankAccountsDao);

		// ACT AND ASSERT
		AddOrUpdateBankAccountDTO addToDto = null;
		Assertions.assertThrows(NullPointerException.class, () -> {
			bankAccountsService.addBankAccount("1", addToDto);
		});
	}

	/*
	 * BankAccountsService's getBankAccount() test
	 */

	// Happy Path
	@Test
	public void testGetBankAccountPositive() throws SQLException, BankAccountNotFoundException, InvalidInputException {

		// ARRANGE
		BankAccountsDAO mockBankAccountsDao = mock(BankAccountsDAO.class);

		when(mockBankAccountsDao.selectBankAccountsById(eq(1), eq(4)))
				.thenReturn(new BankAccounts(4, 1, "1234567890", "Savings", "2890"));

		BankAccountsService bankAccountsService = new BankAccountsService(mockBankAccountsDao);

		// ACT
		BankAccounts actual = bankAccountsService.getBankAccount("1", "4");

		// ASSERT
		Assertions.assertEquals(new BankAccounts(4, 1, "1234567890", "Savings", "2890"), actual);
	}

	// Sad Path
	@Test
	public void testGetBankAccountClientIdIsNotValidAccountIdIsValid() throws InvalidInputException {

		// ARRANGE
		BankAccountsDAO mockBankAccountsDao = mock(BankAccountsDAO.class);
		BankAccountsService bankAccountsService = new BankAccountsService(mockBankAccountsDao);

		// ACT AND ASSERT
		Assertions.assertThrows(InvalidInputException.class, () -> {
			bankAccountsService.getBankAccount("ab", "1");
		});
	}

	// Sad Path
	@Test
	public void testGetBankAccountClientIdIsValidAccountIdIsNotValid() throws InvalidInputException {

		// ARRANGE
		BankAccountsDAO mockBankAccountsDao = mock(BankAccountsDAO.class);
		BankAccountsService bankAccountsService = new BankAccountsService(mockBankAccountsDao);

		// ACT AND ASSERT
		Assertions.assertThrows(InvalidInputException.class, () -> {
			bankAccountsService.getBankAccount("1", "abc");
		});
	}

	// Sad Path
	@Test
	public void testGetBankAccountClientIdAndAccountIdIsNotValid() throws InvalidInputException {

		// ARRANGE
		BankAccountsDAO mockBankAccountsDao = mock(BankAccountsDAO.class);
		BankAccountsService bankAccountsService = new BankAccountsService(mockBankAccountsDao);

		// ACT AND ASSERT
		Assertions.assertThrows(InvalidInputException.class, () -> {
			bankAccountsService.getBankAccount("abd", "abc");
		});
	}

	// Sad Path
	@Test
	public void testGetBankAccountClientIdContainsWhiteSpaceAccountIdIsValid() throws InvalidInputException {

		// ARRANGE
		BankAccountsDAO mockBankAccountsDao = mock(BankAccountsDAO.class);
		BankAccountsService bankAccountsService = new BankAccountsService(mockBankAccountsDao);

		// ACT AND ASSERT
		Assertions.assertThrows(InvalidInputException.class, () -> {
			bankAccountsService.getBankAccount(" ", "abc");
		});
	}

	// Sad Path
	@Test
	public void testGetBankAccountClientIdIsValidAccountIdContainsWhiteSpace() throws InvalidInputException {

		// ARRANGE
		BankAccountsDAO mockBankAccountsDao = mock(BankAccountsDAO.class);
		BankAccountsService bankAccountsService = new BankAccountsService(mockBankAccountsDao);

		// ACT AND ASSERT
		Assertions.assertThrows(InvalidInputException.class, () -> {
			bankAccountsService.getBankAccount("1", " ");
		});
	}

	// Sad Path
	@Test
	public void testGetBankAccountClientIdAndAccountIdContainsWhiteSpace() throws InvalidInputException {

		// ARRANGE
		BankAccountsDAO mockBankAccountsDao = mock(BankAccountsDAO.class);
		BankAccountsService bankAccountsService = new BankAccountsService(mockBankAccountsDao);

		// ACT AND ASSERT
		Assertions.assertThrows(InvalidInputException.class, () -> {
			bankAccountsService.getBankAccount(" ", " ");
		});
	}

	// Sad Path
	@Test
	public void testGetBankAccountClientIdFoundButNoExistingBankAccount() throws BankAccountNotFoundException {

		// ARRANGE
		BankAccountsDAO mockBankAccountsDao = mock(BankAccountsDAO.class);
		BankAccountsService bankAccountsService = new BankAccountsService(mockBankAccountsDao);

		// ACT AND ASSERT
		Assertions.assertThrows(BankAccountNotFoundException.class, () -> {
			bankAccountsService.getBankAccount("1", "1");
		});
	}
	
}
