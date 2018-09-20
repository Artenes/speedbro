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
public class RunHtmlParser {

    /**
     * Parse a run from the given document
     *
     * @param document the HTML page
     * @return the run found in the page. If nothing was found, a run with empty attributes will be returned
     */
    public Run parse(Document document) {

        //search for the game info
        Game game = Game.Builder.aGame()
                .withId(Utils.lastSegmentOfUri(document.select("h5 a").attr("href")))
                .withTitle(document.select("h5").text())
                .withCover(Contract.asAbsolutePath(document.select("div#sidebar div.sidebar p img").attr("src")))
                .withYear(document.select("div#sidebar small p:nth-child(1)").text())
                .withPlatforms(document.select("div#sidebar small p:nth-child(2)").text())
                .build();

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
        Placement placement = Placement.Builder.aPlacement()
                .withPlace(Utils.getFirstWordOfSentence(document.select("div#main div.maincontent div.panel p:nth-child(2) span.nobr").text()))
                .withIcon(Contract.asAbsolutePath(document.select("div#main div.maincontent div.panel p:nth-child(2) span.nobr img").attr("src")))
                .build();

        //build the run
        //also search for its category, time and commentary
        return Run.Builder.aRun()
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