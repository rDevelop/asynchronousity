package us.rlit.asynchronousity.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *  Articles is a pojo from the news api
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Articles {
    private String status;
    private String source;
    private String sortBy;
    private Article[] articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Article[] getArticles() {
        return articles;
    }

    public void setArticles(Article[] articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder("Articles [status=" + status + ", source=" + source + "sortBy=" + sortBy);
        buffer.append(", article: ");
        for(Article article: articles) {
            buffer.append(article.toString());
        }
        buffer.append("]");
        return buffer.toString();
    }

}
