package com.egs.atmemulator.repository;

import com.egs.atmemulator.enums.Gender;
import com.egs.atmemulator.enums.IdentityType;
import com.egs.atmemulator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE (?1 is null or u.firstName = ?1) " +
            "and (?2 is null or u.lastName >= ?2) " +
            "and (?3 is null or u.gender <= ?3) " +
            "and (?4 is null or u.birthdate= ?4) " +
            "and (?5 is null or u.mobile = ?5) " +
            "and (?6 is null or u.phoneNumber = ?6) " +
            "and (?7 is null or u.identityType = ?7) " +
            "and (?8 is null or u.identity = ?8) " +
            "and (?9 is null or u.email = ?9) " +
            "and (?10 is null or u.status = ?10) ")
    List<User> search(String firstName,
                      String lastName,
                      Gender gender,
                      Date birthdate,
                      String mobile,
                      String phoneNumber,
                      IdentityType identityType,
                      String identity,
                      String email,
                      String status);
}
