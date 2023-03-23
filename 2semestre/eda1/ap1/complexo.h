//
//  complexo.h
//  revisoes
//
//  Created by Ligia Ferreira on 12/02/2023.
//

#ifndef complexo_h
#define complexo_h
struct ComplexoStruct;
typedef struct ComplexoStruct Complexo;

Complexo CreateComplexo(float r, float i);
void print(Complexo x);
Complexo soma(Complexo x, Complexo y);
Complexo mult(Complexo x, Complexo y);
Complexo sub(Complexo x, Complexo y);
Complexo divisao(Complexo x, Complexo y);
Complexo conjugado(Complexo c);





#endif /* complexo_h */