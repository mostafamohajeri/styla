package prolog.fluents
object Lib {
  val code = """
    
/* default library */
    
% '='(X,X). 
    
% stubs for special operations

return(_). 
dynamic(_).
multifile(_).
initialization(X):-X.
    
var(X):-type_of(X,var).
compound(X):-type_of(X,compound).
atom(X):-type_of(X,atom).
float(X):-type_of(X,float).
integer(X):-type_of(X,integer).
              
system_object(X):-type_of(X,system_object).

nonvar(X):-var(X),!,fail.
nonvar(_).

atomic(X):-type_of(X,T),member(T,[atom,float,integer,system_object]),!.

number(X):-type_of(X,T),member(T,[float,integer]),!.

new_engine(X,G,E):-new_engine([],X,G,E). 
    

first_solution(X0,G0,A):-copy_term(the(X0,G0),the(X,G)),G,!,eq(A,the(X)).
first_solution(_,_,no).

source_list(S,Xs):-source_to_list(yes,S,Xs). 

findall(X,G,Xs):-new_engine(X,G,E),source_list(E,Xs). 

to_chars(T,Cs):-string_source(T,S),source_list(S,Cs).

from_chars(Cs,T):-string_sink(S),from_chars1(Cs,S),collect(S,T).
    
from_chars1(Cs,S):-    
   member(C,Cs),
   put(S,C),
   fail.
from_chars1(_,_).    
    
once(G):-G,!.
    
if(C,T,_):-C,!,T. 
if(_,_,E):-E. 

\+(X):-X,!,fail.
\+(_).

member(X,[X|_]).
member(X,[_|Xs]):-member(X,Xs).

app0([],Ys,Ys). 
app0([X|Xs],Ys,[X|Zs]):-app0(Xs,Ys,Zs). 

append(Xs,Ys,Zs):-det_append(Xs,Ys,R),!,eq(R,Zs). 
append(Xs,Ys,Zs):-app0(Xs,Ys,Zs). 

println(Any):-traceln(Any). 
portray_clause(C):-println(C).

errmes(Mes,Culprit):-
    traceln('***'(Mes,Culprit)).
    
time(G,T):-ctime(T1),if(G,true,true),ctime(T2),'-'(T2,T1,T). 
time(G):-time(G,T),println(time(G,T)). 

element_of(I,X):-get(I,the(A)),select_from(I,A,X). 

select_from(_,A,A). 
select_from(I,_,X):-element_of(I,X). 

char_of(F,X):-file_clause_reader(F,S),element_of(S,X). 
    
clause_of(F,X):-file_clause_reader(F,S),element_of(S,X). 
    
term_of(F,T):-clause_of(F,C),clause2term(C,T).
 
    
assertz(C):-assert(C).
abolish(F/N):-abolish(F,N).

current_predicate(FN):-ground(FN),!,FN=F/N,fun(F,N,T),has_clauses(T).
current_predicate(FN):-predicate_iterator(I),element_of(I,FN).
    
clause(H,B):-
  clause_iterator(H,I),
  element_of(I,(H:-B)).  

is_defined(H):-has_clauses(H,N),N>-1.
    
has_clauses(H):-has_clauses(H,N),N>0.
    
retract(H):-retract1(H).
retract(H):-has_clauses(H,N),N>0,retract(H).   
    
listing:-listing(_). 
    
include(F):-consult(F).
[F]:-reconsult(F). 
   
'+'(X,Y,Z):-is(Z,'+'(X,Y)). 
'-'(X,Y,Z):-is(Z,'-'(X,Y)). 
'*'(X,Y,Z):-is(Z,'*'(X,Y)). 
'/'(X,Y,Z):-is(Z,'/'(X,Y)). 
        
'<'(X,Y):-is(-1,'?'(X,Y)). 
'>'(X,Y):-is(1,'?'(X,Y)).  
'=:='(X,Y):-is(0,'?'(X,Y)). 
    
'=\='(X,Y):-'=:='(X,Y),!,fail.
'=\='(_,_).
    
'>='(X,Y):- \+('<'(X,Y)).
'=<'(X,Y):- \+('>'(X,Y)).

';'('->'(A,B),C) :- !,if(A,B,C).
';'(X,_):-X.
';'(_,X):-X.

','(X,Y):-X,Y.
    
repeat.
repeat:-repeat.

succ(X,Y):-is(Y,'+'(X,1)).

length(Xs,L):-nonvar(L),!,len2list(L,Xs).
length(Xs,L):-list2len(Xs,L).

list2len(Xs,L):-list2len(Xs,0,L).

list2len([],N,N).
list2len([_|Xs],N,L):-succ(N,N1),list2len(Xs,N1,L).

len2list(L,Xs):-len2list(0,L,Xs).

len2list(To,To,Xs):-!,Xs=[].
len2list(From,To,[_|Xs]):-succ(From,Next),len2list(Next,To,Xs).

functor(T,F,N):-atomic(T),!,F=T,N=0.
functor(T,F,N):-nonvar(T),!,arg(0,T,F),arity(T,N).
functor(T,F,N):-integer(N),atom(F),N>=0,fun(F,N,T).

'=..'(T,FXs):-number(T),!,FXs=[T].
'=..'(T,FXs):-nonvar(T),!,arity(T,N),succ(N,M),term_list(0,M,T,FXs).
'=..'(T,[F|Xs]):-list2len(Xs,N),fun(F,N,T),succ(N,M),term_list(1,M,T,Xs).
        
term_list(To,To,_,[]):-!.
term_list(From,To,F,[X|Xs]):-
  succ(From,Next),
  arg(From,F,X),
  term_list(Next,To,F,Xs).
   
call(X):-X.
call(F,X):-termcat(F,x(X),G),G.
call(F,X,Y):-termcat(F,x(X,Y),G),G.
call(F,X,Y,Z):-termcat(F,x(X,Y,Z),G),G.
call(F,X,Y,Z,U):-termcat(F,x(X,Y,Z,U),G),G.

callN(FXs,Ys):-   
  FYs=..[x|Ys],
  termcat(FXs,FYs,G),
  G.
    
numlist(From,To,Xs):-End is To+1,range(From,End,Xs).
    
compare(R,X,Y):-term_compare(X,Y,D),int2ordrel(D,R).

int2ordrel(-1,'<').
int2ordrel(0,'=').
int2ordrel(1,'>').

'@<'(X,Y):-term_compare(X,Y,-1).
'=='(X,Y):-term_compare(X,Y,0).
'\=='(X,Y):-term_compare(X,Y,R),R=\=0.
'@>'(X,Y):-term_compare(X,Y,1).
'@>='(X,Y):- \+('@<'(X,Y)).
'@=<'(X,Y):- \+('@>'(X,Y)).

ground(X):-vars_of(X,[]).
    
numbervars('$VAR'(N0), N0, N) :- !, succ(N0,N).
numbervars('$VAR'(_), N, N) :- !.
numbervars(X, N0, N) :- atomic(X), !, N0=N.
numbervars([X|Xs], N0, N) :- !,
  numbervars(X, N0, N1),
  numbervars(Xs, N1, N).
numbervars(X, N0, N) :- 
  arity(X,A),
  numbervar_args(0, A, X, N0, N).
        
numbervar_args(A, A, _, N0, N) :- !, N0=N.
numbervar_args(A0, A, X, N0, N) :- A0<A,
  succ(A0,A1),
  arg(A1, X, X1),
  numbervars(X1, N0, N1),    
  numbervar_args(A1, A, X, N1, N).
  
unify_with_occurs_check(X,T):-var(X),!,var_does_not_occur_in(T,X),X=T.
unify_with_occurs_check(T,X):-var(X),!,var_does_not_occur_in(T,X),X=T.
unify_with_occurs_check(X,T):-atomic(X),!,X=T.
unify_with_occurs_check(T,X):-atomic(X),!,X=T.
unify_with_occurs_check(T1,T2):-
  arity(T1,N),arity(T2,N),
  uoc_list(0,N,T1,T2).

uoc_list(I,N,_,_):-I>N,!.
uoc_list(I,N,T1,T2):-succ(I,J),arg(I,T1,X),arg(I,T2,Y),
  unify_with_occurs_check(X,Y),
  uoc_list(J,N,T1,T2).

var_does_not_occur_in(T,X):-var_occurs_in(T,X),!,fail.
var_does_not_occur_in(_,_).

var_occurs_in(T,X):-var(T),!,same_vars1(X,T).
var_occurs_in(T,_):-atomic(T),!,fail.
var_occurs_in(T,X):-argn(_,T,A),var_occurs_in(A,X),!.

argn(I,T,X):-arity(T,N),between(0,N,I),arg(I,T,X).

between(A,B,X):-F is (B-A)+1,integer_source(F,1,A,1,S),element_of(S,X).

for(I,A,B):-between(A,B,I).
    
same_vars1(0,1):-!,fail.
same_vars1(1,0):-!,fail.
same_vars1(_,_).

same_vars(X,Y):-var(X),var(Y),same_vars1(X,Y).

foreach(When,Then):- When,once(Then),fail.
foreach(_,_).
    
and(X,Y):-X,Y.
        
forall(G1,G2):- \+(and(G1,\+(G2))).

false:-fail.
    
open(F,r,S):-file_clause_reader(F,S).
open(F,cr,S):-file_char_reader(F,S).
open(F,w,S):-file_writer(F,S).

read(S,X):-from_source(S,C),clause2term(C,X).

clause2term(':-'(true,B),R):-!,R=B.
clause2term(':-'(H,true),R):-!,R=H.
clause2term(T,T).
    
read(X):-file_clause_reader(stdio,S),read(S,X).
    
write(S,X):-to_sink(S,X).

write(X):-file_writer(stdio,S),to_sink(S,X).
    
nl(S):-to_sink(S,'\n').
nl:-file_writer(stdio,S),nl(S).
    
char_code(C,N):-var(N),!,char2code(C,N).
char_code(C,N):-code2char(N,C).
    
%% maplist(F,Xs1,Xs2,...): applies F to elements in the same position in Xs1,Xs2..

maplist(F,Xs):-maplist0(Xs,F).

maplist0([],_F):-!.
maplist0([X|Xs],F):-call(F,X),maplist0(Xs,F).

maplist(F,Xs,Ys):-maplist0(Xs,F,Ys).

maplist0([],_F,[]).
maplist0([X|Xs],F,[Y|Ys]):-call(F,X,Y),maplist0(Xs,F,Ys).

maplist(F,Xs,Ys,Zs):-maplist0(Xs,F,Ys,Zs).

maplist0([],_F,[],[]).
maplist0([X|Xs],F,[Y|Ys],[Z|Zs]):-call(F,X,Y,Z),maplist0(Xs,F,Ys,Zs).

%% sumlist(Xs,S): returns S, the sum of numbers on list Xs
sumlist([],0).
sumlist([X|Xs],R):-sumlist(Xs,R1),'+'(R1,X,R).

%% sumlist(Xs,P): returns P, the product of numbers on list Xs
prodlist([],1).
prodlist([X|Xs],R):-prodlist(Xs,R1),'*'(R1,X,R).

%% foldl(F,Z,Xs,R): combines with F all members of Xs, from left to right, starting with Z    
foldl(F,Z,Xs,R):-foldl0(Xs,F,Z,R).
  
foldl0([],_,R,R).
foldl0([X|Xs],F,R1,R3):-call(F,R1,X,R2),foldl0(Xs,F,R2,R3).

%% foldl(F,Z,Xs,R): combines with F all members of Xs, from right to left, starting with Z
foldr(F,Z,Xs,R):-foldr0(Xs,F,Z,R).
  
foldr0([],_,Z,Z).
foldr0([X|Xs],F,Z,R2):-foldr0(Xs,F,Z,R1),call(F,X,R1,R2).

%% combines Xs and Ys into pairs X-Y with X in Xs and Y in Ys
zip([],[],[]).
zip([X|Xs],[Y|Ys],[X-Y|Zs]):-zip(Xs,Ys,Zs).

%% zipWith(F,Xs,Ys,Zs): for X in Xs and Y in Zs computes and collects Z in Zs such that F(X,Y,Z)
zipWith(F,Xs,Ys,Zs):-zipWith2(Xs,Ys,F, Zs).

zipWith2([],[],_,[]).
zipWith2([X|Xs],[Y|Ys],F,[Z|Zs]):-call(F,X,Y,Z),zipWith2(Xs,Ys,F,Zs).
  
% LIST PROCESSING

%% is_list(X): true if X is instantiated to a list
is_list(X) :- var(X),!,fail.
is_list([]).
is_list([_|Xs]) :- is_list(Xs).

%% append(Xss,Xs): Xs is the concatentation of Xss obtained by folding with append/3
append(Xss,Xs):-foldl(det_append,[],Xss,Xs).

%% select(X,Xs,Ys) : pulls out an element X from list Xs returning the others as Ys
select(X,[X|S],S).
select(X,[Y|S1],[Y|S2]):- %nonvar(S1),
  select(X,S1,S2).

%% true if X occurs on list Xs - only checks
memberchk(X,[X|_]):-!.
memberchk(X,[_|Xs]):-memberchk(X,Xs).

rmember(X,[_|Xs]):-rmember(X,Xs).
rmember(X,[X|_]).

%% rselect/3: like select/3 but starts with the last one
rselect(X,[X|S],S).
rselect(X,[Y|S1],[Y|S2]):- %nonvar(S1),
  rselect(X,S1,S2).

%% select_nonvar/3: like select/3 but checks that list ends in a nonvar
select_nonvar(X,XXs,Xs):-nonvar(XXs),XXs=[X|Xs].
select_nonvar(X,YXs,[Y|Ys]):-nonvar(YXs),YXs=[Y|Xs],select_nonvar(X,Xs,Ys).

%% picks N-th element X starting from 0 occuring in Xs
nth0(N,Xs,X):-member_i(X,Xs,0,N).

%% nth1(N,Xs,X): picks N-th element X starting from 1 occuring in Xs  
nth1(N,Xs,X):-member_i(X,Xs,1,N).

member_i(X,[X|_],N,N).
member_i(X,[_|Xs],N1,N3):-
  succ(N1,N2),
  member_i(X,Xs,N2,N3).

%% last(Xs,X): picks X, the last element of list Xs
last([X],Last):-!,Last=X.
last([_|Xs],Last):-last(Xs,Last).

%% reverse(Xs,Ys) : Ys is Xs reversed
reverse(Xs,Ys):-rev(Xs,[],Ys).

rev([],Ys,Ys).
rev([X|Xs],Ys,Zs):-rev(Xs,[X|Ys],Zs).

%% init(Xs,AllButLast): returns all but the last element of a list
init([_],[]):-!.
init([X|Xs],[X|Ys]):-init(Xs,Ys).

%% take(N,Xs,Ys): takes the first N elements Ys of list Xs
take(N,Xs,Ys):-split_at(N,Xs,Ys,_).

%% split_at(N,Xs,Ys,Zs): splits into first N elements Ys of list Xs and other elements Zs (if any) 
split_at(N,[X|Xs],[X|Ys],Zs):- N>0,!,N1 is (N-1),split_at(N1,Xs,Ys,Zs).
split_at(_,Xs,[],Xs).

%% drop(N,Xs,Ys): drops the first N elements of list Xs and returns the left over elements Ys
drop(N,[_|Xs],Ys):-N>0,!,N1 is (N-1),drop(N1,Xs,Ys).
drop(_,Ys,Ys).

%% split_to_groups(K,Xs,Xss): splits Xs into a list of K lists
split_to_groups(K,Xs,Yss):-K>0,
  length(Xs,L),
  Mod is (1*mod(L,K)),
  Size is (Mod+'//'(L,K)),
  ksplit(K,Size,Xs,Xss),
  !,
  Yss=Xss.

%% split_to_size(K,Xs,Xss): splits Xs into a list of lists of size K or less each
split_to_size(K,Xs,Yss):-
  K>0,
  length(Xs,L),
  Groups is '//'(L,K),
  ksplit(Groups,K,Xs,Xss),
  !,
  Yss=Xss.

ksplit(_,_,[],[]):-!.     
ksplit(0,_,Xs,[Xs]):-!.
ksplit(I,K,Xs,[Hs|Yss]):-
  I>0,
  I1 is (I-1),
  split_at(K,Xs,Hs,Ts),
  ksplit(I1,K,Ts,Yss).
  
%% partition(Pred, Xs, Included, Excluded): separates Xs in two lists using Pred
partition(Pred, Xs, Included, Excluded) :-
  partition1(Xs, Pred, Included, Excluded).

partition1([],_,[],[]).
partition1([X|Xs],Pred,Is,Es) :-
   call(Pred, X),
   !,
   Is=[X|Is0],
   partition1(Xs,Pred,Is0,Es).
partition1([X|Xs],Pred,Is,[X|Es]):-
   partition1(Xs,Pred,Is,Es).
   
atom_chars(A,Cs):-var(Cs),!,to_chars(A,Cs).
atom_chars(A,Cs):-from_chars(Cs,A).

atom_codes(A,Ns):-var(Ns),!,to_chars(A,Cs),maplist(char2code,Cs,Ns).
atom_codes(A,Ns):-maplist(code2char,Ns,Cs),from_chars(Cs,A).

number_chars(N,Cs):-var(Cs),!,to_string(N,S),to_chars(S,Cs).
number_chars(N,Cs):-from_chars(Cs,S),to_number(S,N).    
    
number_codes(N,Ns):-var(Ns),!,to_string(N,A),to_chars(A,Cs),maplist(char2code,Cs,Ns).
number_codes(N,Ns):-maplist(to_string,Ns,Xs),maplist(code2char,Xs,Cs),from_chars(Cs,N).    
 
atom_concat(A,B,C):-to_chars(A,As),to_chars(B,Bs),append(As,Bs,Cs),from_chars(Cs,C).
    
phrase(B,S1,S2):-dcg_phrase(x,B,x(S1,S2),DcgGoal),DcgGoal.
    
phrase(P,S1):-phrase(P,S1,[]).

cputime(T):-ctime(Tms),T is Tms/1000.

    
% sort
ksort(List, Sorted) :-
    '-'(0,1,X),
    keysort(List, X , S, []), !,
    Sorted = S.
ksort(X, Y):-
    errmes('illegal_arguments',keysort(X,Y)).

%% keygroup(KsVs,Keys,Vals): keysorts KsVs, then backtrack over groups of the form Key,Vals
keygroup(KsVs,K,Vs):-
  keysort(KsVs,Sorted),
  concordant_subset(Sorted,K,Vs).
  
keysort([Head|Tail], Lim, Sorted, Rest) :- !,
    nonvar(Head),
    Head = _-_,
    Qh = [Head|_],
    samkeyrun(Tail, Qh, Qh, Run, Rest0),
    keysort(Rest0, 1, Lim, Run, Sorted, Rest).
keysort(Rest, _, [], Rest).

keysort([Head|Tail], J, Lim, Run0, Sorted, Rest) :-
    J =\= Lim, !,
    nonvar(Head),
    Head = _-_,
    Qh = [Head|_],
    samkeyrun(Tail, Qh, Qh, Run1, Rest0),
    keysort(Rest0, 1, J, Run1, Run2, Rest1),
    keymerge(Run0, Run2, Run),
    K is J+J,
    keysort(Rest1, K, Lim, Run, Sorted, Rest).
keysort(Rest, _, _, Sorted, Sorted, Rest).

samkeyrun([Hd|Tail], QH, QT, Run, Rest) :-
    nonvar(Hd),
    Hd = H-_,
    QT = [Q-_|QT2], 
    Q @=< H, !,
    QT2 = [Hd|_],
    samkeyrun(Tail, QH, QT2, Run, Rest).
samkeyrun([Hd|Tail], QH, QT, Run, Rest) :-
    nonvar(Hd),
    Hd = H-_,
    QH = [Q-_|_],
    H @< Q, !,
    samkeyrun(Tail, [Hd|QH], QT, Run, Rest).
samkeyrun(Rest, Run, [_], Run, Rest).

% keymerge(+List, +List, -List).
keymerge([], L2, Out) :- !,
    Out = L2.
keymerge([H1|T1], L2, Out) :-   
    L2 = [K2-_|_],
    H1 = K1-_,
    K1 @=< K2, !,
    Out = [H1|Out1],
    keymerge(T1, L2, Out1).
keymerge(L1, [H2|L2], Out) :- !,
    Out = [H2|Out1],
    keymerge(L1, L2, Out1).
keymerge(List, _, List).


%% sort(Xs,SortedSetXs): sorts a list then removes duplicates
sort(L1,L2):-stable_sort('<',L1,DupL),remdup(DupL,L2).

msort(L1,L2):-stable_sort('<',L1,L2).

%% sort(Xs,SortedMultisetXs): sorts a list without removing duplicates
merge_sort(Dir,L1,L2):-stable_sort(Dir,L1,L2).

%% keysort(KVs,NewKVs): sorts by keys, a list of Key-Value pairs
keysort(L,S):-ksort(L,S).

%% remdup(Multiset,Set) : removes duplicates from a sorted list Multiset
remdup([],[]).
remdup([X,Y|Xs],Ys):-compare('=',X,Y),!,remdup([X|Xs],Ys).
remdup([X|Xs],[X|Ys]):-remdup(Xs,Ys).
    



% derived from code by R.A. O'Keefe

%% setof(X,Vs^G,Xs) : collects set of X such that G into Xs - it may backtrack if variables not in Vs occur in G or if Vs is absent
setof(Template, Filter, Set) :-
    bagof(Template, Filter, Bag),
    sort(Bag, Set).

%% bagof(X,Vs^G,Xs) : collects list of X such that G into Xs - it may backtrack if variables not in Vs occur in G or if Vs is absent 
bagof(Template, Generator, Bag) :-
    free_variables(Generator, Template, [], Vars,1),
    Vars \== [],
    !,
    Key =.. ['.'|Vars],
    functor(Key, '.', N),
    findall(Key-Template,Generator,Recorded),
    replace_instance(Recorded, Key, N, [], OmniumGatherum),
    keysort(OmniumGatherum, Gamut), 
    !,
    concordant_subset(Gamut, Key, Answer),
    Bag = Answer.
bagof(Template, Generator, [B|Bag]) :-
    findall(Template,Generator,[B|Bag]).
    
_^Goal:-Goal.

replace_instance([], _, _, AnsBag, AnsBag) :- !.
replace_instance([NewKey-Term|Xs], Key, NVars, OldBag, NewBag) :-
        replace_key_variables(NVars, Key, NewKey), !,
        replace_instance(Xs,Key, NVars, [NewKey-Term|OldBag], NewBag).

replace_key_variables(0, _, _) :- !.
replace_key_variables(N, OldKey, NewKey) :-
    arg(N, NewKey, Arg),
    nonvar(Arg), 
    !,
    M is N-1,
    replace_key_variables(M, OldKey, NewKey).
replace_key_variables(N, OldKey, NewKey) :-
    arg(N, OldKey, OldVar),
    arg(N, NewKey, OldVar),
    M is N-1,
    replace_key_variables(M, OldKey, NewKey).

/*
%   concordant_subset([Key-Val list], Key, [Val list]).
%   takes a list of Key-Val pairs which has been keysorted to bring
%   all the identical keys together, and enumerates each different
%   Key and the corresponding lists of values.
*/
concordant_subset([Key-Val|Rest], Clavis, Answer) :-
    concordant_subset(Rest, Key, List, More),
    concordant_subset(More, Key, [Val|List], Clavis, Answer).

/*
%   concordant_subset(Rest, Key, List, More)
%   strips off all the Key-Val pairs from the from of Rest,
%   putting the Val elements into List, and returning the
%   left-over pairs, if any, as More.
*/
concordant_subset([Key-Val|Rest], Clavis, [Val|List], More) :-
    Key == Clavis,
    !,
    concordant_subset(Rest, Clavis, List, More).
concordant_subset(More, _, [], More).

/*
%   concordant_subset/5 tries the current subset, and if that
%   doesn't work if backs up and tries the next subset.  The
%   first clause is there to save a choice point when this is
%   the last possible subset.
*/
concordant_subset([],   Key, Subset, Key, Subset) :- !.
concordant_subset(_,    Key, Subset, Key, Subset).
concordant_subset(More, _,   _,   Clavis, Answer) :-
    concordant_subset(More, Clavis, Answer).

%% vars_of(Term,Vars) :: extracts set of free varibles Vars occurring in Term - built-in
% vars_of(Term,Vars):-free_variables(Term,[],[],Vars).

%% term_variables(Term,Vars) :: extracts set of free varibles Vars occurring in Term
term_variables(Term,Vars):-free_variables(Term,[],[],Vars).

% 0 disables use of explicit_binding, 1 enables them
% setof stuff still uses 1, that's closer to it's usual implementation
free_variables(A,B,C,D) :- free_variables(A,B,C,D,0). 

% ---extracted from: not.pl --------------------%

%   Author : R.A.O'Keefe
%   Updated: 17 November 1983
%   Purpose: "suspicious" negation 

%   In order to handle variables properly, we have to find all the 
%   universally quantified variables in the Generator.  All variables
%   as yet unbound are universally quantified, unless
%   a)  they occur in the template
%   b)  they are bound by X^P, setof, or bagof
%   free_variables(Generator, Template, OldList, NewList,CheckBindings=0,1)
%   finds this set, using OldList as an accumulator.

free_variables(Term, Bound, VarList, [Term|VarList],_) :-
    var(Term),
    term_is_free_of(Bound, Term),
    list_is_free_of(VarList, Term),
    !.
free_variables(Term, _, VarList, VarList,_) :-
    var(Term),
    !.
free_variables(Term, Bound, OldList, NewList,B) :- B=:=1,
    explicit_binding(Term, Bound, NewTerm, NewBound),
    !,
    free_variables(NewTerm, NewBound, OldList, NewList,B).
free_variables(Term, Bound, OldList, NewList,B) :-
    functor(Term, _, N),
    free_variables(N, Term, Bound, OldList, NewList,B).

free_variables(0,    _,     _, VarList, VarList,_) :- !.
free_variables(N, Term, Bound, OldList, NewList,B) :-
    arg(N, Term, Argument),
    free_variables(Argument, Bound, OldList, MidList,B),
    M is N-1, !,
    free_variables(M, Term, Bound, MidList, NewList,B).

%   explicit_binding checks for goals known to existentially quantify
%   one or more variables.  In particular "not" is quite common.

explicit_binding('\+'(_),                  Bound, fail, Bound    ).
explicit_binding(not(_),           Bound, fail, Bound    ).
explicit_binding(Var^Goal,         Bound, Goal, Bound+Var).
explicit_binding(setof(Var,Goal,Set),  Bound, Goal-Set, Bound+Var).
explicit_binding(bagof(Var,Goal,Bag),  Bound, Goal-Bag, Bound+Var).

term_is_free_of(Term, Var) :-
    var(Term), !,
    Term \== Var.
term_is_free_of(Term, Var) :-
    functor(Term, _, N),
    term_is_free_of(N, Term, Var).

term_is_free_of(0, _, _) :- !.
term_is_free_of(N, Term, Var) :-
    arg(N, Term, Argument),
    term_is_free_of(Argument, Var),
    M is N-1, !,
    term_is_free_of(M, Term, Var).

list_is_free_of([], _).
list_is_free_of([Head|Tail], Var) :-
    Head \== Var,
    list_is_free_of(Tail, Var).
  
sleep(Sec):-Ms is Sec*1000,sleep_ms(Ms).
    
% actors 

actor_scala_new(A):-actor_scala_new([],A). 

actor_akka_new(A):-actor_akka_new([],A). 

set_last_sender(no).
set_last_sender(the(Sender)):-
  retractall('$last_sender'(_)),
  assert('$last_sender'(Sender)).    

get_last_sender(Sender):-
  % println('ENTERING_get_last_SENDER'),
  is_defined('$last_sender'(_)),
  '$last_sender'(Sender),
  % println('EXITING_get_last_sender'(Sender)),
  true.
    
actor_reply(Msg):-
  get_last_sender(Sender),
  actor_send(Sender,Msg).

actor_stop(Actor):-actor_send(Actor,'$stop').
    
% experiments
    
appendx(Xs,Ys,Zs):- 'prolog.tests.append0'(Xs,Ys,Zs).
appendx(Xs,Ys,Zs):- 'prolog.tests.append1'(Xs,Ys,Zs).
        
"""
}