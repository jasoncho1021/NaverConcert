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

function makeCommentsByHandleBar(comments, lenLimit) {
	var template = document.querySelector('#reservationCommentHandleBar').innerHTML;
	var bindTemplate = Handlebars.compile(template);

	var len = comments.length;
	if(len >= lenLimit) {
		len = lenLimit;
	}

	var innerHtml = "";
	comments.some(function (comment, index) {
		if(index == len) {
			return true;
		}
		if(comment.commentImages.length > 0) {
			comment.imgExist = "block";
			comment.imgUrl = comment.commentImages[0].saveFileName;
			comment.imgCount = comment.commentImages.length;
		} else {
			comment.imgExist = "none";
			comment.imgUrl = "";
			comment.imgCount = 0;
		}
		comment.score = comment.score.toFixed(1);
		comment.name = comment.reservationEmail.split('@')[0];
		comment.date = comment.reservationDate.split(' ')[0];
		innerHtml += bindTemplate(comment);
	});

	document.querySelector('.list_short_review').innerHTML = innerHtml;
}

function makeComments(response, lenLimit) {
	if(response.comments.length < 1) {
		return;
	}
	var score = response.averageScore.toFixed(1);
	var percentage = score * 100 / 5.0;
	var gradeArea = document.querySelector('.grade_area');
	gradeArea.querySelector('.graph_value').style.width = percentage + "%"; 
	gradeArea.querySelector('.text_value span').innerHTML = score; 
	gradeArea.querySelector('.green').innerHTML = response.comments.length + "건";

	makeCommentsByHandleBar(response.comments, lenLimit);
}