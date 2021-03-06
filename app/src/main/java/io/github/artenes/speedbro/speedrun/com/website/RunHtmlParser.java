package io.github.artenes.speedbro.speedrun.com.website;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.github.artenes.speedbro.speedrun.com.Contract;
import io.github.artenes.speedbro.speedrun.com.Utils;
import io.github.artenes.speedbro.speedrun.com.models.Game;
import io.github.artenes.speedbro.speedrun.com.models.Placement;
import io.github.artenes.speedbro.speedrun.com.models.Run;
import io.github.artenes.speedbro.speedrun.com.models.Runner;
import io.github.artenes.speedbro.speedrun.com.models.Video;

/**
 * Parser that extracts run information from a HTML page
 */
public class RunHtmlParser implements Parser<Run> {

    private final String runId;
    private final GameHtmlParser gameParser;

    public RunHtmlParser(String runId) {
        gameParser = new GameHtmlParser();
        this.runId = runId;
    }

    /**
     * Parse a run from the given document
     *
     * @param document the HTML page
     * @return the run found in the page. If nothing was found, a run with empty attributes will be returned
     */
    @Override
    public Run parse(Document document) {

        //search for the game info
        Game game = gameParser.parse(document);

        //search for the video info
        String videoUrl = document.select("iframe").attr("src");
        Video video = new Video(videoUrl);

        //search for the runners info
        List<Runner> runners = new ArrayList<>();
        Elements runnersAnchors = document.select("div#main div.maincontent div.panel p:nth-child(2) a:not(:first-child)");
        for (Element anchor : runnersAnchors) {
            String id = Utils.lastSegmentOfUri(anchor.attr("href"));
            runners.add(Runner.Builder.aRunner()
                    .withId(id)
                    .withName(anchor.text())
                    .withFlag(Contract.asAbsolutePath(anchor.select("img.flagicon").attr("src")))
                    .withCountry(anchor.select("img.flagicon").attr("title").trim())
                    .withIcon(Contract.runnerAvatar(id))
                    .build());
        }

        //search for the placement info
        Placement placement = new Placement(
                Utils.getFirstWordOfSentence(document.select("div#main div.maincontent div.panel p:nth-child(2) span.nobr").text()),
                Contract.asAbsolutePath(document.select("div#main div.maincontent div.panel p:nth-child(2) span.nobr img").attr("src")));

        //build the run
        //also search for its category, time and commentary
        return Run.Builder.aRun()
                .withId(runId)
                .withCommentary(document.select("div[title='Description from the player']").text())
                .withCategory(document.select("div#main div.maincontent div.panel p:nth-child(2) a:nth-child(1) strong:nth-child(1)").text())
                .withTime(document.select("div#main div.maincontent div.panel p:nth-child(2) strong:nth-child(2)").text())
                .withGame(game)
                .withVideo(video)
                .withRunners(runners)
                .withPlacement(placement)
                .build();

    }

}