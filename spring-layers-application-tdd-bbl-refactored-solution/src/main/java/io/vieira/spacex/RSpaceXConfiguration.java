package io.vieira.spacex;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties("r-spacex")
public class RSpaceXConfiguration {

    @NestedConfigurationProperty
    private final Api api;

    @ConstructorBinding
    public RSpaceXConfiguration(Api api) {
        this.api = api;
    }

    public Api getApi() {
        return api;
    }

    public static class Api {
        private final String url;

        @ConstructorBinding
        public Api(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }
}
