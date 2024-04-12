package nvb.dev.springadvancedproject.aspect;

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
    @Pointcut(value = "execution(* nvb.dev.springadvancedproject.*.*.*(..))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object applicationLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ObjectMapper objectMapper = new ObjectMapper();

        String className = proceedingJoinPoint.getTarget().getClass().toString();
        String methodName = proceedingJoinPoint.getSignature().getName();
        Object[] argsArray = proceedingJoinPoint.getArgs();

        log.info("Invoked method : {}", methodName);
        log.info("Class name : {}", className);
        log.info("Arguments : {}", objectMapper.writeValueAsString(argsArray));

        Object result = proceedingJoinPoint.proceed(); // proceed() captures the result

        log.info("Method name : {}", methodName);
        log.info("Class name : {}", className);
        log.info("Response : {}", objectMapper.writeValueAsString(result));

        return result;
    }

}
