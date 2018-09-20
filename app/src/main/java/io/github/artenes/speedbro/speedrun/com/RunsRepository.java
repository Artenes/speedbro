package io.github.artenes.speedbro.speedrun.com;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

import io.github.artenes.speedbro.speedrun.com.models.LatestRun;
import io.github.artenes.speedbro.speedrun.com.models.Run;
import io.github.artenes.speedbro.speedrun.com.website.LatestRunsHtmlParser;
import io.github.artenes.speedbro.speedrun.com.website.RunHtmlParser;

/**
 * The repository of runs
 */
public class RunsRepository {

    /**
     * Get the latest runs
     *
     * @return the latest runs from the website
     * @throws IOException if connection error occurs
     */
    public List<LatestRun> getLatestRuns() throws IOException {
        Document document = Jsoup.connect(Contract.LATEST_RUNS).get();
        LatestRunsHtmlParser parser = new LatestRunsHtmlParser();
        return parser.parseLatestRuns(document);
    }

    /**
     * Get a run
     *
     * @param gameId the game id
     * @param runId  the run id
     * @return the Run
     * @throws IOException if connection error occurs
     */
    public Run getRun(String gameId, String runId) throws IOException {
        Document document = Jsoup.connect(Contract.runUrl(gameId, runId)).get();
        RunHtmlParser parser = new RunHtmlParser();
        return parser.parse(document);
    }

}