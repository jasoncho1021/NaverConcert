document.addEventListener("DOMContentLoaded", function() {
	currentUrl = document.location.href;
	displayInfoId = currentUrl.split('=')[1];
	sendAJAX('/api/products/' + displayInfoId, makePage);

	document.querySelector('.section_btn > a').href += displayInfoId;

	document.querySelector('.nxt_inn').addEventListener('click', function(evt) {
		carouselObj.moveNext();
		setPageNum();
	});

	document.querySelector('.prev_inn').addEventListener('click', function(evt) {
		carouselObj.movePrev();
		setPageNum();
	});

	var openBtn = document.querySelector('._open');
	var closeBtn = document.querySelector('._close');
	openBtn.addEventListener('click', function(evt) {
		openBtn.style.display = 'none';
		closeBtn.style.display = 'block';
		removeClassName(document.querySelector('.store_details'), 'close3');
	});

	closeBtn.addEventListener('click', function(evt) {
		closeBtn.style.display = 'none';
		openBtn.style.display = 'block';
		addClassName(document.querySelector('.store_details'), 'close3');
	});

	var tabMenu = document.querySelector('.info_tab_lst');
	tabMenu.addEventListener('click', function(evt) {
		var el = evt.target.closest('.item');
		var tabActive = document.querySelector('.info_tab_lst .item .active');
		removeClassName(tabActive, 'active');
		addClassName(el.querySelector('a'), 'active');

		var location = document.querySelector('.detail_location');
		var detail = document.querySelector('.detail_area_wrap');
		if (el.matches('._detail')) { 
			removeClassName(detail, 'hide');
			addClassName(location, 'hide');
		} else if (el.matches('._path')) { 
			removeClassName(location, 'hide');
			addClassName(detail, 'hide');
		}
	});

	var reviewMoreBtn = document.querySelector('.btn_review_more');
	reviewMoreBtn.addEventListener('click', function(evt) {
		var reviewMoreHtml = reviewMoreBtn.getAttribute('href');
		reviewMoreBtn.setAttribute('href', reviewMoreHtml + "?id=" + displayInfoId);
	});
});

function makePage(response) {
	if (response.productImages.length < 2) {
		document.querySelector('.prev').style.display = 'none';
		document.querySelector('.nxt').style.display = 'none';
	} else {
		document.querySelector('.prev').style.display = 'block';
		document.querySelector('.nxt').style.display = 'block';
	}

	makeProductImageCarousel(response.productImages, response.displayInfo.productDescription);

	var description = document.querySelector('.store_details .dsc');
	description.innerHTML = response.displayInfo.productContent;

	makeLocationInfo(response);
}

function makeProductImageCarousel(productImages, productDescription) {
	var template = document.querySelector('#productImageHandleBar').innerHTML;
	var bindTemplate = Handlebars.compile(template);
	var innerHtml = "";

	var productImageAdapter = {
		"imgUrl" : "",
		"productDescription" : productDescription
	};

	var imageLength = productImages.length;

	if(imageLength > carouselObj.SETTING_NUMBER) {
		imageLength = carouselObj.SETTING_NUMBER;
	}

	if (imageLength == carouselObj.SETTING_NUMBER - 1) {
		productImageAdapter.imgUrl = productImages[0].fileInfoId;
		innerHtml += bindTemplate(productImageAdapter);
	} else if ( imageLength == carouselObj.SETTING_NUMBER ) {
		productImageAdapter.imgUrl = productImages[1].fileInfoId;
		innerHtml += bindTemplate(productImageAdapter);
		productImageAdapter.imgUrl = productImages[0].fileInfoId;
		innerHtml += bindTemplate(productImageAdapter);
		productImageAdapter.imgUrl = productImages[1].fileInfoId;
		innerHtml += bindTemplate(productImageAdapter);
		productImageAdapter.imgUrl = productImages[0].fileInfoId;
		innerHtml += bindTemplate(productImageAdapter);
		carouselObj.twoImageFlag = true;
	} else if ( imageLength > carouselObj.SETTING_NUMBER ){
		productImageAdapter.imgUrl = productImages[imageLength-1].fileInfoId;
		innerHtml += bindTemplate(productImageAdapter);
		for(let i = 0; i < imageLength-1; i++) {
			productImageAdapter.imgUrl = productImages[i].fileInfoId;
			innerHtml += bindTemplate(productImageAdapter);
		}
	}

	document.querySelector('.visual_img').innerHTML = innerHtml;
	document.querySelector('.figure_pagination .off span').innerHTML = imageLength;

	if(imageLength > 1) {
		carouselObj.initCarousel(document.querySelectorAll('.visual_img .item'));
		setPageNum();
	}
}

function makeLocationInfo(response) {
	document.querySelector('.store_name').innerHTML = response.displayInfo.productDescription;
	document.querySelector('.detail_info_lst .in_dsc').innerHTML = response.displayInfo.productContent;
	document.querySelector('.store_addr_bold').innerHTML = response.displayInfo.placeLot;
	document.querySelector('.addr_old_detail').innerHTML = response.displayInfo.placeStreet;
	document.querySelector('.addr_detail').innerHTML = response.displayInfo.placeName;
	document.querySelector('.store_map').src = 'images/' + response.displayInfoImage.fileId;

	var tel = document.querySelector('.store_tel');
	tel.setAttribute('href', response.displayInfo.telephone);
	tel.innerHTML = response.displayInfo.telephone;

	makeComments(response, 3);
}

function setPageNum() {
	if(!carouselObj.twoImageFlag) {
		document.querySelector('.figure_pagination span.num').innerHTML = carouselObj.currentTopIndex + 1;
		return;
	}
	setTwoImagePageNum();
}

function setTwoImagePageNum() {
	if(carouselObj.currentTopIndex > 1) {
		document.querySelector('.figure_pagination span.num').innerHTML = carouselObj.currentTopIndex - 1;	
	} else {
		document.querySelector('.figure_pagination span.num').innerHTML = carouselObj.currentTopIndex + 1;
	}
}
