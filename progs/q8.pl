go:-bm(T),println(T).

bm(T):-bm(11,T).

bm(N,T):-time(run_bm(N),T).

run_bm(N):-queens(N,_Qs),fail.
run_bm(_):-true.

qs(Qs):-queens(8,Qs).

queens(N,Ps):-gen_queens(N,Qs),gen_places(Qs,Ps),place_queens(Qs,Ps,_1,_2).

% at each step inc Us and dec Ds
place_queens([],_,_,_).
place_queens([I|Is],Cs,Us,[_|Ds]):-
  place_queens(Is,Cs,[_|Us],Ds),
  place_queen(I,Cs,Us,Ds).

place_queen(I,[I|_],[I|_],[I|_]).
place_queen(I,[_|Cs],[_|Us],[_|Ds]):-place_queen(I,Cs,Us,Ds).

gen_queens(Max,Qs):-Max1 is Max-1,numlist(0,Max1,Qs).

gen_places([],[]).
gen_places([_|Qs],[_|Ps]):-gen_places(Qs,Ps).


loop(X):-eq(X,Y),loop(Y).

loop:-loop(_).
