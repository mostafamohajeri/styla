
vertex(1,~C1). 
vertex(2,~C2).
vertex(3,~C3).
vertex(4,~C4).
vertex(5,~C5).
    
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
  
 