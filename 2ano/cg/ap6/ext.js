function vertex(i, j, section, spine){
   
    if(i>=section.lenght || i<0 || j<0 ||j>=spine.lenght ){
        return null;
    }



    return {
       x: section[i].x + spine[j].x,
       y: spine[j].z,
       z: section[i].z + spine[j].z,

    };
}
     let canto = [
        {x: 0.0, z: 0.0},
        {x: 1.0, z: 0.0},
        {x: 0.0, z: 1.0} ];
    
    let caminho = [
        {x: 0.0, y: 0.0, z: 0.0},
        {x: 0.0, y: 1.0, z: 0.0},
        {x: 1.0, y: 2.0, z: -1.0} ];

    console.log( vertex(1, 2, canto, caminho));

