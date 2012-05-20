% length_

length_([],0).
length_([_|Xs],L) :- length_(Xs,L1), L is L1+1.

% append_

append_([],Ys,Ys).
append_([X|Xs],Ys,[X|Zs]) :- append_(Xs,Ys,Zs).

% member_

member_(X,[X|_]).
member_(X,[_|Xs]):-member_(X,Xs).

member__(X,Xs):-append_(_,[X|_],Xs).

% call_

call_(X):-X.
% call_(F,X,Y):-F(X,Y).
% call_(F,X,Y,Z):-F(X,Y,Z)
% ....

% maplist_

maplist_(_, [],[], []).
maplist_(P,[X|Xs],[Y|Ys],[Z|Zs]):-
  call(P,X,Y,Z),
  maplist_(P,Xs,Ys,Zs).
  



/*

swipl
Welcome to SWI-Prolog (Multi-threaded, 64 bits, Version 6.0.0)
Copyright (c) 1990-2011 University of Amsterdam, VU Amsterdam
SWI-Prolog comes with ABSOLUTELY NO WARRANTY. This is free software,
and you are welcome to redistribute it under certain conditions.
Please visit http://www.swi-prolog.org for details.

For help, use ?- help(Topic). or ?- apropos(Word).

?- X=[1,2,3,4].
X = [1, 2, 3, 4].

?- [X|Xs]=[1,2,3,4].
X = 1,
Xs = [2, 3, 4].

?- [X|Xs]=[1].
X = 1,
Xs = [].

?- append([1,2,3],[4,5],R).
R = [1, 2, 3, 4, 5].

?- member(X,[1,2,3]).
X = 1 ;
X = 2 ;
X = 3.

?- member(2,[1,2,3]).
true .

?- member(10,[1,2,3]).
false.

?- length([a,b,c],L).
L = 3.

?- length(Xs,3).
Xs = [_G303, _G306, _G309].

?- append(Xs,[A,B],[1,2,3,4,5]).
Xs = [1, 2, 3],
A = 4,
B = 5 ;
false.

?- append(Xs,Sublist,[1,2,3,4,5]).
Xs = [],
Sublist = [1, 2, 3, 4, 5] ;
Xs = [1],
Sublist = [2, 3, 4, 5] ;
Xs = [1, 2],
Sublist = [3, 4, 5] ;
Xs = [1, 2, 3],
Sublist = [4, 5] ;
Xs = [1, 2, 3, 4],
Sublist = [5] ;
Xs = [1, 2, 3, 4, 5],
Sublist = [] ;
false.

?- plus(2,3,X).
X = 5.

?- maplist(plus,[1,2,3],[10,20,30],R).
R = [11, 22, 33].

?- G=append([2,3],[4,5],Result),call(G).
G = append([2, 3], [4, 5], [2, 3, 4, 5]),
Result = [2, 3, 4, 5].

?- 

?- [lists].
Warning: /Users/tarau/Desktop/go/styla_a_prolog_in_scala/styla/progs/lists.pl:4:
	Singleton variables: [X]
% lists compiled 0.00 sec, 4 clauses
true.

?- [lists].
% lists compiled 0.00 sec, 2 clauses
true.

?- [lists].
% lists compiled 0.00 sec, 2 clauses
true.

?- length_([cat,dog,42],R).
R = 3.

?- [lists].
Warning: /Users/tarau/Desktop/go/styla_a_prolog_in_scala/styla/progs/lists.pl:4:
	Singleton variables: [X]
% lists compiled 0.00 sec, 2 clauses
true.

?- length_([cat,dog,42],R).
R = 3.

?- [lists].
% lists compiled 0.00 sec, 2 clauses
true.

?- 
|    append([1,2,3],[4],R).
R = [1, 2, 3, 4].

?- [lists].
% lists compiled 0.00 sec, 4 clauses
true.

?- append_([1,2,3],[4],R).
R = [1, 2, 3, 4].

?- append_([1,2,3],[4,5],R).
R = [1, 2, 3, 4, 5].

?- append_(Xs,Ys,[1,2,3]).
Xs = [],
Ys = [1, 2, 3] ;
Xs = [1],
Ys = [2, 3] ;
Xs = [1, 2],
Ys = [3] ;
Xs = [1, 2, 3],
Ys = [] ;
false.

?- append_([B,B],Ys,[1,A,3]).
B = 1,
Ys = [3],
A = 1.

?- X=Y,Y=Z,Z=U,X=42.
X = Y, Y = Z, Z = U, U = 42.

?- [X,Y,10,20]=[A,B,B,A].
X = 20,
Y = 10,
A = 20,
B = 10.

?- trace.
true.

[trace]  ?- append_(Xs,Ys,[1,2,3]).
   Call: (6) append_(_G375, _G376, [1, 2, 3]) ? creep
   Exit: (6) append_([], [1, 2, 3], [1, 2, 3]) ? creep
Xs = [],
Ys = [1, 2, 3] ;
   Redo: (6) append_(_G375, _G376, [1, 2, 3]) ? creep
   Call: (7) append_(_G468, _G376, [2, 3]) ? creep
   Exit: (7) append_([], [2, 3], [2, 3]) ? creep
   Exit: (6) append_([1], [2, 3], [1, 2, 3]) ? creep
Xs = [1],
Ys = [2, 3] ;
   Redo: (7) append_(_G468, _G376, [2, 3]) ? creep
   Call: (8) append_(_G471, _G376, [3]) ? creep
   Exit: (8) append_([], [3], [3]) ? creep
   Exit: (7) append_([2], [3], [2, 3]) ? creep
   Exit: (6) append_([1, 2], [3], [1, 2, 3]) ? creep
Xs = [1, 2],
Ys = [3] ;
   Redo: (8) append_(_G471, _G376, [3]) ? creep
   Call: (9) append_(_G474, _G376, []) ? creep
   Exit: (9) append_([], [], []) ? creep
   Exit: (8) append_([3], [], [3]) ? creep
   Exit: (7) append_([2, 3], [], [2, 3]) ? creep
   Exit: (6) append_([1, 2, 3], [], [1, 2, 3]) ? creep
Xs = [1, 2, 3],
Ys = [] ;
   Redo: (9) append_(_G474, _G376, []) ? creep
   Fail: (9) append_(_G474, _G376, []) ? creep
   Fail: (8) append_(_G471, _G376, [3]) ? creep
   Fail: (7) append_(_G468, _G376, [2, 3]) ? creep
   Fail: (6) append_(_G375, _G376, [1, 2, 3]) ? creep
false.

[trace]  ?- append([1,2],[3],R).
R = [1, 2, 3].

[trace]  ?- notrace.
true.

[debug]  ?- member(1,[1,2,3]).
true ;
false.

[debug]  ?- member(2,[1,2,3]).
true .

[debug]  ?- [lists].
Warning: /Users/tarau/Desktop/go/styla_a_prolog_in_scala/styla/progs/lists.pl:14:
	Singleton variables: [Y]
% lists compiled 0.00 sec, 4 clauses
true.

[debug]  ?- [lists].
% lists compiled 0.00 sec, 2 clauses
true.

[debug]  ?- member_(X,[1,2,3]).
X = 1 ;
X = 2 ;
X = 3 ;
false.

[debug]  ?- member_(2,[1,2,3]).
true .

[debug]  ?- [lists].
Warning: /Users/tarau/Desktop/go/styla_a_prolog_in_scala/styla/progs/lists.pl:16:
	Singleton variables: [X]
% lists compiled 0.00 sec, 3 clauses
true.

[debug]  ?- [lists].
% lists compiled 0.00 sec, 2 clauses
true.

[debug]  ?- member__(X,[1,2,3]).
X = 3 ;
false.

[debug]  ?- [lists].
% lists compiled 0.00 sec, 2 clauses
true.

[debug]  ?- member__(X,[1,2,3]).
X = 1 ;
X = 2 ;
X = 3 ;
false.

[debug]  ?- member__(3,[1,2,3]).
true .

[debug]  ?- member__(3,[1,X,4,5,6]).
X = 3 ;
false.

[debug]  ?- 

*/
