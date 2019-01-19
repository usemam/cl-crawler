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

    function getAll() {
        $.ajax({
            method: 'GET',
            url: _config.api.invokeUrl + '/search',
            headers: {
                Authorization: authToken
            },
            contentType: 'application/json',
            success: refreshGrid,
            error: function (xhr, status, errorMessage) {
                console.error("Error getting user's search requests: ", status, ", details: ", errorMessage);
                console.error("Response: ", xhr.responseText);
                alert("Error getting user's search requests:\n" + xhr.responseText);
            }
        });
    }

    function refreshGrid(result) {
        // todo
    }

    function randomId() {
        var id = "";
        var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (var i = 0; i < 6; i++) {
            id += possible.charAt(Math.floor(Math.random() * possible.length));
        }
        return id;
    }

    $(function onDocReady() {
        $("#logOut").click(function() {
            Crawler.logOut();
            alert("You have logged out.");
            window.location = "login.html";
        });

        if (!_config.api.invokeUrl) {
            $("#noApiMessage").show();
        }
        else {
            getAll();
        }
    });
}(jQuery));