package com.cuetodev.backempresa.Mail.application.port;

import com.cuetodev.backempresa.Mail.domain.Mail;
import com.cuetodev.backempresa.Mail.infrastructure.controller.dto.input.MailInputDTO;
import com.cuetodev.backempresa.Mail.infrastructure.controller.dto.output.MailOutputDTO;

import java.util.HashMap;
import java.util.List;

public interface MailPort {
    public Mail postMail(Mail mailReceived);
    public List<MailOutputDTO> getMailsByConditions(HashMap<String, Object> conditions);
    public MailOutputDTO resend(MailInputDTO mail);
}
