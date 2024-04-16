package nvb.dev.springadvancedproject.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class LoggingAspectTest {

    @Autowired
    LoggingAspect loggingAspect;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    ProceedingJoinPoint proceedingJoinPoint;

    @Mock
    Signature signature;

    @Test
    void pointCut() {
    }

    @Test
    void applicationLoggerIsValid() throws Throwable {
        when(proceedingJoinPoint.getSignature()).thenReturn(signature);
        when(proceedingJoinPoint.getTarget()).thenReturn(getClass());
        when(proceedingJoinPoint.proceed()).thenReturn(new Object());
        when(signature.getName()).thenReturn("applicationLogger");
        when(proceedingJoinPoint.getArgs()).thenReturn(new Object[0]);

        loggingAspect.applicationLogger(proceedingJoinPoint);

        verify(signature, times(1)).getName();
        verify(proceedingJoinPoint, times(1)).getSignature();
        verify(proceedingJoinPoint, times(1)).getTarget();
        verify(proceedingJoinPoint, times(1)).getArgs();
    }
}