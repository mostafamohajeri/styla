scala_actor_test:-
  scala_actor_new([scalaActor1],A),
  scala_actor_new([scalaActor2],B),
  scala_actor_start(A),
  scala_actor_start(B),
  scala_actor_send(A,
    (member(X,[hello,hola]),println(X),fail)),
  scala_actor_send(A,
    assert(self(A))),
  scala_actor_send(A,
    scala_actor_send(B,handle(println(hello_from(A))))),
  scala_actor_send(A,println(bye)),

  (for(_,1,1000000),fail;true),
  
  println(stopping),
  scala_actor_stop(A),
  scala_actor_stop(B),
  scala_actor_send(A,println(post_bye)),
  scala_actor_send(B,println(post_bye)),
  println(done).

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
 