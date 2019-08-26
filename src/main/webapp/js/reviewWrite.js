document.addEventListener("DOMContentLoaded", function() {
	initThumbChangeListener();
	initAjaxSend();
	initTextAreaReaction();
});

function initTextAreaReaction() {
	document.querySelector('.review_contents').addEventListener('click', function(evt) {
		evt.preventDefault();
		if(this.querySelector('a').style.visibility != 'hidden') {
			this.querySelector('a').style.visibility = 'hidden';
			this.querySelector('textarea').focus();
		}
	});

	document.querySelector('.review_contents textarea').addEventListener('focusout', function(evt) {
		evt.preventDefault();
		if (this.value.length == 0) {
			this.parentNode.querySelector('a').style.visibility = 'visible';
		}
	});

	document.querySelector('.review_contents textarea').addEventListener('keyup', function(evt) {
		evt.preventDefault();		
		document.querySelector('.guide_review span').innerHTML = this.value.length;
	});
}

function isValidTextLength(textLength) {
	let regExp = /^[\s\S]{5,400}$/;
	return (regExp).test(textLength);
}

function initThumbChangeListener() {
	document.querySelector('#reviewImageFileOpenInput').addEventListener('change', function(evt) {
		const image = evt.target.files[0];
		console.log(image.type);

		const elImage = document.querySelector(".item_thumb");
		if(!validImageType(image)) {
			alert("지원하지 않는 형식입니다");
			console.warn("invalid image");
			elImage.src = "";
			elImage.closest('li').style.display = 'none';
			return;
		}

		elImage.closest('li').style.display = 'block';
		elImage.src = window.URL.createObjectURL(image);
	});
}

function validImageType(image) {
	const result = ([ 'image/png',
					  'image/jpg' ].indexOf(image.type) > -1);
	return result;
}

function makeRequestParams() {
	var comment = document.querySelector('textarea').value;
	var productId = document.querySelector('#productId').value;
	var score = document.querySelector('.star_rank').innerHTML;

	var requestParams = "comment=" + comment + "&"
						+ "productId=" + productId + "&"
						+ "score=" + score;
	return requestParams;
}

function initAjaxSend() {
	var form = document.forms.namedItem("fileinfo");
	document.querySelector('.bk_btn').addEventListener('click', function(ev) {

		if(!isValidTextLength(document.querySelector('.review_contents textarea').value)) {
			alert("글자수를 맞춰주세요");
			return;
		}

		var oData = new FormData(form);
		console.log(oData);

		var reservationInfoId = document.querySelector('#reservationInfoId').value;
		var url = "api/reservations/"+ reservationInfoId +"/comments?" + makeRequestParams();
		var oReq = new XMLHttpRequest();
		oReq.open("POST", url, true);
		oReq.onload = function(oEvent) {
			if (oReq.status == 200) {
				reviewWriteSuccess(oEvent);
			} else {
				console.log("failed:"+ oEvent);
			}
		};

		oReq.send(oData);
		ev.preventDefault();
	}, false);
}

function reviewWriteSuccess(response) {
	location.replace("/login");
}
