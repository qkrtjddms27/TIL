const li = document.createElement('li');

// 클래스 추가하기
li.className = 'list-group-item';

// id 추가하기
li.id = 'new-item';
li.setAttribute('name', 'New list item');
// 한노드를 특정부모 노드의 마지막 자식으로 붙입니다.
const link = document.createElement('a');

li.appendChild(link);

link.className = 'alarm-item';
link.innnerHTML = '<i class="bi-alarm></i>'

li.appendChild(link);

document.querySelector('ul.list-group').appendChild(li);

