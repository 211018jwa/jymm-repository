package com.revature.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.revature.dao.BankAccountsDAO;
import com.revature.dto.AddOrUpdateBankAccountDTO;
import com.revature.dto.JoinTableForClientAndBankAccountDTO;
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

	/*
	 * BankAccountsService's getAccountsWithSpecificAmount() test
	 */

	// Happy Path
	@Test
	public void testGetAccountsWithSpecificAmountNoGreaterThanAndNoLessThanPositive()
			throws SQLException, InvalidInputException {

		// ARRANGE
		BankAccountsDAO mockBankAccountsDao = mock(BankAccountsDAO.class);

		JoinTableForClientAndBankAccountDTO joinClientAndBankAccount = new JoinTableForClientAndBankAccountDTO(1,
				"Jymm", "Enriquez", "1234567890", "Savings", "2000");
		JoinTableForClientAndBankAccountDTO joinClientAndBankAccount1 = new JoinTableForClientAndBankAccountDTO(1,
				"Jymm", "Enriquez", "1234567891", "Checkings", "3000");

		List<JoinTableForClientAndBankAccountDTO> clientAndBankAccountDto = new ArrayList<>();
		clientAndBankAccountDto.add(joinClientAndBankAccount);
		clientAndBankAccountDto.add(joinClientAndBankAccount1);

		when(mockBankAccountsDao.selectAccountsWithSpecificAmount(eq(1), eq(0), eq(1000000)))
				.thenReturn(clientAndBankAccountDto);
		BankAccountsService bankAccountsService = new BankAccountsService(mockBankAccountsDao);

		// ACT
		List<JoinTableForClientAndBankAccountDTO> actual = bankAccountsService.getAccountsWithSpecificAmount("1", "0",
				"1000000");

		// ASSERT
		List<JoinTableForClientAndBankAccountDTO> expected = new ArrayList<>();
		expected.add(new JoinTableForClientAndBankAccountDTO(1, "Jymm", "Enriquez", "1234567890", "Savings", "2000"));
		expected.add(new JoinTableForClientAndBankAccountDTO(1, "Jymm", "Enriquez", "1234567891", "Checkings", "3000"));
		Assertions.assertEquals(expected, actual);
	}

	// Happy Path
	@Test
	public void testGetAccountWithSpecificAmountWithGreaterThanAndLessThanPositive()
			throws SQLException, InvalidInputException {
		
		// ARRANGE
		BankAccountsDAO mockBankAccountsDao = mock(BankAccountsDAO.class);

		JoinTableForClientAndBankAccountDTO joinClientAndBankAccount = new JoinTableForClientAndBankAccountDTO(1,
				"Jymm", "Enriquez", "1234567890", "Savings", "400");
		JoinTableForClientAndBankAccountDTO joinClientAndBankAccount1 = new JoinTableForClientAndBankAccountDTO(1,
				"Jymm", "Enriquez", "1234567891", "Checkings", "2000");

		List<JoinTableForClientAndBankAccountDTO> clientAndBankAccountDto = new ArrayList<>();
		clientAndBankAccountDto.add(joinClientAndBankAccount);
		clientAndBankAccountDto.add(joinClientAndBankAccount1);

		when(mockBankAccountsDao.selectAccountsWithSpecificAmount(eq(1), eq(400), eq(2000)))
				.thenReturn(clientAndBankAccountDto);
		BankAccountsService bankAccountsService = new BankAccountsService(mockBankAccountsDao);

		// ACT
		List<JoinTableForClientAndBankAccountDTO> actual = bankAccountsService.getAccountsWithSpecificAmount("1", "400",
				"2000");

		// ASSERT
		List<JoinTableForClientAndBankAccountDTO> expected = new ArrayList<>();
		expected.add(new JoinTableForClientAndBankAccountDTO(1, "Jymm", "Enriquez", "1234567890", "Savings", "400"));
		expected.add(new JoinTableForClientAndBankAccountDTO(1, "Jymm", "Enriquez", "1234567891", "Checkings", "2000"));
		Assertions.assertEquals(expected, actual);

	}

	/*
	 * BankAccountsService's editBankAccount() test
	 */

	// Happy Path
	@Test
	public void testEditBankAccountPositive() throws SQLException, InvalidInputException {

		// ARRANGE
		BankAccountsDAO mockBankAccountsDao = mock(BankAccountsDAO.class);

		AddOrUpdateBankAccountDTO addDto = new AddOrUpdateBankAccountDTO("1234567890", "Checkings", "2000");

		when(mockBankAccountsDao.updateBankAccount(eq(1), eq(1), eq(addDto)))
				.thenReturn(new BankAccounts(1, 1, "1234567890", "Checkings", "2000"));

		BankAccountsService bankAccountsService = new BankAccountsService(mockBankAccountsDao);

		// ACT
		AddOrUpdateBankAccountDTO updateDto = new AddOrUpdateBankAccountDTO("1234567890", "Checkings", "2000");
		BankAccounts actual = bankAccountsService.editBankAccount("1", "1", updateDto);

		// ASSERT
		BankAccounts expected = new BankAccounts(1, 1, "1234567890", "Checkings", "2000");
		Assertions.assertEquals(expected, actual);

	}

	// Sad Path
	@Test
	public void testEditBankAccountClientIdNotValidAccountIdValid() throws InvalidInputException {

		// ARRANGE
		BankAccountsDAO mockBankAccountsDao = mock(BankAccountsDAO.class);
		BankAccountsService bankAccountsService = new BankAccountsService(mockBankAccountsDao);

		// ACT AND ASSERT
		AddOrUpdateBankAccountDTO updateDto = new AddOrUpdateBankAccountDTO("1234567890", "Checkings", "2000");
		Assertions.assertThrows(InvalidInputException.class, () -> {
			bankAccountsService.editBankAccount("abc", "1", updateDto);
		});
	}

	// Sad Path
	@Test
	public void testEditBankAccountClientIdValidAccountIdNotValid() throws InvalidInputException {

		// ARRANGE
		BankAccountsDAO mockBankAccountsDao = mock(BankAccountsDAO.class);
		BankAccountsService bankAccountsService = new BankAccountsService(mockBankAccountsDao);

		// ACT AND ASSERT
		AddOrUpdateBankAccountDTO updateDto = new AddOrUpdateBankAccountDTO("1234567890", "Checkings", "2000");
		Assertions.assertThrows(InvalidInputException.class, () -> {
			bankAccountsService.editBankAccount("1", "abc", updateDto);
		});
	}

	// Sad Path
	@Test
	public void testEditBankAccountBothClientIdAndAccountIdNotValid() throws InvalidInputException {

		// ARRANGE
		BankAccountsDAO mockBankAccountsDao = mock(BankAccountsDAO.class);
		BankAccountsService bankAccountsService = new BankAccountsService(mockBankAccountsDao);

		// ACT AND ASSERT
		AddOrUpdateBankAccountDTO updateDto = new AddOrUpdateBankAccountDTO("1234567890", "Checkings", "2000");
		Assertions.assertThrows(InvalidInputException.class, () -> {
			bankAccountsService.editBankAccount("abc", "abc", updateDto);
		});
	}

	// Sad Path
	@Test
	public void testEditBankAccountUpdateDTOIsNullEverythingIsValid() throws NullPointerException {

		// ARRANGE
		BankAccountsDAO mockBankAccountsDao = mock(BankAccountsDAO.class);
		BankAccountsService bankAccountsService = new BankAccountsService(mockBankAccountsDao);

		// ACT AND ASSERT
		AddOrUpdateBankAccountDTO updateDto = null;
		Assertions.assertThrows(NullPointerException.class, () -> {
			bankAccountsService.editBankAccount("1", "1", updateDto);
		});
	}

//	/*
//	 * BankAccountsService's removeBankAccount() test
//	 */
//	
//	// Happy Path
//	@Test	
//	 public void testRemoveBankAccountPositive() throws InvalidInputException, SQLException {
//		
//		//ARRANGE
//		BankAccountsService bankAccountsService = mock (BankAccountsService.class);
//		
//		doThrow(NumberFormatException.class).when(bankAccountsService).removeBankAccount(eq("0"), eq("0"));
//		doAnswer((i) -> {
//			i.getArgument(0);
//			Assertions.assertTrue();
//			return null;
//		}).when(bankAccountsService).removeBankAccount(eq("1"), eq("1"));
//		
//		
//
//	}

}
