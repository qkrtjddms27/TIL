let val;

val = document;
val = document.baseURI; // 웹페이지의 절대 URI 반환
val = document.head; // <head> 태그 반환
val = document.body; // <body> 태그 반환
val = document.forms; // <form> 태그 반환
val = document.forms[0].id; // <form> 태그 반환
val = document.forms[0].classList; // <form> 태그 반환
val = document.forms[0].name; // <form> 태그 반환

val = document.scripts; // 코드에서는 사용한건 2개 밖에 없지만, 크롬 extension 이나 브라우저에서 사용하는 script가 있기 때문에 추가로 집계될 수 있다.
console.log(val)

/**
 * 하나의 요소에 접근하는 방법
 */

document.getElementById('요소아이디')
document.getElementByName('name속성값')
document.querySelector('선택자')


const headerContainer = document.getElementById('header-container')
headerContainer.style.display = 'none';
console.log(headerContainer)

headerContainer.textContent = 'Text Content'
headerContainer.innerText = 'inner Content'

/**
 * 여러 요소에 접근하는 방법
 */

document.getElementById('태그이름')
document.getElementByClassName('클래스이름')
document.querySelectorAll('선택자')
 
const items = document.getElementByClassName('list=group-item')

item[0].style.color = 'blue';
item[3].textContent = 'Hi';

let list = document.getElementsByTagName('li');

list = Array.from(list); // Collection -> Array

list.forEach(element => {
    console.log('element : ', element)
    list.textContent = `${index} . List`
});
console.log(list);

const liOdd = document.querySelectorAll('li:nth-child(odd)')

/**
 * html까지 모두 보여줌
 * 
 * innerText 사용자에게 보여지는 텍스트 값을 읽어오며 여러 공백은 무시하고 하나의 공백만 처리합니다.
 * 
 * textContent display:none 스타일이 적용된 숨겨진 텍스트도 가져오는 노드가 가지고 있는 텍스트 값 그대로 보여줍니다.
 */



