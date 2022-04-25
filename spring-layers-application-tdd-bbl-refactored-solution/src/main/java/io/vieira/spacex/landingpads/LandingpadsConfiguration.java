package io.vieira.spacex.landingpads;

import io.vieira.spacex.RSpaceXConfiguration;
import io.vieira.spacex.landingpads.data.RSpaceXApiLandingpadsRepository;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LandingpadsConfiguration {

    @Bean
    LandingpadsRepository rSpaceXApiLandpadsRepository(RSpaceXConfiguration configuration, RestTemplateBuilder templateBuilder) {
        return new RSpaceXApiLandingpadsRepository(templateBuilder
                .rootUri(configuration.getApi().getUrl())
                .build()
        );
    }

    @Bean
    LandingpadsService repositoryBasedLandpadsService(LandingpadsRepository repository) {
        return new RepositoryBasedLandingpadsService(repository);
    }
}
