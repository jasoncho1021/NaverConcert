document.addEventListener("DOMContentLoaded", function() {
	currentUrl = document.location.href;
	displayInfoId = currentUrl.split('=')[1];
	getAJAX('/api/reservations', setTime); // 서버시간 요청
	getAJAX('/api/products/' + displayInfoId, renderPage);

	document.querySelector('.chk_txt_label').addEventListener('click', function(evt) {
		if(!document.querySelector('#chk3').checked) {
			document.querySelector('.bk_btn_wrap').classList.remove('disable');
		} else {
			document.querySelector('.bk_btn_wrap').classList.add('disable');
		}
	});
	
	document.querySelector('.bk_btn_wrap').addEventListener('click', function(evt) {
		if (this.classList.contains('disable')) {
			return;
		} else {
			makeData();
		}
	});
	
});

function setTime(response) {
	let reservationDate = response.split('T')[0];
	document.querySelector('.inline_control > .inline_txt').innerHTML = reservationDate.replace('-','.').replace('-','.') + ", 총 <span id=\"totalCount\">0</span>매";
	document.querySelector('#reservationDate').value = response;
}

function renderPage(response) {
	console.log("INFO" + response);
	document.querySelector('.img_thumb').src = "/" + response.productImages[0].saveFileName;
	makePriceList(response.productPrices);
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

	ticket_body.addEventListener("click",function(evt) {
		if(evt.target.classList.contains('ico_minus3')) {
			let count = Number(evt.target.parentNode.children[1].value);
			evt.target.parentNode.children[1].value = --count;
			setTotalPrice(evt.target.closest('.qty'), count);
			document.querySelector('#totalCount').innerHTML = Number(document.querySelector('#totalCount').innerHTML) - 1;
		} else if (evt.target.classList.contains('ico_plus3')) {
			let count = Number(evt.target.parentNode.children[1].value);
			evt.target.parentNode.children[1].value = ++count;
			setTotalPrice(evt.target.closest('.qty'), count);
			document.querySelector('#totalCount').innerHTML = Number(document.querySelector('#totalCount').innerHTML) + 1;
		}
	});
}

function setTotalPrice(parent, count) {
	let price = Number(parent.querySelector('.price').innerHTML);
	parent.querySelector('.total_price').innerText = price * count;
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
	console.log(data);
	
	postAJAX('/api/reservations', postSuccess, data);
}

function postSuccess(response) {
	alert("예약 성공!");
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