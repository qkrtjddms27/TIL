var A = 1;
console.log(A);
/**
 * var
 * 아래와 같이 중복 선언과 재할당이 가능하며 마지막에 할당된 값이 변수에 저장됩니다.
 * 소스 코드가 복잡해질 경우 기존 선언해둔 변수를 잊고 다시 선언하거나 재 할당을 해서 어떤 부분에서 
 */

var gretting = 'hello';
console.log('gretting : ', gretting);

var gretting = 'hi';
console.log('gretting : ', gretting);

gretting = "how are you?"
console.log('gretting : ', gretting);

/** 
 * let
 * 중복선언 불가하지만 재할당은 가능하다
 * 
*/

let letGretting = 'hello';
console.log('letGretting : ', letGretting)

letGretting = 'hi';
console.log('letGretting : ', letGretting)
 
/**
 * const
 * 중복선언과 재할당이 불가
 */

const constGretting = 'hello';
console.log(constGretting);

// constGretting = // << 에러가 발생함

/**
 * 스코프 : 특정한 함수내에서 유효한 범위
 * var 는 funciton level scope 
 * if 안에서 사용되는게 아니라 함수 전체를 범위로 하기때문에 함수에 전부 됩니다.
 * */

function func() {
  if(true) {
    var a = 'a';
    console.log(a)
  }
  console.log(a)
}

/**
 * let, const => block 레벨 스코프
 */

function func() {
  if (true) {
    let a = 'a'
    console.log(a);
  }
  console.log(a);
}

func();