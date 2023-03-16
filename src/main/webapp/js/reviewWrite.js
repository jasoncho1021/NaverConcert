document.addEventListener("DOMContentLoaded", function() {
	initThumbNailChangeListener();
	initAjaxSend();
	//initAjax();
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
		if(this.value.length > 400 ) {
			this.value = this.value.substring(0, 400);
			alert("400자 초과!");
			return;
		} 
		document.querySelector('.guide_review span').innerHTML = this.value.length;
	});
}

function isValidTextLength(textLength) {
	const regExp = /^[\s\S]{5,400}$/;
	return (regExp).test(textLength);
}

function initThumbNailChangeListener() {
	document.querySelector('#reviewImageFileOpenInput').addEventListener('change', function(evt) {
		const image = evt.target.files[0];
		if(image) {
			const elImage = document.querySelector(".item_thumb");
			if(!isValidImageType(image)) {
				alert("지원하지 않는 형식입니다");
				elImage.src = "";
				elImage.closest('li').style.display = 'none';
				return;
			}
			elImage.closest('li').style.display = 'block';
			elImage.src = window.URL.createObjectURL(image);
		}
	});
}

function isValidImageType(image) {
	console.log("imageType:"+ image.type);
	const reg = /^(image)\/(jpg|png)$/;
	return reg.test(image.type);
}

function makeRequestParams() {
	var comment = document.querySelector('textarea').value;
	var productId = document.querySelector('#productId').value;
	var score = document.querySelector('.star_rank').innerHTML;

	var requestParams = "comment=" + encodeURI(comment) + "&"
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

		var formData = new FormData(form);
		var reservationInfoId = document.querySelector('#reservationInfoId').value;
		var url = "api/reservations/"+ reservationInfoId +"/comments?" + makeRequestParams();
		var xhttp = new XMLHttpRequest();
		xhttp.open("POST", url, true);
		xhttp.onload = function(response) {
			if (xhttp.status == 200) {
				reviewWriteSuccess(response);
			} else {
				console.log("failed:"+ response);
			}
		};

		//xhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
		xhttp.send(formData);
		ev.preventDefault();
	}, false);
}

function initAjax() {
	var form = document.forms.namedItem("fileinfo");
	document.querySelector('.bk_btn').addEventListener('click', function(ev) {
		if(!isValidTextLength(document.querySelector('.review_contents textarea').value)) {
			alert("글자수를 맞춰주세요");
			return;
		}

		var formData = new FormData(form);
		var comment = document.querySelector('textarea').value;
		var productId = document.querySelector('#productId').value;
		var score = document.querySelector('.star_rank').innerHTML;
		formData.append("comment",comment);
		formData.append("productId",productId);
		formData.append("score",score);

		var reservationInfoId = document.querySelector('#reservationInfoId').value;
		var url = "api/reservations/"+ reservationInfoId +"/comments";
		var xhttp = new XMLHttpRequest();
		xhttp.open("POST", url, true);
		xhttp.onload = function(response) {
			if (xhttp.status == 200) {
				reviewWriteSuccess(response);
			} else {
				console.log("failed:"+ response);
			}
		};

		xhttp.send(formData);
		ev.preventDefault();
	}, false);
}

function reviewWriteSuccess(response) {
	alert("리뷰 등록 성공!");
	console.log(response);
	//location.replace("/mainpage");
}
