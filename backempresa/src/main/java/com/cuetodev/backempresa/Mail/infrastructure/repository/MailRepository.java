package com.cuetodev.backempresa.Mail.infrastructure.repository;

import com.cuetodev.backempresa.Mail.domain.Mail;
import com.cuetodev.backempresa.Mail.infrastructure.repository.jpa.MailRepositoryJPA;
import com.cuetodev.backempresa.Mail.infrastructure.repository.port.MailRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailRepository implements MailRepositoryPort {

    @Autowired
    MailRepositoryJPA mailRepositoryJPA;

    @Override
    public Mail postMail(Mail mailReceived) {
        return mailRepositoryJPA.save(mailReceived);
    }
}
