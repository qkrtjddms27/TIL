window.onload = function() {
    let text = document.getElementById('text');

    text.innerText ='HTML 문서가 loaded됨'
}

/**
 * html 
 * <button onclick="alert('버튼이 클릭됐습니다.')">버튼입니다.</button>
 */


/**
 * element.addEventListener(이벤트명, 실행할 함수명(listener), 옵션)
 */
const aElement  = document.querySelector('a');
aElement.addEventListener('click', () => {
    alert('a element clicked');
})

aElement.addEventListener('click', () => {
    alert('a element clicked');
})

const buttonElement = document.querySelector('click', (event) =>{
    let val;
    val = event.target;
    val = event.target.id;
    val = event.target.className;

    val = event.type;

    // 윈도우부터로의 거리 좌표
    val = event.clientY;

    // 요소로부터의 거리 좌표
    val = event.offsetY;

    console.log(event);
})

const submitBtn = document.querySelector('.submit-btn');
const form = document.querySelector('form');
const title = document.querySelector('h2');

submitBtn.addEventListener('click', handleEvent);
submitBtn.addEventListener('dblclck', handleEvent);
submitBtn.addEventListener('mousedwon', handleEvent);
submitBtn.addEventListener('mouseenter', handleEvent);
submitBtn.addEventListener('mouseleave', handleEvent);
submitBtn.addEventListener('mousemove', handleEvent);

function handleEvent(e) {
    if (e.type === 'submit') {
        e.preventDefault(); // 새로 고침 안함
    }
    console.log(`Event Type: ${e.type}`);
    // title.textContent = `MouseX: ${e.offsetX} MouseY: ${e.offsetY}`
}

const bubbleForm = document.querySelector('form');
const bubbleDiv = document.querySelector('div');
const bubbleP = document.querySelector('p');


form.onclick = function (event) {
    event.target.style.backgroundColor = "yellow";

    setTimeout(() => {
        alert("target = " + event.style.backgroundColor)
    })
}