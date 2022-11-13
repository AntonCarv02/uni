let arr = [
    {x: 0.0,y: 0, z: 0.0},
    {x: 1.0,y: 0, z: 0.0},
    {x: 0.0,y: 0, z: 1.0} 
];


function center(points){
    let n = Object.keys(points).length;

    let cx=0, cy=0, cz=0;
    for (n of points){

        cx +=n.x;
        cy +=n.y;
        cz +=n.z;

    }
    cx +cx/n;
    cy =cy/n;
    cz =cz/n;
    return {cx,cy,cz};
}

//console.log(center(arr));

function dist(a, b){

    let dist = Math.sqrt((a.x-b.x)*(a.x-b.x)+(a.y-b.y)*(a.y-b.y)+(a.z-b.z)*(a.z-b.z));

    return dist;
}

function prox(p, points){

    if(points.length<=0) return null;


    let best_point = points[0];
    let best_distance = dist(best_point, p);

    for ( n as points.lenght){

    }

    return {
        d: d
        p: best_point
    }
}

console.log(prox({x:0,y:1,z:3}, arr));