package io.github.artenes.speedbro.speedrun.com.website;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Helper class to fetch a document from an url
 * This was wrapped like this to help in tests
 */
public class DocumentFetcher {

    /**
     * Fetch a html document from an url
     *
     * @param url the url to the document
     * @return the document
     * @throws IOException in case of a connection error
     */
    public Document asHtml(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

}