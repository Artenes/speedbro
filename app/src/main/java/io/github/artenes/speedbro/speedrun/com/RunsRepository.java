package io.github.artenes.speedbro.speedrun.com;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

import io.github.artenes.speedbro.speedrun.com.models.LatestRun;
import io.github.artenes.speedbro.speedrun.com.models.Run;
import io.github.artenes.speedbro.speedrun.com.models.Runner;
import io.github.artenes.speedbro.speedrun.com.website.DocumentBuilder;
import io.github.artenes.speedbro.speedrun.com.website.DocumentFetcher;
import io.github.artenes.speedbro.speedrun.com.website.LatestRunsHtmlParser;
import io.github.artenes.speedbro.speedrun.com.website.RunHtmlParser;
import io.github.artenes.speedbro.speedrun.com.website.RunnerHtmlParser;

/**
 * The repository of runs
 */
public class RunsRepository {

    private final DocumentBuilder builder;

    public RunsRepository() {
        builder = new DocumentBuilder(new DocumentFetcher());
    }

    /**
     * Get the latest runs
     *
     * @return the latest runs from the website
     * @throws IOException if connection error occurs
     */
    public List<Run> getLatestRuns() throws IOException {
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

    /**
     * Get a runner
     *
     * @param id the id of the runner (its username)
     * @return the runner
     * @throws IOException if connection error occurs
     */
    public Runner getRunner(String id) throws IOException {
        Document document = builder.buildRunnerDocument(id);
        RunnerHtmlParser parser = new RunnerHtmlParser();
        return parser.parse(document);
    }

}