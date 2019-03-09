var Crawler = window.Crawler || {};

(function scopeWrapper($, ko) {
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
        /* $.ajax({
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
        }); */
        refreshGrid(testData());

        function testData() {
            return [
                {
                    SearchId: randomId(),
                    UserEmail: "useinm@gmail.com",
                    URL: "first_test_url",
                    IsActive: true,
                    Created: new Date(),
                    Updated: new Date()
                },
                {
                    SearchId: randomId(),
                    UserEmail: "useinm@gmail.com",
                    URL: "second_test_url",
                    IsActive: true,
                    Created: new Date(),
                    Updated: new Date()
                }
            ];
        }
    }

    var ItemViewModel = function(parent, data, isNew) {
        this.isNew = ko.observable(isNew);
        this.url = ko.observable(data.URL);
        this.isActive = ko.observable(data.IsActive);
        this.isDirty = ko.computed(
            function() {
                return this.url() != data.URL || this.isActive() != data.IsActive;
            },
            this);
        var self = this;
        this.save = function() {
            data.URL = self.url();
            data.IsActive = self.isActive();
            if (self.isNew()) {
                self.isNew(false);
            }
        }
        this.remove = function() {
            parent.items.remove(self);
        }
    }

    var GridViewModel = function(data) {
        var items = [];
        for (var i = 0; i < data.length; i++) {
            items.push(new ItemViewModel(this, data[i], false));
        }
        this.items = ko.observableArray(items);
        var self = this;
        this.addNew = function() {
            var itemData = {
                SearchId: randomId(),
                URL: "",
                IsActive: true,
                Created: new Date(),
                Updated: new Date()
            };
            self.items.push(new ItemViewModel(self, itemData, true));
        }
    }

    function refreshGrid(result) {
        ko.applyBindings(new GridViewModel(result));
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
}(jQuery, ko));