var carouselObj = {
	productImageList : [],
	productImageListLen : 0,
	clientWidth : 0,
	currentTopIndex : 0,
	aniStart : 0,
	aniTid : 0,
	twoImageFlag: false,
	SETTING_NUMBER : 2,

	initCarousel(productImageList) {
		this.productImageList = productImageList;
		this.productImageListLen = this.productImageList.length;
		this.clientWidth = this.productImageList[0].offsetWidth;

		this.productImageList[0].style.transform = "translate("+ -this.clientWidth + "px)";
		this.productImageList[1].style.transform = "translate("+ 0 + "px)";
		for( var i = 2; i < this.productImageListLen; i++) {
			this.productImageList[i].style.transform = "translate("+ this.clientWidth + "px)";
		}
	},

	moveNext() {
		this.setLeft(this.productImageList[(this.currentTopIndex + 1) % this.productImageListLen], 1);
		this.setCenter(this.productImageList[(this.currentTopIndex + 2) % this.productImageListLen]);
		this.setRight(this.productImageList[this.currentTopIndex], 0);

		this.currentTopIndex = ( this.currentTopIndex + 1 ) % this.productImageListLen;
	},

	movePrev() {
		this.setLeft(this.productImageList[(this.currentTopIndex - 1 + this.productImageListLen) % this.productImageListLen], 0);
		this.setCenter(this.productImageList[this.currentTopIndex]);
		this.setRight(this.productImageList[(this.currentTopIndex + 1) % this.productImageListLen], 1);

		this.currentTopIndex = ( this.currentTopIndex - 1 + this.productImageListLen ) % this.productImageListLen;
	},

	setLeft(leftItem, second) {
		leftItem.style.transition = second + "s"; 
		leftItem.style.transform = "translate("+ -this.clientWidth + "px)";
	},

	setCenter(centerItem) {
		centerItem.style.transition = "1s";
		centerItem.style.transform = "translate("+ 0 + "px)";;
	},

	setRight(rightItem, second) {
		rightItem.style.transition = second + "s"; 
		rightItem.style.transform = "translate("+ this.clientWidth + "px)";
	},

	aniRframe(timestamp) {
		var progress = timestamp - this.aniStart;
		if (progress > 2000) {
			this.aniStart = timestamp;
			this.moveNext();
		}
		this.aniTid = requestAnimationFrame(this.aniRframe.bind(this));
	},

	startAni() {
		requestAnimationFrame = window.requestAnimationFrame
		|| window.mozRequestAnimationFrame
		|| window.webkitRequestAnimationFrame
		|| window.msRequestAnimationFrame;
		this.aniTid = requestAnimationFrame(this.aniRframe.bind(this));
	}
};
