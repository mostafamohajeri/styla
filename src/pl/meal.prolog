
%
%
%
:- dynamic(at/1).
:- dynamic(meal/3).
:- dynamic(order/1).
:- dynamic(go/1).
:- dynamic(go_eat/2).
%dominates(place(paris),place(rome)) :- meal(_,_,wine(red)). 
%dominates(place(rome),place(paris)) :- meal(_,_,wine(white)).

/*dominates(main(meat),main(fish)) :- meal(_,_,_).

dominates(soup(fish),soup(veg)) :- meal(_,main(meat),_).
dominates(soup(veg),soup(soup)) :- meal(_,main(fish),_).

dominates(wine(red),wine(white)) :- meal(soup(veg),_,_).
dominates(wine(white),wine(yellow)) :- meal(soup(fish),_,_).
dominates(wine(yellow),wine(red)) :- meal(soup(fish),_,_).
*/

dominates(P,P):- !,false.

dominates(meal(S,main(meat),W),meal(S,main(fish),W)) :- at(italian).
dominates(meal(S,main(fish),W),meal(S,main(meat),W)) :- at(french).
dominates(meal(soup(fish),M,W),meal(soup(veg),M,W)) :- M == main(meat).
dominates(meal(soup(veg),M,W),meal(soup(fish),M,W)) :- M == main(fish).
dominates(meal(S,M,wine(red)),meal(S,M,_)) :- S == soup(veg).
dominates(meal(S,M,wine(white)),meal(S,M,_)) :- S == soup(fish).

dominates(go_eat(L1,_),go_eat(_,_)) :- at(L1).
%dominates(go_eat(italian,M),go_eat(L,M)) :- \+at(italian),\+at(L).
dominates(go_eat(italian,meal(S,main(meat),W)),go_eat(L,meal(S,_,W))) :- \+at(L).
%dominates(go_eat(french,meal(S,main(fish),W)),go_eat(L,meal(S,_,W))) :- \+at(french),\+at(L).
%dominates(location(italian),location(_)) :- true.

dominates(P1,P2) :- dominates(P1,T),dominates(T,P2).

%find_dominated(meal(A,B,C),meal(X,Y,Z)) :- meal(X,Y,Z), dominates(meal(A,B,C),meal(X,Y,Z)).
find_dominated(P1,P2) :- P2, dominates(P1,P2).

meal(soup(S),main(M),wine(W)) :- soup(S), main(M), wine(W).



go_eat(L,M) :- location(L), \+at(L).
go_eat(L,M) :- location(L), at(L).


%prefered(meal(X_1,Y_1,Z_1)):-
%    meal(X_1,Y_1,Z_1),
%    forall(meal(X_2,Y_2,Z_2),(dominates(meal(X_2,Y_2,Z_2),meal(X_1,Y_1,Z_1))->fail;true)).



%p(wine(X_1)) :- findall(X,(wine(P),X=wine(P)),Z), selection_sort(Z,L),member(wine(X_1),L).
%p(Pred) :- findall(X,(Pred,X=Pred),Z),bubble_sort(Z,L),member(Pred,L).

p(meal(X_1,X_2,X_3)) :- findall(X,(P1=X_1,P2=X_2,P3=X_3,meal(P1,P2,P3),X=meal(P1,P2,P3)),Z),
    sel_sort(Z,L),
    member(meal(X_1,X_2,X_3),L).

p(go_eat(X_1,X_2)) :-  findall(X,(P1 = X_1,P2 = X_2,go_eat(P1,P2),X=go_eat(P1,P2)),Z),
    sel_sort(Z,L),
    member(go_eat(X_1,X_2),L).

p(order(X_1)) :- findall(X,(P1 = X_1,order(P1),X=order(P1)),Z),
    sel_sort(Z,L),
    member(order(X_1),L).



p(location(X_1)) :- findall(X,(P1 = X_1, location(P1),X=location(P1)),Z),member(location(X_1),Z).

xx(M) :- meal(A,B,C), M = meal(A,B,C).

pp(Pred) :- functor(Pred,F,N) ,functor(Pred2,F,N).

dominance(Pred) :- copy_term(Pred,Pred2), Pred, Pred2, dominates(Pred,Pred2), write(Pred),write("->"),write(Pred2).

most_prefered(Pred) :- copy_term(Pred,Pred2),Pred, forall(Pred2,(dominates(Pred2,Pred)->fail;true)).

min([X],X) :- ! .

min(L,meal(X_1,Y_1,Z_1)):-
    meal(X_1,Y_1,Z_1),member(meal(X_1,Y_1,Z_1),L),
    forall((meal(X_2,Y_2,Z_2),member(meal(X_2,Y_2,Z_2),L)),(dominates(meal(X_2,Y_2,Z_2),meal(X_1,Y_1,Z_1))->fail;true)).

min(L,order(X_1)):-
    order(X_1),member(order(X_1),L),
    forall((order(X_2),member(order(X_2),L)),(dominates(order(X_2),order(X_1))->fail;true)).


min(L,location(X_1)):-
    location(X_1),member(location(X_1),L),
    forall((location(X_2),member(location(X_2),L)),(dominates(location(X_2),location(X_1))->fail;true)).


min(L,go_eat(X_1,Y_1)):-
    go_eat(X_1,Y_1),member(go_eat(X_1,Y_1),L),
    forall((go_eat(X_2,Y_2),member(go_eat(X_2,Y_2),L)),(dominates(go_eat(X_2,Y_2),go_eat(X_1,Y_1))->fail;true)).

main(fish).
main(meat).

soup(veg).
soup(fish).


wine(white).
wine(red).


at(home).
location(italian).
location(french).






distance(italian,10).
distance(french,15).


distance_to(X,Y) :- distance(X,Y).


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%555555
bubble_sort(List,Sorted):-b_sort(List,[],Sorted),!.
b_sort([],Acc,Acc).
b_sort([H|T],Acc,Sorted):-bubble(H,T,NT,Max),b_sort(NT,[Max|Acc],Sorted).
   
bubble(X,[],[],X).
bubble(X,[Y|T],[Y|NT],Max):- \+dominates(X,Y),bubble(X,T,NT,Max).
bubble(X,[Y|T],[X|NT],Max):- dominates(X,Y),bubble(Y,T,NT,Max).
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%555555

sel_sort(L,L2):-selection_sort(L,L2),!.
selection_sort([],[]).
selection_sort(L,[Min|KQ]):-min(L,Min),
delete(L,Min,L1),
selection_sort(L1,KQ).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%?