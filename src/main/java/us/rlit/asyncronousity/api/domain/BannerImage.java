package us.rlit.asyncronousity.api.domain;

/**
 * Created by rob on 6/5/17.
 */
public class BannerImage {
    private String imageUrl = "";
    private String altText = "";
    private String sourceName = "";
    private String category = "";

    public BannerImage(String imageUrl, String altText, String sourceName, String category) {
        this.imageUrl = imageUrl;
        this.altText = altText;
        this.sourceName = sourceName;
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAltText() {
        return altText;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getCategory() {
        return category;
    }
}
