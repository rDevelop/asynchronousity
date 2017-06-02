package us.rlit.asyncronousity.api;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ApiInfo application.yml object loaded on spring boot.
 */
@Component
@ConfigurationProperties(prefix = "api")
public class ApiInfo {

    private String key;

    private String url;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
