window.addEventListener("load", function(){
    let searchForm = document.querySelector("#search-word_form");
    let searchInput = searchForm.querySelector("#search-input");
    let meanBox = document.querySelector("#mean-box");

    searchForm.onsubmit = function(e){
        e.preventDefault();
        let keyword = searchInput.value;
        search(keyword);
    }
    function search(keyword){       
        let reqJson = new Object();       // 요청 Data
        reqJson.keyword = keyword.toLowerCase();
        let result = null;                // 응답 Data 저장할 변수

        $.ajax({
            url : "http://localhost:8080/api/word/select",
            data : JSON.stringify(reqJson),
            type : "POST",
            contentType : "application/json",
            async : false,
            success : function(data, status, xhr) {
                // result = JSON.parse(JSON.stringify(data));
                // result = JSON.stringify(data);
                result = data;
            },
            error : function(){
                alert("시스템 에러!");
            }
        })
        meanBox.style.display = "block";
        if(isEmpty(result)) {
            showNoResult();
        } else {
            showResult(result);
        }
    }

    function isEmpty(str){
		if(typeof str == "undefined" || str == null || str == "")
			return true;
		else
			return false ;
	}

    function showResult(result) {
        meanBox.innerHTML = "";
        let keyArr = Object.keys(result.meanList);
        let ul = document.createElement("ul");
        ul.classList.add("mean_list");
        let template = document.querySelector(".mean_template");
        let idxCount = 0;
        for(let i = 0; i < keyArr.length; i++) {
            let key = keyArr[i];
            for(let j = 0; j< result.meanList[key].length; j++) {
                let cloneNode = document.importNode(template.content, true);
                let columnId = cloneNode.querySelector(".column_id");
                columnId.innerText = ++idxCount;
                let columnPart = cloneNode.querySelector(".column_part");
                columnPart.innerText = key;
                let columnMean = cloneNode.querySelector(".column_mean");
                columnMean.innerText = result.meanList[key][j];
                ul.append(cloneNode);
            }
        }
        meanBox.append(ul);
    }

    function showNoResult() {
        meanBox.innerHTML = "";
        let template = document.querySelector(".noResult_template");
        let cloneNode = document.importNode(template.content, true);
        meanBox.append(cloneNode);
    }


    function showErrorMessage2() {
        meanBox.innerHTML = "";    
        let div = document.createElement("div");
        div.classList.add("not_exist");

        let pError = document.createElement("p");
        pError.classList.add("error_message");
        pError.innerText = "검색결과가 없습니다.";

        let pAdd = document.createElement("p");
        pAdd.classList.add("add_message");
        pAdd.innerText = "도감에 추가하시겠습니까?";
        
        let addBtn = document.createElement("button");
        addBtn.classList.add("add_btn");
        addBtn.innerText = "추가";

        div.append(pError);
        div.append(pAdd);
        div.append(addBtn);

        meanBox.append(div);
    }

    function showListBox2(result){
        let keys = Object.keys(result.meanList);
        meanBox.innerHTML = "";
        let ul = document.createElement("ul");
        ul.classList.add("mean_list");
        let count = 1;
        for(let i = 0; i<keys.length; i++){
            let key = keys[i];
            for(let j = 0; j < result.meanList[key].length; j++) {
                let li = document.createElement("li");
                let divId = document.createElement("div");
                divId.classList.add("column_id");
                divId.innerText = count++;
                
                let divPart = document.createElement("div");
                divPart.classList.add("column_part");
                divPart.innerText = key;

                let divMean = document.createElement("div");
                divMean.classList.add("column_mean");
                divMean.innerText = result.meanList[key][j];
                
                li.append(divId);
                li.append(divPart);
                li.append(divMean);
                ul.append(li);
            }
        }
        meanBox.append(ul);
    }
})