package io.github.artenes.speedbro.speedrun.com;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.github.artenes.speedbro.speedrun.com.api.Endpoints;
import io.github.artenes.speedbro.speedrun.com.models.Game;
import io.github.artenes.speedbro.speedrun.com.models.Run;
import io.github.artenes.speedbro.speedrun.com.models.Runner;
import io.github.artenes.speedbro.speedrun.com.models.SearchItem;
import io.github.artenes.speedbro.speedrun.com.website.DocumentBuilder;
import io.github.artenes.speedbro.speedrun.com.website.DocumentFetcher;
import io.github.artenes.speedbro.speedrun.com.website.GameHtmlParser;
import io.github.artenes.speedbro.speedrun.com.website.RunHtmlParser;
import io.github.artenes.speedbro.speedrun.com.website.RunnerHtmlParser;
import io.github.artenes.speedbro.speedrun.com.website.RunsHtmlParser;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The repository of runs
 */
public class RunsRepository {

    private final DocumentBuilder builder;
    private final Endpoints endpoints;

    public RunsRepository() {
        builder = new DocumentBuilder(new DocumentFetcher());

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Contract.AUTHORITY)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        endpoints = retrofit.create(Endpoints.class);
    }

    /**
     * Get the latest runs
     *
     * @return the latest runs from the website
     * @throws IOException if connection error occurs
     */
    public List<Run> getLatestRuns() throws IOException {
        Document document = Jsoup.connect(Contract.LATEST_RUNS).get();
        RunsHtmlParser parser = new RunsHtmlParser(RunsHtmlParser.Source.HOME_PAGE);
        return parser.parse(document);
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
        RunHtmlParser parser = new RunHtmlParser(runId);
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

    /**
     * Get a game
     *
     * @param id the id of the game (its abbreviation)
     * @return the found game
     * @throws IOException if connection error occurs
     */
    public Game getGameWithoutLeaderBoards(String id) throws IOException {
        Document document = Jsoup.connect(Contract.gameUrl(id)).get();
        GameHtmlParser parser = new GameHtmlParser();
        return parser.parse(document);
    }

    /**
     * Get a leader board from the given url
     *
     * @param url the url with the leader board
     * @return the list of runs from the leader board
     * @throws IOException if connection error occurs
     */
    public List<Run> getLeaderBoardDirectly(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        RunsHtmlParser parser = new RunsHtmlParser(RunsHtmlParser.Source.CATEGORIES);
        return parser.parse(document);
    }

    /**
     * Search for users or games
     *
     * @param query the query string
     * @return the list of results
     * @throws IOException if connection error occurs
     */
    public List<SearchItem> search(String query) throws IOException {
        List<SearchItem> filteredItems = new ArrayList<>();
        List<SearchItem> items = endpoints.search(query).execute().body();

        if (items == null || items.isEmpty()) {
            return filteredItems;
        }

        String lastCategory = "";
        for (SearchItem item : items) {
            //ignore some types of results
            if (item.isMore() || item.isNoResults() || item.isSeries()) {
                continue;
            }
            //create sections items to separate games from users
            //enters when the current item is in a different section from the previous
            if (!item.getCategory().equalsIgnoreCase(lastCategory)) {
                lastCategory = item.getCategory();
                filteredItems.add(SearchItem.makeSection(lastCategory));
            }
            filteredItems.add(item);
        }

        return filteredItems;
    }

}