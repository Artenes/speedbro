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
import io.github.artenes.speedbro.speedrun.com.models.Platform;
import io.github.artenes.speedbro.speedrun.com.models.Run;
import io.github.artenes.speedbro.speedrun.com.models.Runner;

/**
 * Parses a list of runs from a category HTML page
 */
public class CategoryRunsHtmlParser implements Parser<List<Run>> {

    private static final String INVALID_SELECTOR_INDEX = "-1";

    @Override
    public List<Run> parse(Document document) {
        //select the first table of the document
        Element header = document.selectFirst("table tbody tr");

        //if there is no table, return empty list
        if (header == null) {
            return new ArrayList<>();
        }

        //since we don't which columns might exist in this table
        //we will search for the index of the ones we need to them
        //start extracting the data
        String rankIndex = INVALID_SELECTOR_INDEX;
        String playerIndex = INVALID_SELECTOR_INDEX;
        String realTimeIndex = INVALID_SELECTOR_INDEX;
        String inGameTimeIndex = INVALID_SELECTOR_INDEX;
        String platformIndex = INVALID_SELECTOR_INDEX;
        String dateIndex = INVALID_SELECTOR_INDEX;

        Elements headers = header.select("th");
        int currentIndex = 0;
        boolean evaluatedTimes = false;

        //first pass through all the headers to find the index of the ones we need
        for (Element title : headers) {

            currentIndex++;

            if (title.text().equalsIgnoreCase("Rank")) {
                rankIndex = String.valueOf(currentIndex);
                continue;
            }

            if (title.text().equalsIgnoreCase("Player")) {
                playerIndex = String.valueOf(currentIndex);
                continue;
            }

            if (title.text().equalsIgnoreCase("Real time") || title.text().equalsIgnoreCase("time") || title.text().equalsIgnoreCase("Time with loads")) {
                realTimeIndex = String.valueOf(currentIndex);
                continue;
            }

            if (title.text().equalsIgnoreCase("In-game time") || title.text().equalsIgnoreCase("Time without loads")) {
                inGameTimeIndex = String.valueOf(currentIndex);
                continue;
            }

            //an index must be skipped due to a duplication in the last time column
            if ((!realTimeIndex.equalsIgnoreCase(INVALID_SELECTOR_INDEX) || !inGameTimeIndex.equalsIgnoreCase(INVALID_SELECTOR_INDEX)) && !evaluatedTimes) {
                evaluatedTimes = true;
                currentIndex++;
            }

            if (title.text().equalsIgnoreCase("Platform")) {
                platformIndex = String.valueOf(currentIndex);
                continue;
            }

            if (title.text().equalsIgnoreCase("Date")) {
                dateIndex = String.valueOf(currentIndex);
            }

        }

        List<Run> runs = new ArrayList<>();

        //now that we have the indexes for each column, we just fetch the data
        for (Element runRow : document.select("table tbody tr.linked")) {

            //prepare the run to receive the data
            Run.Builder currentRun = Run.build();
            currentRun.withId(Utils.lastSegmentOfUri(runRow.attr("data-target")));
            //add the id of the game so we know from where this run came
            currentRun.withGame(Game.build()
                    .withId(Utils.firstSegmentOfUri(runRow.attr("data-target")))
                    .build()
            );

            //if the index is different from the invalid one, it means the index
            //was found and that we have to extract data from this column
            if (!rankIndex.equals(INVALID_SELECTOR_INDEX)) {
                Elements td = runRow.select("td:nth-child(" + rankIndex + ")");
                Placement placement = Placement.Builder.aPlacement()
                        .withPlace(td.text())
                        .withIcon(Contract.asAbsolutePath(td.select("img.trophy").attr("src")))
                        .build();
                currentRun.withPlacement(placement);
            }

            if (!playerIndex.equals(INVALID_SELECTOR_INDEX)) {
                Elements td = runRow.select("td:nth-child(" + playerIndex + ")");
                Runner runner = Runner.build()
                        .withId(Utils.lastSegmentOfUri(td.select("a").attr("href")))
                        .withName(td.text())
                        .withCountry(td.select("img.flagicon").attr("title").trim())
                        .withFlag(Contract.asAbsolutePath(td.select("img.flagicon").attr("src")))
                        .build();
                //noinspection ArraysAsListWithZeroOrOneArgument
                currentRun.withRunners(Arrays.asList(runner));
            }

            if (!realTimeIndex.equals(INVALID_SELECTOR_INDEX)) {
                Elements td = runRow.select("td:nth-child(" + realTimeIndex + ")");
                currentRun.withTime(td.text());
            }

            if (!inGameTimeIndex.equals(INVALID_SELECTOR_INDEX)) {
                Elements td = runRow.select("td:nth-child(" + inGameTimeIndex + ")");
                currentRun.withInGameTime(td.text());
            }

            if (!platformIndex.equals(INVALID_SELECTOR_INDEX)) {
                Elements td = runRow.select("td:nth-child(" + platformIndex + ")");
                Platform platform = new Platform(
                        td.text(),
                        td.select("img.flagicon").attr("title"),
                        Contract.asAbsolutePath(td.select("img.flagicon").attr("src"))
                );
                currentRun.withPlatform(platform);
            }

            if (!dateIndex.equals(INVALID_SELECTOR_INDEX)) {
                Elements td = runRow.select("td:nth-child(" + dateIndex + ")");
                currentRun.withDate(td.text());
            }

            runs.add(currentRun.build());

        }
        return runs;
    }

}