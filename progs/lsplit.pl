% splits Xs in N parts collected on a list Xss and the left-over in Ys
/*
usage:

?- lsplit(3,[a,b,c,d,e,f,g,h],Xss,Ys).
Xss = [[a, b, c], [d, e, f]],
Ys = [g, h]

*/
lsplit(N,Xs, Xss,Ys):-
  length(Xs,L),
  M is L // N,
  lsplit1(M,N,Xss,Xs,Ys).

lsplit1(0,_,[]) --> [].
lsplit1(M,N,[Xs|Xss])-->{M>0,M1 is M-1},lsplit2(N,Xs),lsplit1(M1,N,Xss).

lsplit2(0,[]) --> [].
lsplit2(N,[X|Xs]) --> {N>0,N1 is N-1},[X],lsplit2(N1,Xs).
