package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.dto.AddOrUpdateBankAccountDTO;
import com.revature.dto.JoinTableForClientAndBankAccountDTO;
import com.revature.models.BankAccounts;
import com.revature.util.JDBCUtility;

public class BankAccountsDAO {

	public BankAccounts insertBankAccount(int clientsId, AddOrUpdateBankAccountDTO bankDto) throws SQLException {
		
		try (Connection con = JDBCUtility.getConnection()) {
			
			String sql = "INSERT INTO \r\n"
					+ "	bank_accounts (client_id, bank_account_no, bank_account_type, amount)\r\n"
					+ "VALUES\r\n"
					+ "	(?, ?, ?, ?);";
			PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			ps.setInt(1, clientsId);
			ps.setString(2, bankDto.getBankAccountNo());
			ps.setString(3, bankDto.getBankAccountType());
			ps.setDouble(4, Double.parseDouble(bankDto.getAmount()));
			
			ps.execute();
			
			ResultSet rs = ps.getGeneratedKeys();
			
			rs.next();
			int autoGeneratedKey = rs.getInt(1);
			
			return new BankAccounts (autoGeneratedKey, clientsId, bankDto.getBankAccountNo(), bankDto.getBankAccountType(), bankDto.getAmount());			
		} 		
	}

	public List<JoinTableForClientAndBankAccountDTO> selectAccountsById(int clientsId) throws SQLException {
		
		List<JoinTableForClientAndBankAccountDTO> bankAccountsList = new ArrayList<>();
		
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT c.client_id, c.client_first_name, c.client_last_name, b.bank_account_no, b.bank_account_type, b.amount\r\n"
					+ "FROM clients c\r\n"
					+ "INNER JOIN bank_accounts b\r\n"
					+ "ON c.client_id = b.client_id\r\n"
					+ "WHERE\r\n"
					+ "c.client_id = ?;";
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setInt(1, clientsId);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				bankAccountsList.add(new JoinTableForClientAndBankAccountDTO(rs.getInt("client_id"), rs.getString("client_first_name"),
						rs.getString("client_last_name"), rs.getString("bank_account_no"), rs.getString("bank_account_type"),
						rs.getString("amount")));
			} 
			
			return bankAccountsList;
		}
		
		
		
	}

	
	public List<JoinTableForClientAndBankAccountDTO> selectAccountsWithSpecificAmount(int clientsId, int amountGreater,
			int amountLess) throws SQLException {
		
		List<JoinTableForClientAndBankAccountDTO> bankAccountsList = new ArrayList<>();
		
		try(Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT c.client_id, c.client_first_name, c.client_last_name, b.bank_account_no, b.bank_account_type, b.amount\r\n"
					+ "FROM clients c\r\n"
					+ "INNER JOIN bank_accounts b\r\n"
					+ "ON c.client_id = b.client_id\r\n"
					+ "WHERE\r\n"
					+ "c.client_id = ? AND b.amount BETWEEN ? AND ?;";
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setInt(1, clientsId);
			ps.setInt(2, amountGreater);
			ps.setInt(3, amountLess);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				bankAccountsList.add(new JoinTableForClientAndBankAccountDTO(rs.getInt("client_id"), rs.getString("client_first_name"),
						rs.getString("client_last_name"), rs.getString("bank_account_no"), rs.getString("bank_account_type"),
						rs.getString("amount")));
			} 
			
		}

		return bankAccountsList;
	}

	public BankAccounts selectBankAccountsById(int clientId, int bankId) throws SQLException {
		
		try (Connection con = JDBCUtility.getConnection()) {
			
			String sql = "SELECT *\r\n"
					+ "FROM bank_accounts\r\n"
					+ "WHERE\r\n"
					+ "bank_id = ? AND client_id = ?;";
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setInt(1, bankId);
			ps.setInt(2, clientId);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				return new BankAccounts(rs.getInt("bank_id"), rs.getInt("client_id"), rs.getString("bank_account_no"), rs.getString("bank_account_type"),
						rs.getString("amount"));
			}
			return null;
		}

	}

	public BankAccounts updateBankAccount(int clientId, int accountId, AddOrUpdateBankAccountDTO bankDto) throws SQLException {
		
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "UPDATE bank_accounts\r\n"
					+ "SET bank_account_no = ?, bank_account_type = ?, amount = ?\r\n"
					+ "WHERE\r\n"
					+ "client_id = ? AND bank_id = ?;";
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, bankDto.getBankAccountNo());
			ps.setString(2, bankDto.getBankAccountType());
			ps.setDouble(3, Double.parseDouble(bankDto.getAmount()));
			ps.setInt(4, clientId);
			ps.setInt(5, accountId);
			
			int i = ps.executeUpdate();

			if (i != 1) {
				throw new SQLException("Unable to update a client with a client id of "+ clientId + " and account id of " +accountId);	
			} 
			
		}
		
		return new BankAccounts(accountId, clientId, bankDto.getBankAccountNo(), bankDto.getBankAccountType(), bankDto.getAmount());
	}

	public void deleteBankAccount(int clientId, int accountId) throws SQLException {

		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "DELETE FROM bank_accounts \r\n"
					+ "WHERE client_id = ? AND bank_id = ?;";
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setInt(1, clientId);
			ps.setInt(2, accountId);
			
			
		}
	}

}
