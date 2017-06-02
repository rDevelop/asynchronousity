package us.rlit.asyncronousity.api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import us.rlit.asyncronousity.api.domain.Articles;
import us.rlit.asyncronousity.api.domain.Source;
import us.rlit.asyncronousity.api.domain.Sources;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Service runner that will get sources on WebStart.
 * Categories:
 * business, entertainment, gaming, general, music, politics, science-and-nature, sport, technology
 */
public class ServiceRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(ServiceRunner.class);
    private final NewsService newsService;
    private static final Map<String, Future<Sources>> categories = new HashMap<>();
    private static final Map<String, Future<Sources>> sortables = new HashMap<>();


    private static Future<Sources> allSources;
    private static Future<Sources> source;
    private static Future<Articles> firstArticle;

//    private static Future<Sources> businessSources;
//    private static Future<Sources> entertainmentSources;
//    private static Future<Sources> gamingSources;
//    private static Future<Sources> generalSources;
//    private static Future<Sources> musicSources;
//    private static Future<Sources> politicsSources;
//    private static Future<Sources> scienceAndNatureSources;
//    private static Future<Sources> sportSources;
//    private static Future<Sources> technologySources;

    public ServiceRunner(NewsService newsService) {

        this.newsService = newsService;
//        categories.put("business", businessSources);
//        categories.put("entertainment", entertainmentSources);
//        categories.put("gaming", gamingSources);
//        categories.put("general", generalSources);
//        categories.put("music", musicSources);
//        categories.put("politics", politicsSources);
//        categories.put("science-and-nature", scienceAndNatureSources);
//        categories.put("sport", sportSources);
//        categories.put("technology", technologySources);
        categories.put("business", source);
        categories.put("entertainment", source);
        categories.put("gaming", source);
        categories.put("general", source);
        categories.put("music", source);
        categories.put("politics", source);
        categories.put("science-and-nature", source);
        categories.put("sport", source);
        categories.put("technology", source);
    }

    @Override
    public void run(String... args) throws Exception {
        allSources = newsService.getSources(null, "en");
        firstArticle = newsService.getArticles("the-next-web", "latest");
        for (Map.Entry<String, Future<Sources>> entry : categories.entrySet()) {
            System.out.println(entry.getKey());

            //Future<Sources> future = categories.get(entry.getKey());
            source = newsService.getSources(entry.getKey(), "en");
            categories.put(entry.getKey(), source);
            for( Source s : entry.getValue().get().getSources() ) {
                System.out.println(s.getName() + " " + s.getCategory());
                for(String sort : s.getSortBysAvailable()) {
                    sortables.put(sort, source);
                }
            }
        }
    }

    public static Future<Articles> getFirstArticle() throws Exception {
        while(!firstArticle.isDone()) {
            logger.info("Fetching first article");
        }
        return firstArticle;
    }

    public static Future<Sources> getAllSources() throws Exception {
        while(!allSources.isDone()) {
            logger.info("Fetching all sources");
        }
        return allSources;
    }

    public static Future<Sources> getSources(String category) throws Exception {
        Future<Sources> source = categories.get(category);
        while(!source.isDone()) {
            logger.info("Fetching source: " + category);
        }
        return source;
    }
}
