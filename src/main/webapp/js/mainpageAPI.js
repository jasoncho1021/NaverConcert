document.addEventListener("DOMContentLoaded", function() {
	sendAJAX('/api/products?start=0', makeList);
	sendAJAX('/api/categories', getCategories);
	sendAJAX('/api/promotions', makePromoList);

	var bttn = document.querySelector(".more .btn");
	bttn.addEventListener("click", function(evt) {
		var tabActive = document.querySelector('.item .active');
		var categories = tabActive.closest('.item').getAttribute(
				'data-category');
		var start = document.querySelectorAll('.wrap_event_box li').length;

		if (categories == 0) {
			sendAJAX('/api/products?start=' + start, makeList);
		} else {
			sendAJAX('/api/products?categoryId=' + categories
					+ '&start=' + start, makeList);
		}
	});

	var topbutton = document.querySelector('.lnk_top_text');
	topbutton.addEventListener('click', scroll(0, 0));

	var tabmenu = document.querySelector('.section_event_tab .tab_lst_min');
	tabmenu.addEventListener('click', function(evt) {
		var el = evt.target.closest('.item');
		if (el != null) {
			var tabActive = document.querySelector('.item .active');
			tabActive.classList.remove('active');
			el.querySelector('a').classList.add('active');

			var ul = document.querySelectorAll('.lst_event_box');
			ul[0].innerHTML = "";
			ul[1].innerHTML = "";

			var categories = el.getAttribute('data-category');
			var categoryCount = document.querySelector('.pink');
			if (categories == 0) {
				sendAJAX('/api/products?start=0', makeList);
				categoryCount.innerHTML = document.querySelector(
						'.tab_lst_min li').getAttribute('category-count')
						+ "개";
			} else {
				sendAJAX('/api/products?categoryId=' + categories
						+ '&start=0', makeList);
				categoryCount.innerHTML = el.querySelector('a')
						.closest('.item').getAttribute('category-count')
						+ "개";
			}
		}
		bttn.setAttribute('loadedCnt', 0);
		bttn.style.display = 'block';
	});
});

function makeList(response) {
	var html = document.querySelector("#itemList").innerHTML;
	var resultHTML = [ "", "" ];
	var ul = document.querySelectorAll('.lst_event_box');
	var productLimitList = response.productLimitList;
	var len = productLimitList.length;

	var bttn = document.querySelector(".more .btn");
	var categoryCnt = document.querySelector('.active').closest('.item')
			.getAttribute('category-count');
	var loadedCnt = Number(bttn.getAttribute('loadedCnt'));
	var loadingCnt = loadedCnt + len;
	if (loadingCnt == categoryCnt) {
		bttn.style.display = 'none';
	}
	bttn.setAttribute('loadedCnt', loadingCnt);
	for (var i = 0; i < len; i++) {
		resultHTML[i % 2] += html.replace("${id}",
				productLimitList[i].displayInfoId).replace("${id}",
				productLimitList[i].id).replace("${description}",
				productLimitList[i].description).replace("${imgUrl}",
				productLimitList[i].imageUrl).replace("${description}",
				productLimitList[i].description).replace("${placename}",
				productLimitList[i].placeName).replace("${content}",
				productLimitList[i].content);
	}
	ul[0].innerHTML += resultHTML[0];
	ul[1].innerHTML += resultHTML[1];
}

function makePromoList(response) {
	var html = document.querySelector("#promotionItem").innerHTML;
	var resultHTML = "";
	var ul = document.querySelector('.visual_img');
	var promoList = response.items;
	var len = promoList.length;

	resultHTML += html.replace("${itemclass}", "item currentImg").replace(
			"${imgUrl}", promoList[0].productImageUrl);
	for (var i = 1; i < len; i++) {
		resultHTML += html.replace("${itemclass}", "item").replace("${imgUrl}",
				promoList[i].productImageUrl);
	}
	ul.innerHTML += resultHTML;
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

function sendAJAX(url, callback) {
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
