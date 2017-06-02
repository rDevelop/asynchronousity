package us.rlit.asyncronousity.api.services;

import us.rlit.asyncronousity.api.domain.Articles;
import us.rlit.asyncronousity.api.domain.Sources;

import java.util.concurrent.Future;

/**
 * Created by rob on 4/4/17.
 */
public interface NewsServices {
    Future<Articles> getArticles(String source) throws InterruptedException;
    Future<Articles> getArticles(String source, String sort) throws InterruptedException;
    Future<Sources> getSources(String category, String language) throws InterruptedException;
}
