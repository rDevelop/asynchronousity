package us.rlit.asynchronousity.api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import us.rlit.asynchronousity.api.domain.Articles;
import us.rlit.asynchronousity.api.domain.BannerImage;
import us.rlit.asynchronousity.api.domain.Source;
import us.rlit.asynchronousity.api.domain.Sources;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Service runner that will get sources on WebStart.
 * It will also pre-load articles for quicker access after the web server starts.
 * Categories:
 * business, entertainment, gaming, general, music, politics, science-and-nature, sport, technology
 */
public class ServiceRunner implements CommandLineRunner {
    private final NewsService newsService;

    private static final Logger logger = LoggerFactory.getLogger(ServiceRunner.class);
    private static final Map<String, Future<Sources>> categories = new HashMap<>();
    private static final Map<String, String> sortableBy = new HashMap<>();
    private static Future<Sources> allSources;
    private static Future<Sources> source;
    private static Future<Articles> firstArticles;
    private static List<Future<Articles>> topArticles = new LinkedList<>();
    private static List<Future<Articles>> latestArticles = new LinkedList<>();
    private static BannerImage[] bannerImages = new BannerImage[4];

    static {
        bannerImages[0] = new BannerImage("/images/bloomberg.png", "Bloomberg", "bloomberg", "business");
        bannerImages[1] = new BannerImage("/images/espn.png", "ESPN", "espn", "sport");
        bannerImages[2] = new BannerImage("/images/techcrunch.png", "TechCrunch", "techcrunch", "technology");
        bannerImages[3] = new BannerImage("/images/buzzfeed.jpg", "Buzzfeed", "buzzfeed", "entertainment");
    }

    public ServiceRunner(NewsService newsService) {

        this.newsService = newsService;
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
    public void run(String... args) throws ExecutionException, InterruptedException  {
        Thread backgroundServices = new Thread(() -> {
            try {
                firstArticles = newsService.getArticles("reuters", "latest");
                allSources = newsService.getSources(null, "en");
                    for (Map.Entry<String, Future<Sources>> entry : categories.entrySet()) {
                        source = newsService.getSources(entry.getKey(), "en");
                        categories.put(entry.getKey(), source);
                        for (Source s : entry.getValue().get().getSources()) {
                            for (String sort : s.getSortBysAvailable()) {
                                sortableBy.put(s.getId(), sort);
                            }
                        }
                    }
                    setArticlesBySort();
                } catch(ExecutionException | InterruptedException e ){
                    logger.error("Background thread exception {}", e);
                }
        });
        backgroundServices.start();
        logger.info("Started background services, moving on....");
    }


    public static BannerImage[] getBannerImages() {
        return bannerImages;
    }

    public static Future<Articles> getFirstArticles() {
        while (!firstArticles.isDone()) {
            logger.info("Fetching first article");
        }
        return firstArticles;
    }

    public static Future<Sources> getAllSources() {
        while (!allSources.isDone()) {
            logger.info("Fetching all sources");
        }
        return allSources;
    }

    public static Future<Sources> getSources(String category) {
        if ("all".equalsIgnoreCase(category.toLowerCase())) {
            return getAllSources();
        }
        Future<Sources> source = categories.get(category.toLowerCase());
        while (!source.isDone()) {
            logger.info("Fetching source: {}", category);
        }
        return source;
    }

    public static Future<Articles> getTopArticles() {
        int randomIndex = ThreadLocalRandom.current().nextInt(0, topArticles.size());
        return topArticles.get(randomIndex);
    }

    public static Future<Articles> getLatestArticles() {
        int randomIndex = ThreadLocalRandom.current().nextInt(0, latestArticles.size());
        return latestArticles.get(randomIndex);
    }

    public void setArticlesBySort() throws ExecutionException, InterruptedException {
        for (Map.Entry<String, String> entry : sortableBy.entrySet()) {
            Future<Articles> articlesFuture = newsService.getArticles(entry.getKey(), entry.getValue());
            articlesFuture.get();
            if (entry.getValue().equalsIgnoreCase("top")) {
                topArticles.add(articlesFuture);
            } else {
                latestArticles.add(articlesFuture);
            }
        }
    }

}
