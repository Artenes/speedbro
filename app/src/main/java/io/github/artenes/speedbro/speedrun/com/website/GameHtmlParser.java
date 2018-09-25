package io.github.artenes.speedbro.speedrun.com.website;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.github.artenes.speedbro.speedrun.com.Contract;
import io.github.artenes.speedbro.speedrun.com.Utils;
import io.github.artenes.speedbro.speedrun.com.models.Category;
import io.github.artenes.speedbro.speedrun.com.models.Game;

/**
 * Parse the data of a game from a HTML page
 */
public class GameHtmlParser implements Parser<Game> {

    private static final int AMOUNT_OF_PARAGRAPHS_TO_HAVE_SERIES = 3;

    @Override
    public Game parse(Document document) {

        String gameId = Utils.lastSegmentOfUri(document.select("h5 a").attr("href"));

        List<Category> categories = new ArrayList<>();
        Elements categoryListItems = document.select("ul.nav.nav-tabs li.category");
        for (Element listItem : categoryListItems) {
            //ignore empty categories
            if (listItem.selectFirst("a.empty") != null) {
                continue;
            }
            String id = listItem.attr("id");
            categories.add(new Category(
                    listItem.select("a").text(),
                    Contract.leaderBoardUrl(gameId, id.replace("category", ""))
            ));
        }

        //identify if the game has a series or not by the number of paragraphs in a div element
        //that holds all the the game data
        boolean hasSeries = document.select("div#sidebar small p").size() == AMOUNT_OF_PARAGRAPHS_TO_HAVE_SERIES;

        String indexForYear = hasSeries ? "2" : "1";

        //search for the game info
        return Game.build()
                .withId(gameId)
                .withTitle(document.select("h5").text())
                .withCover(Contract.asAbsolutePath(document.select("div#sidebar div.sidebar p img").attr("src")))
                .withYear(document.select("div#sidebar small p:nth-child(" + indexForYear + ")").text())
                .withPlatforms(document.select("div#sidebar small p:last-child").text())
                .withCategories(categories)
                .build();

    }

}