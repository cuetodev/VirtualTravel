package com.cuetodev.backempresa.User.infrastructure.repository.port;

import com.cuetodev.backempresa.User.domain.User;

public interface UserRepositoryPort {
    public User findUserByEmailAndByPassword(String email, String password);
    public User userSave(User user);
}
