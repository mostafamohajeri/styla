numbervars('$VAR'(N0), N0, N) :- !, succ(N0,N).
numbervars('$VAR'(_), N, N) :- !.
numbervars(X, N0, N) :- atomic(X), !, N0=N.
numbervars([X|Xs], N0, N) :- !,
  numbervars(X, N0, N1),
  numbervars(Xs, N1, N).
numbervars(X, N0, N) :- 
  arity(X,A),
  numbervar_args(0, A, X, N0, N).
        

%numbervar_args(A, B, C, D, E):-traceln(numbervar_args(A, B, C, D, E)),type_of(A,TA),type_of(B,TB),traceln(eq(TA,TB)),fail.
numbervar_args(A, A, _, N0, N) :- !, N0=N.
numbervar_args(A0, A, X, N0, N) :- A0<A,
  succ(A0,A1),
  arg(A1, X, X1),
  numbervars(X1, N0, N1),    
  numbervar_args(A1, A, X, N1, N).
 

ground(X):-numbervars(X,0,M),M=0.
  
 go:- numbervars(f(X),0,Z),println(X+Z).