package nvb.dev.springadvancedproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableCaching
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SpringAdvancedProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAdvancedProjectApplication.class, args);
    }

}
