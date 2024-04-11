package nvb.dev.springadvancedproject.service;

import nvb.dev.springadvancedproject.dto.request.MailRequest;

public interface MailService {

    void sendMail(MailRequest mailRequest);

}
