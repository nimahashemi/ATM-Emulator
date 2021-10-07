package com.egs.atmemulator.service;

import com.egs.atmemulator.dto.AccountDTO;
import com.egs.atmemulator.model.Account;

import java.util.List;

public interface AccountService extends CrudService<Account, Long> {

    Account update(Long accountId, Account account);
    List<Account> inquiry(AccountDTO accountDTO);

}
