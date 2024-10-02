let val;

const list = document.querySelector('ul.list-group');
const listItem = document.querySelector('li.list-group-itme:frist-child');

console.log('list', list)
console.log('listItem', listItem)

val = list.childNodes; // NodeList 반환, line break 도 포함 0
val = list.childNodes[0];
val = list.childNodes[0].nodeName;
val = list.childNodes[0].nodeType;

// NodeType

// 1 - Element
// 2 - Array(deprecated)
// 3 - Text node
// 8 - commnet node
// 9 - Documnet itself
// 10 - Doctype

// childeren element nodes 반환
val = list.children;
val = list.children[1];
list.children[1].textContent = 'Hi'

// First Child
val = list.firstChild;
// list.firstChild === list.childNodes[0];
val = list.firstElementChild

// Last child
val = list.lastChild;
// list.lastCild === list.childNodes[list.childNodes.length-1]

val = list.lastElementChild;

// child 요소 count
val = list.childElementCount;


// parent node 반환
val = listItem.parentNodes;
val = listItem.parentElement;
val = listItem.parentElement

// next sibling 반환
val = listItem.nextSibling;
val = listItem.nextElementSibling;
val = listItem.nextElementSibling.nextElementSibling;
val = listItem.nextElementSibling.nextElementSibling.previousElementSibling;4

val = listItem.previousSibling;
val = listItem.previousElementSibling;
console.log('val:', val)


/**
 * childNodes는 마치 배열 같아 보이지만, 배열이 아닌 반복 가능한(iterable, 이터러블) 유사 배열 객체인 컬렉션.
 * for...of >> 배열을 순화할 때 사용 (사용가능)
 * for...in >> 객체를 순환할 때 사용 (사용불가능)
 */