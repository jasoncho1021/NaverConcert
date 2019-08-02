document.addEventListener("DOMContentLoaded", function() {
	myurl = document.location.href;
	displayInfoId = myurl.split('=')[1];
	sendAJAX('/api/products/' + displayInfoId, makeList);

	document.querySelector('.nxt_inn').addEventListener('click', function(evt) {
		carouselObj.moveNext();
		setSpan();
	});

	document.querySelector('.prev_inn').addEventListener('click', function(evt) {
		carouselObj.movePrev();
		setSpan();
	});

	var openBtn = document.querySelector('._open');
	openBtn.addEventListener('click', function(evt) {
		openBtn.style.display = 'none';
		closeBtn.style.display = 'block';
		document.querySelector('.store_details').classList.remove('close3');
	});

	var closeBtn = document.querySelector('._close');
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
		resultHTML += li.replace(
				"${imgUrl}", productImages[0].saveFileName).replace(
				"${productDescription}",
				response.displayInfo.productDescription);
		pageNum = "1";
	} else if (len > 1) {
		resultHTML += li.replace("${imgUrl}", productImages[1].saveFileName)
						.replace("${productDescription}",response.displayInfo.productDescription);
		resultHTML += li.replace("${imgUrl}", productImages[0].saveFileName)
						.replace("${productDescription}",response.displayInfo.productDescription);
		resultHTML += li.replace("${imgUrl}", productImages[1].saveFileName)
						.replace("${productDescription}",response.displayInfo.productDescription);
		resultHTML += li.replace("${imgUrl}", productImages[0].saveFileName)
						.replace("${productDescription}",response.displayInfo.productDescription);
		pageNum = "2";
	}
	ul.innerHTML += resultHTML;
	document.querySelector('.figure_pagination .off span').innerHTML = pageNum;

	if(len == 1) {
		ul.querySelector('.item').style.transform = "translate(0px)";
	} else {
		carouselObj.initCarousel(document.querySelectorAll('.visual_img .item'));
		setSpan();
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

function setSpan() {
	if(carouselObj.currentTopIndex > 1) {
		document.querySelector('.figure_pagination span.num').innerHTML = carouselObj.currentTopIndex - 1;	
	} else {
		document.querySelector('.figure_pagination span.num').innerHTML = carouselObj.currentTopIndex + 1;
	}
}
