window.addEventListener("load", function(){
    let header = document.querySelector("header");
    let headerLabel = header.querySelector(".header-label")
    let headerBtnBox = header.querySelector(".header_btn");
    let addBtn = headerBtnBox.querySelector(".add-button");
    let formBox = document.querySelector("#form-box");
    let meanBox = document.querySelector("#mean-box");
    let homeButton = document.querySelector(".home-button");
    let searchSource = ['돈가스', '돈가스덮밥', '라면', '라면사리', '돈가스카레'];
    init();
    
    addBtn.addEventListener("click", showInsertForm);

    function init() {
        showSearchForm();
    }
    homeButton.addEventListener("click", function(){
        // Home 페이지로 이동
        // window.open("url");
        return null;
    })

    // $('#search-input').autocomplete({ // autocomplete 구현 시작부
    //     source : function(){
    //             $.ajax({
    //                 type : "POST",
    //                 url : "http://localhost:8080/api/word/select/eng",
    //                 async : false,
    //                 success:function(data) {
    //                     return JSON.stringify(data);
    //                 }
    //             })
    //         },
    //     select : function(event, ui) { // item 선택 시 이벤트
    //         console.log(ui.item);
    //     },
    //     focus : function(event, ui) { // 포커스 시 이벤트
    //         return false;
    //     },
    //     minLength : 1, // 최소 글자 수
    //     autoFocus : true, // true로 설정 시 메뉴가 표시 될 때, 첫 번째 항목에 자동으로 초점이 맞춰짐
    //     classes : { // 위젯 요소에 추가 할 클래스를 지정
    //         'ui-autocomplete' : 'highlight'
    //     },
    //     delay : 500, // 입력창에 글자가 써지고 나서 autocomplete 이벤트 발생될 떄 까지 지연 시간(ms)
    //     disable : false, // 해당 값 true 시, 자동완성 기능 꺼짐
    //     position : { my : 'right top', at : 'right bottom'}, // 제안 메뉴의 위치를 식별
    //     close : function(event) { // 자동완성 창 닫아질 때의 이벤트
    //         console.log(event);
    //     }
    // });

    function search(event){       
        event.preventDefault();
        let keyword = document.querySelector(".search-input").value;

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

        if(isEmpty(result)) {
            showNoResult();
        } else {
            showResult(result);
        }
    }

    function insert(event, reqJson){
        event.preventDefault();             // 브라우저 Requet 방지
        let result = false;
        $.ajax({
            url :"http://localhost:8080/api/word/insert",
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

        if(result == false) {
            alert("추가에 실패했습니다...");
        } else {
            alert("추가에 성공했습니다!");
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
        let template = document.querySelector(".mean-template");
        let idxCount = 0;
        for(let i = 0; i < keyArr.length; i++) {
            let key = keyArr[i];
            for(let j = 0; j< result.meanList[key].length; j++) {
                let cloneNode = document.importNode(template.content, true);
                let columnId = cloneNode.querySelector(".column_id");
                columnId.innerText = ++idxCount + ".";
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
        let template = document.querySelector(".noResult-template");
        let cloneNode = document.importNode(template.content, true);
        meanBox.append(cloneNode);
    }

// --------------------------------- createElement -----------------------------------
    function showErrorMessage2() {
        meanBox.innerHTML = "";    
        let div = document.createElement("div");
        div.classList.add("not_exist");

        let pError = document.createElement("p");
        pError.classList.add("error_message");
        pError.innerText = "검색결과가 없습니다.";

        div.append(pError);

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
                li.classList.add("mean");
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

    function showSearchForm() {
        if(meanBox.classList.contains("hidden")){      // meanBox에 hidden 클래스가 있으면 삭제
            meanBox.classList.remove("hidden");
        }
        headerLabel.innerText = "Search";

        formBox.innerHTML = "";
        let searchForm = document.createElement("form");
        searchForm.classList.add("search-word-form");
        searchForm.addEventListener("submit", (event) => 
            search(event)
        );

        let engInput = document.createElement("input");
        engInput.classList.add("search-input");
        engInput.setAttribute("id", "search-input");
        engInput.setAttribute("type", "text");
        engInput.setAttribute("placeholder", "Insert Word");
        
        let submitBtn = document.createElement("button");
        submitBtn.hidden = true;
        submitBtn.setAttribute("type", "submit");
        submitBtn.innerText = "검색";

        searchForm.append(engInput);
        searchForm.append(submitBtn);
        formBox.append(searchForm);
        $('#search-input').autocomplete({ // autocomplete 구현 시작부
            source : function(request, response){
                    $.ajax({
                        type : "POST",
                        url : "http://localhost:8080/api/word/selectall/word",
                        async : false,  
                        success : function(data) {
                            meanBox.innerText = typeof data;
                        }
                    })
                },
            select : function(event, ui) { // item 선택 시 이벤트
                console.log(ui.item);
            },
            focus : function(event, ui) { // 포커스 시 이벤트
                return false;
            },
            minLength : 1, // 최소 글자 수
            autoFocus : true, // true로 설정 시 메뉴가 표시 될 때, 첫 번째 항목에 자동으로 초점이 맞춰짐
            classes : { // 위젯 요소에 추가 할 클래스를 지정
                'ui-autocomplete' : 'highlight'
            },
            delay : 500, // 입력창에 글자가 써지고 나서 autocomplete 이벤트 발생될 떄 까지 지연 시간(ms)
            disable : false, // 해당 값 true 시, 자동완성 기능 꺼짐
            position : { my : 'right top', at : 'right bottom'}, // 제안 메뉴의 위치를 식별
            close : function(event) { // 자동완성 창 닫아질 때의 이벤트
                // console.log(event);
            }
        });

        let searchBtn = headerBtnBox.querySelector(".search-button");
        if(!isEmpty(searchBtn)) {
            searchBtn.remove();
            let AddBtn = document.createElement("button");
            AddBtn.classList.add("add-button");
            AddBtn.textContent = "Add";
            AddBtn.addEventListener("click", showInsertForm);
            headerBtnBox.prepend(AddBtn);
        }
    }

    function showInsertForm() {
        if(!meanBox.classList.contains("hidden")) {
            meanBox.classList.add("hidden");
        }
        // Insert버튼 눌렀을 시  searchForm
        // search-input에 값이 있을 시 insert-form에 대입
        let insertKeyword = document.querySelector(".search-input").value;
        
        headerLabel.innerText = "Insert";
        meanBox.innerHTML = "";
        headerBtnBox.querySelector(".add-button").remove();

        let searchBtn = document.createElement("button");
        searchBtn.classList.add("search-button");
        searchBtn.textContent = "Search";
        searchBtn.addEventListener("click", showSearchForm);
        headerBtnBox.prepend(searchBtn);

        formBox.innerHTML = "";
        let optionArr = ['명사', '동사', '형용사'];

        let insertForm = document.createElement("form");
        insertForm.classList.add("insert-word-form");

        let engInput = document.createElement("input");
        engInput.classList.add("insert-eng-input");
        engInput.setAttribute("type", "text");
        engInput.setAttribute("placeholder", "eng..");
        engInput.required = true;
        if(!isEmpty(insertKeyword)) {
            engInput.value = insertKeyword;
        }

        let meanInput = document.createElement("input");
        meanInput.classList.add("insert-mean-input");
        meanInput.setAttribute("type", "text");
        meanInput.setAttribute("placeholder", "mean..");
        meanInput.required = true;

        let insertRow = document.createElement("div");
        insertRow.classList.add("insert-row");
        
        let selectBox = document.createElement("select");
        selectBox.classList.add("insert-select-part")
        
        let insertBtn = document.createElement("button");
        insertBtn.classList.add("insert-button");
        insertBtn.setAttribute("type", "submit");
        insertBtn.innerText = "Insert Word";

        insertRow.append(selectBox);
        insertRow.append(insertBtn);

        for(let i = 0; i<optionArr.length; i++){
            let option = document.createElement("option");
            option.innerHTML = optionArr[i];
            option.setAttribute("value", optionArr[i]);
            selectBox.append(option);
        }
        
        insertForm.append(engInput);
        insertForm.append(meanInput);
        insertForm.append(insertRow);
        
        formBox.append(insertForm);
        insertForm.addEventListener("submit", function(event) {
            let reqJson = new Object();
            reqJson.eng = engInput.value;
            reqJson.mean = meanInput.value;
            reqJson.part = selectBox.value;
            insert(event, reqJson);
        });
        // insertForm.addEventListener("submit", (event)=>
        //     insert(event)
        // );

        
    }   
})
