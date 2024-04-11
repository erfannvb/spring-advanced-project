package nvb.dev.springadvancedproject.service;

import nvb.dev.springadvancedproject.dto.request.MailRequest;
import nvb.dev.springadvancedproject.service.impl.MailServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;

import static nvb.dev.springadvancedproject.MotherObject.anyValidMailRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class MailServiceTest {

    @Mock
    JavaMailSender mailSender;

    @InjectMocks
    MailServiceImpl mailService;

    @Test
    void testThatSendMailSuccessfullySendsEmail() {
        MailRequest mailRequest = anyValidMailRequest();
        mailService.sendMail(mailRequest);

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(captor.capture());

        SimpleMailMessage sentMessage = captor.getValue();
        assertEquals(mailRequest.getRecipient(), Objects.requireNonNull(sentMessage.getTo())[0]);
        assertEquals(mailRequest.getSubject(), sentMessage.getSubject());
        assertEquals(mailRequest.getMessage(), sentMessage.getText());
    }
}