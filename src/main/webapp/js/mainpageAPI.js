document.addEventListener("DOMContentLoaded", function() {
	sendAJAX('/api/products?start=0', makeList);
	sendAJAX('/api/categories', getCategories);
	sendAJAX('/api/promotions', makePromoList);

	var moreButton = document.querySelector(".more .btn");
	moreButton.addEventListener("click", function(evt) {
		var tabActive = document.querySelector('.item .active');
		var categories = tabActive.closest('.item').getAttribute('data-category');
		var start = document.querySelectorAll('.wrap_event_box li').length;

		if (categories == 0) {
			sendAJAX('/api/products?start=' + start, makeList);
		} else {
			sendAJAX('/api/products?categoryId=' + categories + '&start=' + start, makeList);
		}
	});

	var topButton = document.querySelector('.lnk_top_text');
	topButton.addEventListener('click', scroll(0, 0));

	var tabMenu = document.querySelector('.section_event_tab .tab_lst_min');
	tabMenu.addEventListener('click', function(evt) {
		var tabItem = evt.target.closest('.item');
		if (tabItem != null) {
			var tabActive = document.querySelector('.item .active');
			tabActive.classList.remove('active');
			tabItem.querySelector('a').classList.add('active');

			var eventBoxUl = document.querySelectorAll('.lst_event_box');
			eventBoxUl[0].innerHTML = "";
			eventBoxUl[1].innerHTML = "";

			var categories = tabItem.getAttribute('data-category');
			var categoryCount = document.querySelector('.pink');
			if (categories == 0) {
				sendAJAX('/api/products?start=0', makeList);
				categoryCount.innerHTML = document.querySelector('.tab_lst_min li').getAttribute('category-count') + "개";
			} else {
				sendAJAX('/api/products?categoryId=' + categories + '&start=0', makeList);
				categoryCount.innerHTML = tabItem.querySelector('a').closest('.item').getAttribute('category-count') + "개";
			}
		}
		moreButton.setAttribute('loadedCnt', 0);
		moreButton.style.display = 'block';
	});
});

function makeList(response) {
	var itemListHtml = document.querySelector("#itemList").innerHTML;
	var resultHTML = [ "", "" ];
	var eventBoxUl = document.querySelectorAll('.lst_event_box');
	var productLimitList = response.items;
	var len = productLimitList.length;

	var moreButton = document.querySelector(".more .btn");
	var categoryCnt = document.querySelector('.active').closest('.item').getAttribute('category-count');
	var loadedCnt = Number(moreButton.getAttribute('loadedCnt'));
	var loadingCnt = loadedCnt + len;
	if (loadingCnt == categoryCnt) {
		moreButton.style.display = 'none';
	}
	moreButton.setAttribute('loadedCnt', loadingCnt);
	for (var i = 0; i < len; i++) {
		resultHTML[i % 2] += itemListHtml.replace("{{id}}",productLimitList[i].displayInfoId)
								.replace("{{id}}}", productLimitList[i].productId)
								.replace("{{description}}", productLimitList[i].productDescription)
								.replace("{{imgUrl}}", productLimitList[i].productId)
								.replace("{{description}}", productLimitList[i].productDescription)
								.replace("{{placename}}", productLimitList[i].placeName)
								.replace("{{content}}", productLimitList[i].productContent);
	}
	eventBoxUl[0].innerHTML += resultHTML[0];
	eventBoxUl[1].innerHTML += resultHTML[1];
}

function makePromoList(response) {
	var promotionItems = document.querySelector("#promotionItem").innerHTML;
	var resultHTML = "";
	var promotionImgUlTag = document.querySelector('.visual_img');
	var promoList = response.items;
	var len = promoList.length;

	resultHTML += promotionItems.replace("{{imgUrl}}", promoList[len-1].productId);
	for (var i = 0; i < len-1; i++) {
		resultHTML += promotionItems.replace("{{imgUrl}}", promoList[i].productId);
	}
	promotionImgUlTag.innerHTML += resultHTML;

	startMainPageCarousel();
}

function getCategories(response) {
	var tabLi = document.querySelectorAll('.tab_lst_min li');
	var len = response.items.length;
	var sum = 0;
	for (var i = 0; i < len; i++) {
		var cnt = response.items[i].count;
		tabLi[i + 1].setAttribute('category-count', cnt);
		sum += cnt;
	}
	tabLi[0].setAttribute('category-count', sum);
	document.querySelector('.pink').innerHTML = sum + "개";
}

function startMainPageCarousel() {
	carouselObj.initCarousel(document.querySelectorAll('.visual_img .item'));
	carouselObj.startAni();
}
