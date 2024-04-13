package nvb.dev.springadvancedproject.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    // * matches any return type
    // *.*.* match any class, any method, and any number of arguments
    // (..) matches any number of arguments of any type
    @Pointcut(value = "execution(* nvb.dev.springadvancedproject.controller.*.*(..))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object applicationLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ObjectMapper objectMapper = new ObjectMapper();

        String className = proceedingJoinPoint.getTarget().getClass().toString();
        String methodName = proceedingJoinPoint.getSignature().getName();
        Object[] argsArray = proceedingJoinPoint.getArgs();

        log.info("Class name : {}", className);
        log.info("Method name : {}", methodName);
        try {
            log.info("Arguments : {}", objectMapper.writeValueAsString(argsArray));
        } catch (JsonProcessingException e) {
            log.error("Error converting arguments to JSON: {}", e.getMessage());
        }

        Object result = proceedingJoinPoint.proceed(); // proceed() captures the result

        log.info("Class name : {}", className);
        log.info("Method name : {}", methodName);
        try {
            log.info("Response : {}", objectMapper.writeValueAsString(result));
        } catch (JsonProcessingException e) {
            log.error("Error converting arguments to JSON: {}", e.getMessage());
        }

        return result;
    }

}
