function Tab(tabElement) {
	this.tabmenu = tabElement;
	this.checkboxArr = tabElement.querySelectorAll('input[type="checkbox"]');
	this.registerEvents();
}

Tab.prototype = {
	registerEvents : function() {
		this.tabmenu.addEventListener("click", function(evt) {
			if(evt.target.type == 'checkbox') {
				console.log(evt.target.value);
				this.setChecker( evt.target.value );
			}
		}.bind(this));
	},
	setChecker : function(clickedValue) {
		document.querySelector(".star_rank").innerHTML = clickedValue; 

		for(let i = 0; i < this.checkboxArr.length; i++) {
			if(this.checkboxArr[i].value <= clickedValue) {
				this.checkboxArr[i].checked = true;
			} else {
				this.checkboxArr[i].checked = false;
			}
		}
	}
}

var tabmenu = document.querySelector(".rating");
var o = new Tab(tabmenu);