<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="utf-8">
	<meta name="description" content="네이버 예약, 네이버 예약이 연동된 곳 어디서나 바로 예약하고, 네이버 예약 홈(나의예약)에서 모두 관리할 수 있습니다.">
	<!--<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no">
    <title>ADMIN</title>
     
    <link href="/css/nstyle.css" rel="stylesheet">
    <style>
        .container_visual {
            height: 414px;
        }
    </style>
-->
	<link href="/css/ostyle.css" rel="stylesheet">
	<link href="/css/deletemark.css" rel="stylesheet">

	<style>
		.outputbox {
			background: white;
			width: 40%;
			min-width: 414px;
			height: 95%;
			border: 1px solid black;
			position: fixed;
			bottom: 10px;
			right: 10px;
		}
	</style>
</head>

<body>

	<main>
		<div>사진 추가</div>
		<form method="post" name="fileinfo" enctype="multipart/form-data">
			<input type="file" class="hidden_input" id="reviewImageFileOpenInput" name="attachedImage"
				onclick="this.value=null;">
		</form>

		<ul class="lst_thumb" style="list-style-type: none;">
			<!--
		<li class="item">
			<div class="img-wrap">
				<span class="close">&times;</span>
					<img
					src="http://images.freeimages.com/images/premium/previews/2282/2282459-fisheye-tank.jpg"
					width="200" data-id="123">
			</div>
		</li>
		-->
		</ul>

		<div>사진 타이틀</div>
		<input id="imgTitle" type="text" onInput="edValueKeyPress(this)" value="타이틀">

		<div>소개 내용</div>
		<textarea id="productContent" onInput="edValueKeyPress(this)"></textarea>

		<div>이벤트 내용</div>
		<textarea id="eventContent" onInput="edValueKeyPress(this)"></textarea>

		<div class="search" style=""></div>
		<input id="address" type="text" placeholder="검색할 주소" value="불정로 6" />
		<input id="submit" type="button" value="주소 검색" />
		</div>

		<div>
			<button id="sender">
				send
			</button>
		</div>
	</main>

	<section class="outputbox">
		<iframe id="outputiframe" src="uploadDetail" width="100%" height="100%" style="overscroll-behavior-y: contain"
			frameborder="0"> </iframe>
	</section>
</body>
<script id="nmap" type="text/javascript"
	src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=688kianals&submodules=geocoder">
</script>

<script type="rv-template" id="titleImageHandleBar">
	<li class="item" idx={{idx}}>
				<div class="img-wrap">
					<span class="close">&times;</span>
					<img src={{imgUrl}} width="100" data-id="123">
				</div>
		</li>
</script>

<script src="/js/manager/handlebars.min.js"></script>
<script src="/js/manager/productInfo.js"></script>

<script>
	var spoint = null;

	function searchAddressToCoordinate(address) {
		naver.maps.Service.geocode({
			query: address
		}, function (status, response) {
			if (status === naver.maps.Service.Status.ERROR) {
				return alert('Something Wrong!');
			}

			if (response.v2.meta.totalCount === 0) {
				return alert('totalCount' + response.v2.meta.totalCount);
			}

			var htmlAddresses = [],
				item = response.v2.addresses[0];

			spoint = new naver.maps.Point(item.x, item.y);

			if (item.roadAddress) {
				htmlAddresses.push('[도로명 주소] ' + item.roadAddress);
			}

			if (item.jibunAddress) {
				htmlAddresses.push('[지번 주소] ' + item.jibunAddress);
			}

			if (item.englishAddress) {
				htmlAddresses.push('[영문명 주소] ' + item.englishAddress);
			}

			console.log(item.x, item.y);

			if (iframeContentWindow.map != null)
				iframeContentWindow.map.setCenter(spoint);
		});
	}

	function initGeocoder() {
		document.querySelector('#address').addEventListener('keydown', function (e) {
			var keyCode = e.which;

			if (keyCode === 13) { // Enter Key
				searchAddressToCoordinate(document.querySelector('#address').value);
			}
		});

		document.querySelector('#submit').addEventListener('click', function (e) {
			e.preventDefault();

			searchAddressToCoordinate(document.querySelector('#address').value);
		});

		//searchAddressToCoordinate('정자동 178-1');
	}

	var iframeContentWindow;
	var iframeDoc;

	var storedFiles = new Map(); //store the object of the all files

	document.addEventListener("DOMContentLoaded", function () {
		console.log("DOM ContentLoaded");

		var iframe = document.getElementById("outputiframe");
		iframe.contentWindow.addEventListener("DOMContentLoaded", onFrameDOMContentLoaded, false);
		iframe.addEventListener("load", siteLoaded, false);
	});

	function onFrameDOMContentLoaded() {
		console.log(">> iframe DOMContentLoaded");
		initThumbNailChangeListener();
		initDeleteImgListener();
		initSubmit();
		initGeocoder();
	};


	function initSubmit() {
		document.querySelector('#sender').addEventListener('click', (e) => {
			var formData = new FormData();


			for (const key of Object.keys(productInfo)) {
				if (key === 'productImages') {
					for (let value of storedFiles.values()) {
						//productInfo[key].push(value);
						productInfo.mapImage = value;
						formData.append(key, value);
					}
					continue;
				}
				formData.append(key, productInfo[key]);
			}

			//for (const key of Object.keys(productInfo))
			//	console.log(key, formData.get(key));


			//for (let value of storedFiles.values()) {
			//	formData.append('files', value);
			//}

			//var comment = document.querySelector('#productContent').value;
			//formData.append('comment', comment);



			xhr = new XMLHttpRequest();
			xhr.open('POST', '/upload', true);

			//xhr.setRequestHeader('Content-type', 'multipart/form-data');

			xhr.onload = function (response) {
				if (xhr.status == 200) {
					console.log("success:" + response);
				} else {
					console.log("failed:" + response);
				}
			};

			xhr.send(formData);

			return false;
		});
	}

	function siteLoaded(evt) {
		console.log("iframe content loaded");
		var url = evt.currentTarget.contentWindow.location.pathname;

		iframeContentWindow = document.getElementById('outputiframe').contentWindow;
		iframeDoc = iframeContentWindow.document;

		if (url.includes('detail')) {
			reloadDetail();
		} else if (url.includes('reserve')) {

		}
	}

	function makeMapImg() {
		var canvas = document.getElementById("canvas");
		var ctx = canvas.getContext("2d");
		ctx.drawImage(document.getElementById('smap'), 0, 0); // <img 

		/*
		var image = canvas.toDataURL("image/png").replace("image/png",
			"image/octet-stream"
		); // here is the most important part because if you dont replace you will get a DOM 18 exception.
		window.location.href = image; // it will save locally
		*/

		var imgDataUrl = canvas.toDataURL('image/png');

		var blobBin = atob(imgDataUrl.split(',')[1]); // base64 데이터 디코딩
		var array = [];
		for (var i = 0; i < blobBin.length; i++) {
			array.push(blobBin.charCodeAt(i));
		}
		var blob = new Blob([new Uint8Array(array)], {
			type: 'image/png'
		}); // Blob 생성
		var file = new File([blob], "myblob.png");
		storedFiles.set("myimg", file);
		console.log(storedFiles);

		//document.querySelector('#sender').dispatchEvent(new Event('click'));
	}

	function reloadDetail() {
		// img_title -> 
		var title = document.querySelector('#imgTitle').value;

		// carousel -> make
		let nodes = Array.from(document.querySelector('.lst_thumb').children); // get array
		for (var node of nodes) {
			var srcUrl = node.querySelector('img').currentSrc;
			var index = node.getAttribute('idx');
			iframeContentWindow.makeProductImageCarousel(srcUrl, title, index);
		}

		// product_content ->
		document.querySelector('#productContent').dispatchEvent(new Event('input'));
		document.querySelector('#eventContent').dispatchEvent(new Event('input'));

		if (spoint != null) {
			iframeContentWindow.map.setCenter(spoint);
		}
	}

	var uniqueIdx = -1;

	function initThumbNailChangeListener() {
		console.log("--initThumbNail here!", iframeDoc);
		document.querySelector('#reviewImageFileOpenInput').addEventListener('change',
			function (evt) {
				const image = evt.target.files[0];

				if (image) {
					var productImageName = window.URL.createObjectURL(image);

					storedFiles.set(productImageName, image);

					var template = document.querySelector('#titleImageHandleBar').innerHTML;
					var bindTemplate = Handlebars.compile(template);
					var innerHtml = "";

					let nodes = Array.from(document.querySelector('.lst_thumb').children);
					let index = ++uniqueIdx;

					var productImageAdapter = {
						"imgUrl": productImageName,
						"idx": index
					};

					innerHtml += bindTemplate(productImageAdapter);

					document.querySelector('.lst_thumb').innerHTML += innerHtml;

					try {
						iframeContentWindow.makeProductImageCarousel(productImageName, document.querySelector(
								'#imgTitle')
							.value, index);
					} catch (e) {
						console.log(e);
					}
				} else {
					console.log('no');
				}
			});
	}

	function initDeleteImgListener() {
		document.querySelector('.lst_thumb').addEventListener('click',
			function (evt) {
				//var imgWrap = evt.target.parentElement; // (div) span
				// (ul) li div span -> (li) div span
				//imgWrap.parentElement.parentElement.removeChild(imgWrap.parentElement);

				var li = evt.target.closest('li');

				var imgUrl = li.querySelector('img').src;
				storedFiles.delete(imgUrl);

				let idx = li.getAttribute('idx');
				let ul = li.parentElement;
				ul.removeChild(li);

				iframeContentWindow.removeProductImage(idx);

				//let nodes = ul.querySelectorAll('li img'); // li > img 바로뒤
				//console.log(nodes[nodes.length-1].src);

			});
	}

	function edValueKeyPress(target) {
		var id = target.id;

		if (id === "productContent") {
			var ele = iframeDoc.querySelector('.main .store_details .dsc');
			if (ele != null) {
				productInfo.content = target.value;
				ele.innerHTML = target.value;
				ele.scrollIntoView({
					behavior: "smooth",
					block: "center",
					inline: "center"
				});
			}
		} else if (id === "eventContent") {
			var ele = iframeDoc.querySelector('.in_dsc');
			if (ele != null) {
				productInfo.event = target.value;
				ele.innerText = target.value;
			}
		} else if (id === "imgTitle") {
			var ele = iframeDoc.querySelectorAll('.visual_txt_tit > span');
			if (ele != null) {
				productInfo.description = target.value;
				for (var el of ele) {
					el.textContent = target.value;
				}
			}
		}
	}
</script>

</html>