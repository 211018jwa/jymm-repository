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

}
