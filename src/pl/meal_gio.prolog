
%
%
%
:- dynamic(at/1).
:- dynamic(meal/3).
:- dynamic(order/1).
:- dynamic(go/1).
:- dynamic(go_eat/2).
:- dynamic(x/1).

%PREFERENCES
%dominates(P,P):- !,false.

dominates(meal(S,main(meat),W),meal(S,main(fish),W)) :- at(italian).
dominates(meal(S,main(fish),W),meal(S,main(meat),W)) :- at(french).
dominates(meal(soup(fish),M,W),meal(soup(veg),M,W)) :- M == main(meat).
dominates(meal(soup(veg),M,W),meal(soup(fish),M,W)) :- M == main(fish).
dominates(meal(S,M,wine(red)),meal(S,M,_)) :- S == soup(veg).
dominates(meal(S,M,wine(white)),meal(S,M,_)) :- S == soup(fish).

dominates(go_eat(L1,_),go_eat(_,_)) :- at(L1).
dominates(go_eat(italian,meal(S,main(meat),W)),go_eat(L,meal(S,_,W))) :- \+at(L).
dominates(location(italian),location(_)) :- true.
dominates(x(A),_) :- A == hello.


%APPLICABILITY
meal(soup(S),main(M),wine(W)) :- soup(S), main(M), wine(W).
go_eat(L,M) :- location(L), \+at(L).
go_eat(L,M) :- location(L),at(L).


%The ONLY RULE!
most_prefered(Pred) :- copy_term(Pred,Pred2), Pred, forall(Pred2,( (dominates(Pred2,Pred) , Pred \== Pred2 )->fail;true)).


%BELIEFS
main(fish).
main(meat).

soup(veg).
soup(fish).


wine(white).
wine(red).
x(chello).
x(hello).
x(gello).

at(french).

location(italian).
location(french).

