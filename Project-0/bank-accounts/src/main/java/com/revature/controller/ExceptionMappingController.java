package com.revature.controller;

import java.sql.SQLException;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.dto.ExceptionMessageDTO;
import com.revature.exceptions.BankAccountNotFoundException;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.exceptions.InvalidInputException;

import io.javalin.Javalin;

public class ExceptionMappingController {

	public void mapExceptions(Javalin app) {
		app.exception(BankAccountNotFoundException.class, (e, ctx) -> {
			ctx.status(404);
			ctx.json(new ExceptionMessageDTO(e));
		});

		app.exception(ClientNotFoundException.class, (e, ctx) -> {
			ctx.status(404);
			ctx.json(new ExceptionMessageDTO(e));
		});

		app.exception(InvalidInputException.class, (e, ctx) -> {
			ctx.status(400);
			ctx.json(new ExceptionMessageDTO(e));
		});
		
		app.exception(MismatchedInputException.class, (e, ctx) -> {
			ctx.status(400);
			ctx.json(new ExceptionMessageDTO(e));
		});
		
		app.exception(SQLException.class, (e, ctx) -> {
			ctx.status(400);
			ctx.json(new ExceptionMessageDTO(e));
		});
	}

}
