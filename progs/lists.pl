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
  