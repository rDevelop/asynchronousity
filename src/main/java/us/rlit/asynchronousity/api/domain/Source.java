package us.rlit.asynchronousity.api.domain;

import java.util.Arrays;

/**
 * Source
 *
 "id": "abc-news-au",
 "name": "ABC News (AU)",
 "description": "Australia's most trusted source of local, national and world news. Comprehensive, independent, in-depth analysis, the latest business, sport, weather and more.",
 "url": "http://www.abc.net.au/news",
 "category": "general",
 "language": "en",
 "country": "au",
 -"urlsToLogos": {
 "small": "",
 "medium": "",
 "large": ""
 },
 -"sortBysAvailable": [
 "top"
 ]
 */
public class Source {
    private String id;
    private String name;
    private String description;
    private String url;
    private String category;
    private String language;
    private String country;
    // Deprecated urlsToLogos
    //private UrlsToLogos urlsToLogos;
    private String[] sortBysAvailable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

// Deprecated
//    public UrlsToLogos getUrlsToLogos() {
//        return urlsToLogos;
//    }
//
//    public void setUrlsToLogos(UrlsToLogos urlsToLogos) {
//        this.urlsToLogos = urlsToLogos;
//    }

    public String[] getSortBysAvailable() {
        return sortBysAvailable;
    }

    public void setSortBysAvailable(String[] sortBysAvailable) {
        this.sortBysAvailable = sortBysAvailable;
    }

    @Override
    public String toString() {
        return "Source{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", category='" + category + '\'' +
                ", language='" + language + '\'' +
                ", country='" + country + '\'' +
                //", urlsToLogos=" + urlsToLogos +
                ", sortBysAvailable=" + Arrays.toString(sortBysAvailable) +
                '}';
    }
}
