nrev([],[]).
nrev([X|Xs],Zs):-nrev(Xs,Ys),append(Ys,[X],Zs).

integers(Max,Ps):-range(0,Max,Ps).

full_range(Its,Ns):-member(_1,Its),nrev(Ns,Xs),fail.
full_range(_1,_2).

dummypred(_1,_2).

empty_range(Its,Ns):-member(_1,Its),dummypred(Ns,_2),fail.
empty_range(_1,_2).

bm(It,T):-integers(It,Its),integers(100,Ns),
  time(empty_range(Its,Ns),T1),
  time(full_range(Its,Ns),T2),
  '-'(T2,T1,T).

huge:-bm(30000,T),println(T).

big:-bm(3000,T),println(T).

mid:-bm(1000,T),println(T).

double:-bm(2000,T),println(T).

small:-bm(100,T),println(T).

tiny:-bm(50,T),println(T). 

go:-double.
