package com.revature.controller;

import com.revature.dto.AddOrUpdateBankAccountDTO;
import com.revature.dto.AddOrUpdateClientDTO;
//import com.revature.exceptions.BankAccountNotFoundException;
//import com.revature.exceptions.ClientNotFoundException;
//import com.revature.exceptions.InvalidInputException;
import com.revature.models.BankAccounts;
import com.revature.models.Clients;
import com.revature.service.BankAccountsService;
import com.revature.service.ClientsService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class ClientsController {

	public ClientsService clientsService;
	public BankAccountsService bankAccountsService;

	public ClientsController() {

		this.clientsService = new ClientsService();
		this.bankAccountsService = new BankAccountsService();
	}

	// ----------------------------------- Client Information Related
	// ----------------------------------

	public Handler clients = (ctx) -> {

		AddOrUpdateClientDTO addDto = ctx.bodyAsClass(AddOrUpdateClientDTO.class);

		Clients c = this.clientsService.addNewClient(addDto);
		ctx.status(201);
		ctx.json(c);

	};

	public Handler getAllClients = (ctx) -> {

		ctx.json(this.clientsService.getAllClients());

	};

	public Handler getClientById = (ctx) -> {

		String id = ctx.pathParam("client_id");

		Clients c = this.clientsService.getClientById(id);
		ctx.json(c);
	};

	public Handler updateClientsById = (ctx) -> {

		String id = ctx.pathParam("client_id");

		AddOrUpdateClientDTO dto = ctx.bodyAsClass(AddOrUpdateClientDTO.class);

		Clients clientThatNeedsToBeUpdated = this.clientsService.modifyClientsById(id, dto);

		ctx.json(clientThatNeedsToBeUpdated);
	};

	public Handler deleteClientById = (ctx) -> {

		String id = ctx.pathParam("client_id");
		if (this.clientsService.removeClientById(id)) {
			ctx.result("Client with an id of " + id + " has been deleted");
		}

	};

	// -------------------------------------- Bank Account Related
	// -----------------------------------

	public Handler newAccountForAClient = (ctx) -> {

		String id = ctx.pathParam("client_id");

		if (this.clientsService.getClientById(id) != null) {

			AddOrUpdateBankAccountDTO bankDto = ctx.bodyAsClass(AddOrUpdateBankAccountDTO.class);

			BankAccounts bankAccount = this.bankAccountsService.addBankAccount(id, bankDto);
			
			ctx.json(bankAccount);
		}
	};

	public Handler getAllAccountsWithSpecificAmountOrAllAccounts = (ctx) -> {

		String clientId = ctx.pathParam("client_id");

		String amountGreaterThan = ctx.queryParam("amountGreaterThan");
		String amountLessThan = ctx.queryParam("amountLessThan");

		if (this.clientsService.getClientById(clientId) != null) {
			
				ctx.json(this.bankAccountsService.getAccountsWithSpecificAmount(clientId, amountGreaterThan,
						amountLessThan));
		}
	};

	public Handler getASpecificAccountOfAClient = (ctx) -> {

		String clientId = ctx.pathParam("client_id");
		String accountId = ctx.pathParam("account_id");

		if (this.clientsService.getClientById(clientId) != null) {
			ctx.json(this.bankAccountsService.getBankAccount(clientId, accountId));
		}
	};

	public Handler updateBankAccountByClientAndAccountId = (ctx) -> {

		String clientId = ctx.pathParam("client_id");
		String accountId = ctx.pathParam("account_id");

		if (this.clientsService.getClientById(clientId) != null) {
			if (this.bankAccountsService.getBankAccount(clientId, accountId) != null) {

				AddOrUpdateBankAccountDTO bankDto = ctx.bodyAsClass(AddOrUpdateBankAccountDTO.class);

				BankAccounts updatedBankAccount = this.bankAccountsService.editBankAccount(clientId, accountId,
						bankDto);

				ctx.json(updatedBankAccount);
			}
		}
	};

	public Handler removeBankAccountByClientAndAccountId = (ctx) -> {

		String clientId = ctx.pathParam("client_id");
		String accountId = ctx.pathParam("account_id");

		if (this.clientsService.getClientById(clientId) != null) {
			if (this.bankAccountsService.getBankAccount(clientId, accountId) != null) {
				this.bankAccountsService.removeBankAccount(clientId, accountId);
				ctx.result("The account with an id of " + accountId + " that belongs to client " + clientId
						+ " has been deleted.");
			}
		}

	};

	public void registerEndpoint(Javalin app) {
		// ------------------------------ Client Information Related
		// -----------------------------------
		app.post("/clients", clients);
		app.get("/clients", getAllClients);
		app.get("/clients/{client_id}", getClientById);
		app.put("/clients/{client_id}", updateClientsById);
		app.delete("/clients/{client_id}", deleteClientById);

		// --------------------------------- Bank Account Related
		// --------------------------------------
		app.post("/clients/{client_id}/accounts", newAccountForAClient);
		app.get("/clients/{client_id}/accounts", getAllAccountsWithSpecificAmountOrAllAccounts);
		app.get("/clients/{client_id}/accounts/{account_id}", getASpecificAccountOfAClient);
		app.put("/clients/{client_id}/accounts/{account_id}", updateBankAccountByClientAndAccountId);
		app.delete("/clients/{client_id}/accounts/{account_id}", removeBankAccountByClientAndAccountId);

	}
}
