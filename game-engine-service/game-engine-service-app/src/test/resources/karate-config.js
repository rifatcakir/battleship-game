function fn() {
    var env = karate.env; // get java system property 'karate.env'
    karate.log('karate.env system property was:', env);

    let port = karate.properties['karate.port']
    config = {
        baseUrl: 'http://localhost:' + port
    }
    // don't waste time waiting for a connection or if servers don't respond within 0,3 seconds
    karate.configure('connectTimeout', 300);
    karate.configure('readTimeout', 300);
    return config;
}