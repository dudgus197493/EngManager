// 배열을 선언하여 사용하는 방식
$(function() {
    // var searchSource = [
    //     {"eng" : "apple", "mean" : "사과"},
    //     {"eng" : "banana", "mean" : "바나나"},
    //     {"eng" : "melon", "mean" : "멜론"},
    //     {"eng" : "inheritance", "mean" : "상속"},
    // ]; // 배열 생성
    var searchSource = ['돈가스', '돈가스덮밥', '라면', '라면사리', '돈가스카레'];
    $('#search-input').autocomplete({ // autocomplete 구현 시작부
        source : searchSource, //source 는 자동완성의 대상
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
            console.log(event);
        }
    });
});