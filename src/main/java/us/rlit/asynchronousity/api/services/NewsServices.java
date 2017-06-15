package us.rlit.asynchronousity.api.services;

import us.rlit.asynchronousity.api.domain.Articles;
import us.rlit.asynchronousity.api.domain.Sources;

import java.util.concurrent.Future;

/**
 * Interface for the NewsService class. Sources are gathered in Service Runner and not needed at this time.
 */
public interface NewsServices {
    Future<Articles> getArticles(String source) throws InterruptedException;
    Future<Articles> getArticles(String source, String sort) throws InterruptedException;
    Future<Sources> getSources(String category, String language) throws InterruptedException;
}
