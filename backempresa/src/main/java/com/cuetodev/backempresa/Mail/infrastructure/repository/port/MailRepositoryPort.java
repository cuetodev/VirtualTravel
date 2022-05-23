package com.cuetodev.backempresa.Mail.infrastructure.repository.port;

import com.cuetodev.backempresa.Mail.domain.Mail;

public interface MailRepositoryPort {
    public Mail
    postMail(Mail mailReceived);
}
