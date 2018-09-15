package io.github.artenes.speedbro.speedrun.com;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ContractTest {

    @Test
    public void appendPathToAuthority() {
        assertEquals("https://www.speedrun.com/mypath", Contract.asAbsolutePath("mypath"));
    }

    @Test
    public void returnEmptyAbsoluteStringIfEmptyPathIsGiven() {
        assertEquals("", Contract.asAbsolutePath(""));
    }

    @Test
    public void appendRunnerToAuthority() {
        assertEquals("https://www.speedrun.com/themes/user/runner/image.png", Contract.runnerAvatar("runner"));
    }

    @Test
    public void returnEmptyRunnerAvatarIfNoRunnerIsGiven() {
        assertEquals("", Contract.runnerAvatar(""));
    }

}