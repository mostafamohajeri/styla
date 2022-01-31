%
%
%
:- dynamic(meal/3).
dominates(place(paris),place(rome)) :- meal(_,_,wine(red)).
dominates(place(rome),place(paris)) :- meal(_,_,wine(white)).

/*dominates(main(meat),main(fish)) :- meal(_,_,_).

dominates(soup(fish),soup(veg)) :- meal(_,main(meat),_).
dominates(soup(veg),soup(soup)) :- meal(_,main(fish),_).

dominates(wine(red),wine(white)) :- meal(soup(veg),_,_).
dominates(wine(white),wine(yellow)) :- meal(soup(fish),_,_).
dominates(wine(yellow),wine(red)) :- meal(soup(fish),_,_).
*/
dominates(meal(S,main(meat),W),meal(S,main(fish),W)) :- true.
dominates(meal(soup(fish),M,W),meal(soup(veg),M,W)) :- M == main(meat).
dominates(meal(soup(veg),M,W),meal(soup(fish),M,W)) :- M == main(fish).
dominates(meal(S,M,wine(red)),meal(S,M,_)) :- S == soup(veg).
dominates(meal(S,M,wine(white)),meal(S,M,wine(red))) :- S == soup(fish).
dominates(meal(S,M,wine(champagne)),meal(S,M,wine(white))) :- S == soup(fish).

meal(soup(S),main(M),wine(W)) :- soup(S), main(M), wine(W).



%prefered(wine(X_1)):-
%    wine(X_1),
%    forall(wine(X_2),((dominates(wine(X_2),wine(X_1)))->fail;true)).
/*
prefered(soup(X_1)):-
    soup(X_1),
    forall(main(X_2),(dominates(soup(X_2),soup(X_1))->fail;true)).

prefered(main(X_1)):-
    main(X_1),
    forall(soup(X_2),(dominates(main(X_2),main(X_1))->fail;true)).

prefered(place(X_1)):-
    place(X_1),
    forall(place(X_2),(dominates(place(X_2),place(X_1))->fail;true)).

prefered(meal(X_1,Y_1,Z_1)):-
    meal(X_1,Y_1,Z_1),
    forall(meal(X_2,Y_2,Z_2),(dominates(meal(X_2,Y_2,Z_2),meal(X_1,Y_1,Z_1))->fail;true)).

prefered(wine(X_1)):-
    findall(X,prefered(wine(X),[]),Z),member(X_1,Z).

prefered(wine(X_1),T):-
     wine(X_1),\+member(X_1,T),
     forall((wine(X_2),\+member(X_2,T)),((dominates(wine(X_2),wine(X_1)))->fail;true))
    .

*/
p(wine(X_1)) :- findall(X,(wine(P),X=wine(P)),Z), bubble_sort(Z,L),member(wine(X_1),L).
p(meal(X_1,X_2,X_3)) :- findall(X,(meal(P1,P2,P3),X=meal(P1,P2,P3)),Z),bubble_sort(Z,L),member(meal(X_1,X_2,X_3)
,L).


bubble_sort(List,Sorted):-b_sort(List,[],Sorted),!.
b_sort([],Acc,Acc).
b_sort([H|T],Acc,Sorted):-bubble(H,T,NT,Max),b_sort(NT,[Max|Acc],Sorted).
   
bubble(X,[],[],X).
bubble(X,[Y|T],[Y|NT],Max):- \+dominates(X,Y),bubble(X,T,NT,Max).
bubble(X,[Y|T],[X|NT],Max):- dominates(X,Y),bubble(Y,T,NT,Max).


succ(X, Y) :- Y is X + 1.

    
hh(X) :- findall(X,wine(X),Z), member(X,Z).


main(meat).
main(fish).


soup(veg).
soup(fish).


wine(red).
wine(white).
wine(champagne).


place(rome).
place(paris).
