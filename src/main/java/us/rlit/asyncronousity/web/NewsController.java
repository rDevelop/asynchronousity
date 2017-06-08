package us.rlit.asyncronousity.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import us.rlit.asyncronousity.api.domain.Articles;
import us.rlit.asyncronousity.api.domain.Sources;
import us.rlit.asyncronousity.api.services.NewsServices;
import us.rlit.asyncronousity.api.services.ServiceRunner;

import java.util.concurrent.Future;

/**
 * Created by rob on 4/4/17.
 */
@Controller
public class NewsController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final static String news = "news";
    private final static String title = "title";
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
        model.addAttribute(title, "About");
        return news;
    }


    @RequestMapping("/news")
    public String news() {
        logger.info("news");
        return "redirect:/news/first";
    }

    @RequestMapping("/news/first")
    public String first(Model model) throws Exception {
        logger.info("news - first article");
        Future<Articles> articles = ServiceRunner.getFirstArticles();
        model.addAttribute(title, "Articles");
        model.addAttribute("source", articles.get().getSource());
        model.addAttribute("articles", articles.get().getArticles());
        model.addAttribute("bannerImages" , ServiceRunner.getBannerImages());
        return news;
    }

    @RequestMapping("/news/sources")
    public String sources(Model model) throws Exception {
        logger.info("news/sources");
        Future<Sources> sources = ServiceRunner.getAllSources();
        model.addAttribute(title, "Sources");
        model.addAttribute("category", "all");
        model.addAttribute("sources", sources.get().getSources());
        model.addAttribute("bannerImages" , ServiceRunner.getBannerImages());
        return news;
    }

    @RequestMapping("/news/sources/{category}")
    public String sources(@PathVariable String category, Model model) throws Exception {
        logger.info("news/sources/{" + category +"}");
        Future<Sources> sources = ServiceRunner.getSources(category);
        model.addAttribute(title, "Sources");
        model.addAttribute("category", category);
        model.addAttribute("sources", sources.get().getSources());
        model.addAttribute("bannerImages" , ServiceRunner.getBannerImages());
        return news;
    }

    @RequestMapping("/news/top")
    public String top(Model model) throws Exception {
        logger.info("news/top");
        Future<Articles> tops = ServiceRunner.getTopArticles();
        while(!tops.isDone()) {
            // wait for it..
        }
        model.addAttribute(title, "Articles");
        model.addAttribute("source", tops.get().getSource());
        model.addAttribute("articles", tops.get().getArticles());
        model.addAttribute("bannerImages" , ServiceRunner.getBannerImages());
        return news;
    }

    @RequestMapping("/news/latest")
    public String latest(Model model) throws Exception {
        logger.info("news/latest");
        Future<Articles> latest = ServiceRunner.getLatestArticles();
        while(!latest.isDone()) {
            // wait for it..
        }
        model.addAttribute(title, "Articles");
        model.addAttribute("source", latest.get().getSource());
        model.addAttribute("articles", latest.get().getArticles());
        model.addAttribute("bannerImages" , ServiceRunner.getBannerImages());
        return news;
    }

    @RequestMapping("/news/articles/{source}")
    public String articleBySource(@PathVariable String source, Model model) throws Exception {
        logger.info("news/articles/{" + source +"}");
        Future<Articles> articles = newsService.getArticles(source);
        while(!articles.isDone()) {
            // wait for service to finish fetching articles.
        }
        model.addAttribute(title, "Articles");
        model.addAttribute("source", articles.get().getSource());
        model.addAttribute("articles", articles.get().getArticles());
        model.addAttribute("bannerImages" , ServiceRunner.getBannerImages());
        return news;
    }

    @RequestMapping("/news/articles/{source}/{sort}")
    public String articleBySourceAndSort(@PathVariable String source, @PathVariable String sort, Model model) throws Exception {
        logger.info("news/articles/{" + source +"}/{" + sort +"}");
        Future<Articles> articles = newsService.getArticles(source,sort);
        while(!articles.isDone()) {
            // wait for service to finish fetching articles.
        }
        model.addAttribute(title, "Articles");
        model.addAttribute("source", articles.get().getSource());
        model.addAttribute("articles", articles.get().getArticles());
        model.addAttribute("bannerImages" , ServiceRunner.getBannerImages());
        return news;
    }
}
