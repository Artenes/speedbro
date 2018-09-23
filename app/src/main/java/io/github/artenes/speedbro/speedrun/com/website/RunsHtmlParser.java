package io.github.artenes.speedbro.speedrun.com.website;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.artenes.speedbro.speedrun.com.Contract;
import io.github.artenes.speedbro.speedrun.com.Utils;
import io.github.artenes.speedbro.speedrun.com.models.Game;
import io.github.artenes.speedbro.speedrun.com.models.Placement;
import io.github.artenes.speedbro.speedrun.com.models.Run;
import io.github.artenes.speedbro.speedrun.com.models.Runner;

/**
 * A parser that gets an HTML page with runs and creates a list of POJOs
 * with the info of these runs. We had to scrap the website for these
 * because the endpoint to return the latest runs was taking
 * up to 20 seconds to respond (2018-09-11).
 */
public class RunsHtmlParser implements Parser<List<Run>> {

    private final Source sourceOfDocument;

    public RunsHtmlParser(Source sourceOfDocument) {
        this.sourceOfDocument = sourceOfDocument;
    }

    /**
     * Parse the runs from the given document.
     *
     * @param document the document with the element of the HTML page
     * @return the list of runs, if any was found
     */
    @Override
    public List<Run> parse(Document document) {

        //we assume that the given page contains only one table that contains the list of latest runs
        Elements rows = document.select("table tbody tr");

        //the list that will hold the found runs
        List<Run> latestRuns = new ArrayList<>();

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

                Element gameRow = runElements.get(0);

                //we first get the game details because one game can have multiple runs in this table
                Game game = Game.Builder.aGame()
                        .withId(Utils.withoutStartingSlash(gameRow.select("td:nth-child(2) a").attr("href")))
                        .withTitle(gameRow.select("td:nth-child(2) a").text())
                        .withCover(Contract.asAbsolutePath(gameRow.select("td:nth-child(1) a img").attr("src")))
                        .build();

                //then for the next rows, until we reach the "filler" one, we have the runs for the game
                for (int index = 1; index < runElements.size(); index++) {
                    Element runRow = runElements.get(index);

                    //this checks if the table has data from the homepage or not
                    String runTimeIndex = sourceOfDocument == Source.HOME_PAGE ? "4" : "3";

                    Placement placement = Placement.Builder.aPlacement()
                            .withPlace(runRow.select("td:nth-child(2)").text())
                            .withIcon(Contract.asAbsolutePath(runRow.select("td:nth-child(2) img").attr("src")))
                            .build();

                    Runner runner;
                    if (sourceOfDocument == Source.HOME_PAGE) {
                        String runnerId = Utils.lastSegmentOfUri(runRow.select("td:nth-child(3) a").attr("href"));
                        runner = Runner.Builder.aRunner()
                                .withId(runnerId)
                                .withName(runRow.select("td:nth-child(3)").text())
                                .withIcon(Contract.runnerAvatar(runnerId))
                                .withCountry(runRow.select("td:nth-child(3) a img.flagicon").attr("title").trim())
                                .withFlag(Contract.asAbsolutePath(runRow.select("td:nth-child(3) a img.flagicon").attr("src")))
                                .build();
                    } else {
                        runner = Runner.Builder.aRunner().build();
                    }

                    //noinspection ArraysAsListWithZeroOrOneArgument
                    Run run = Run.Builder.aRun()
                            .withId(Utils.lastSegmentOfUri(runRow.attr("data-target")))
                            .withCategory(runRow.select("td:nth-child(1) a").text())
                            .withTime(runRow.select("td:nth-child(" + runTimeIndex + ")").text())
                            .withGame(game)
                            .withPlacement(placement)
                            .withRunners(Arrays.asList(runner))
                            .build();

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
        return row.selectFirst("td.gamecover") != null || row.selectFirst("td.nobr.hidden-xs") != null;

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