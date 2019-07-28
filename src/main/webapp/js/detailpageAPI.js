document.addEventListener("DOMContentLoaded", function() {
	myurl = document.location.href;
	displayInfoId = myurl.split('=')[1];
	sendAJAX('/api/products/' + displayInfoId, makeList);

	var nxtBtn = document.querySelector('.nxt_inn');
	nxtBtn.addEventListener('click', function(evt) {
		var productImageList = document.querySelectorAll('.visual_img .item');
		var amount = productImageList.length;
		var clientWidth = productImageList[0].offsetWidth;

		var i;
		var currentIndex = 1;
		var endIndex = amount - 1;
		var leftestIndex = 0;
		var translateXval = [];
		for (i = 0; i < amount; i++) {
			var tMatrix = window.getComputedStyle(productImageList[i])
					.getPropertyValue("transform");
			var values = tMatrix.split(',');

			translateXval[i] = parseInt(values[4]) - clientWidth; // <- all
			productImageList[i].style.transform = "translate("
					+ translateXval[i] + "px)";
			productImageList[i].style.transition = "1s";

			if (productImageList[i].classList.contains('currentImg')) {
				currentIndex = i;
			}
			if (productImageList[i].classList.contains('endImg')) {
				endIndex = i;
			}
			if (productImageList[i].classList.contains('leftestImg')) {
				leftestIndex = i;
			}
		}
		var goRight = translateXval[leftestIndex] + (clientWidth * amount);
		productImageList[leftestIndex].style.transform = "translate(" + goRight + "px)";
		productImageList[leftestIndex].style.transition = "0s";
		productImageList[leftestIndex].classList.remove('leftestImg');

		productImageList[endIndex].classList.remove('endImg');
		productImageList[leftestIndex].classList.add('endImg');

		var rightIndex = (currentIndex + 1) % amount;

		productImageList[rightIndex].classList.add('currentImg');
		productImageList[currentIndex].classList.remove('currentImg');
		productImageList[currentIndex].classList.add('leftestImg');
		
		var pageNum = document.querySelector('.figure_pagination span.num').innerHTML;
		if(pageNum == 1) {
			document.querySelector('.figure_pagination span.num').innerHTML = 2;
		} else {
			document.querySelector('.figure_pagination span.num').innerHTML = 1;
		}
	});

	var prevBtn = document.querySelector('.prev_inn');
	prevBtn.addEventListener('click', function(evt) {
		var productImageList = document.querySelectorAll('.visual_img .item');
		var amount = productImageList.length;
		var clientWidth = productImageList[0].offsetWidth;

		var i;
		var currentIndex = 1;
		var endIndex = amount - 1;
		var leftestIndex = 0;
		var translateXval = [];
		for (i = 0; i < amount; i++) {
			var tMatrix = window.getComputedStyle(productImageList[i])
					.getPropertyValue("transform");
			var values = tMatrix.split(',');

			translateXval[i] = parseInt(values[4]) + clientWidth; // <- all
			productImageList[i].style.transform = "translate("
					+ translateXval[i] + "px)";
			productImageList[i].style.transition = "1s";

			if (productImageList[i].classList.contains('currentImg')) {
				currentIndex = i;
			}
			if (productImageList[i].classList.contains('endImg')) {
				endIndex = i;
			}
			if (productImageList[i].classList.contains('leftestImg')) {
				leftestIndex = i;
			}
		}

		var preEndIndex = (endIndex - 1 + amount) % amount;

		var goLeft = translateXval[endIndex] - (clientWidth * amount);
		productImageList[endIndex].style.transform = "translate(" + goLeft + "px)";
		productImageList[endIndex].style.transition = "0s";
		productImageList[endIndex].classList.remove('endImg');
		productImageList[endIndex].classList.add('leftestImg');

		productImageList[preEndIndex].classList.add('endImg');

		productImageList[leftestIndex].classList.remove('leftestImg');
		productImageList[leftestIndex].classList.add('currentImg');

		productImageList[currentIndex].classList.remove('currentImg');
		
		var pageNum = document.querySelector('.figure_pagination span.num').innerHTML;
		if(pageNum == 1) {
			document.querySelector('.figure_pagination span.num').innerHTML = 2;
		} else {
			document.querySelector('.figure_pagination span.num').innerHTML = 1;
		}
	});

	var openBtn = document.querySelector('._open');
	var closeBtn = document.querySelector('._close');
	openBtn.addEventListener('click', function(evt) {
		openBtn.style.display = 'none';
		closeBtn.style.display = 'block';
		document.querySelector('.store_details').classList.remove('close3');
	});
	closeBtn.addEventListener('click', function(evt) {
		closeBtn.style.display = 'none';
		openBtn.style.display = 'block';
		document.querySelector('.store_details').classList.add('close3');
	});

	var tabMenu = document.querySelector('.info_tab_lst');
	tabMenu.addEventListener('click', function(evt) {
		var el = evt.target.closest('.item');

		var tabActive = document.querySelector('.info_tab_lst .item .active');
		tabActive.classList.remove('active');
		el.querySelector('a').classList.add('active');

		var location = document.querySelector('.detail_location');
		var detail = document.querySelector('.detail_area_wrap');
		if (el.classList.contains('_detail')) {
			detail.classList.remove('hide');
			location.classList.add('hide');
		} else if (el.classList.contains('_path')) {
			location.classList.remove('hide');
			detail.classList.add('hide');
		}

	});

	var reviewMoreBtn = document.querySelector('.btn_review_more');
	reviewMoreBtn.addEventListener('click', function(evt) {
		var html = reviewMoreBtn.getAttribute('href');
		reviewMoreBtn.setAttribute('href', html + "?id=" + displayInfoId);
	});
});

function makeList(response) {
	var ul = document.querySelector('.visual_img');
	var resultHTML = "";
	var li = document.querySelector('#productImage').innerHTML;
	var productImages = response.productImages;

	if (productImages.length < 2) {
		document.querySelector('.prev').style.display = 'none';
		document.querySelector('.nxt').style.display = 'none';
	} else {
		document.querySelector('.prev').style.display = 'block';
		document.querySelector('.nxt').style.display = 'block';
	}

	var pageNum = 0;
	var len = productImages.length;
	if (len == 1) {
		resultHTML += li.replace("${itemClass}", "item").replace(
				"${imgUrl}", productImages[0].saveFileName).replace(
				"${productDescription}",
				response.displayInfo.productDescription);
		pageNum = "1";
	} else if (len > 1) {
			resultHTML += li.replace("${itemClass}", "item leftestImg").replace(
					"${imgUrl}", productImages[1].saveFileName).replace(
					"${productDescription}",
					response.displayInfo.productDescription);
			resultHTML += li.replace("${itemClass}", "item currentImg").replace(
					"${imgUrl}", productImages[0].saveFileName).replace(
					"${productDescription}",
					response.displayInfo.productDescription);
			resultHTML += li.replace("${itemClass}", "item").replace(
					"${imgUrl}", productImages[1].saveFileName).replace(
					"${productDescription}",
					response.displayInfo.productDescription);
			resultHTML += li.replace("${itemClass}", "item endImg").replace(
					"${imgUrl}", productImages[0].saveFileName).replace(
					"${productDescription}",
					response.displayInfo.productDescription);
			pageNum = "2";
	}
	ul.innerHTML += resultHTML;
	document.querySelector('.figure_pagination .off span').innerHTML = pageNum;
	
	if(len == 1) {
		ul.querySelector('.item').style.transform = "translate(0px)";
	}
	
	

	var description = document.querySelector('.store_details .dsc');
	description.innerHTML = response.displayInfo.productContent;

	makeLocationInfo(response);
}

function makeLocationInfo(response) {

	document.querySelector('.store_name').innerHTML = response.displayInfo.productDescription;
	document.querySelector('.detail_info_lst .in_dsc').innerHTML = response.displayInfo.productContent;
	document.querySelector('.store_addr_bold').innerHTML = response.displayInfo.placeLot;
	document.querySelector('.addr_old_detail').innerHTML = response.displayInfo.placeStreet;
	document.querySelector('.addr_detail').innerHTML = response.displayInfo.placeName;

	var tel = document.querySelector('.store_tel');
	tel.setAttribute('href', response.displayInfo.telephone);
	tel.innerHTML = response.displayInfo.telephone;

	makeComments(response, 3);
}
