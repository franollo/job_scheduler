angular.module('auth', []).factory('auth', function($http) {

			var auth = {

				authenticated : false,
				error: false,			
				authenticate : function(credentials, callback) {

					var headers = credentials && credentials.username ? {
						authorization : "Basic "
								+ btoa(credentials.username + ":"
										+ credentials.password)
					} : {};

					return $http.get('user', {
						"headers" : headers
					}).then(function(response) {
						if(response.status == 200) {
							auth.authenticated = response.data.authenticated;
							auth.error = false;
						}
					}, function(data) {
						if(data.status != 200) {
							auth.authenticated = false;
							auth.error = true;
						}
					});
				},
				
				logout : function() {
					  return $http.post('logout', {}).then(function(data) {
						  if(data.status == 200) {
							  auth.error = false;
						  }
						  else {
							  auth.error = true;
						  }
						  auth.authenticated = false;
					  });
				},
				
				isAuthenticated : function() {
					return authenticated;
				}
			};

			return auth;

		});