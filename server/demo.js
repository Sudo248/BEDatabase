const Demo = require('./test')

Demo.prototype.mul = function(){
    return this.a*this.b;
}

let array = [1,2,3,4,5,6]

const demo = new Demo(4,5);

for(let i of array){
    demo.c.push(i)
}

demo.show();
