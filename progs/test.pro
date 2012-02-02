rtest:-
 new_engine([],X,do_ret(X),E),
 get(E,A),
 println(got(A)),
 get(E,B),
 println(got(B)),
 get(E,C),
 println(got(C)),
 get(E,D),
 println(got(D)),
 get(E,R),
 println(got(R)),
 get(E,S),
 println(got(S)),
 stop(E),
 get(E,U),
 println(got(U)).

do_ret(R):-println(before),return(hello),println(after),(R=one;R=two).
do_ret(R):-return(bye),R=done.


run:-
 new_engine([perms],A,perm([1,2,3],A),E),
 println(here),
 get(E,X),
 println(X),
 get(E,Y),
 println(Y),
 stop(E),
 get(E,Z),
 println(after_stop(Z)).

c1(a).
c1(b(X)):-member(X,[1,2,3]),member(X,[A,A]).
c1(c).

c2(a).
c2(b):-member(5,[1,2,3]),!,member(X,[A,A]).
c2(c).
c2(d):-member(2,[1,2,3]),!,member(X,[A,A]).
c2(e).

fact(0,R):-!,eq(R,1).
fact(N,R) :- '-'(N,1,N1),fact(N1,R1),'*'(N,R1,R).

wtest:-
  file_writer('boo.txt',S),
  traceln(S),write(S,hello),nl(S),
  stop(S).
 