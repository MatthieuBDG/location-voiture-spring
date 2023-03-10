package location.voiture.location.voiture;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppCongif {
    @Bean
    public TimeConverter timeConverter() {
        return new TimeConverter();
    }
}
