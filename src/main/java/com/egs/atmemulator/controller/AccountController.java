package com.egs.atmemulator.controller;

import com.egs.atmemulator.dto.AccountDTO;
import com.egs.atmemulator.enums.*;
import com.egs.atmemulator.model.Account;
import com.egs.atmemulator.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/accounts", method = RequestMethod.GET, produces = {"application/json"})
    public List<Account> getAllAccounts(@RequestParam(value = "user_id", required = false) Long user_id,
                                        @RequestParam(value = "accountType", required = false) AccountType accountType,
                                        @RequestParam(value = "accountNumber", required = false) Long accountNumber,
                                        @RequestParam(value = "balance", required = false) Long balance,
                                        @RequestParam(value = "status", required = false) AccountStatus status) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUserId(user_id);
        accountDTO.setAccountType(accountType);
        accountDTO.setAccountNumber(accountNumber);
        accountDTO.setBalance(balance);
        accountDTO.setStatus(status);
        return accountService.inquiry(accountDTO);
    }

    @RequestMapping(value = "/account/{id}", method = RequestMethod.GET, produces = {"application/json"})
    public Account findAccountById(@PathVariable("id") Long id) {
        return accountService.findById(id);
    }

    @RequestMapping(value = "/account", method = RequestMethod.POST, produces = {"application/json"})
    public ResponseEntity<Account> add(@Valid @RequestBody Account account) {
        Account result = accountService.add(account);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/account/{id}", method = RequestMethod.PUT, produces = {"application/json"})
    public ResponseEntity<Object> update(@PathVariable("id") Long id,
                                         @Valid @RequestBody Account account) {
        accountService.update(id, account);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @RequestMapping(value = "/account/{id}", method = RequestMethod.DELETE, produces = {"application/json"})
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        accountService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
