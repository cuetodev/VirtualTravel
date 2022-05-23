package com.cuetodev.backempresa.User.application;

import com.cuetodev.backempresa.User.application.port.UserPort;
import com.cuetodev.backempresa.User.domain.User;
import com.cuetodev.backempresa.User.infrastructure.repository.port.UserRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserUseCase implements UserPort {

    @Autowired
    UserRepositoryPort userRepositoryPort;

    @Override
    public User findUserByEmailAndByPassword(String email, String password) {
        return userRepositoryPort.findUserByEmailAndByPassword(email, password);
    }

    @Override
    public User saveUser(User user) {
        return userRepositoryPort.userSave(user);
    }
}
