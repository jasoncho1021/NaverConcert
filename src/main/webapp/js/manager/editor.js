var iframeContentWindow;
var iframeDoc;

var storedFiles = new Map(); //store the object of the all files

document.addEventListener("DOMContentLoaded", function (evt) {
    console.log("DOM ContentLoaded", evt);

    var iframe = document.getElementById("outputiframe");
    iframeContentWindow = iframe.contentWindow;
    iframeDoc = iframeContentWindow.document;

    onFrameDOMContentLoaded();

    iframe.addEventListener("load", siteLoaded, false);
});

const CLASS_ONLY_THUMB = '.only_thumb';
const CLASS_LST_THUMB = '.lst_thumb';

function onFrameDOMContentLoaded() {
    console.log(">> iframe DOMContentLoaded");
    initThumbNailChangeListener();
    initDeleteImgListener(CLASS_LST_THUMB);
    initDeleteImgListener(CLASS_ONLY_THUMB);
    initSubmit();
    initGeocoder();
};

function siteLoaded(evt) {
    console.log("iframe content loaded");
    if (evt == null) {
        return;
    }
    iframeContentWindow = document.getElementById('outputiframe').contentWindow;
    iframeDoc = iframeContentWindow.document;
    var url = evt.currentTarget.contentWindow.location.pathname;

    console.log(url);
    if (url.includes('uploadDetail')) {
        reloadDetail();
    } else if (url.includes('uploadReserve')) {

    }
}

function serializePrices(formData) {
    var prices = document.querySelectorAll('#prices li');
    var priceTypeName, price, discountRate;
    var inputs;

    prices.forEach(function (ele, idx) {
        inputs = ele.querySelectorAll('input');
        priceTypeName = inputs[0].value;
        price = inputs[2].value;
        discountRate = inputs[3].value;

        formData.append("inputProductPriceDto[" + idx + "].priceTypeName", priceTypeName);
        formData.append("inputProductPriceDto[" + idx + "].price", price);
        formData.append("inputProductPriceDto[" + idx + "].discountRate", discountRate);
    });
}

function serializeProductImage(formData) {
    var i = 0;
    for (let value of storedFiles.values()) {
        formData.append("productImages[" + i + "].imageType", value.imageType);
        formData.append("productImages[" + i + "].productImage", value.productImage);
        i++;
    }
}

function initSubmit() {
    document.querySelector('#sender').addEventListener('click', (e) => {
        var formData = new FormData();

        serializeProductImage(formData);
        serializePrices(formData);
        formData.append('mapImage', mapImage);

        for (const key of Object.keys(inputProductDto)) {
            var ele = document.getElementById(key);
            formData.append('inputProductDto.' + key, ele.value);
        }

        for (const key of Object.keys(inputDisplayInfoDto)) {
            var ele = document.getElementById(key);
            formData.append('inputDisplayInfoDto.' + key, ele.value);
        }

        xhr = new XMLHttpRequest();
        xhr.open('POST', '/uploadmanager/upload', true);

        //xhr.setRequestHeader('Content-type', 'multipart/form-data');

        xhr.onload = function (response) {
            if (xhr.status == 201) {
                console.log("success:" + response);
            } else {
                console.log("failed:" + response);
            }
        };

        xhr.send(formData);

        return false;
    });
}

function reloadDetail() {
    // img_title -> 
    var title = document.querySelector('#description').value;

    // carousel -> make
    let nodes = Array.from(document.querySelector(CLASS_LST_THUMB).children); // get array
    for (var node of nodes) {
        var srcUrl = node.querySelector('img').currentSrc;
        var index = node.getAttribute('idx');
        iframeContentWindow.makeProductImageCarousel(srcUrl, title, index);
    }

    // product_content ->
    document.querySelector('#content').dispatchEvent(new Event('input'));
    document.querySelector('#event').dispatchEvent(new Event('input'));

    /*
    if (spoint != null) {
        iframeContentWindow.map.setCenter(spoint);
    }
    */
}

var uniqueIdx = 0; // 0 == th, 1 <= ma

function setImage(image, className, index) {
    // url 생성
    var productImageName = window.URL.createObjectURL(image);
    var imageType = 'ma';

    if (index == 0) {
        imageType = 'th';
    }

    let data = {
        "imageType": imageType,
        "productImage": image
    }

    // map 저장 
    storedFiles.set(productImageName, data);

    var template = document.querySelector('#titleImageHandleBar').innerHTML;
    var bindTemplate = Handlebars.compile(template);

    var productImageAdapter = {
        "imgUrl": productImageName,
        "idx": index
    };

    var doc = document.querySelector(className);
    doc.innerHTML += bindTemplate(productImageAdapter);

    return productImageName;
}

function initThumbNailChangeListener() {
    console.log("--initThumbNailChangeListener", iframeDoc);

    document.querySelector('#thumbNailByAdmin').addEventListener('change', function (evt) {
        const image = evt.target.files[0];

        if (image) {
            setImage(image, CLASS_ONLY_THUMB, 0);
            document.querySelector('#thumbNailByAdmin').disabled = true;
        } else {
            console.log('not image');
        }
    });

    document.querySelector('#productImageByAdmin').addEventListener('change',
        function (evt) {
            const image = evt.target.files[0];

            if (image) {
                var index = ++uniqueIdx;
                var productImageName = setImage(image, CLASS_LST_THUMB, index);

                try {
                    iframeContentWindow.makeProductImageCarousel(productImageName, document.querySelector(
                            '#description')
                        .value, index);
                } catch (e) {
                    console.log(e);
                }
            } else {
                console.log('not Image');
            }
        });
}

function initDeleteImgListener(className) {
    document.querySelector(className).addEventListener('click',
        function (evt) {
            //var imgWrap = evt.target.parentElement; // (div) span
            // (ul) li div span -> (li) div span
            //imgWrap.parentElement.parentElement.removeChild(imgWrap.parentElement);

            var li = evt.target.closest('li');

            var imgUrl = li.querySelector('img').src;
            storedFiles.delete(imgUrl);

            let idx = li.getAttribute('idx');
            let ul = li.parentElement;
            ul.removeChild(li);

            if (idx > 0) {
                iframeContentWindow.removeProductImage(idx);
            } else {
                document.querySelector('#thumbNailByAdmin').disabled = false;
            }

            //let nodes = ul.querySelectorAll('li img'); // li > img 바로뒤
            //console.log(nodes[nodes.length-1].src);

        });
}

function edValueKeyPress(target) {
    var id = target.id;

    if (id === "content") {
        console.log('content!');
        var ele = iframeDoc.querySelector('.main .store_details .dsc');
        console.log(ele, target.value);
        if (ele != null) {
            ele.innerHTML = target.value;
            ele.scrollIntoView({
                behavior: "smooth",
                block: "center",
                inline: "center"
            });
        }
    }

    if (id === "event") {
        var ele = iframeDoc.querySelector('.in_dsc');
        if (ele != null) {
            ele.innerText = target.value;
        }
    }

    if (id === "description") {
        var ele = iframeDoc.querySelectorAll('.visual_txt_tit > span');
        if (ele != null) {
            for (var el of ele) {
                el.textContent = target.value;
            }
        }
    }
}