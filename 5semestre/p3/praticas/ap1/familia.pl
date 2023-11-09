progenitor(adelia,antoniom).
progenitor(adelia,mteresa).
progenitor(antonio,antoniom).
progenitor(antonio,mteresa).
progenitor(alice,adelia).
progenitor(alice,teresa).
progenitor(alice,telmo).
progenitor(sebastiao,adelia).
progenitor(sebastiao,teresa).
progenitor(sebastiao,telmo).
progenitor(telmo,carmo).
progenitor(joana,carmo).
progenitor(teresa,maria).
progenitor(manel,maria).

mulher(adelia).
mulher(mteresa).
mulher(alice).
mulher(teresa).
mulher(joana).
mulher(carmo).
mulher(maria).

homem(antoniom).
homem(telmo).
homem(antonio).
homem(sebastiao).
homem(manel).


pai(A,X) :- progenitor(A,X), homem(A).

mae(A,X) :- progenitor(A,X), mulher(A).

irmao(A,X) :- progenitor(P,A), progenitor(P,X), homem(P),progenitor(M,A), progenitor(P,X), mulher(M), A\=X .




