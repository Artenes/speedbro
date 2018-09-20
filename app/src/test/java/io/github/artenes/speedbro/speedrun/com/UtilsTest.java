package io.github.artenes.speedbro.speedrun.com;

import org.junit.Test;

import okhttp3.internal.Util;

import static org.junit.Assert.assertEquals;

public class UtilsTest {

    @Test
    public void returnEmptySegmentWhenUriIsEmpty() {
        assertEquals("", Utils.lastSegmentOfUri(""));
    }

    @Test
    public void returnSameValueAsSegmentWhenItIsNotAnUri() {
        assertEquals("no_uri", Utils.lastSegmentOfUri("no_uri"));
    }

    @Test
    public void returnLastSegmentOfUri() {
        assertEquals("uri", Utils.lastSegmentOfUri("/my/uri"));
        assertEquals("uri", Utils.lastSegmentOfUri("my/uri"));
        assertEquals("uri", Utils.lastSegmentOfUri("/uri"));
    }

    @Test
    public void returnPathWithoutFirstSlash() {
        assertEquals("my/uri", Utils.withoutStartingSlash("/my/uri"));
    }

    @Test
    public void returnEmptyPathWhenTryToRemoveFirstSlashFromEmptyString() {
        assertEquals("", Utils.withoutStartingSlash(""));
    }

    @Test
    public void doNotRemoveSlashIfStringDoesNotStartsWithSlash() {
        assertEquals("my/uri", Utils.withoutStartingSlash("my/uri"));
    }

    @Test
    public void parsePrimaryTime() {
        assertEquals("0h7m21s", Utils.parseTime("PT0H7M21S"));
    }

    @Test
    public void returnEmptyOrSameTimeForInvalidFormat() {
        assertEquals("", Utils.parseTime("PT"));
        assertEquals("KS9128h9i", Utils.parseTime("KS9128h9i"));
    }

    @Test
    public void getFirstWordOfSentence() {
        assertEquals("1st", Utils.getFirstWordOfSentence("1st place"));
        assertEquals("1st", Utils.getFirstWordOfSentence("1st place 2nd place"));
    }

    @Test
    public void returnSameSentenceWhenThereIsNoSpace() {
        assertEquals("", Utils.getFirstWordOfSentence(""));
        assertEquals("Sentence", Utils.getFirstWordOfSentence("Sentence"));
    }

    @Test
    public void returnSameSentenceWhereThereAreOnlySpaces() {
        assertEquals(" ", Utils.getFirstWordOfSentence(" "));
        assertEquals("   ", Utils.getFirstWordOfSentence("   "));
    }

}