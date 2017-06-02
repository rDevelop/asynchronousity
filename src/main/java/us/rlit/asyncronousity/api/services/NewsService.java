package us.rlit.asyncronousity.api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import us.rlit.asyncronousity.Application;
import us.rlit.asyncronousity.api.domain.Articles;
import us.rlit.asyncronousity.api.domain.Sources;

import java.util.concurrent.Future;

import static us.rlit.asyncronousity.api.ApiManager.apiInfo;

/**
 * Create a news service to query the news api.
 */
@Service
public class NewsService {
    private static final Logger logger = LoggerFactory.getLogger(NewsService.class);
    private final RestTemplate restTemplate;

    /**
     * Initialize restTemplate on construction
     * @param restTemplateBuilder to build restTemplate
     */
    public NewsService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     * returns asynchronous threads when initialized in
     * {@link Application#getAsyncExecutor()}} and implemented in {@link ServiceRunner}
     * @param source is base url for all news apis
     * @param sort is latest, top, popular
     * @return Future Articles
     * @throws InterruptedException if Executor is interrupted before completion
     */
    @Async
    public Future<Articles> getArticles(String source, String sort) throws InterruptedException {
        //https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest
        String url = String.format("%s/articles?source=%s&sortBy=%s&apiKey=%s",apiInfo().getUrl(), source, sort, apiInfo().getKey());
        logger.info("Looking up articles at " + url);
        Articles results = restTemplate.getForObject(url, Articles.class);
        logger.info("Found articles...");
        return new AsyncResult<>(results);
    }

    @Async
    public Future<Articles> getArticles(String source) throws InterruptedException {
        //https://newsapi.org/v1/articles?source=the-next-web
        String url = String.format("%s/articles?source=%s&apiKey=%s",apiInfo().getUrl(), source, apiInfo().getKey());
        logger.info("Looking up articles at " + url);
        Articles results = restTemplate.getForObject(url, Articles.class);
        logger.info("Found articles...");
        return new AsyncResult<>(results);
    }

    @Async
    public Future<Sources> getSources(String category, String language) throws InterruptedException {
        //"https://newsapi.org/v1/sources?language=en"
        String url = String.format("%s/sources?language=%s&apiKey=%s", apiInfo().getUrl(), language, apiInfo().getKey());
        if(category != null) {
            url = String.format("%s/sources?category=%s&language=%s&apiKey=%s", apiInfo().getUrl(), category, language, apiInfo().getKey());
        }
        logger.info("Looking up news sources at " + url);
        Sources results = restTemplate.getForObject(url, Sources.class);
        logger.info("Found sources...");
        return new AsyncResult<>(results);
    }


}
