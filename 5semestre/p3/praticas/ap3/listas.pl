
membro(X,[X,_]).
membro(X,[_|L]) :- membro(X,L).


prefixo([],_).
prefixo([X|A],[X|B]) :- prefixo(A,B).


sufixo(A,A).
sufixo(A, [_|B]) :- sufixo(A, B).

sublista(A,B) :- prefixo(A,B).
sublista(A,[_|B]) :- sublista(A,B).

cantena([],L,L).
cantena([X|L1],L2,[X|L3]) :- cantena(L1,L2,L3).

nrev([],[]).
%nrev([X|A], B) :- nrev([X|A], AR), catena(AR,X,B).


tamanho([],0).
tamanho([_|L],T+1) :- tamanho(L,T).