package com.finalproject.StayFinderApi.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalproject.StayFinderApi.dto.AccountLogin;
import com.finalproject.StayFinderApi.dto.AccountProfile;
import com.finalproject.StayFinderApi.dto.AccountReq;
import com.finalproject.StayFinderApi.entity.Account;
import com.finalproject.StayFinderApi.entity.AccountStatusEnum;
import com.finalproject.StayFinderApi.entity.Position;
import com.finalproject.StayFinderApi.entity.PositionNameEnum;
import com.finalproject.StayFinderApi.repository.AccountRepository;
import com.finalproject.StayFinderApi.repository.PositionRepository;
import com.finalproject.StayFinderApi.service.IAccountService;

@Service
public class AccountServiceImpl implements IAccountService{

	@Autowired
	private AccountRepository accountRepo;
	
	@Autowired
	private PositionRepository positionRepo;

	@Override
	public Account addAccount(AccountReq newAccount) {
		if(accountRepo.existsByUsername(newAccount.getUsername())){
			throw new RuntimeException("Exists username " + newAccount.getUsername());
		}
		
		Account account = new Account();
		account.setAvatar(newAccount.getAvatar());
		account.setUsername(newAccount.getUsername());
		account.setPassword(newAccount.getPassword());
		account.setName(newAccount.getName());
		account.setStatus(AccountStatusEnum.ENABLE.getValue());
		Position position = new Position();
		Optional<Position> optional = positionRepo.findById((long)1);
		if(optional.isPresent())
			position = optional.get();
		else {
			throw new RuntimeException("Account not set!");
		}
		account.setPosition(position);
		return accountRepo.save(account);
	}

	@Override
	public Account updateAccountProfile(AccountProfile newAccount) {
		Optional<Account> optional = accountRepo.findByUsername(newAccount.getUsername());
		if(optional.isPresent())
		{
			Account account = optional.get();
			account.setAvatar(newAccount.getAvatar());
			account.setName(newAccount.getName());
			account.setPhonenumber(newAccount.getPhonenumber());
			account.setGender(newAccount.isGender());
			return accountRepo.save(account);
		}
		else {
			throw new RuntimeException("Can't find Account by username");
		}
		
		
//		
//		Account account = accountRepo.getAccountByUserName(newAccount.getUsername());
//		account.setAvatar(newAccount.getAvatar());
//		account.setName(newAccount.getName());
//		account.setPhonenumber(newAccount.getPhonenumber());
//		account.setPassword(newAccount.getPassword());
//		account.setGender(newAccount.isGender());
////		account.setPosition(newAccount.getPosition());
		
		
		
	}

	@Override
	public void deleteAccount(String username) {
		Account account = accountRepo.getAccountByUserName(username);
		account.setStatus(AccountStatusEnum.DISTABLE.getValue());
		accountRepo.save(account);
	}

	@Override
	public List<Account> getAllAccount() {
		return accountRepo.findAll();
	}
	

	@Override
	public Account getOneAccount(Long id) {
		Optional<Account> optional = accountRepo.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	@Override
	public Account checkLogin(AccountLogin accountReq) {
		Boolean isNewAccount = accountRepo.existsByUsername(accountReq.getUsername());
		if (isNewAccount) {
			Account account = accountRepo.getAccountByUserName(accountReq.getUsername());
			if(account.getPassword().equals(accountReq.getPassword()))
				return account;
			else {
				throw new RuntimeException("Wrong password!");
			}
		}
		throw new RuntimeException("Account not available !");
	}

	@Override
	public Account getAccountByUsername(String username) {
		Optional<Account> optional = accountRepo.findByUsername(username);
		if(optional.isPresent()) {
			return optional.get();
		}
		throw new RuntimeException("Can't find account by username: " + username);
	}

	@Override
	public boolean checkUsernameAvailability(String username) {
		
		return accountRepo.existsByUsername(username);
		
	}

	@Override
	public List<Account> getDisableAccount() {
		return accountRepo.findAll()
				.stream()
				.filter(item -> item.getStatus() == AccountStatusEnum.DISTABLE.getValue()).collect(Collectors.toList());
	}

	@Override
	public List<Account> getEnableAccount() {
		return accountRepo.findAll()
				.stream()
				.filter(item -> item.getStatus() == AccountStatusEnum.ENABLE.getValue()).collect(Collectors.toList());
	}

	public Boolean changePassword(String username, String password) {
		Optional<Account> optional = accountRepo.findByUsername(username);
		if(optional.isPresent()) {
			Account account = optional.get();
			account.setPassword(password);
			accountRepo.save(account);
			return true;
		}
		else {
			throw new RuntimeException("Can't find account by username: " + username);
		}
	}

	@Override
	public Boolean enableAccount(String username) {
		Optional<Account> optional = accountRepo.findByUsername(username);
		if(optional.isPresent()) {
			Account account = optional.get();
			account.setStatus(AccountStatusEnum.ENABLE.getValue());
			accountRepo.save(account);
			return true;
		}
		throw new RuntimeException("Can't find Account by username " + username);
	}
	
	@Override
	public Boolean disableAccount(String username) {
		Optional<Account> optional = accountRepo.findByUsername(username);
		if(optional.isPresent()) {
			Account account = optional.get();
			account.setStatus(AccountStatusEnum.DISTABLE.getValue());
			accountRepo.save(account);
			return true;
		}
		return false;
	}

	@Override
	public Account giveAdmin(String username) {
		Optional<Account> optional = accountRepo.findByUsername(username);
		if(optional.isPresent()) {
			Account account = optional.get();
			account.setPosition(positionRepo.findByPositionName(PositionNameEnum.ROLE_ADMIN).get());
			return accountRepo.save(account);
		}
		throw new RuntimeException("Can't find account by username: " + username);
	}
}
