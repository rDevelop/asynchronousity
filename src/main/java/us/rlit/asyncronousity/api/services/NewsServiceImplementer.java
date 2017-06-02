package us.rlit.asyncronousity.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.rlit.asyncronousity.api.domain.Articles;
import us.rlit.asyncronousity.api.domain.Sources;

import java.util.concurrent.Future;

/**
 * Created by rob on 4/4/17.
 */
@Service
public class NewsServiceImplementer implements NewsServices {
    private NewsService newsService;

    @Autowired
    public void setNewsService(NewsService newsService) {
        this.newsService = newsService;
    }


    public Future<Articles> getArticles(String source) throws InterruptedException {
        return newsService.getArticles(source);
    };

    public Future<Articles> getArticles(String source, String sort) throws InterruptedException {
        return newsService.getArticles(source, sort);
    };


    public Future<Sources> getSources(String category, String language) throws InterruptedException {
        return newsService.getSources(category, language);
    };

}
