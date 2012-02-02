perm([],[]).
perm([X|Xs],Zs):-perm(Xs,Ys),ins(X,Ys,Zs).

ins(X,Ys,[X|Ys]).
ins(X,[Y|Ys],[Y|Zs]):-ins(X,Ys,Zs).

subset_of([],[]).
subset_of([_|Xs],Zs):-subset_of(Xs,Zs).
subset_of([X|Xs],[X|Zs]):-subset_of(Xs,Zs).

g0(N):-range(0,N,Ns),perm(Ns,_),fail.
g0(_).

bm(N,T):-time(g0(N),T).

bm(T):-bm(9,T).

go:-bm(T),println(T).

g1(N):-range(0,N,Ns),findall(Ps,perm(Ns,Ps),_),fail.
g1(_).

go1(N,T):-time(g1(N),T).

go1:-go1(9,T),println(T).

g2(N):-range(0,N,Ns),subset_of(Ns,_),fail.
g2(_).

go2:-time(g2(18)).
