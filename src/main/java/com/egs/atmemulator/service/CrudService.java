package com.egs.atmemulator.service;

import com.egs.atmemulator.dto.CardDTO;
import com.egs.atmemulator.model.Account;
import com.egs.atmemulator.model.Card;

import java.util.List;

public interface CrudService<T,ID> {

    List<T> findAll();
    T findById(ID id);
    T add(T object);
    T update(T object);
    void delete(T object);
    void deleteById(ID id);
    List<T> search(T object);

}
