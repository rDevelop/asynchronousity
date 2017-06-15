package us.rlit.asynchronousity.api;

import org.springframework.stereotype.Component;

/**
 * Static ApiInfo object to get application.yml variables
 */
@Component
public class ApiManager {
    private static ApiInfo apiInfo;

    /**
     * Will get the bean info on boot
     * @param apiInfo bean info
     */
    private ApiManager(ApiInfo apiInfo)
    {
        ApiManager.apiInfo = apiInfo;
    }

    /**
     * public access for returning ApiInfo
     * @return ApiInfo
     */
    public static ApiInfo apiInfo() {
        return apiInfo;
    }
}
