package com.spring.AddressBook.interfaces;

import com.spring.AddressBook.model.User;

public interface IUserService {
    User registerUser(String email, String password);
    String loginUser(String email, String password);
}
