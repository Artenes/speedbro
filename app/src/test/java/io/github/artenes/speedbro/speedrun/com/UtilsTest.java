package io.github.artenes.speedbro.speedrun.com;

import org.junit.Test;

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

}