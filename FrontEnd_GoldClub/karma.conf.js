module.exports = function(config) {
    config.set({
      frameworks: ['jasmine', '@angular-devkit/build-angular'],
      plugins: [
        require('karma-jasmine'),
        require('karma-chrome-launcher'),
        require('karma-edge-launcher'), // Asegúrate de que este esté incluido
        require('karma-jasmine-html-reporter'),
        require('karma-coverage')
      ],
      client: {
        clearContext: false // leave Jasmine Spec Runner output visible in browser
      },
      coverageReporter: {
        dir: require('path').join(__dirname, 'coverage'),
        subdir: '.',
        reporters: [
          { type: 'html' },
          { type: 'text-summary' }
        ]
      },
      reporters: ['progress', 'kjhtml'],
      port: 9876,
      colors: true,
      logLevel: config.LOG_INFO,
      autoWatch: true,
      browsers: ['Edge'], // Cambia esto a 'Chrome' si prefieres usar Chrome
      singleRun: false,
      restartOnFileChange: true
    });
  };
  