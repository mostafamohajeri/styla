% comp

holds(_,G):-G,!.
holds(Mes,G):-errmes(assertion_failed(Mes),G).

bcomp_args(X,T):-
  holds(bcomp_args,var(X)),
  X=T.

get_oldvar(X,T):-X=T.

get_const(X,T):-
  holds(get_const,atomic(X)),
  X=T.

get_fun(F,N,T):-var(T),!,put_fun(F,N,T).
get_fun(F,N,T):-holds(get_fun,compound(T)),arg(0,T,F),arity(T,N).

get_arg(I,T,X):- 
  holds(get_arg,I>0),
  %\+(holds(get_arg_NOT,compound(X))),
  arg(I,T,X).
  
put_fun(F,N,T):-fun(F,N,T).

put_arg(I,T,X):-get_arg(I,T,X).

put_var(X,T):-
  holds(put_var,var(X)), % assertion
  X=T.

put_val(X,T):-
  holds(put_val,var(X)),
  X=T.

put_const(X,T):-
  holds(put_const,atomic(X)),
  X=T.

ccomp(HB,cls(CH,CB),Code):-ccomp(HB,cls(CH,CB),Is,[]),list2conj(Is,Code).

ccomp(cls(H,B),cls(CH,CB))-->hcomp(H,CH),bcomp(B,CB).
    
%hcomp(T,R)-->{println(hcomp(T,R)),fail}.
hcomp(T,R)-->{var(T),!,R=T}. %[get_oldvar(T,R)].
hcomp(T,R)-->{compound(T)},!,
  {arg(0,T,F)},{arity(T,N)},
  [get_fun(F,N,R)],
  hcomp_args(0,N,T,R).
hcomp(T,R)-->[get_const(T,R)].

%hcomp_args(A,B,C,D)-->{println(hcomp_args(A,B,C,D)),fail}.
hcomp_args(N,N,_,_)-->!,[].
hcomp_args(I,N,T,R)-->
  {
  I<N,I1 is I+1,
  arg(I1,T,T1)
  },
  [get_arg(I1,R,R1)],
  hcomp(T1,R1),
  hcomp_args(I1,N,T,R).
  
%bcomp(T,R)-->{println(bcomp(T,R)),fail}.
bcomp(T,R)-->{var(T),!,R=T}. %[get_oldvar(T,R)].
bcomp(T,R)-->{compound(T)},!,
  {arg(0,T,F),arity(T,N)},
  [put_fun(F,N,R)],
  bcomp_args(0,N,T,R).
bcomp(T,R)-->[put_const(T,R)].

%bcomp_args(A,B,C,D)-->{println(bcomp_args(A,B,C,D)),fail}.
bcomp_args(N,N,_,_)-->!,[].
bcomp_args(I,N,T,R)-->
  {
  I<N,I1 is I+1,
  arg(I1,T,T1)
  },
  [put_arg(I1,R,R1)],
  bcomp(T1,R1),
  bcomp_args(I1,N,T,R).  

list2conj([],R):-!,R=true.
list2conj([X],R):-!,R=X.
list2conj([X|Xs],(X,Cs)):-list2conj(Xs,Cs).

go:-
  ccomp(cls(f(X,g(Y,a),h(_,X),10,Y),
            g(f(X),h(X,Y),b,20)),cls(H,B),Code),
  println(Code),
  abolish(cls/2),
  assert(':-'(cls(H,B),Code)),
  listing(cls),
  T=cls(_,_),
  call(T),
  println(T),
  fail.
go.