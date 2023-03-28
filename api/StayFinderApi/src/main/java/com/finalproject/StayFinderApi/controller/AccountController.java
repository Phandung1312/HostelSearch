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

import com.finalproject.StayFinderApi.dto.AccountLogin;
import com.finalproject.StayFinderApi.dto.AccountProfile;
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
	public Account addAccount(@RequestBody AccountReq account) {
		return accountService.addAccount(account);
	}
	
	@PutMapping
	public Account updateAccount(@RequestBody AccountProfile account) {
		return accountService.updateAccountProfile(account);
	}
	
	@DeleteMapping("/{username}")
	public void deleteAccount(@PathVariable String username ) {
		 accountService.deleteAccount(username);
	}
	
	@PostMapping("/login")
	public Account login(@RequestBody AccountLogin accountLogin) {
		return accountService.checkLogin(accountLogin);
	}
	
	@PutMapping("/enable/{username}")
	public Boolean enableAccount(@PathVariable String username) {
		return accountService.enableAccount(username);
	}
	@PutMapping("/disable/{username}")
	public Boolean disableAccount(@PathVariable String username) {
		return accountService.enableAccount(username);
	}
	
	@PutMapping("/admin/{username}")
	public Account giveAdmin(@PathVariable String username) {
		return accountService.giveAdmin(username);
	}
	
}
