let foo = 42
foo = 'bar'
foo = true

console.log(typeof foo)

// 원시 타입
// 문자열
const name = 'han';

// Number
const age = '30';

// Boolean
const hasJob = true;

// null
const car = null

// undefined
let anything;

// Symbol
const sym = Symbol();

// 참조 타입
// Array 배열
const hobbies = ['walking', 'books']

// Object
const address = {
    province : '경기도'
}

console.log(typeof hobbies) // Object O, Array X,
console.log(Array.isArray(hobbies)) // 배열여부는 이렇게 확인하는게 아니다.