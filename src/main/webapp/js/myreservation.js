document.addEventListener("DOMContentLoaded", function() {
	requestAjax.getData('/api/reservations', compareTime); // 서버시간 요청

	document.querySelector('.list_cards').addEventListener('click', function(evt) {
		if(evt.target.parentNode.classList.contains('booking_cancel')) {
			var confirmResult = confirm("취소하시겠습니까?");
			if(confirmResult == true){
				let reservationInfoId = evt.target.closest('article').querySelector('#reservationInfoId').value;

				cancelCallback = new CancelCallback(evt.target);
				requestAjax.cancelReservation(reservationInfoId, passToCancelDelegator);
			}
		}
	});
});

var serverTodayDate;

function compareTime(response) {
	serverTodayDate = new Date(response);
	var url = '/api/reservations?reservationEmail='+ document.querySelector('#reservationEmail').value;
	requestAjax.getData(url, makeReservationResult);
}

var cancelCallback;

function CancelCallback(target){
	this.target = target;
};

CancelCallback.prototype.moveToCanceled = function (response){  
	let article = this.target.closest('article');
	this.target.closest('li').removeChild(article);
	let cancelButton = article.querySelector('.booking_cancel');
	let shareButton = article.querySelector('.btn_goto_share');
	shareButton.parentNode.removeChild(shareButton);
	cancelButton.parentNode.removeChild(cancelButton);
	document.querySelector('li.cancel').appendChild(article);

	setSummaryBoardCountAfterCancel();
};

function setSummaryBoardCountAfterCancel() {
	let summaryList = document.querySelectorAll('.summary_board .item');
	let confirmedCount = Number(summaryList[1].querySelector('.figure').innerHTML);
	let canceledCount = Number(summaryList[3].querySelector('.figure').innerHTML);

	summaryList[1].querySelector('.figure').innerHTML = confirmedCount - 1;; 
	summaryList[3].querySelector('.figure').innerHTML = canceledCount + 1;
}

function passToCancelDelegator(response) {
	cancelCallback.moveToCanceled(response);
}

function makeReservationResult(response) {
	let resultConfirmHtml = "";
	let resultCancelHtml = "";
	let resultUsedHtml = "";

	let reservationTemplate = document.querySelector("#reservationItem").innerHTML;
	let cancelButton = document.querySelector("#cancelButton").innerHTML;
	let shareButton = document.querySelector("#shareButton").innerHTML;
	let reviewButton = document.querySelector("#reviewButton").innerHTML;

	let confirmedCount = 0;
	let usedCount = 0;
	let canceledCount = 0;

	let reservations = response.reservations;
	for(let i = 0; i < response.size; i++) {
		if( reservations[i].cancelYn ) {
			resultCancelHtml += reservationTemplate.replace("{{reservationInfoId}}", reservations[i].reservationInfoId)
											.replace("{{productDescription}}", reservations[i].displayInfo.productDescription)
											.replace("{{placeName}}", reservations[i].displayInfo.placeName)
											.replace("{{totalPrice}}", reservations[i].totalPrice)
											.replace("{{editButton}}", "")
											.replace("{{shareButton}}", "");
			canceledCount++;
		} else { 
			let date = new Date(reservations[i].reservationDate);
			if(date < serverTodayDate) {
				let requestParams = "productId=" + reservations[i].productId + "&" 
									+ "reservationInfoId=" + reservations[i].reservationInfoId + "&"
									+ "productDescription=" + reservations[i].displayInfo.productDescription;
				let reviewButtonWithHref = reviewButton.replace("{{requestParams}}", requestParams);

				resultUsedHtml += reservationTemplate.replace("{{reservationInfoId}}", reservations[i].reservationInfoId)
											.replace("{{productDescription}}", reservations[i].displayInfo.productDescription)
											.replace("{{placeName}}", reservations[i].displayInfo.placeName)
											.replace("{{totalPrice}}", reservations[i].totalPrice)
											.replace("{{editButton}}", reviewButtonWithHref)
											.replace("{{shareButton}}", "");
				usedCount++;
			} else {
				resultConfirmHtml += reservationTemplate.replace("{{reservationInfoId}}", reservations[i].reservationInfoId)
								.replace("{{productDescription}}", reservations[i].displayInfo.productDescription)
								.replace("{{placeName}}", reservations[i].displayInfo.placeName)
								.replace("{{totalPrice}}", reservations[i].totalPrice)
								.replace("{{editButton}}", cancelButton)
								.replace("{{shareButton}}", shareButton);
				confirmedCount++;
			}
		}
	}
	document.querySelector('li.confirmed').innerHTML += resultConfirmHtml;
	document.querySelector('li.used').innerHTML += resultUsedHtml;
	document.querySelector('li.cancel').innerHTML += resultCancelHtml;

	let summaryList = document.querySelectorAll('.summary_board .item');
	summaryList[0].querySelector('.figure').innerHTML = confirmedCount + usedCount + canceledCount;
	summaryList[1].querySelector('.figure').innerHTML = confirmedCount;
	summaryList[2].querySelector('.figure').innerHTML = usedCount;
	summaryList[3].querySelector('.figure').innerHTML = canceledCount;
}

