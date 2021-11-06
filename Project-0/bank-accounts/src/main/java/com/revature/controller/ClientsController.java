package com.revature.controller;

import com.revature.dto.AddOrUpdateBankAccountDTO;
import com.revature.dto.AddOrUpdateClientDTO;
import com.revature.dto.JoinTableForClientAndBankAccountDTO;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.exceptions.InvalidInputException;
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

		try {
			AddOrUpdateClientDTO addDto = ctx.bodyAsClass(AddOrUpdateClientDTO.class);

			Clients c = this.clientsService.addNewClient(addDto);

			ctx.json(c);
			ctx.status(201);
		} catch (InvalidInputException e) {
			ctx.status(400);
			ctx.json(e);
		}

	};

	public Handler getAllClients = (ctx) -> {

		ctx.json(this.clientsService.getAllClients());

	};

	public Handler getClientById = (ctx) -> {

		try {
			String id = ctx.pathParam("client_id");

			Clients c = this.clientsService.getClientById(id);
			ctx.json(c);

		} catch (InvalidInputException e) {
			ctx.status(400);
			ctx.json(e);
		} catch (ClientNotFoundException e) {
			ctx.status(404);
			ctx.json(e);
		}

	};

	public Handler updateClientsById = (ctx) -> {

		try {
			String id = ctx.pathParam("client_id");
			AddOrUpdateClientDTO dto = ctx.bodyAsClass(AddOrUpdateClientDTO.class);

			Clients clientThatNeedsToBeUpdated = this.clientsService.modifyClientsById(id, dto);

			ctx.json(clientThatNeedsToBeUpdated);

		} catch (InvalidInputException e) {
			ctx.status(400);
			ctx.json(e);
		} catch (ClientNotFoundException e) {
			ctx.status(404);
			ctx.json(e);
		}

	};

	public Handler deleteClientById = (ctx) -> {

		try {
			String id = ctx.pathParam("client_id");
			if (this.clientsService.removeClientById(id)) {
				ctx.result("Client with an id of " + id + " has been deleted");
			}

		} catch (InvalidInputException e) {
			ctx.status(400);
			ctx.json(e);
		} catch (ClientNotFoundException e) {
			ctx.status(404);
			ctx.json(e);
		}

	};

	// -------------------------------------- Bank Account Related
	// -----------------------------------

	public Handler newAccountForAClient = (ctx) -> {

		String id = ctx.pathParam("client_id");

		if (this.clientsService.getClientById(id) != null) {
			try {
				AddOrUpdateBankAccountDTO bankDto = ctx.bodyAsClass(AddOrUpdateBankAccountDTO.class);

				BankAccounts bankAccount = this.bankAccountsService.addBankAccount(id, bankDto);

				ctx.json(bankAccount);

			} catch (NumberFormatException e) {
				ctx.status(400);
				ctx.json(e);
			} catch (InvalidInputException e) {
				ctx.status(400);
				ctx.json(e);
			}
		}

	};

	public Handler viewAccountOfAClient = (ctx) -> {
		try {
			String clientId = ctx.pathParam("client_id");

			if (this.clientsService.getClientById(clientId) != null) {
				ctx.json(this.bankAccountsService.getAccountsById(clientId));
			}

		} catch (ClientNotFoundException e) {
			ctx.status(404);
			ctx.json(e);
		}

	};

	public Handler getAllAccountsWithSpecificAmount = (ctx) -> {
		
		String clientId = ctx.pathParam("client_id");
		
		String amountGreaterThan = ctx.queryParam("amountGreaterThan");
		String amountLessThan = ctx.queryParam("amountLessThan");
		
		if (this.clientsService.getClientById(clientId) != null) {
			
			ctx.json(this.bankAccountsService.getAccountsWithSpecificAmount(clientId, amountGreaterThan, amountLessThan));
		}
		
		
	};
	
	public void registerEndpoint(Javalin app) {
		// --------------- Client Information Related -----------------
		app.post("/clients", clients);
		app.get("/clients", getAllClients);
		app.get("/clients/{client_id}", getClientById);
		app.put("/clients/{client_id}", updateClientsById);
		app.delete("/clients/{client_id}", deleteClientById);
		// ------------------ Bank Account Related --------------------
		app.post("/clients/{client_id}/accounts", newAccountForAClient);
		app.get("/clients/{client_id}/accounts", viewAccountOfAClient);
		app.get("GET /clients/{client_id}/accounts?"
				+ "amountLessThan=2000&amountGreaterThan=400", getAllAccountsWithSpecificAmount);
	}
}
