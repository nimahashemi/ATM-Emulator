package com.egs.atmemulator.service.impl;

import com.egs.atmemulator.dto.AccountDTO;
import com.egs.atmemulator.enums.AccountStatus;
import com.egs.atmemulator.exceptions.BadRequestException;
import com.egs.atmemulator.exceptions.NotAcceptableException;
import com.egs.atmemulator.model.Account;
import com.egs.atmemulator.model.User;
import com.egs.atmemulator.repository.AccountRepository;
import com.egs.atmemulator.service.AccountService;
import com.egs.atmemulator.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    private AccountRepository accountRepository;
    private UserService userService;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository,
                              UserService userService) {
        this.accountRepository = accountRepository;
        this.userService = userService;
    }

    /**
     *
     * @return
     */
    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Account findById(Long id) {
        Optional<Account> optional = accountRepository.findById(id);
        if (!optional.isPresent()) throw new NotAcceptableException("Account not found");
        return optional.get();
    }

    /**
     *
     * @param object
     * @return
     */
    @Override
    public Account add(Account object) {
        int accLength =String.valueOf(object.getAccountNumber()).length();
        if (accLength <= 6 && accLength >= 16)
            throw new BadRequestException(111, "Invalid account number");

        Account account = accountRepository.save(object);
        return account;
    }

    /**
     *
     * @param object
     * @return
     */
    @Override
    public Account update(Account object) {
        int accLength =String.valueOf(object.getAccountNumber()).length();
        if (accLength <= 6 && accLength >= 16)
            throw new BadRequestException(111, "Invalid account number");

        Account account = accountRepository.save(object);
        return account;
    }

    /**
     *
     * @param accountId
     * @param account
     * @return
     */
    @Override
    public Account update(Long accountId, Account account){
        int accLength =String.valueOf(account.getAccountNumber()).length();
        if (accLength <= 6 && accLength >= 16)
            throw new BadRequestException(111, "Invalid account number");
        Optional<Account> optional = accountRepository.findById(accountId);
        if (!optional.isPresent()) throw new NotAcceptableException("Account not found");

        Account object = accountRepository.save(account);
        return object;
    }

    /**
     *
     * @param object
     */
    @Override
    public void delete(Account object) {
        Account account = object;
        account.setStatus(AccountStatus.DELETED);
        accountRepository.save(account);
    }

    /**
     *
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        Optional<Account> optional = accountRepository.findById(id);
        if (!optional.isPresent()) throw new NotAcceptableException("Account not found");
        Account account = optional.get();
        account.setStatus(AccountStatus.DELETED);
        accountRepository.save(account);
    }

    /**
     *
     * @param object
     * @return
     */
    @Override
    public List<Account> search(Account object) {
        return accountRepository.search(object.getUserId(),
                object.getAccountType(),
                object.getAccountNumber(),
                object.getBalance(),
                object.getStatus());
    }

    /**
     *
     * @param accountDTO
     * @return
     */
    @Override
    public List<Account> inquiry(AccountDTO accountDTO) {
        User user = userService.findById(accountDTO.getUserId());

        return accountRepository.search(user.getId(),
                accountDTO.getAccountType(),
                accountDTO.getAccountNumber(),
                accountDTO.getBalance(),
                accountDTO.getStatus());
    }
}
