package com.finalproject.StayFinderApi.service;

import java.util.List;

import com.finalproject.StayFinderApi.entity.Account;



public interface IAccountService {
	
	public Account addAccount(Account account);
	
	public Account updateAccount(Account account);
	
	public void deleteAccount(String username);
	
	public List<Account> getAllAccount();
	
	public Account getOneAccount(Long id);
	
	public Account checkLogin(String username, String password);
	
	public Account getAccountByUsername(String username);
	
	public boolean checkUsernameAvailability(String username);
	
	public List<Account> getDisableAccount();
	
	public List<Account> getEnableAccount();
	
}
