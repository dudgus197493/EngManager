window.addEventListener("load", function() {
    let word_form = document.querySelector("#insert-word_frm");
    let word_input = word_form.querySelector("#word-input");
    let mean_input = word_form.querySelector("#mean-input");
    word_form.onsubmit = function(e){
        e.preventDefault();
        let reqJson = new Object();
        reqJson.keyword = word_input.value;

        httpRequest = new XMLHttpRequest();
        httpRequest.onreadystatechange = function(){
            if(httpRequest.readyState === XMLHttpRequest.DONE) {
                if(httpRequest.status === 200){
                    alert("Success Request!");
                    mean_input.innerText = searchInput.value;
                } else {
                    alert("Request Error!");
                }
            } 
        }
        httpRequest.open("GET", "api/test", false);
        httpRequest.setRequestHeader('Content-Type', 'application/json');
        httpRequest.send(JSON.stringify(reqJson));
    }
})