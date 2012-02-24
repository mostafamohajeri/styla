pro2pre(File):-
  atom_concat(File,'.pl',InF),
  atom_concat(File,'.tex',OutF),
  open(InF,'read',In),
  open(OutF,'write',Out),
  writeln(Out,'\\documentclass{article}'),
  writeln(Out,'\\usepackage{fullpage}'),
  writeln(Out,'\\begin{document}'),
  repeat,
     read_cls_with_varnames(In,C,Vs),
     ( C=end_of_file-> !
     ;
       % portray_clause(C),
       clause2latex(C,Vs,Ls),
       atomcodes(Line,Ls),
       writeln(Out,Line),
       fail
     ),
   writeln(Out,'\\end{document}'),
   close(Out),
   close(In).
   
to_clause((H:-B),C):-!,C=(H:-B).
to_clause(H,(H:-true)).
     
read_cls_with_varnames(In,C,Vs):-
   init_gensym,
   read_term(In,C,[variable_names(Names),variables(Vs)]), %,singletons(Sings)]),
   maplist(bind_names,Names).

bind_names(Name=Var):-Name=Var. % :-downcase_atom(Name,Var).    

clause2latex(C0,Vs,Ls):-
  to_clause(C0,C),
  to_latex(C,Vs,Ls,[]).
 
%begin_math-->  "\\begin{equation}",[10].
%end_math--> "\\end{equation}",[10].

begin_math --> "$".
end_math --> "$\\\\".

to_latex((H:-B),Vs)-->
  begin_math,
  add_foralls(Vs),
  emit_par(B,"("),
  emit_body(B),
  emit_neck(B),
  emit_head(H),
  emit_par(B,")"),[10],
  end_math.
  
atomcodes(S,Cs):-catch(atom_codes(S,Cs),_,(writeln(error(S,Cs)),fail)).
 
emit_head(H)-->emit_term(H).
  
emit_term(T)-->{term_to_atom(T,A),atomcodes(A,Xs),trim_quotes(Xs,Cs)},
  phrase(Cs).  
  
emit_body(true)-->!,[].
emit_body((T,Ts))-->!,emit_term(T),"~\\wedge~",emit_body(Ts).
emit_body(T)-->emit_term(T).

add_foralls([])-->[].
add_foralls([X|Xs])-->
   emit_forall(X),
   add_foralls(Xs).   

emit_neck(true)-->!,[].   
emit_neck(_)-->"~\\Rightarrow~".

init_gensym:-reset_gensym('G').

emit_forall(X)-->{var(X),gensym('G',X)},!,emit_forall1(X).
emit_forall(X)-->emit_forall1(X).

emit_forall1(X)-->"\\forall ",{atomcodes(X,Cs)},phrase(Cs),"~".

trim_quotes([],[]).
trim_quotes([Q|Xs],Ys):-"'"=[Q],!,trim_quotes(Xs,Ys).
trim_quotes([X|Xs],[B,X|Ys]):-"\\_"=[B,X],!,trim_quotes(Xs,Ys).
trim_quotes([X|Xs],[X|Ys]):-trim_quotes(Xs,Ys).

emit_par(true,_)-->!,[].
emit_par(_,[P])-->[P].

writeln(S,T):-write(S,T),nl(S).

c:-[pro2pre].


t:-pro2pre(ctt0).  

   