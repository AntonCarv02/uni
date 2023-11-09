num(z).
num(s(X)):- num(X).

pares([],[]).
pares([_],[]).
pares([_,B|X],[B|Y]) :- pares(X,Y).

soma(z, X, X) :- num(X).
soma(s(X), Y, s(Z)) :- soma(X, Y, Z).

mult(z, _, z).
mult(s(A), B, X) :-mult(A, B, Y), soma(B, Y, X).

