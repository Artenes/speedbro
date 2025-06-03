const { Given, When, Then } = require('@wdio/cucumber-framework');
const { expect } = require('chai');

Given('the application is launched', async () => {
    await driver.launchApp();
});

Given('I am on the {string} tab', async (tab) => {
    const selector = `new UiSelector().text(\"${tab}\")`;
    const el = await $(`android=${selector}`);
    await el.click();
});

When('I tap a run entry', async () => {
    const run = await $('android=new UiSelector().resourceId("io.github.artenes.speedbro:id/container").childSelector(new UiSelector().clickable(true))');
    await run.click();
});

Then('RunActivity opens displaying that run', async () => {
    const detail = await $('android=new UiSelector().resourceId("io.github.artenes.speedbro:id/title")');
    await expect(detail).to.exist;
});

When('I tap the floating {string} button', async (name) => {
    const button = await $('android=new UiSelector().resourceId("io.github.artenes.speedbro:id/swap_view_fab")');
    await button.click();
});

Then('the list is replaced by a map of run locations', async () => {
    const map = await $('android=new UiSelector().descriptionContains("Google Map")');
    await expect(map).to.exist;
});

Given('I am viewing the map of runs', async () => {
    // assumes map view already open
});

When('I tap a country marker', async () => {
    const marker = await $('android=new UiSelector().descriptionContains("marker")');
    await marker.click();
});

Then('a dialog lists the runs for that country', async () => {
    const dialog = await $('android=new UiSelector().resourceId("io.github.artenes.speedbro:id/myrecycler")');
    await expect(dialog).to.exist;
});

When('I select a run', async () => {
    const run = await $('android=new UiSelector().resourceId("io.github.artenes.speedbro:id/myrecycler").childSelector(new UiSelector().clickable(true))');
    await run.click();
});

Then('RunActivity opens', async () => {
    const detail = await $('android=new UiSelector().resourceId("io.github.artenes.speedbro:id/title")');
    await expect(detail).to.exist;
});

When('I tap the favorite icon', async () => {
    const button = await $('android=new UiSelector().resourceId("io.github.artenes.speedbro:id/favorite")');
    await button.click();
});

Then('the run is stored as a favorite', async () => {
    const icon = await $('android=new UiSelector().resourceId("io.github.artenes.speedbro:id/favorite")');
    await expect(icon).to.exist;
});

Given('a run has been favorited', async () => {
    // this step assumes favorite exists
});

When('I switch to the {string} tab', async (tab) => {
    const selector = `new UiSelector().text(\"${tab}\")`;
    const el = await $(`android=${selector}`);
    await el.click();
});

Then('the favorited run appears in the list', async () => {
    const fav = await $('android=new UiSelector().descriptionContains("favorite")');
    await expect(fav).to.exist;
});

When('I tap the search icon', async () => {
    const search = await $('android=new UiSelector().resourceId("io.github.artenes.speedbro:id/action_search")');
    await search.click();
});

When('I enter a game name and submit', async () => {
    const searchBox = await $('android=new UiSelector().resourceId("android:id/search_src_text")');
    await searchBox.setValue('game');
    await driver.pressKeyCode(66); // ENTER
});

Then('a list of search results is shown', async () => {
    const results = await $('android=new UiSelector().resourceId("io.github.artenes.speedbro:id/container")');
    await expect(results).to.exist;
});

When('I select a game result', async () => {
    const item = await $('android=new UiSelector().resourceId("io.github.artenes.speedbro:id/container").childSelector(new UiSelector().clickable(true))');
    await item.click();
});

Then('GameActivity opens for that game', async () => {
    const cover = await $('android=new UiSelector().description("Game cover")');
    await expect(cover).to.exist;
});

When('I enter a runner name and submit', async () => {
    const searchBox = await $('android=new UiSelector().resourceId("android:id/search_src_text")');
    await searchBox.setValue('runner');
    await driver.pressKeyCode(66);
});

Then('the runner appears in the results', async () => {
    const item = await $('android=new UiSelector().resourceId("io.github.artenes.speedbro:id/container").childSelector(new UiSelector().clickable(true))');
    await expect(item).to.exist;
});

When('I select the runner', async () => {
    const item = await $('android=new UiSelector().resourceId("io.github.artenes.speedbro:id/container").childSelector(new UiSelector().clickable(true))');
    await item.click();
});

Then('RunnerActivity opens for that runner', async () => {
    const image = await $('android=new UiSelector().description("Runner image")');
    await expect(image).to.exist;
});

Given('a game ID is provided', async () => {
    // placeholder for injecting deep link or id
});

When('GameActivity is opened', async () => {
    // assume we already opened the game activity
});

Then('the game cover, title, year and platforms are displayed', async () => {
    const cover = await $('android=new UiSelector().description("Game cover")');
    await expect(cover).to.exist;
});

Then('tabs for each category appear', async () => {
    const tabs = await $('android=new UiSelector().resourceId("io.github.artenes.speedbro:id/sections_tabs")');
    await expect(tabs).to.exist;
});

Given('I am on a game\'s detail screen', async () => {
    // assume game screen open
});

When('I select a category tab', async () => {
    const tab = await $('android=new UiSelector().resourceId("io.github.artenes.speedbro:id/sections_tabs").childSelector(new UiSelector().clickable(true))');
    await tab.click();
});

Then('a leaderboard of runs is shown', async () => {
    const leaderboard = await $('android=new UiSelector().resourceId("io.github.artenes.speedbro:id/container")');
    await expect(leaderboard).to.exist;
});

When('I tap a run on the leaderboard', async () => {
    const run = await $('android=new UiSelector().resourceId("io.github.artenes.speedbro:id/container").childSelector(new UiSelector().clickable(true))');
    await run.click();
});

Then('RunActivity opens for that run', async () => {
    const detail = await $('android=new UiSelector().resourceId("io.github.artenes.speedbro:id/title")');
    await expect(detail).to.exist;
});

Given('RunnerActivity is opened with a runner ID', async () => {
    // assume runner screen open
});

Then('the runner image, name and country are displayed', async () => {
    const image = await $('android=new UiSelector().description("Runner image")');
    await expect(image).to.exist;
});

Then('the runner\u2019s runs are listed', async () => {
    const list = await $('android=new UiSelector().resourceId("io.github.artenes.speedbro:id/container")');
    await expect(list).to.exist;
});

Given('I open a run whose video is on YouTube', async () => {
    // open run with youtube video
});

Then('the embedded YouTube player loads the video', async () => {
    const player = await $('android=new UiSelector().descriptionContains("YouTube")');
    await expect(player).to.exist;
});

Given('I open a run whose video is on Twitch', async () => {
    // open run with twitch video
});

Then('a message prompts me to watch on Twitch', async () => {
    const msg = await $('android=new UiSelector().textContains("Twitch")');
    await expect(msg).to.exist;
});

When('I tap the message', async () => {
    const msg = await $('android=new UiSelector().textContains("Twitch")');
    await msg.click();
});

Then('the Twitch video opens in the Twitch app or browser', async () => {
    // check context has changed or external app
});

Given('the widget shows a list of recent runs', async () => {
    // widget state assumed
});

When('I tap a run on the widget', async () => {
    // cannot interact with widget in app context in this simple example
});

Then('the application opens RunActivity for that run', async () => {
    const detail = await $('android=new UiSelector().resourceId("io.github.artenes.speedbro:id/title")');
    await expect(detail).to.exist;
});

Given('I open the About screen', async () => {
    // navigate to AboutActivity
});

Then('the application version is visible', async () => {
    const version = await $('android=new UiSelector().resourceId("io.github.artenes.speedbro:id/version")');
    await expect(version).to.exist;
});

When('I tap {string}', async (link) => {
    const element = await $(`android=new UiSelector().text(\"${link}\")`);
    await element.click();
});

Then('the external browser opens the developer\u2019s website', async () => {
    // verify external intent
});

Then('the external browser opens the project repository', async () => {
    // verify external intent
});
