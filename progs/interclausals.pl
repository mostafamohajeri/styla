dict(~D).
  
dict_add(X):-dict_add(X,~D).
dict_find(X):-dict_find(X,~D).

dict_add(X,[X|_]).
dict_add(X,Xs):-nonvar(Xs),Xs=[_|Ys],dict_add(X,Ys).

dict_find(X,Xs):-nonvar(Xs),Xs=[X|_].
dict_find(X,Xs):-nonvar(Xs),Xs=[_|Ys],dict_find(X,Ys).

/*
?- dict_add(a),dict_add(b),dict_add(c),dict_find(X).
dict_add(a),dict_add(b),dict_add(c),dict_find(X).
X = a
;
X = b
;
X = c
;
no (more) answers
*/

% graph coloring
    
color(red).
color(green).
color(blue).

vertex(1,~C1). 
vertex(2,~C2).
vertex(3,~C3).
vertex(4,~C4).
vertex(5,~C5).
vertex(6,~C6).
    
edge(1,2).
edge(2,3).
edge(1,3).
edge(3,4).
edge(4,5).
edge(5,6).
edge(4,6).
edge(2,5).
edge(1,6).

coloring(Vs):-
  E=edge(_,_),findall(E,E,Es),
  color_all(Es),
  V=vertex(_,_),findall(V,V,Vs).
    
color_all([]).
color_all([edge(X,Y)|Es]):-
   vertex(X,C),
   vertex(Y,D),
   color(C),
   color(D),
   \+(C=D),
   color_all(Es).

/*

?- coloring(Vs).
coloring(Vs).
Vs = [vertex(1,red),vertex(2,green),vertex(3,blue),vertex(4,red),vertex(5,blue),vertex(6,green)]
;
Vs = [vertex(1,red),vertex(2,green),vertex(3,blue),vertex(4,green),vertex(5,red),vertex(6,blue)]
;
Vs = [vertex(1,red),vertex(2,blue),vertex(3,green),vertex(4,red),vertex(5,green),vertex(6,blue)]
;
Vs = [vertex(1,red),vertex(2,blue),vertex(3,green),vertex(4,blue),vertex(5,red),vertex(6,green)]
;
Vs = [vertex(1,green),vertex(2,red),vertex(3,blue),vertex(4,red),vertex(5,green),vertex(6,blue)]
;
Vs = [vertex(1,green),vertex(2,red),vertex(3,blue),vertex(4,green),vertex(5,blue),vertex(6,red)]
;
Vs = [vertex(1,green),vertex(2,blue),vertex(3,red),vertex(4,green),vertex(5,red),vertex(6,blue)]
;
Vs = [vertex(1,green),vertex(2,blue),vertex(3,red),vertex(4,blue),vertex(5,green),vertex(6,red)]
;
Vs = [vertex(1,blue),vertex(2,red),vertex(3,green),vertex(4,red),vertex(5,blue),vertex(6,green)]
;
Vs = [vertex(1,blue),vertex(2,red),vertex(3,green),vertex(4,blue),vertex(5,green),vertex(6,red)]
;
Vs = [vertex(1,blue),vertex(2,green),vertex(3,red),vertex(4,green),vertex(5,blue),vertex(6,red)]
;
Vs = [vertex(1,blue),vertex(2,green),vertex(3,red),vertex(4,blue),vertex(5,red),vertex(6,green)]
;
no (more) answers


*/



    
% MinSpanTree is a minimum spanning tree of an undirected graph 
% represented by its list of Edges of the form: "edge(Cost,From,To)" 
% with From<To, sorted by cost (in ascending order), 
% and having as vertices natural numbers in 1..NbOfVertices.

mst(NbOfVertices,Edges,MinSpanTree):-
  sort(Edges,SortedEdges),
  mst0(NbOfVertices,SortedEdges,MinSpanTree).

mst0(1,_,[]). % no more vertices left 
mst0(N,[E|Es],T):- N>1,
  E=edge(_Cost,V1,V2),
  vertex(V1,C1), % C1,C2 are the components of V1,V2 
  vertex(V2,C2), 
  mst1(C1,C2,E,T,NewT,N,NewN),
  mst0(NewN,Es,NewT). 

% Both endpoints are already in the same 
% connected component - skip the edge      
mst1(C1,C2,_,T,T,N,N):-C1==C2.       
% Put endpoints in the same component     
mst1(C1,C2,E,T,NewT,N,NewN):-C1\==C2,C1=C2, 
  T=[E|NewT],   % Take the edge 
  NewN is N-1.  % Count a new vertex 
  
test_mst(MinSpanTree):- 
  Edges = [ edge(70,1,3),edge(80,3,4),edge(90,1,5),
            edge(60,2,3),edge(20,4,5),edge(30,1,4), 
            edge(40,2,5),edge(50,3,5),edge(10,1,2)      
          ],
  mst(5,Edges,MinSpanTree).    
   



   