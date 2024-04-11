package nvb.dev.springadvancedproject.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MailRequest {

    private String recipient;
    private String subject;
    private String message;

}
