document.addEventListener("DOMContentLoaded", function() {
	var url = '/api/reservations?reservationEmail='+ document.querySelector('#reservationEmail').value;
	requestAjax.getData(url, makeReservationResult);
	
	document.querySelector('.list_cards').addEventListener('click', function(evt) {
		if(evt.target.parentNode.classList.contains('booking_cancel')) {
			var confirmResult = confirm("취소하시겠습니까?");
			if(confirmResult == true){
				let reservationInfoId = evt.target.closest('article').querySelector('#reservationInfoId').value;
				request( "/api/reservations/" + reservationInfoId, setCanceledReservation, evt.target);
			}
		}
	});
	
});

function request(url, callback, target) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			callback(JSON.parse(this.response), target);
		} else if (this.status > 200) {
			console.log("ajax failure:" + url, this.readyState, this.status);
		}
	};
	xhttp.open("PUT", url, true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send();
}

function setCanceledReservation(response, target) {
	let article = target.closest('article');
	target.closest('li').removeChild(article);
	let cancelButton = article.querySelector('.booking_cancel');
	let shareButton = article.querySelector('.btn_goto_share');
	shareButton.parentNode.removeChild(shareButton);
	cancelButton.parentNode.removeChild(cancelButton);
	document.querySelector('li.cancel').appendChild(article);
}

function makeReservationResult(response) {
	let resultConfirmHtml = document.querySelector("#confirmedDiv").innerHTML;
	resultConfirmHtml = resultConfirmHtml.replace("{{rType}}", "예약 확정");
	let resultCancelHtml = document.querySelector("#confirmedDiv").innerHTML;
	resultCancelHtml = resultCancelHtml.replace("{{rType}}", "취소된 예약");
	let reservationTemplate = document.querySelector("#reservationItem").innerHTML;
	let cancelButton = document.querySelector("#cancelButton").innerHTML;
	let shareButton = document.querySelector("#shareButton").innerHTML;
	
	for(let i = 0; i < response.size; i++) {
		if( response.reservations[i].cancelYn ) {
			resultCancelHtml += reservationTemplate.replace("{{reservationInfoId}}", response.reservations[i].reservationInfoId)
											.replace("{{productDescription}}", response.reservations[i].displayInfo.productDescription)
											.replace("{{placeName}}", response.reservations[i].displayInfo.placeName)
											.replace("{{totalPrice}}", response.reservations[i].totalPrice)
											.replace("{{editButton}}", "")
											.replace("{{shareButton}}", "");
		} else {
			resultConfirmHtml += reservationTemplate.replace("{{reservationInfoId}}", response.reservations[i].reservationInfoId)
							.replace("{{productDescription}}", response.reservations[i].displayInfo.productDescription)
							.replace("{{placeName}}", response.reservations[i].displayInfo.placeName)
							.replace("{{totalPrice}}", response.reservations[i].totalPrice)
							.replace("{{editButton}}", cancelButton)
							.replace("{{shareButton}}", shareButton);
		}
	}
	document.querySelector('li.cancel').innerHTML = resultCancelHtml;
	document.querySelector('li.confirmed').innerHTML = resultConfirmHtml;
}

var requestAjax = {
	defaultType : "application/x-www-form-urlencoded",
	jsonType : "application/json",
	cancelUrl : "/api/reservations/",

	getData(url, callback) {
		this.request(url, callback, "GET", this.defaultType);
	},

	cancelReservation(id, callback) {
		this.request(this.cancelUrl + id, callback, "PUT", this.defaultType);
	},

	makeReservation(url, callback) {
		this.request(url, callback, "POST", this.jsonType);
	},

	request(url, callback, httpMethod, contentType) {
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
		xhttp.send();
	}
};
