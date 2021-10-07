package com.egs.atmemulator.service;

import com.egs.atmemulator.model.User;

public interface UserService extends CrudService<User, Long>{

    User update(Long userId, User user);
}
