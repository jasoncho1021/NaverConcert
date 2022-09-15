var spoint = null;

function searchAddressToCoordinate(address) {
    naver.maps.Service.geocode({
        query: address
    }, function (status, response) {
        if (status === naver.maps.Service.Status.ERROR) {
            return alert('Something Wrong!');
        }

        if (response.v2.meta.totalCount === 0) {
            return alert('totalCount' + response.v2.meta.totalCount);
        }

        var htmlAddresses = [],
            item = response.v2.addresses[0];

        spoint = new naver.maps.Point(item.x, item.y);

        if (item.roadAddress) { // 도로명 주소
            htmlAddresses.push('[도로명 주소] ' + item.roadAddress);
            document.querySelector('#placeStreet').value = item.roadAddress;
        }

        if (item.jibunAddress) { // 지번 주소
            htmlAddresses.push('[지번 주소] ' + item.jibunAddress);
            document.querySelector('#placeLot').value = item.jibunAddress;
        }

        /*
        if (item.englishAddress) { // 영문명 주소
            htmlAddresses.push('[영문명 주소] ' + item.englishAddress);
        }
        */
        console.log(item);

        focusMap();
        updateMap(item.x, item.y);
        makeMapImg();
        /*
        if (iframeContentWindow.map != null)
        	iframeContentWindow.map.setCenter(spoint);
        */
    });
}

function focusMap() {
    iframeDoc.querySelector('._path').click();

    var ele = iframeDoc.querySelector('#smap');
    ele.scrollIntoView({
        behavior: "smooth",
        block: "center",
        inline: "center"
    });
}

function updateMap(x, y) {
    var url =
        "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster-cors?w=300&h=300&markers=type:d|size:mid|pos:" +
        x + "%20" + y +
        "|viewSizeRatio:0.5&scale=2&format=png&X-NCP-APIGW-API-KEY-ID=688kianals";

    document.querySelector('#managermap').src = url;
}


function makeMapImg() {
    var imgObj = document.getElementById('managermap');
    imgObj.onload = function (e) {

        var canvas = document.getElementById("canvas");

        var ctx = canvas.getContext("2d");
        ctx.canvas.width = imgObj.width;
        ctx.canvas.height = imgObj.height;
        ctx.drawImage(imgObj, 0, 0, imgObj.width, imgObj.height);

        var imgDataUrl = canvas.toDataURL('image/png');

        iframeDoc.querySelector('#smap').src = imgDataUrl;

        var blobBin = atob(imgDataUrl.split(',')[1]); // base64 데이터 디코딩
        var array = [];
        for (var i = 0; i < blobBin.length; i++) {
            array.push(blobBin.charCodeAt(i));
        }
        var blob = new Blob([new Uint8Array(array)], {
            type: 'image/png'
        }); // Blob 생성
        var file = new File([blob], "mapblob.png", {
            type: 'image/png'
        });

        mapImage = file;
    }
}

function initGeocoder() {
    document.querySelector('#address').addEventListener('keydown', function (e) {
        var keyCode = e.which;

        if (keyCode === 13) { // Enter Key
            searchAddressToCoordinate(document.querySelector('#address').value);
        }
    });

    document.querySelector('#submit').addEventListener('click', function (e) {
        e.preventDefault();

        searchAddressToCoordinate(document.querySelector('#address').value);
    });
}