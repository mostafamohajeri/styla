/*
 nrev benchmark

*/

app([],Ys,Ys).
app([A|Xs],Ys,[A|Zs]):-
  app(Xs,Ys,Zs).

nrev([],[]).
nrev([X|Xs],Zs):-
  nrev(Xs,Ys),
  app(Ys,[X],Zs).

integers(Max,Ps):-range(0,Max,Ps).

full_range(Its,Ns):-member(_1,Its),nrev(Ns,Xs),fail.
full_range(_1,_2).

dummypred(_1,_2).

empty_range(Its,Ns):-member(_1,Its),dummypred(Ns,_2),fail.
empty_range(_1,_2).

bm(It,T):-
  integers(It,Its),
  integers(100,Ns),
  ctime(T1),
  empty_range(Its,Ns),
  ctime(T2),
  full_range(Its,Ns),
  ctime(T3),
  '-'(T2,T1,A),
  '-'(T3,T2,B),
  '-'(B,A,T).

huge:-bm(30000,T),println(T).

big:-bm(3000,T),println(T).

mid:-bm(1000,T),println(T).

half:-bm(500,T),println(T).

small:-bm(100,T),println(T).

tiny:-bm(50,T),println(T). 

go:-half. 
