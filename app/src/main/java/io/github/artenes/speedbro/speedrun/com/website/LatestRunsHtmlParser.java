package io.github.artenes.speedbro.speedrun.com.website;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.github.artenes.speedbro.speedrun.com.Contract;
import io.github.artenes.speedbro.speedrun.com.Utils;
import io.github.artenes.speedbro.speedrun.com.models.LatestRun;

/**
 * A parser that gets an HTML page with the latest runs available
 * at the homepage of the website and creates a list of POJOs
 * with the info of these runs. We had to scrap the website for these
 * because the endpoint to return the latest runs was taking
 * up to 20 seconds to respond (2018-09-11).
 */
public class LatestRunsHtmlParser {

    /**
     * Parse the runs from the given document.
     *
     * @param document the document with the element of the HTML page
     * @return the list of runs, if any was found
     */
    public List<LatestRun> parseLatestRuns(Document document) {

        //we assume that the given page contains only one table that contains the list of latest runs
        Elements rows = document.select("table tbody tr");

        //the list that will hold the found runs
        List<LatestRun> latestRuns = new ArrayList<>();

        //the runs are organized in a table
        //each row represent either the game or the run of that game
        //these are separated by a row with a class "filler"
        //we pass through all the rows until we find this "filler" row
        //then we parse all the previous rows that we've got up until now
        //finally the process repeat until there are no more rows
        List<Element> runElements = new ArrayList<>();

        for (Element row : rows) {

            if (row.selectFirst("td.filler") != null) {

                //if there are no items from the previous iteration, keep going
                if (runElements.size() == 0) {
                    continue;
                }

                //we first get the game details because one game can have multiple runs in this table
                String gameTitle = runElements.get(0).selectFirst("td:nth-child(2) a").text();
                String gameId = Utils.withoutStartingSlash(runElements.get(0).selectFirst("td:nth-child(2) a").attr("href"));
                String gameCover = Contract.asAbsolutePath(runElements.get(0).selectFirst("td:nth-child(1) a img").attr("src"));

                //then for the next rows, until we reach the "filler" one, we have the runs for the game
                for (int index = 1; index < runElements.size(); index++) {
                    Element runRow = runElements.get(index);

                    LatestRun run = new LatestRun();
                    run.setId(Utils.lastSegmentOfUri(runRow.attr("data-target")));
                    run.setGameId(gameId);
                    run.setRunnerId(Utils.lastSegmentOfUri(runRow.select("td:nth-child(3) a").attr("href")));
                    run.setGameTitle(gameTitle);
                    run.setGameCover(gameCover);
                    run.setCategory(runRow.select("td:nth-child(1) a").text());
                    run.setPosition(runRow.select("td:nth-child(2)").text());
                    run.setPositionIcon(Contract.asAbsolutePath(runRow.select("td:nth-child(2) img").attr("src")));
                    run.setRunnerDisplayName(runRow.select("td:nth-child(3) a span.username").html());
                    run.setRunnerIcon(Contract.runnerAvatar(run.getRunnerId()));
                    run.setCountry(runRow.select("td:nth-child(3) a img.flagicon").attr("title").trim());
                    run.setCountryIcon(Contract.asAbsolutePath(runRow.select("td:nth-child(3) a img.flagicon").attr("src")));
                    run.setTime(runRow.select("td:nth-child(4)").text());

                    //if the runner is just a guest, it does not have a link to its profile
                    run.setRunnerName(runRow.select("td:nth-child(3) a span.username").text());
                    if (!run.hasRunnerName()) {
                        run.setRunnerName(runRow.select("td:nth-child(3)").text());
                    }

                    latestRuns.add(run);
                }

                //when we reach the "filler", we reach the end, so clear the accumulated rows from precious iterations
                runElements.clear();

                continue;
            }

            //only add rows to the list if they are from a game or a run
            if (isAGameOrRunRow(row)) {
                runElements.add(row);
            }

        }

        return latestRuns;

    }

    /**
     * Checks if the run is of a run or a game.
     *
     * @param row the row to check
     * @return true if is either from a game or a run
     */
    private boolean isAGameOrRunRow(Element row) {

        //actually the second check might be not so good for the run (this is just a generic class)
        //but it does not contain any id or class name that identifies that row as a run one.
        return row.selectFirst("td.gamecover") != null || row.selectFirst("td.nobr.center.hidden-xs") != null;

    }

}