package us.rlit.asyncronousity.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import us.rlit.asyncronousity.api.domain.Article;
import us.rlit.asyncronousity.api.domain.Articles;
import us.rlit.asyncronousity.api.domain.Source;
import us.rlit.asyncronousity.api.domain.Sources;
import us.rlit.asyncronousity.api.services.NewsServices;
import us.rlit.asyncronousity.api.services.ServiceRunner;

import java.util.concurrent.Future;

/**
 * Created by rob on 4/4/17.
 */
@Controller
public class IndexController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final static String index = "index";
    private final static String title = "title";
    private NewsServices newsService;
    @Autowired
    public void setNewsSources(NewsServices newsService) {
        this.newsService = newsService;
    }

    @RequestMapping("/")
    public String root() {
        logger.info("root");
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index(Model model) throws Exception {
        logger.info("index - first article");
        Future<Articles> articles = ServiceRunner.getFirstArticle();
        model.addAttribute(title, "Articles");
        model.addAttribute("source", "asyncronousity news feed");
        model.addAttribute("articles", articles.get().getArticles());
        return index;
    }

    @RequestMapping("/index/sources")
    public String sources(Model model) throws Exception {
        logger.info("index/sources");
        Future<Sources> sources = ServiceRunner.getAllSources();

        for(Source s : sources.get().getSources()) {
            logger.info(s.toString());
        }

        model.addAttribute(title, "Sources");
        model.addAttribute("sources", sources.get().getSources());
        return index;
    }

    @RequestMapping("/index/sources/{category}")
    public String sources(@PathVariable String category, Model model) throws Exception {
        logger.info("index/sources/{" + category +"}");


        Future<Sources> sources = ServiceRunner.getSources(category);

        model.addAttribute(title, "Sources");
        model.addAttribute("sources", sources.get().getSources());
        return index;
    }

    @RequestMapping("/index/top")
    public String top(Model model) throws Exception {
        logger.info("index/top");
        //Future<Sources> sources = ServiceRunner.getSources();
        model.addAttribute(title, "Top");
        //model.addAttribute("sources", sources.get().getSources());
        return index;
    }

    @RequestMapping("/index/latest")
    public String latest(Model model) throws Exception {
        logger.info("index/latest");
        //Future<Sources> sources = ServiceRunner.getSources();

        model.addAttribute("title", "Lastest");
        //model.addAttribute("sources", sources.get().getSources());
        return index;
    }

    @RequestMapping("/index/popular")
    public String popular(Model model) throws Exception {
        logger.info("index/popular");
        //Future<Sources> sources = ServiceRunner.getSources();

        model.addAttribute("title", "Popular");
        //model.addAttribute("sources", sources.get().getSources());
        return index;
    }

    @RequestMapping("/index/articles/{source}")
    public String articleBySource(@PathVariable String source, Model model) throws Exception {
        logger.info("index/articles %s %s", source);
        Future<Articles> articles = newsService.getArticles(source);
        while(!articles.isDone()) {
            // wait for service to finish fetching articles.
        }
        for(Article article : articles.get().getArticles()) {
            logger.info(article.toString());
        }
        model.addAttribute(title, "Articles");
        model.addAttribute("source", articles.get().getSource());
        model.addAttribute("articles", articles.get().getArticles());
        return index;
    }

    @RequestMapping("/index/articles/{source}/{sort}")
    public String articleBySourceAndSort(@PathVariable String source, @PathVariable String sort, Model model) throws Exception {
        logger.info("index/articles %s %s", source, sort);
        Future<Articles> articles = newsService.getArticles(source,sort);
        while(!articles.isDone()) {
            // wait for service to finish fetching articles.
        }
        for(Article article : articles.get().getArticles()) {
            logger.info(article.toString());
        }
        model.addAttribute(title, "Articles");
        model.addAttribute("source", articles.get().getSource());
        model.addAttribute("articles", articles.get().getArticles());
        return index;
    }
}
