package com.cuetodev.backempresa.User.infrastructure.repository;

import com.cuetodev.backempresa.User.domain.User;
import com.cuetodev.backempresa.User.infrastructure.repository.jpa.UserRepositoryJPA;
import com.cuetodev.backempresa.User.infrastructure.repository.port.UserRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRepository implements UserRepositoryPort {

    @Autowired
    UserRepositoryJPA userRepositoryJPA;

    @Override
    public User findUserByEmailAndByPassword(String email, String password) {
        return userRepositoryJPA.findByEmailAndPassword(email, password);
    }

    @Override
    public User userSave(User user) {
        return userRepositoryJPA.save(user);
    }
}
