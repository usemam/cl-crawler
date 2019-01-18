var Crawler = window.Crawler || {};

(function scopeWrapper($) {
    var authToken;
    Crawler.authToken.then(function setAuthToken(token) {
        if (token) {
            authToken = token;
        } else {
            window.location.href = '/login.html';
        }
    }).catch(function handleTokenError(error) {
        alert(error);
        window.location.href = '/login.html';
    });
    
}(jQuery));