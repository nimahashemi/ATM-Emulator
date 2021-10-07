package com.egs.atmemulator.repository;

import com.egs.atmemulator.enums.AccountStatus;
import com.egs.atmemulator.enums.AccountType;
import com.egs.atmemulator.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE (?1 is null or a.userId = ?1) " +
            "and (?2 is null or a.accountType >= ?2) " +
            "and (?3 is null or a.accountNumber <= ?3) " +
            "and (?4 is null or a.balance= ?4) " +
            "and (?5 is null or a.status = ?5) ")
    List<Account> search(Long user,
                         AccountType accountType,
                         Long accountNumber,
                         Long balance,
                         AccountStatus status);
}
