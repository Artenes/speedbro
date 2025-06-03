exports.config = {
    runner: 'local',
    path: '/wd/hub',
    port: 4723,
    specs: ['./features/**/*.feature'],
    framework: 'cucumber',
    cucumberOpts: {
        require: ['./steps/*.js'],
        timeout: 60000
    },
    reporters: ['spec'],
    services: [
        ['appium', { command: 'appium' }]
    ],
    capabilities: [{
        platformName: 'Android',
        'appium:automationName': 'UiAutomator2',
        'appium:deviceName': 'Android Emulator',
        'appium:app': '../app/build/outputs/apk/debug/app-debug.apk',
        'appium:autoGrantPermissions': true
    }]
};
