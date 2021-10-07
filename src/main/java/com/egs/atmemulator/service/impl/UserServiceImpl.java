package com.egs.atmemulator.service.impl;

import com.egs.atmemulator.enums.Status;
import com.egs.atmemulator.exceptions.NotAcceptableException;
import com.egs.atmemulator.model.User;
import com.egs.atmemulator.repository.UserRepository;
import com.egs.atmemulator.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);


    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        Optional<User> optional = userRepository.findById(id);
        if (!optional.isPresent()) throw new NotAcceptableException("User not found");
        return optional.get();
    }

    @Override
    public User add(User object) {
        User user = userRepository.save(object);
        return user;
    }

    @Override
    public User update(User object) {
        User user = userRepository.save(object);
        return user;
    }

    @Override
    public User update(Long userId, User user) {
        Optional<User> optional = userRepository.findById(userId);
        if (!optional.isPresent()) throw new NotAcceptableException("User not found");

        User object = userRepository.save(user);
        return object;
    }

    @Override
    public void delete(User object) {
        User user = object;
        user.setStatus(Status.DELETED);
        userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        Optional<User> optional = userRepository.findById(id);
        if (!optional.isPresent()) throw new NotAcceptableException("User not found");
        User user = optional.get();
        user.setStatus(Status.DELETED);
        userRepository.save(user);
    }

    @Override
    public List<User> search(User object) {
        return userRepository.search(object.getFirstName(),
                object.getLastName(),
                object.getGender(),
                object.getBirthdate(),
                object.getMobile(),
                object.getPhoneNumber(),
                object.getIdentityType(),
                object.getIdentity(),
                object.getEmail(),
                object.getEmail());
    }
}
