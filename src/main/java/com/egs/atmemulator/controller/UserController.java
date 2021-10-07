package com.egs.atmemulator.controller;

import com.egs.atmemulator.enums.Gender;
import com.egs.atmemulator.enums.IdentityType;
import com.egs.atmemulator.enums.Status;
import com.egs.atmemulator.model.User;
import com.egs.atmemulator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     *
     * @param firstName
     * @param lastname
     * @param gender
     * @param birthdate
     * @param mobile
     * @param phoneNumber
     * @param email
     * @param identityType
     * @param identity
     * @param status
     * @return
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = {"application/json"})
    public List<User> getAllUsers(@RequestParam(value = "firstName", required = false) String firstName,
                                  @RequestParam(value = "lastname", required = false) String lastname,
                                  @RequestParam(value = "gender", required = false) Gender gender,
                                  @RequestParam(value = "birthdate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthdate,
                                  @RequestParam(value = "mobile", required = false) String mobile,
                                  @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                                  @RequestParam(value = "email", required = false) String email,
                                  @RequestParam(value = "identityType", required = false) IdentityType identityType,
                                  @RequestParam(value = "identity", required = false) String identity,
                                  @RequestParam(value = "status", required = false) Status status) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastname);
        user.setGender(gender);
        user.setBirthdate(birthdate);
        user.setMobile(mobile);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        user.setIdentityType(identityType);
        user.setIdentity(identity);
        user.setStatus(status);
        return userService.search(user);
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = {"application/json"})
    public User findUserById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    /**
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST, produces = {"application/json"})
    public ResponseEntity<User> add(@Valid @RequestBody User user) {
        User result = userService.add(user);
        return ResponseEntity.ok(result);
    }

    /**
     *
     * @param id
     * @param user
     * @return
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT, produces = {"application/json"})
    public ResponseEntity<Object> update(@PathVariable("id") Long id,
                                         @Valid @RequestBody User user) {
        userService.update(id, user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE, produces = {"application/json"})
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
