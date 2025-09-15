package co.com.crediya.api.modules.user.path;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "routes.paths")
public class UserPath {

    private String user;
    private String userId;

}
