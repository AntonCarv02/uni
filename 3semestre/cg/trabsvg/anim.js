function newModel() {
  let model = {
      carang: {
          x: 150,
          y: 450

      },
      mar: {
          x: 400,
          y: 0,
          vx: -0.25
      },
  }
  model.update = update;
  return model;
}



function context() {

  let mar_SVG = document.getElementById("mar");
  let caranguejo_SVG = document.getElementById("caranguejo");

  let context = {
      mar_SVG: mar_SVG,
    caranguejo_SVG: caranguejo_SVG,
  }
  context.render = render;
  return context;
}

function update() {
  let mar = this.mar;
  let carang = this.carang;

  carang.x += mar.vx;
  mar.x += mar.vx;


  if(mar.x < 370){
      mar.vx = 0.25;
  } else if(mar.x > 400){

    mar.vx = -0.25;
  }

}



function render(model) {

  let mar_x = model.mar.x;
  let mar_y = model.mar.y;
  this.mar_SVG.setAttribute(
      "transform", `rotate(${20}) translate(${mar_x}, ${mar_y})`);

  let carang_x = model.carang.x;
  let carang_y = model.carang.y;

  this.caranguejo_SVG.setAttribute(
      "transform", `translate(${carang_x}, ${carang_y})`);
}

function main(){

  let gc = context();
  let model = newModel();

  let step = (ts) => {
      model.update();
      gc.render(model);
      requestAnimationFrame(step);
  };
  requestAnimationFrame(step);
}