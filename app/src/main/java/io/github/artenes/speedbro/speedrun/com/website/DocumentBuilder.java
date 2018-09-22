package io.github.artenes.speedbro.speedrun.com.website;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.artenes.speedbro.speedrun.com.Contract;

/**
 * Helper class used to build documents.
 * This is used mainly to merge different documents into one
 * This can happen when you have a section of a document that is loaded by an ajax call
 */
public class DocumentBuilder {

    private final DocumentFetcher fetcher;

    public DocumentBuilder(DocumentFetcher fetcher) {
        this.fetcher = fetcher;
    }

    /**
     * Build a document for a runner
     *
     * @param id the id of the runner (its username)
     * @return the document with the runner details and runs
     * @throws IOException in case of a network error
     */
    public Document buildRunnerDocument(String id) throws IOException {
        Document runnerDocument = fetcher.fromUrl(Contract.runnerUrl(id));

        //regex to get the id of the user (e.g. user=1982371)
        Pattern userIdPattern = Pattern.compile("(?<=user=)\\d*");
        Matcher matcher = userIdPattern.matcher(runnerDocument.toString());
        //if none is found, return a zero, that is an invalid user
        String userId = matcher.find() ? matcher.group(0) : "0";

        Document runsDocument = fetcher.fromUrl(Contract.runnerRunsUrl(userId));

        //inject the runs in the runner document
        runnerDocument.select("div.loadingbox").after(runsDocument.toString());
        runnerDocument.select("div.loadingbox").remove();

        return runnerDocument;
    }

}