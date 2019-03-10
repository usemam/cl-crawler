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

    function randomId() {
        var id = "";
        var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (var i = 0; i < 6; i++) {
            id += possible.charAt(Math.floor(Math.random() * possible.length));
        }
        return id;
    }

    var ItemViewModel = function(parent, data, isNew) {
        this.url = ko.observable(data.URL);
        this.isActive = ko.observable(data.IsActive);
        this.updated = ko.observable(data.Updated.toDateString());
        this.isNew = ko.observable(isNew);
        this.isDirty = ko.computed(
            function() {
                return this.url() !== data.URL || this.isActive() !== data.IsActive;
            },
            this);
        var self = this;

        this.save = function() {
            data.URL = self.url();
            data.IsActive = self.isActive();
            
            $.ajax({
                method: self.isNew() ? 'PUT' : 'PATCH',
                url: _config.api.invokeUrl + '/search/' + data.SearchId,
                headers: {
                    Authorization: authToken
                },
                data: data,
                contentType: 'application/json',
                success: parent.reload,
                error: function (xhr, status, errorMessage) {
                    console.error("Error saving user's search request: ", status, ", details: ", errorMessage);
                    console.error("Response: ", xhr.responseText);
                    alert("Error saving user's search request:\n" + xhr.responseText);
                }
            });
        };

        this.remove = function() {
            $.ajax({
                method: 'DELETE',
                url: _config.api.invokeUrl + '/search/' + data.SearchId,
                headers: {
                    Authorization: authToken
                },
                contentType: 'application/json',
                success: parent.reload,
                error: function (xhr, status, errorMessage) {
                    console.error("Error deleting user's search request: ", status, ", details: ", errorMessage);
                    console.error("Response: ", xhr.responseText);
                    alert("Error deleting user's search request:\n" + xhr.responseText);
                }
            });
        };
    }

    var GridViewModel = function() {
        this.items = ko.observableArray([]);
        var self = this;

        var refresh = function(data) {
            var items = [];
            for (var i = 0; i < data.length; i++) {
                items.push(new ItemViewModel(self, data[i], false));
            }
            self.items(items);
        }

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

        this.reload = function() {
            $.ajax({
                method: 'GET',
                url: _config.api.invokeUrl + '/search',
                headers: {
                    Authorization: authToken
                },
                contentType: 'application/json',
                success: refresh,
                error: function (xhr, status, errorMessage) {
                    console.error("Error getting user's search requests: ", status, ", details: ", errorMessage);
                    console.error("Response: ", xhr.responseText);
                    alert("Error getting user's search requests:\n" + xhr.responseText);
                }
            });
        }

        this.reload();
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
            ko.applyBindings(new GridViewModel());
        }
    });
}(jQuery, ko));