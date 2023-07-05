package br.com.zensolutions.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import br.com.zensolutions.data.vo.v1.security.AccountCredentialsVO;
import br.com.zensolutions.services.AuthServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	AuthServices authServices;

	@SuppressWarnings("rawtypes")
	@Operation(summary = "Authenticates a user and return a token")
	@PostMapping(value = "/signin")
	public ResponseEntity signin(@RequestBody @Validated AccountCredentialsVO data) {
		var token = authServices.signin(data);
		if (token == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid client request!");
		return token;

	}
	@SuppressWarnings("rawtypes")
	@Operation(summary = "Refresh token for authenticated user and return a token")
	@PutMapping(value = "/refresh/{username}")
	public ResponseEntity refreshToken(@PathVariable("username") String username,
									   @RequestHeader("Authorization") String refreshToken) {
		var token = authServices.refreshToken(username,refreshToken);

		if(refreshToken == null || refreshToken.isBlank()){
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		}
		if (token == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid client request!");
		return token;

	}
}
