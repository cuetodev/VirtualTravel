package com.cuetodev.backempresa.User.infrastructure.repository.jpa;

import com.cuetodev.backempresa.User.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryJPA extends JpaRepository<User, Integer> {
    public User findByEmailAndPassword(String email, String password);
}
