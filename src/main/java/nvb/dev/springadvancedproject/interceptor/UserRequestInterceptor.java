package nvb.dev.springadvancedproject.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

@Component
public class UserRequestInterceptor implements HandlerInterceptor {

    // Only for test purposes

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {
        if (!Objects.equals(request.getHeader("X-Requested-Area"), "dev")) {
            throw new IllegalArgumentException("X-Requested-Area is invalid.");
        }
        return true;
    }
}
