package com.mitrais.rms.controller.commons.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
	value = HttpStatus.NOT_FOUND,
	reason = "No such Location"
)
public class LocationNotFoundException
	extends RuntimeException
{
	// TODO detail implementation
}