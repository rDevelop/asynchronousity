package us.rlit.asynchronousity.api.domain;

/**
 -"urlsToLogos": {
 "small": "",
 "medium": "",
 "large": ""
 }
 */
public class UrlsToLogos {
    private String small;
    private String medium;
    private String large;

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    @Override
    public String toString() {
        return "UrlsToLogos{" +
                "small='" + small + '\'' +
                ", medium='" + medium + '\'' +
                ", large='" + large + '\'' +
                '}';
    }
}
