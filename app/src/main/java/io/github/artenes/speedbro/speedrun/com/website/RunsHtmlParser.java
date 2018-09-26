package io.github.artenes.speedbro.speedrun.com.website;

import org.jsoup.nodes.Document;

import java.util.List;

import io.github.artenes.speedbro.speedrun.com.models.Run;

/**
 * A parser that gets an HTML page with runs and creates a list of POJOs
 * with the info of these runs. We had to scrap the website for these
 * because the endpoint to return the latest runs was taking
 * up to 20 seconds to respond (2018-09-11).
 */
public class RunsHtmlParser implements Parser<List<Run>> {

    private final CategoryRunsHtmlParser categoryRunsHtmlParser;
    private final SimpleRunsHtmlParser simpleRunsHtmlParser;
    private final Source sourceOfDocument;


    public RunsHtmlParser(Source sourceOfDocument) {
        this.sourceOfDocument = sourceOfDocument;
        categoryRunsHtmlParser = new CategoryRunsHtmlParser();
        simpleRunsHtmlParser = new SimpleRunsHtmlParser(sourceOfDocument == Source.HOME_PAGE);
    }

    /**
     * Parse the runs from the given document.
     *
     * @param document the document with the element of the HTML page
     * @return the list of runs, if any was found
     */
    @Override
    public List<Run> parse(Document document) {

        if (sourceOfDocument == Source.CATEGORIES) {
            return categoryRunsHtmlParser.parse(document);
        } else {
            return simpleRunsHtmlParser.parse(document);
        }

    }

    /**
     * Various parts of the website display a list of runs
     * and each one of them display them in a different manner
     * so we have to indicate to the parser which logic to use to parse the data
     */
    public enum Source {

        HOME_PAGE,
        RUNNER,
        CATEGORIES

    }

}