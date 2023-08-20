function fn() {
    var env = karate.env; // get java system property 'karate.env'
    karate.log('karate.env system property was:', env);

    let port = karate.properties['karate.port'] || 7777
    config = {
        baseUrl: 'http://localhost:' + port
    }
    // don't waste time waiting for a connection or if servers don't respond within 0,3 seconds
    karate.configure('connectTimeout', 1200);
    karate.configure('readTimeout', 1200);
    return config;
}