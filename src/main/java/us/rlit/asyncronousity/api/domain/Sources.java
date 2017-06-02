package us.rlit.asyncronousity.api.domain;

import java.util.Arrays;

/**

 status	(string) - If the request was successful or not. Options: ok, error. In the case of error a code and message property will be populated.
 sources	(array) - A list of the news sources and blogs available on News API.
 */
public class Sources {
    private String status;
    private Source[] sources;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Source[] getSources() {
        return sources;
    }

    public void setSources(Source[] sources) {
        this.sources = sources;
    }

    @Override
    public String toString() {
        return "Sources{" +
                "status='" + status + '\'' +
                ", sources=" + Arrays.toString(sources) +
                '}';
    }


}
