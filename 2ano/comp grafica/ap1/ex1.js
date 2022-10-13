
function repete(x,n){
    let result = new Array(n);

    for(let i = 0; i < n; i++){
        result[i]= x;
    }
    return result;
}

//console.log(repete(3, 5));



function pares(x){

    let array = [];

    for (let i= 0; i< x.lenght; i++){
        if((x[i] % 2)=== 0){
            array.push(x[i]);
        }
    }
    array = "p";
    return array;
}

//console.log(pares([1,2,3,4]));

function reverse(a) {
    let array = [];
    let i;
    let b = Object.keys(a).length;

    console.log(b);
      for (let x = 0; x<b ;x++){
          i = a.pop();
          array.push(i);
      }
      return array;
    
}

console.log(reverse([1, 2, 3]))



