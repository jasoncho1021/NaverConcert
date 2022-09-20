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
		<div>썸네일 추가</div>
		<form method="post" name="fileinfo" enctype="multipart/form-data">
			<input type="file" class="hidden_input" id="thumbNailByAdmin" name="attachedImage"
				onclick="this.value=null;">
		</form>

		<ul class="only_thumb" style="list-style-type: none;">
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


		<div>사진 추가</div>
		<form method="post" name="fileinfo" enctype="multipart/form-data">
			<input type="file" class="hidden_input" id="productImageByAdmin" name="attachedImage"
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
		<input id="description" type="text" onInput="edValueKeyPress(this)" value="타이틀">

		<div>소개 내용</div>
		<textarea id="content" onInput="edValueKeyPress(this)"></textarea>

		<div>이벤트 내용</div>
		<textarea id="event" onInput="edValueKeyPress(this)"></textarea>

		<div class="search">지도이미지</div>
		<input id="address" type="text" placeholder="검색할 주소" value="불정로 6" />
		<input id="submit" type="button" value="주소 검색" />

		<canvas id="canvas" hidden></canvas>
		<img class="store_map img_thumb" alt="map" id="managermap" crossorigin="anonymous"
			src="https://naveropenapi.apigw.ntruss.com/map-static/v2/raster-cors?w=300&h=300&center=127.084338,37.5061&level=16&X-NCP-APIGW-API-KEY-ID=688kianals"
			style="display: none;">

		<div>openingHours</div>
		<textarea id="openingHours" onInput="edValueKeyPress(this)"></textarea>
		<div>placeName</div>
		<textarea id="placeName" onInput="edValueKeyPress(this)"></textarea>
		<div>placeLot</div>
		<textarea id="placeLot" onInput="edValueKeyPress(this)"></textarea>
		<div>placeStreet</div>
		<textarea id="placeStreet" onInput="edValueKeyPress(this)"></textarea>
		<div>tel</div>
		<textarea id="tel" onInput="edValueKeyPress(this)"></textarea>
		<div>homepage</div>
		<textarea id="homepage" onInput="edValueKeyPress(this)"></textarea>
		<div>email</div>
		<textarea id="email" onInput="edValueKeyPress(this)"></textarea>

		<div>카테고리</div>
		<select id="categoryId">
			<option value="1">전시</option>
			<option value="2">뮤지컬</option>
			<option value="3">콘서트</option>
			<option value="4">클래식</option>
			<option value="5">연극</option>
		</select>
		<!--
		'성인(A), 청소년(Y), 유아(B), 셋트(S), 장애인(D), 지역주민(C), 어얼리버드(E) 
		기타 다른 유형이 있다면 위와 겹치지 않게 1자로 정의하여 기입,
		VIP(V), R석(R), B석(B), S석(S), 평일(D)',
		-->

		<div>가격</div>
		<select name="priceTypeName" id="priceTypeName">
			<option value="A">성인(A)</option>
			<option value="Y">청소년(Y)</option>
			<option value="B">유아(B)</option>
			<option value="S">세트(S)</option>
			<option value="D">장애인(D)</option>
			<option value="C">지역주민(C)</option>
			<option value="E">얼리버드(E)</option>
		</select>
		가격:<input type='text' id='price' />
		할인율:<input type='text' id='discountRate' />
		<div>
			<input type='button' value='추가' onclick='addList()' />
		</div>

		<ul id='prices'>
		</ul>

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
<script src="/js/manager/editor.js"></script>
<script src="/js/manager/map.js"></script>

<script>
	function addList() {
		// 2. 추가할 li element 생성
		// 2-1. 추가할 li element 생성
		const li = document.createElement("li");
		var target = document.getElementById('priceTypeName');

		const priceTypeNode = document.createElement('input');
		priceTypeNode.setAttribute('hidden', 'true');
		priceTypeNode.setAttribute('value', target.value);

		// 1. 추가할 값을 input창에서 읽어온다
		const priceTypeNameNode = document.createElement('input');
		const priceTypeName = target.options[target.selectedIndex].text;
		priceTypeNameNode.setAttribute('readonly', 'true');
		priceTypeNameNode.setAttribute('value', priceTypeName);

		// 2-2. li에 id 속성 추가 
		//li.setAttribute('id', addValue);

		// 2-3. li에 text node 추가 
		const priceNode = document.createElement('input')
		const price = document.getElementById('price').value;
		priceNode.setAttribute('type', 'text');
		priceNode.setAttribute('value', price);
		priceNode.setAttribute('id', 'priceNode');

		const discountRateNode = document.createElement('input')
		const discountRate = document.getElementById('discountRate').value;
		discountRateNode.setAttribute('type', 'text');
		discountRateNode.setAttribute('value', discountRate);

		li.appendChild(priceTypeNode);
		li.appendChild(priceTypeNameNode);
		li.appendChild(priceNode);
		li.appendChild(discountRateNode);

		// 3. 생성된 li를 ul에 추가
		document.getElementById('prices').appendChild(li);
	}
</script>

</html>