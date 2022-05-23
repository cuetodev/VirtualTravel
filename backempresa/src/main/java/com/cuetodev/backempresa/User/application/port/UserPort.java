package com.cuetodev.backempresa.User.application.port;

import com.cuetodev.backempresa.User.domain.User;

public interface UserPort {
    public User findUserByEmailAndByPassword(String email, String password);
    public User saveUser(User user);
}
