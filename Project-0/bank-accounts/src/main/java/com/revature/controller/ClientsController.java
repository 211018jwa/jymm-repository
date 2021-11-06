package com.revature.controller;

import com.revature.dto.AddOrUpdateClientDTO;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.exceptions.InvalidInputException;
import com.revature.models.Clients;
import com.revature.service.ClientsService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class ClientsController {

	public ClientsService clientsService;

	public ClientsController() {

		this.clientsService = new ClientsService();
	}

	public Handler clients = (ctx) -> {

//		String firstName = ctx.formParam("firstName");
//		String lastName = ctx.formParam("lastName");
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

		String id = ctx.pathParam("client_id");

		try {
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

		String id = ctx.pathParam("client_id");

//		String firstName = ctx.formParam("firstName");
//		String lastName = ctx.formParam("lastName");
		try {
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

//		if (this.clientsService.modifyClientsById(clients_id, firstName, lastName) {
//			ctx.result("success");
//		}

	};

	public Handler deleteClientById = (ctx) -> {

		String id = ctx.pathParam("client_id");

		if (this.clientsService.removeClientById(id)) {
			ctx.result("success");
		}

	};

	public void registerEndpoint(Javalin app) {
		app.post("/clients", clients);
		app.get("/clients", getAllClients);
		app.get("/clients/{client_id}", getClientById);
		app.put("/clients/{client_id}", updateClientsById);
		app.delete("/clients/{client_id}", deleteClientById);
	}
}
