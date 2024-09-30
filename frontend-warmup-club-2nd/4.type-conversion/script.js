let val;

// Nubmer to String
val = String(111);
val = String(8+4);

console.log(val);
console.log(typeof val)
console.log(val.length)


// Boolean to String
val = String(false);

// Date to String
val = String(new Date());

// toString()
val = (5).toString();

// String to number
val = Number('1');

val = Number('1')
val = Number('true') // 1
val = Number('false') // 0
val = Number('null') // 0
val = Number([1,2,3]) // NaN Not a Number

val = parseInt('111.40')
val = parseFloat('111.40')

console.log(val);
console.log(typeof val);
console.log(val.length)

