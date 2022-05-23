package com.cuetodev.backempresa.Mail.infrastructure.repository.jpa;

import com.cuetodev.backempresa.Mail.domain.Mail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRepositoryJPA extends JpaRepository<Mail, Integer> {
}
