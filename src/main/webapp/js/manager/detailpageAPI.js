document.addEventListener("DOMContentLoaded", function () {
	currentUrl = document.location.href;
	displayInfoId = currentUrl.split('=')[1];

	//document.querySelector('.section_btn > a').href += displayInfoId;

	var openBtn = document.querySelector('._open');
	var closeBtn = document.querySelector('._close');
	openBtn.addEventListener('click', function (evt) {
		openBtn.style.display = 'none';
		closeBtn.style.display = 'block';
		removeClassName(document.querySelector('.store_details'), 'close3');
	});

	closeBtn.addEventListener('click', function (evt) {
		closeBtn.style.display = 'none';
		openBtn.style.display = 'block';
		addClassName(document.querySelector('.store_details'), 'close3');
	});

	var tabMenu = document.querySelector('.info_tab_lst');
	tabMenu.addEventListener('click', function (evt) {
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
	reviewMoreBtn.addEventListener('click', function (evt) {
		var reviewMoreHtml = reviewMoreBtn.getAttribute('href');
		reviewMoreBtn.setAttribute('href', reviewMoreHtml + "?id=" + displayInfoId);
	});
});

function activateMoveButton() {
	document.querySelector('.nxt_inn').addEventListener('click', moveNext);
	document.querySelector('.prev_inn').addEventListener('click', movePrev);
}

function deactivateMoveButton() {
	document.querySelector('.nxt_inn').removeEventListener('click', moveNext);
	document.querySelector('.prev_inn').removeEventListener('click', movePrev);
}

function movePrev() {
	carouselObj.movePrev();
	setPageNum();
}

function moveNext() {
	carouselObj.moveNext();
	setPageNum();
}

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

function makeProductImageCarousel(productImage, productDescription, index) {
	var template = document.querySelector('#productImageHandleBar').innerHTML;
	var bindTemplate = Handlebars.compile(template);
	var siblings = document.querySelectorAll('.visual_img li img');
	var innerHtml = "";

	var productImageAdapter = {
		"imgUrl": productImage,
		"productDescription": productDescription,
		"idx": index
	};

	var imageLength = siblings.length + 1;

	/*if(imageLength + 1 > carouselObj.SETTING_NUMBER) {
		imageLength = carouselObj.SETTING_NUMBER;
	}*/

	activateMoveButton();

	if (imageLength == carouselObj.SETTING_NUMBER) { // 2
		innerHtml += bindTemplate(productImageAdapter);

		var dummyImageAdapter = {
			"imgUrl": productImage,
			"productDescription": productDescription,
			"idx": index
		};

		dummyImageAdapter.imgUrl = siblings[0].currentSrc;
		dummyImageAdapter.idx = siblings[0].parentElement.getAttribute('idx');
		innerHtml += bindTemplate(dummyImageAdapter);

		innerHtml += bindTemplate(productImageAdapter);

		innerHtml += bindTemplate(dummyImageAdapter);

		carouselObj.twoImageFlag = true;
	} else if (imageLength > carouselObj.SETTING_NUMBER) {

		if (carouselObj.twoImageFlag) { // 3
			carouselObj.twoImageFlag = false;

			innerHtml += bindTemplate(productImageAdapter);

			for (var i = 1; i <= 2; i++) {
				productImageAdapter.imgUrl = siblings[i].currentSrc;
				productImageAdapter.idx = siblings[i].parentElement.getAttribute('idx');
				innerHtml += bindTemplate(productImageAdapter);
			}
		} else {
			innerHtml += bindTemplate(productImageAdapter);

			productImageAdapter.imgUrl = siblings[1].currentSrc;
			productImageAdapter.idx = siblings[1].parentElement.getAttribute('idx');
			innerHtml += bindTemplate(productImageAdapter);

			for (let i = 2; i < siblings.length; i++) {
				productImageAdapter.imgUrl = siblings[i].currentSrc;
				productImageAdapter.idx = siblings[i].parentElement.getAttribute('idx');
				innerHtml += bindTemplate(productImageAdapter);
			}

			productImageAdapter.imgUrl = siblings[0].currentSrc;
			productImageAdapter.idx = siblings[0].parentElement.getAttribute('idx');
			innerHtml += bindTemplate(productImageAdapter);
		}
	} else { // 1
		innerHtml = bindTemplate(productImageAdapter);
		deactivateMoveButton();
	}

	document.querySelector('.visual_img').innerHTML = innerHtml;

	imageLength = document.querySelectorAll('.visual_img li img').length;

	if (carouselObj.twoImageFlag) {
		document.querySelector('.figure_pagination .off span').innerHTML = 2;
	} else {
		document.querySelector('.figure_pagination .off span').innerHTML = imageLength;
	}

	if (imageLength > 1) {
		carouselObj.initCarousel(document.querySelectorAll('.visual_img .item'));
	}
	setPageNum();
}

function removeProductImage(index) {
	var target = document.querySelectorAll(".visual_img [idx='" + index + "']");
	var parent = target[0].parentElement;
	if (carouselObj.twoImageFlag) {
		for (let value of target) {
			parent.removeChild(value);
		}
		var child = parent.querySelector('li');
		parent.removeChild(child);
		carouselObj.twoImageFlag = false;

		document.querySelector('.figure_pagination .off span').innerHTML = 1;
	} else {
		parent.removeChild(target[0]);

		var children = parent.querySelectorAll('li');
		document.querySelector('.figure_pagination .off span').innerHTML = children.length;

		if (children.length == 2) {
			parent.appendChild(children[0].cloneNode(true));
			parent.appendChild(children[1].cloneNode(true));
			carouselObj.twoImageFlag = true;
		}
	}

	var list = document.querySelectorAll('.visual_img .item');
	if (list.length > 1) {
		carouselObj.initCarousel(list);
		setPageNum();
	} else {
		if (list.length == 1) {
			document.querySelector('.figure_pagination span.num').innerHTML = 1;
			list[0].style.transform = "translate(" + 0 + "px)";
		} else {
			document.querySelector('.figure_pagination span.num').innerHTML = 0;
		}
		carouselObj.currentTopIndex = 0;
		deactivateMoveButton();
	}
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

function setPageNum() {
	if (!carouselObj.twoImageFlag) {
		document.querySelector('.figure_pagination span.num').innerHTML = carouselObj.currentTopIndex + 1;
		return;
	}
	setTwoImagePageNum();
}

function setTwoImagePageNum() {
	if (carouselObj.currentTopIndex > 1) {
		document.querySelector('.figure_pagination span.num').innerHTML = carouselObj.currentTopIndex - 1;
	} else {
		document.querySelector('.figure_pagination span.num').innerHTML = carouselObj.currentTopIndex + 1;
	}
}