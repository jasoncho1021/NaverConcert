document.addEventListener("DOMContentLoaded", function() {
	currentUrl = document.location.href;
	displayInfoId = currentUrl.split('=')[1];
	requestAjax.getData('/api/reservations', setTime); // 서버시간 요청
	requestAjax.getData('/api/products/' + displayInfoId, renderPage);

	initAgreementCheckListener();
	initReservationConfirmBtnListener();
	initToggleListener();
});

function initAgreementCheckListener() {
	document.querySelector('#chk3').addEventListener( 'change', function() {
		enableSendButtonIfAllInputsAreValid();
	});
}

function initReservationConfirmBtnListener() {
	document.querySelector('.bk_btn_wrap').addEventListener('click', function(evt) {
		if (this.matches('.disable')) {
			return;
		} else {
			makeData();
		}
	});
}

function initToggleListener() {
	document.querySelector('.section_booking_agreement').addEventListener('click', function(evt) {
		if(evt.target.matches('.btn_text')) {
			let agreement = evt.target.closest('.agreement');
			if(!agreement.matches('.open')) {
				addClassName(agreement, 'open');
			} else {
				removeClassName(agreement, 'open');
			}
		}
	});
}

function enableSendButtonIfAllInputsAreValid() {
	let name = document.querySelector('#name').getAttribute('validation');
	let tel = document.querySelector('#tel').getAttribute('validation');
	let email = document.querySelector('#email').getAttribute('validation');
	let agreement = document.querySelector('#chk3').checked
	let totalCount = (Number(document.querySelector('#totalCount').innerHTML) > 0);

	let sendButton = document.querySelector('.bk_btn_wrap');
	if ( (name != null)
		&& (tel != null)
		&& (email != null)
		&& agreement
		&& totalCount ) {
		removeClassName(sendButton, 'disable');
	} else {
		addClassName(sendButton, 'disable');
	}
}

function setTime(response) {
	let reservationDate = response.split('T')[0];
	document.querySelector('.inline_control > .inline_txt').firstChild.nodeValue = reservationDate.replace('-','.').replace('-','.') + ", 총 ";
	document.querySelector('#reservationDate').value = response;
}

function renderPage(response) {
	document.querySelector('.img_thumb').src = "images/" + response.productImages[0].fileInfoId;
	makePriceList(response.productPrices);
	setStoreDetail(response.displayInfo);
}

function setStoreDetail(displayInfo) {
	document.querySelector('.top_title .title').innerHTML = displayInfo.productDescription;
	var storeDetails = document.querySelectorAll('.store_details .dsc');
	storeDetails[0].innerHTML = displayInfo.placeStreet;
	storeDetails[1].innerHTML = displayInfo.openingHours;
}

function makePriceList(productPrices) {
	var priceTemplate = document.querySelector("#priceList").innerHTML;
	var resultHtml = "";
	var selectedType = "";

	for (var i = 0; i < productPrices.length; i++) {
		switch(productPrices[i].priceTypeName) {
			case "A":
				selectedType = priceTemplate.replace("{{priceType}}", "성인")
			break;
			case "Y" :
				selectedType = priceTemplate.replace("{{priceType}}", "청소년");
			break;
			case "B" :
				selectedType = priceTemplate.replace("{{priceType}}", "유아");
			break;
			case "S" :
				selectedType = priceTemplate.replace("{{priceType}}", "세트");
			break;
		}
		resultHtml += selectedType.replace("{{price}}", productPrices[i].price)
									.replace("{{price}}", productPrices[i].price)
									.replace("{{discountRate}}", productPrices[i].discountRate)
									.replace("{{productPriceId}}", productPrices[i].productPriceId)
									.replace("{{productId}}", productPrices[i].productId);
	}
	var ticket_body = document.querySelector(".ticket_body");
	ticket_body.innerHTML = resultHtml;

	ticket_body.addEventListener("click", function(evt) {
		ticketCounter(evt);
	});
}

function ticketCounter(evt) {
	if(evt.target.matches('.ico_minus3')) {
		let count = Number(evt.target.parentNode.children[1].value);
		if(count < 1) {
			return;
		}
		evt.target.parentNode.children[1].value = --count;
		if(count < 1) {
			setMinusBtnDisabled(evt.target.closest('.qty'));
		}
		setTotalPrice(evt.target.closest('.qty'), count);
		document.querySelector('#totalCount').innerHTML = Number(document.querySelector('#totalCount').innerHTML) - 1;
	} else if (evt.target.matches('.ico_plus3')) {
		let count = Number(evt.target.parentNode.children[1].value);
		if(count < 1) {
			setMinusBtnEnabled(evt.target.closest('.qty'));
		}
		evt.target.parentNode.children[1].value = ++count;
		setTotalPrice(evt.target.closest('.qty'), count);
		document.querySelector('#totalCount').innerHTML = Number(document.querySelector('#totalCount').innerHTML) + 1;
	}
	enableSendButtonIfAllInputsAreValid();
}

function setTotalPrice(parent, count) {
	let price = Number(parent.querySelector('.price').innerHTML);
	parent.querySelector('.total_price').innerText = price * count;
}

function setMinusBtnDisabled(parent) {
	addClassName(parent.querySelector('.ico_minus3'),'disabled');
	addClassName(parent.querySelector('.count_control_input'),'disabled');
	removeClassName(parent.querySelector('.individual_price'),'on_color');
}

function setMinusBtnEnabled(parent) {
	removeClassName(parent.querySelector('.ico_minus3'),'disabled');
	removeClassName(parent.querySelector('.count_control_input'),'disabled');
	addClassName(parent.querySelector('.individual_price'), 'on_color');
}

function makeData() {
	let inputPrices = document.querySelectorAll('.qty');
	let priceList = [];
	let data = {
			displayInfoId : 0,
			prices : [],
			productId: 0,
			reservationEmail: "",
			reservationName: "",
			reservationTelephone: "",
			reservationYearMonthDay: ""
	}
	for(let i = 0; i < inputPrices.length; i++) {
		let price = {
				count: 0,
				productPriceId: 0,
				reservationInfoId: 0,
				reservationInfoPriceId: 0
		}
		price.count = inputPrices[i].querySelector('.count_control_input').value;
		price.productPriceId = inputPrices[i].querySelector('#productPriceId').value;
		if(price.count > 0) {
			priceList.push(price);
		}
	}
	data.prices = priceList;
	data.displayInfoId = displayInfoId;
	data.productId = inputPrices[0].querySelector('#productId').value;
	data.reservationEmail = document.querySelector('#email').value;
	data.reservationName = document.querySelector('#name').value;
	data.reservationTelephone = document.querySelector('#tel').value;
	data.reservationYearMonthDay =  document.querySelector('#reservationDate').value;

	requestAjax.makeReservation(handlePostSuccess, data);
}

function handlePostSuccess(response) {
	alert("예약 성공!");
	location.replace("/mainpage");
}

function clearInvalidInput(target) {
	if(target.getAttribute('validation') == null) {
		target.value = "";
	}
}

function isValidInput(target) {
	if (target.id == "name") {
		if( target.value.length > 0 ) {
			target.setAttribute('validation', '');
		} else {
			target.removeAttribute('validation');
		}
		enableSendButtonIfAllInputsAreValid();
		return;
	}

	var regExp = getRegExpById(target.id);
	if(!target.value.match(regExp)) {
		target.value = "형식이 틀렸거나 너무 짧아요";
		target.removeAttribute('validation');
	} else {
		target.setAttribute('validation', '');
	}
	enableSendButtonIfAllInputsAreValid();
}

function getRegExpById(id) {
	switch(id) {
		case 'tel':
			return /^\d{2,3}-\d{3,4}-\d{4}$/;
		case 'email':
			return /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
	}
}
