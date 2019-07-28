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

function makeComments(response, lenLimit) {
	var replyUl = document.querySelector('.list_short_review');
	var replyLi = document.querySelector('#reservationComment').innerHTML;
	var resultHTML = "";
	
	var score = response.averageScore.toFixed(1);
	var percentage = score * 100 / 5.0;
	var gradeArea = document.querySelector('.grade_area');
	gradeArea.querySelector('.graph_value').style.width = percentage + "%"; 
	gradeArea.querySelector('.text_value span').innerHTML = score; 
	gradeArea.querySelector('.green').innerHTML = response.comments.length + "건";
	
	var len = response.comments.length;
	if(len >= lenLimit) {
		len = lenLimit;
	}

	var comments = response.comments;
	var imgHTML = "";
	for(var i =0; i < len; i++) {
		if(comments[i].commentImages.length > 0) {
			imgHTML = replyLi.replace("${imgExist}", "block")
								.replace("${imgUrl}", comments[i].commentImages[0].saveFileName)
								.replace("${imgCount", comments[i].commentImages.length);
		} else {
			imgHTML = replyLi.replace("${imgExist}", "none")
								.replace("${imgUrl}", "")
								.replace("${imgCount}", "0");
		}
			resultHTML += imgHTML.replace("${comment}", comments[i].comment)
							.replace("${score}", comments[i].score)
							.replace("${name}", comments[i].reservationEmail.split('@')[0])
							.replace("${date}", comments[i].reservationDate.split(' ')[0]);
	}
	replyUl.innerHTML += resultHTML;
}