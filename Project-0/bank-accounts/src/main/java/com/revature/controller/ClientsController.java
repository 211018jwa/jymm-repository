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

		//try {
			AddOrUpdateClientDTO addDto = ctx.bodyAsClass(AddOrUpdateClientDTO.class);

			Clients c = this.clientsService.addNewClient(addDto);
			ctx.status(201);
			ctx.json(c);
			
//		} catch (InvalidInputException e) {
//			ctx.status(400);
//			ctx.json(e);
//		}

	};

	public Handler getAllClients = (ctx) -> {

		ctx.json(this.clientsService.getAllClients());

	};

	public Handler getClientById = (ctx) -> {

//		try {
			String id = ctx.pathParam("client_id");

			Clients c = this.clientsService.getClientById(id);
			ctx.json(c);

//		} catch (InvalidInputException e) {
//			ctx.status(400);
//			ctx.json(e);
//		} catch (ClientNotFoundException e) {
//			ctx.status(404);
//			ctx.json(e);
//		}

	};

	public Handler updateClientsById = (ctx) -> {

//		try {
			String id = ctx.pathParam("client_id");
			
			AddOrUpdateClientDTO dto = ctx.bodyAsClass(AddOrUpdateClientDTO.class);

			Clients clientThatNeedsToBeUpdated = this.clientsService.modifyClientsById(id, dto);

			ctx.json(clientThatNeedsToBeUpdated);

//		} catch (InvalidInputException e) {
//			ctx.status(400);
//			ctx.json(e);
//		} catch (ClientNotFoundException e) {
//			ctx.status(404);
//			ctx.json(e);
//		}

	};

	public Handler deleteClientById = (ctx) -> {

//		try {
			String id = ctx.pathParam("client_id");
			if (this.clientsService.removeClientById(id)) {
				ctx.result("Client with an id of " + id + " has been deleted");
			}

//		} catch (InvalidInputException e) {
//			ctx.status(400);
//			ctx.json(e);
//		} catch (ClientNotFoundException e) {
//			ctx.status(404);
//			ctx.json(e);
//		}

	};

	// -------------------------------------- Bank Account Related
	// -----------------------------------

	public Handler newAccountForAClient = (ctx) -> {

//		try {
			String id = ctx.pathParam("client_id");

			if (this.clientsService.getClientById(id) != null) {

				AddOrUpdateBankAccountDTO bankDto = ctx.bodyAsClass(AddOrUpdateBankAccountDTO.class);

				BankAccounts bankAccount = this.bankAccountsService.addBankAccount(id, bankDto);
				ctx.json(bankAccount);

			}

//		} catch (InvalidInputException e) {
//			ctx.status(400);
//			ctx.json(e);
//		} catch (ClientNotFoundException e) {
//			ctx.status(404);
//			ctx.json(e);
//		}

	};

//	public Handler viewAccountOfAClient = (ctx) -> {
//		try {
//			String clientId = ctx.pathParam("client_id");
//
//			if (this.clientsService.getClientById(clientId) != null) {
//				ctx.json(this.bankAccountsService.getAccountsById(clientId));
//			}
//
//		} catch (ClientNotFoundException e) {
//			ctx.status(404);
//			ctx.json(e);
//		}
//
//	};

	public Handler getAllAccountsWithSpecificAmountOrAllAccounts = (ctx) -> {

//		try {
			String clientId = ctx.pathParam("client_id");

			String amountGreaterThan = ctx.queryParam("amountGreaterThan");
			String amountLessThan = ctx.queryParam("amountLessThan");

			if (this.clientsService.getClientById(clientId) != null) {

				if (amountGreaterThan != null && amountLessThan != null) {
					ctx.json(this.bankAccountsService.getAccountsWithSpecificAmount(clientId, amountGreaterThan,
							amountLessThan));
				} else {
					ctx.json(this.bankAccountsService.getAccountsById(clientId));
				}
			}
//		} catch (InvalidInputException e) {
//			ctx.status(400);
//			ctx.json(e);
//		} catch (ClientNotFoundException e) {
//			ctx.status(404);
//			ctx.json(e);
//		}

	};

	public Handler getASpecificAccountOfAClient = (ctx) -> {

//		try {
			String clientId = ctx.pathParam("client_id");
			String accountId = ctx.pathParam("account_id");

			if (this.clientsService.getClientById(clientId) != null) {
				ctx.json(this.bankAccountsService.getBankAccount(clientId, accountId));

			}
//		} catch (InvalidInputException e) {
//			ctx.status(400);
//			ctx.json(e);
//		} catch (ClientNotFoundException e) {
//			ctx.status(404);
//			ctx.json(e);
//		} catch (BankAccountNotFoundException e) {
//			ctx.status(404);
//			ctx.json(e);
//		}
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
				ctx.result("The account with an id of "+accountId + " that belongs to client " +clientId+ " has been deleted.");
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
		// app.get("/clients/{client_id}/accounts", viewAccountOfAClient);
		app.get("/clients/{client_id}/accounts", getAllAccountsWithSpecificAmountOrAllAccounts);
		app.get("/clients/{client_id}/accounts/{account_id}", getASpecificAccountOfAClient);
		app.put("/clients/{client_id}/accounts/{account_id}", updateBankAccountByClientAndAccountId);
		app.delete("/clients/{client_id}/accounts/{account_id}", removeBankAccountByClientAndAccountId);

	}
}
