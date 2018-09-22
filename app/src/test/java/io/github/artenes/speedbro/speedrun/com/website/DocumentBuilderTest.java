package io.github.artenes.speedbro.speedrun.com.website;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;

import io.github.artenes.speedbro.TestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test for the DocumentBuilder class
 */
public class DocumentBuilderTest {

    private DocumentFetcher fetcher;
    private DocumentBuilder builder;

    @Before
    public void setUp() {
        fetcher = Mockito.mock(DocumentFetcher.class);
        builder = new DocumentBuilder(fetcher);
    }

    @Test
    public void mergeRunnerDetailsWithItsRuns() throws IOException {
        File runner = TestUtils.getFile(this, "runner_with_unloaded_runs.html");
        File runs = TestUtils.getFile(this, "runner_runs.html");

        Document runnerDocument = Jsoup.parse(runner, "UTF-8");
        Document runsDocument = Jsoup.parse(runs, "UTF-8");

        Mockito.when(fetcher.fromUrl("https://www.speedrun.com/user/SolidSpiderZnake")).thenReturn(runnerDocument);
        Mockito.when(fetcher.fromUrl("https://www.speedrun.com/ajax_userleaderboard.php?user=4269")).thenReturn(runsDocument);

        Document fullDocument = builder.buildRunnerDocument("SolidSpiderZnake");
        assertEquals(75, fullDocument.select("table tbody tr").size());
        assertNull(fullDocument.selectFirst("div.loadingbox"));
    }

    @Test
    public void mergeRunnerDetailsWithItsRunsWhenAUserWasNotFound() throws IOException {
        File runner = TestUtils.getFile(this, "runner_with_unloaded_runs_and_user.html");
        File runs = TestUtils.getFile(this, "runner_runs.html");

        Document runnerDocument = Jsoup.parse(runner, "UTF-8");
        Document runsDocument = Jsoup.parse("invalid user");

        Mockito.when(fetcher.fromUrl("https://www.speedrun.com/user/SolidSpiderZnake")).thenReturn(runnerDocument);
        Mockito.when(fetcher.fromUrl("https://www.speedrun.com/ajax_userleaderboard.php?user=0")).thenReturn(runsDocument);

        Document fullDocument = builder.buildRunnerDocument("SolidSpiderZnake");
        assertEquals(0, fullDocument.select("table tbody tr").size());
        assertNull(fullDocument.selectFirst("div.loadingbox"));
    }

}