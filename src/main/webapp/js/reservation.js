document.addEventListener("DOMContentLoaded", function() {
	getAJAX('/api/reservations', checkResult);
});

function getAJAX(url, callback) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			callback(JSON.parse(this.response));
		} else if (this.status > 200) {
			console.log("ajax failure:" + url, this.readyState, this.status);
		}
	};
	xhttp.open("GET", url, true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send();
}

function checkResult(response) {
	console.log(response);
	const data = {
			displayInfoId : 7,
			prices : [
				{
					count: 3,
					productPriceId: 1,
					reservationInfoId: 0,
					reservationInfoPriceId: 0
				},
				{
					count: 2,
					productPriceId: 3,
					reservationInfoId: 0,
					reservationInfoPriceId: 0
				}
			],
			productId: 8,
			reservationEmail: "huni@navercorp.com",
			reservationName: "조재",
			reservationTelephone: "010-3022-0560",
			reservationYearMonthDay: response//"2018-12-15T10:00:00"
		};
	postAJAX('/api/reservations', null, data);
}

function postAJAX(url, callback, data) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			callback(JSON.parse(this.response));
		} else if (this.status > 200) {
			console.log("ajax failure:" + url, this.readyState, this.status);
		}
	};
	xhttp.open("POST", url, true);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.send(JSON.stringify(data));
}