package io.github.artenes.speedbro.speedrun.com;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConvertersTest {

    @Test
    public void toReadableTime_returnsProperTime() {

        assertEquals("00:35:52", Converters.toReadableTime("2152"));
        assertEquals("00:02:38", Converters.toReadableTime("158.34"));
        assertEquals("00:00:05", Converters.toReadableTime("5"));
        assertEquals("03:21:54", Converters.toReadableTime("12114"));
        assertEquals("336:41:54", Converters.toReadableTime("1212114"));

    }
}