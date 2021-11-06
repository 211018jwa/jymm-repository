package com.revature.controller;

import com.revature.dto.AddOrUpdateBankAccountDTO;
import com.revature.dto.AddOrUpdateClientDTO;
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
	
	public void registerEndpoint(Javalin app) {
		app.post("/clients", clients);
		app.get("/clients", getAllClients);
		app.get("/clients/{client_id}", getClientById);
		app.put("/clients/{client_id}", updateClientsById);
		app.delete("/clients/{client_id}", deleteClientById);
		app.post("/clients/{client_id}/accounts", newAccountForAClient);
	}
}
