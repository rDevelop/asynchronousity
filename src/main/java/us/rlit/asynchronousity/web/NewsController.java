package us.rlit.asynchronousity.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import us.rlit.asynchronousity.api.domain.Articles;
import us.rlit.asynchronousity.api.domain.Sources;
import us.rlit.asynchronousity.api.services.NewsServices;
import us.rlit.asynchronousity.api.services.ServiceRunner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by rob on 4/4/17.
 */
@Controller
public class NewsController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String NEWS = "news";
    private static final String TITLE = "title";
    private NewsServices newsService;
    @Autowired
    public void setNewsSources(NewsServices newsService) {
        this.newsService = newsService;
    }

    @RequestMapping("/")
    public String root() {
        logger.info("root");
        return "redirect:/news/first";
    }

    @RequestMapping("/news/about")
    public String about(Model model) {
        logger.info("/news/about");
        model.addAttribute(TITLE, "About");
        return NEWS;
    }


    @RequestMapping("/news")
    public String news() {
        logger.info("news");
        return "redirect:/news/first";
    }

    @RequestMapping("/news/first")
    public String first(Model model) throws InterruptedException, ExecutionException {
        logger.info("news - first article");
        Future<Articles> articles = ServiceRunner.getFirstArticles();
        setModelAttributes(model, articles);
        return NEWS;
    }

    @RequestMapping("/news/sources")
    public String sources(Model model) throws InterruptedException, ExecutionException {
        logger.info("news/sources");
        Future<Sources> sources = ServiceRunner.getAllSources();
        setModelAttributes(model, sources, "All");
        return NEWS;
    }

    @RequestMapping("/news/sources/{category}")
    public String sources(@PathVariable String category, Model model) throws InterruptedException, ExecutionException {
        logger.info("news/sources/{}", category);
        Future<Sources> sources = ServiceRunner.getSources(category);
        setModelAttributes(model, sources, category);
        return NEWS;
    }

    @RequestMapping("/news/top")
    public String top(Model model) throws InterruptedException, ExecutionException {
        logger.info("news/top");
        Future<Articles> tops = ServiceRunner.getTopArticles();
        while(!tops.isDone()) {
            // wait for it..
        }
        setModelAttributes(model, tops);
        return NEWS;
    }

    @RequestMapping("/news/latest")
    public String latest(Model model) throws InterruptedException, ExecutionException {
        logger.info("news/latest");
        Future<Articles> latest = ServiceRunner.getLatestArticles();
        while(!latest.isDone()) {
            // wait for it..
        }
        setModelAttributes(model, latest);
        return NEWS;
    }

    @RequestMapping("/news/articles/{source}")
    public String articleBySource(@PathVariable String source, Model model) throws InterruptedException, ExecutionException {
        logger.info("news/articles/{}", source);
        Future<Articles> articles = newsService.getArticles(source);
        while(!articles.isDone()) {
            // wait for service to finish fetching articles.
        }
        setModelAttributes(model, articles);
        return NEWS;
    }

    @RequestMapping("/news/articles/{source}/{sort}")
    public String articleBySourceAndSort(@PathVariable String source, @PathVariable String sort, Model model) throws InterruptedException, ExecutionException {
        logger.info("/news/articles/{}/{}", source, sort );
        Future<Articles> articles = newsService.getArticles(source,sort);
        while(!articles.isDone()) {
            // wait for service to finish fetching articles.
        }
        setModelAttributes(model, articles);
        return NEWS;
    }

    private void setModelAttributes(Model model, Future<Articles> articlesFuture) throws InterruptedException, ExecutionException {
        model.addAttribute(TITLE, "Articles");
        model.addAttribute("source", articlesFuture.get().getSource());
        model.addAttribute("articles", articlesFuture.get().getArticles());
        model.addAttribute("bannerImages" , ServiceRunner.getBannerImages());
    }

    private void setModelAttributes(Model model, Future<Sources> sourcesFuture, String category) throws InterruptedException, ExecutionException {
        model.addAttribute(TITLE, "Sources");
        model.addAttribute("category", category);
        model.addAttribute("sources", sourcesFuture.get().getSources());
        model.addAttribute("bannerImages" , ServiceRunner.getBannerImages());
    }
}
