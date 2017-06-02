package us.rlit.asyncronousity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import us.rlit.asyncronousity.api.services.NewsService;
import us.rlit.asyncronousity.api.services.ServiceRunner;

import java.util.concurrent.Executor;

/**
 * Create a spring boot application that calls command line application
 * Next version will run a web interface to show the news
 */
@EnableAsync
@SpringBootApplication
public class Application extends AsyncConfigurerSupport {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    /**
     * Entry point for all spring boot applications.
     * @param args arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        try {
            new ServiceRunner(new NewsService(new RestTemplateBuilder())).run();
        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }
    }

    /**
     * Implemented from {@link AsyncConfigurerSupport} creates ThreadPoolTaskExecutor
     * @return ThreadPoolTaskExecutor
     */
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("NewService-");
        executor.initialize();
        return executor;
    }
}
