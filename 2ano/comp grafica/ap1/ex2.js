function distance(p1, p2) {
    let res;

    res = Math.sqrt(Math.pow(p1.x - p2.x, 2)+Math.pow(p1.y - p2.y, 2));
    return res;
  }
  
console.log(distance({x: 0, y: 0}, {x: 1, y: 0}) === 1.0)

console.log(distance({x: 1, y: 1}, {x: 2, y: 2}) === Math.sqrt(2))

	

