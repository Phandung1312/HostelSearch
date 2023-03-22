package com.finalproject.StayFinderApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.StayFinderApi.dto.AccountReq;
import com.finalproject.StayFinderApi.entity.Account;
import com.finalproject.StayFinderApi.service.IAccountService;

@RestController
@RequestMapping("/api/account")
public class AccountController {

	@Autowired
	private IAccountService accountService;
	
	@GetMapping
	public List<Account> getAll(){
		return accountService.getAllAccount();
	}
	
	@GetMapping("/disable")
	public List<Account> getDistableAccounts(){
		return accountService.getDisableAccount();
	}
	
	@GetMapping("/enable")
	public List<Account> getEnableAccounts(){
		return accountService.getEnableAccount();
	}
	
	@GetMapping("/{username}")
	public Account getAccountByUsername(@PathVariable String username){
		return accountService.getAccountByUsername(username);
	}
	
	@PostMapping
	public Account addAccount(@RequestBody Account account) {
		return accountService.addAccount(account);
	}
	
	@PutMapping
	public Account updateAccount(@RequestBody Account account) {
		return accountService.updateAccount(account);
	}
	
	@DeleteMapping("/{username}")
	public void deleteAccount(@PathVariable String username ) {
		 accountService.deleteAccount(username);
	}
	
	@PostMapping("/login")
	public Account login(@RequestBody AccountReq accountReq) {
		return accountService.checkLogin(accountReq.getUsername(), accountReq.getPassword());
	}
	
}
