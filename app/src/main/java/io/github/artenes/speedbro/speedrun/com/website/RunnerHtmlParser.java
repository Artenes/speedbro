package io.github.artenes.speedbro.speedrun.com.website;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.github.artenes.speedbro.speedrun.com.Contract;
import io.github.artenes.speedbro.speedrun.com.models.Run;
import io.github.artenes.speedbro.speedrun.com.models.Runner;
import io.github.artenes.speedbro.speedrun.com.models.SocialMedia;

/**
 * Parse a html document to a Runner
 */
public class RunnerHtmlParser implements Parser<Runner> {

    private final RunsHtmlParser runParser;

    public RunnerHtmlParser() {
        runParser = new RunsHtmlParser(RunsHtmlParser.Source.RUNNER);
    }

    @Override
    public Runner parse(Document document) {

        //get all the social media links
        List<SocialMedia> socialMedias = new ArrayList<>();
        Elements socialMediaAnchors = document.select("div.userinfo p.socialicons a");
        for (Element anchor : socialMediaAnchors) {
            socialMedias.add(new SocialMedia(
                    Contract.asAbsolutePath(anchor.select("img").attr("src")),
                    anchor.attr("href")
            ));
        }

        //get all the runs
        List<Run> runs = runParser.parse(document);

        //finally return the runner
        return Runner.Builder.aRunner()
                .withName(document.select("div.userinfo p span.username").text())
                .withIcon(Contract.asAbsolutePath(document.select("div.userinfo p img.userpicture").attr("src")))
                .withFlag(Contract.asAbsolutePath(document.select("div.userinfo p a img.flagicon").attr("src")))
                .withCountry(document.select("div.userinfo p a img.flagicon").attr("title").trim())
                .withSocialMedia(socialMedias)
                .withRuns(runs)
                .build();

    }

}