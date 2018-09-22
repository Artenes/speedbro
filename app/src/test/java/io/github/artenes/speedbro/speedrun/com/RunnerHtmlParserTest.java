package io.github.artenes.speedbro.speedrun.com;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import io.github.artenes.speedbro.TestUtils;
import io.github.artenes.speedbro.speedrun.com.models.Run;
import io.github.artenes.speedbro.speedrun.com.models.Runner;
import io.github.artenes.speedbro.speedrun.com.models.SocialMedia;
import io.github.artenes.speedbro.speedrun.com.website.RunnerHtmlParser;

import static org.junit.Assert.assertEquals;

/**
 * Test for RunnerHtmlParser class
 */
public class RunnerHtmlParserTest {

    private RunnerHtmlParser parser;
    private Runner runner;

    @Before
    public void setUp() throws IOException {
        parser = new RunnerHtmlParser();
        File file = TestUtils.getFile(this, "runner.html");
        Document fullDocument = Jsoup.parse(file, "UTF-8");
        runner = parser.parse(fullDocument);
    }

    @Test
    public void extractRunnerDetails() {
        assertEquals("SolidSpiderZnake", runner.getName());
        assertEquals("https://www.speedrun.com/themes/user/SolidSpiderZnake/image.png", runner.getIcon());
        assertEquals("https://www.speedrun.com/images/flags/sa.png", runner.getFlag());
        assertEquals("Saudi Arabia", runner.getCountry());
    }

    @Test
    public void extractSocialMediaIcons() {
        assertEquals(4, runner.getSocialMedias().size());

        SocialMedia twitch = runner.getSocialMedias().get(0);
        assertEquals("https://www.speedrun.com/images/socialmedia/twitch.png", twitch.getIcon());
        assertEquals("https://www.twitch.tv/solidspiderznake", twitch.getLink());

        SocialMedia twitter = runner.getSocialMedias().get(1);
        assertEquals("https://www.speedrun.com/images/socialmedia/twitter.png", twitter.getIcon());
        assertEquals("https://www.twitter.com/Soliduz_Znake", twitter.getLink());

        SocialMedia youtube = runner.getSocialMedias().get(2);
        assertEquals("https://www.speedrun.com/images/socialmedia/youtube.png", youtube.getIcon());
        assertEquals("https://www.youtube.com/channel/UCXPQI3XTNkqkrDKWWqkAxig%3Fdisable_polymertrue", youtube.getLink());

        SocialMedia steam = runner.getSocialMedias().get(3);
        assertEquals("https://www.speedrun.com/images/socialmedia/steam.png", steam.getIcon());
        assertEquals("https://steamcommunity.com/profiles/76561198007394891", steam.getLink());
    }

    @Test
    public void extractRunsList() {
        //there are 43 runs
        assertEquals(43, runner.getRuns().size());

        //but we just check some of them
        Run godFatherBankRobberies = runner.getRuns().get(0);
        assertEquals("y23j816m", godFatherBankRobberies.getId());
        assertEquals("the_godfather_dons_edition_", godFatherBankRobberies.getGame().getId());
        assertEquals("The Godfather: The Don's Edition", godFatherBankRobberies.getGame().getTitle());
        assertEquals("Miscellaneous - All Bank Robberies", godFatherBankRobberies.getCategory());
        assertEquals("1st", godFatherBankRobberies.getPlacement().getPlace());
        assertEquals("https://www.speedrun.com/themes/the_godfather_dons_edition_/1st.png", godFatherBankRobberies.getPlacement().getIcon());
        assertEquals("22m 06s", godFatherBankRobberies.getTime());

        Run godFatherAnyPercent = runner.getRuns().get(3);
        assertEquals("z135wkrm", godFatherAnyPercent.getId());
        assertEquals("the_godfather_dons_edition_", godFatherAnyPercent.getGame().getId());
        assertEquals("The Godfather: The Don's Edition", godFatherAnyPercent.getGame().getTitle());
        assertEquals("Any%", godFatherAnyPercent.getCategory());
        assertEquals("-", godFatherAnyPercent.getPlacement().getPlace());
        assertEquals("", godFatherAnyPercent.getPlacement().getIcon());
        assertEquals("3h 02m 35s", godFatherAnyPercent.getTime());

        Run spiderManAnyPercent = runner.getRuns().get(11);
        assertEquals("zgnpk3ey", spiderManAnyPercent.getId());
        assertEquals("smsd", spiderManAnyPercent.getGame().getId());
        assertEquals("Spider-Man: Shattered Dimensions", spiderManAnyPercent.getGame().getTitle());
        assertEquals("NG+, Any% Normal", spiderManAnyPercent.getCategory());
        assertEquals("1st", spiderManAnyPercent.getPlacement().getPlace());
        assertEquals("https://www.speedrun.com/themes/smsd/1st.png", spiderManAnyPercent.getPlacement().getIcon());
        assertEquals("3h 56m 52s", spiderManAnyPercent.getTime());

        Run gexAnyPercent = runner.getRuns().get(18);
        assertEquals("zpq8o6xy", gexAnyPercent.getId());
        assertEquals("gex1", gexAnyPercent.getGame().getId());
        assertEquals("Gex", gexAnyPercent.getGame().getTitle());
        assertEquals("Any% - Console", gexAnyPercent.getCategory());
        assertEquals("4th", gexAnyPercent.getPlacement().getPlace());
        assertEquals("", gexAnyPercent.getPlacement().getIcon());
        assertEquals("1h 07m 13s", gexAnyPercent.getTime());
    }

    @Test
    public void extractNothingFromEmptyDocument() {
        Runner emptyRunner = parser.parse(Jsoup.parse(""));
        assertEquals("", emptyRunner.getName());
        assertEquals("", emptyRunner.getIcon());
        assertEquals(0, emptyRunner.getRuns().size());
    }

    @Test
    public void extractRunnerDetailsEvenIfTheUserHasNoImage() throws IOException {
        File file = TestUtils.getFile(this, "runner_without_image.html");
        Document document = Jsoup.parse(file, "UTF-8");
        Runner runner = parser.parse(document);

        assertEquals("SolidSpiderZnake", runner.getName());
        assertEquals("", runner.getIcon());
        assertEquals("https://www.speedrun.com/images/flags/sa.png", runner.getFlag());
        assertEquals("Saudi Arabia", runner.getCountry());
    }

    @Test
    public void extractRunnerDetailsThatHasOnlyTheName() throws IOException {
        File file = TestUtils.getFile(this, "runner_with_just_the_name.html");
        Document document = Jsoup.parse(file, "UTF-8");
        Runner runner = parser.parse(document);

        assertEquals("SolidSpiderZnake", runner.getName());
        assertEquals("", runner.getIcon());
        assertEquals("", runner.getFlag());
        assertEquals("", runner.getCountry());
        assertEquals(0, runner.getSocialMedias().size());
    }

}