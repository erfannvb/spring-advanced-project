package nvb.dev.springadvancedproject.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class RequestInterceptorTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @InjectMocks
    RequestInterceptor requestInterceptor;

    @Test
    void testValidRequest() {
        when(request.getHeader("x-requested-area")).thenReturn("dev");
        boolean result = requestInterceptor.preHandle(request, response, new Object());
        assertTrue(result);
    }

    @Test
    void testInvalidRequest() {
        when(request.getHeader("x-requested-area")).thenReturn("wrongHeader");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                requestInterceptor.preHandle(request, response, new Object()));
        assertEquals("x-requested-area is invalid.", exception.getMessage());
    }

    @Test
    void testNullHeader() {
        when(request.getHeader("x-requested-area")).thenReturn(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                requestInterceptor.preHandle(request, response, new Object()));
        assertEquals("x-requested-area is invalid.", exception.getMessage());
    }

}