package com.egs.atmemulator.model;

import com.egs.atmemulator.enums.Gender;
import com.egs.atmemulator.enums.IdentityType;
import com.egs.atmemulator.enums.Status;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "first_name")
    @Size(min = 3, max = 50, message = "First Name must be between 3 & 50")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    @Size(min = 3, max = 50, message = "Last Name must be between 3 & 50")
    private String lastName;

    @NotNull
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    @Column(name = "birthdate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;

    @NotNull
    @Column(name = "mobile")
    @Size(min = 8, max = 16, message = "Email must be between 8 & 16")
    private int mobile;

    @Column(name = "phone_number")
    private int phoneNumber;

    @Column(name = "email")
    @Email
    @Size(min = 10, max = 50, message = "Email must be between 10 & 50")
    private String email;

    @NotNull
    @Column(name = "identity_type")
    @Enumerated(EnumType.STRING)
    private IdentityType identityType;

    @NotNull
    @Column(name = "identity", unique = true)
    private String identity;

    @NotNull
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

}
