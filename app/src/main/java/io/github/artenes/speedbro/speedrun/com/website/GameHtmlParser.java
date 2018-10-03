package io.github.artenes.speedbro.speedrun.com.website;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String categoryBaseArguments = getCategoryBaseArguments(document);
        for (Element listItem : categoryListItems) {
            String id = listItem.attr("id");
            categories.add(new Category(
                    listItem.select("a").text(),
                    Contract.leaderBoardUrl(gameId, id.replace("category", ""), categoryBaseArguments)
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
                .withBackground(Contract.gameBackgroundImage(gameId))
                .withCategories(categories)
                .build();

    }

    /**
     * Gets all arguments that should be present in the categories urls
     *
     * @param document the current document being parsed
     * @return any argument if found in the document
     */
    private String getCategoryBaseArguments(Document document) {

        StringBuilder arguments = new StringBuilder();

        //in the html there is a form element that contains all this arguments
        //we get the inputs that contains the names and values of these arguments
        //and some script tags that also contains some arguments
        Elements inputs = document.select("form#leaderboardform input");
        Elements scripts = document.select("form#leaderboardform script");

        //these patterns are used to extract the data from the script tags above
        Pattern variableNamePattern = Pattern.compile("(?<=#)\\w+");
        Pattern variableValuePattern = Pattern.compile("(?<=val\\()\\d+");

        for (Element input : inputs) {
            //ignore inputs with no value
            if (!input.hasAttr("value")) {
                continue;
            }
            String argument = input.attr("name");
            //ignore the game and category arguments since these two are appended in the render method to the arguments list
            if (argument.equalsIgnoreCase("game") || argument.equalsIgnoreCase("category")) {
                continue;
            }
            //argument=value&
            arguments.append(argument).append("=").append(input.attr("value")).append("&");
        }

        for (Element script : scripts) {
            String js = script.text();

            //use the regex to get the attribute name and value
            Matcher variableNameMatch = variableNamePattern.matcher(js);
            Matcher variableValueMatch = variableValuePattern.matcher(js);

            //if one of them were not found, continue
            if (!variableNameMatch.find() || !variableValueMatch.find()) {
                continue;
            }
            //argument=value&
            arguments.append(variableNameMatch.group()).append("=").append(variableValueMatch.group()).append("&");
        }

        if (arguments.length() > 0) {
            //since we were adding '&' in the end of the arguments list
            //we have to remove the final one
            arguments.deleteCharAt(arguments.length() - 1);
        }

        return arguments.toString();
    }

}