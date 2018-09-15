package io.github.artenes.speedbro.speedrun.com;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import io.github.artenes.speedbro.speedrun.com.models.LatestRun;
import io.github.artenes.speedbro.speedrun.com.website.LatestRunsHtmlParser;

import static org.junit.Assert.*;

/**
 * Test for latest runs parser
 */
public class LatestRunsHtmlParserTest {

    private LatestRunsHtmlParser parser;

    @Before
    public void setUp() {
        parser = new LatestRunsHtmlParser();
    }

    @Test
    public void parseARunWithAllItsDataAvailable() {
        Document document = Jsoup.parse("<table><tr class=\"height-minimal\"><td class=\"top center gamecover\" rowspan=\"3\"><a href=\"/sk\"><img class=\"cover-tall-64 border\" src=\"/themes/sk/cover-128.png\" alt=\"\"></a></td><td colspan=\"100%\" class=\"gamename\"><a href=\"/sk\">Spiral Knights</a></td></tr><tr class=\"height-minimal linked\" data-target=\"/sk/run/mk9gw85z\" title=\"Assembly was pretty messy with the Quicksilvers in the last arena, along with one major mistake in Workshop. Twins pulling through to keep the run alive.\"><td class=\"center-sm\"><a href=\"/sk/Ironclaw_Munitions_Factory#Solo\">Ironclaw Munitions Factory: Solo - Elite</a><span class=\"hidden-sm hidden-md hidden-lg\"> &nbsp; 8<small>m </small>09<small>s</small></span></td><td class=\"nobr center hidden-xs\"><img class=\"trophy\" src=\"/themes/sk/1st.png\" alt=\"\">1st</td><td class=\"center hidden-xs\"><a class='nobr nounderline' href='/user/Tenka' ><img class=\"flagicon\" src=\"/images/flags/gb/eng.png\" title=\" England\"><span class='blackoutline username color4' ><span style=\"color:#8ac951\">T</span><span style=\"color:#69c45a\">e</span><span style=\"color:#49c063\">n</span><span style=\"color:#29bc6c\">k</span><span style=\"color:#09b876\">a</span></span></a></td><td class=\"center hidden-xs\">8<small>m </small>09<small>s</small></td></tr><tr><td class='filler'></td></tr></table>");

        List<LatestRun> latestRuns = parser.parseLatestRuns(document);
        LatestRun latestRun = latestRuns.get(0);

        assertEquals(1, latestRuns.size());

        assertEquals("mk9gw85z", latestRun.getId());

        assertEquals("sk", latestRun.getGameId());
        assertEquals("Spiral Knights", latestRun.getGameTitle());
        assertEquals("https://www.speedrun.com/themes/sk/cover-128.png", latestRun.getGameCover());

        assertEquals("Ironclaw Munitions Factory: Solo - Elite", latestRun.getCategory());
        assertEquals("1st", latestRun.getPosition());
        assertEquals("https://www.speedrun.com/themes/sk/1st.png", latestRun.getPositionIcon());
        assertEquals("8m 09s", latestRun.getTime());

        assertEquals("Tenka", latestRun.getRunnerId());
        assertEquals("Tenka", latestRun.getRunnerName());
        assertEquals("<span style=\"color:#8ac951\">T</span><span style=\"color:#69c45a\">e</span><span style=\"color:#49c063\">n</span><span style=\"color:#29bc6c\">k</span><span style=\"color:#09b876\">a</span>", latestRun.getRunnerDisplayName());
        assertEquals("https://www.speedrun.com/themes/user/Tenka/image.png", latestRun.getRunnerIcon());

        assertEquals("England", latestRun.getCountry());
        assertEquals("https://www.speedrun.com/images/flags/gb/eng.png", latestRun.getCountryIcon());
    }

    @Test
    public void parseAGameWithMultipleRuns() {
        Document document = Jsoup.parse("<html><head></head><body cz-shortcut-listen=\"true\"><table><tbody><tr class=\"height-minimal\"><td class=\"top center gamecover\" rowspan=\"4\"><a href=\"/mario_sonic_at_the_winter_olympic_games_\"><img class=\"cover-wide-64 border\" src=\"/themes/mario_sonic_at_the_winter_olympic_games_/cover-128.png\" alt=\"\"></a></td><td colspan=\"100%\" class=\"gamename\"><a href=\"/mario_sonic_at_the_winter_olympic_games_\">Mario &amp; Sonic at the Winter Olympic Games (DS)</a></td></tr><tr class=\"height-minimal linked\" data-target=\"/mario_sonic_at_the_winter_olympic_games_/run/z03rko8z\" title=\"\"><td class=\"center-sm\"><a href=\"/mario_sonic_at_the_winter_olympic_games_/Short_Track_500m#Any\">Short Track 500m: Any%</a><span class=\"hidden-sm hidden-md hidden-lg\"> &nbsp; 0<small>m </small>37<small>s </small>452<small>ms</small><span class=\"hidden-xs\"> (in-game time)</span></span></td><td class=\"nobr center hidden-xs\"><img class=\"trophy\" src=\"/themes/Default/1st.png\" alt=\"\">1st</td><td class=\"center hidden-xs\"><a class=\"nobr nounderline\" href=\"/user/Spielpro\"><img class=\"flagicon\" src=\"/images/flags/lu.png\" title=\" Luxembourg\"><span class=\"blackoutline username color3\">Spielpro</span></a></td><td class=\"center hidden-xs\">0<small>m </small>37<small>s </small>452<small>ms</small><span class=\"hidden-xs\"> (in-game time)</span></td></tr><tr class=\"height-minimal linked\" data-target=\"/mario_sonic_at_the_winter_olympic_games_/run/y2qxr0wy\" title=\"\"><td class=\"center-sm\"><a href=\"/mario_sonic_at_the_winter_olympic_games_/Speed_Skating_500m#Any\">Speed Skating 500m: Any%</a><span class=\"hidden-sm hidden-md hidden-lg\"> &nbsp; 0<small>m </small>28<small>s </small>892<small>ms</small><span class=\"hidden-xs\"> (in-game time)</span></span></td><td class=\"nobr center hidden-xs\"><img class=\"trophy\" src=\"/themes/Default/2nd.png\" alt=\"\">2nd</td><td class=\"center hidden-xs\"><a class=\"nobr nounderline\" href=\"/user/Spielpro\"><img class=\"flagicon\" src=\"/images/flags/lu.png\" title=\" Luxembourg\"><span class=\"blackoutline username color3\">Spielpro</span></a></td><td class=\"center hidden-xs\">0<small>m </small>28<small>s </small>892<small>ms</small><span class=\"hidden-xs\"> (in-game time)</span></td></tr><tr><td class=\"filler\"></td></tr></tbody></table></body></html>");
        List<LatestRun> latestRuns = parser.parseLatestRuns(document);

        assertEquals(2, latestRuns.size());

        LatestRun firstRun = latestRuns.get(0);
        LatestRun secondRun = latestRuns.get(1);

        assertEquals("Mario & Sonic at the Winter Olympic Games (DS)", firstRun.getGameTitle());
        assertEquals("Mario & Sonic at the Winter Olympic Games (DS)", secondRun.getGameTitle());
    }

    @Test
    public void parseARunWithRunnerThatHasNoUser() {
        Document document = Jsoup.parse("<table><tr class=\"height-minimal\"><td class=\"top center gamecover\" rowspan=\"3\"><a href=\"/sk\"><img class=\"cover-tall-64 border\" src=\"/themes/sk/cover-128.png\" alt=\"\"></a></td><td colspan=\"100%\" class=\"gamename\"><a href=\"/sk\">Spiral Knights</a></td></tr><tr class=\"height-minimal linked\" data-target=\"/sk/run/mk9gw85z\" title=\"Assembly was pretty messy with the Quicksilvers in the last arena, along with one major mistake in Workshop. Twins pulling through to keep the run alive.\"><td class=\"center-sm\"><a href=\"/sk/Ironclaw_Munitions_Factory#Solo\">Ironclaw Munitions Factory: Solo - Elite</a><span class=\"hidden-sm hidden-md hidden-lg\"> &nbsp; 8<small>m </small>09<small>s</small></span></td><td class=\"nobr center hidden-xs\"><img class=\"trophy\" src=\"/themes/sk/1st.png\" alt=\"\">1st</td><td class=\"center hidden-xs\">Tenka</td><td class=\"center hidden-xs\">8<small>m </small>09<small>s</small></td></tr><tr><td class='filler'></td></tr></table>");

        List<LatestRun> latestRuns = parser.parseLatestRuns(document);
        LatestRun latestRun = latestRuns.get(0);

        assertFalse(latestRun.hasRunnerId());
        assertTrue(latestRun.getCountry().isEmpty());
        assertTrue(latestRun.getCountryIcon().isEmpty());
    }

    @Test
    public void ignoreHtmlTableThatDoesNotFollowStructure() {
        Document document = Jsoup.parse("<html><body><table><tbody><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr></tbody></table></body></html>");

        List<LatestRun> latestRuns = parser.parseLatestRuns(document);

        assertTrue(latestRuns.isEmpty());
    }

}