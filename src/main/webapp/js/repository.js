var requestAjax = {
	defaultType : "application/x-www-form-urlencoded",
	jsonType : "application/json",
	cancelUrl : "/api/reservations/",
	reservationUrl : "/api/reservations",

	getData(url, callback) {
		this.sendRequest(this.makeRequest(url, callback, "GET", this.defaultType));
	},

	cancelReservation(id, callback) {
		this.sendRequest(this.makeRequest(this.cancelUrl + id, callback, "PUT", this.defaultType));
	},

	makeReservation(callback, data) {
		this.sendRequestWithData(this.makeRequest(this.reservationUrl, callback, "POST", this.jsonType), data);
	},

	makeRequest(url, callback, httpMethod, contentType) {
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				callback(JSON.parse(this.response));
			} else if (this.status > 200) {
				console.log("ajax failure:" + url, this.readyState, this.status);
			}
		};
		xhttp.open(httpMethod, url, true);
		xhttp.setRequestHeader("Content-type", contentType);
		return xhttp;
	},

	sendRequest(xhttp) {
		xhttp.send();
	},

	sendRequestWithData(xhttp, data) {
		xhttp.send(JSON.stringify(data));
	}
};