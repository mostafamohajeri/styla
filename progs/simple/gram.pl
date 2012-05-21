sent --> subj(A),pred(A),obj(_).

subj(A) --> ng(A).
pred(A) --> vg(A).
obj(A) --> ng(A).

ng(non_human) --> art,adjs,cn.
ng(human) --> pn.

adjs-->adj.
adjs-->[]. % adj,adjs.

% adj-->[blind].
adj-->[great].

art --> [a].
art --> [the].

cn --> [cow].
cn --> [moon].

vg(_) --> [jumps,over].
vg(human) --> [admires].

pn --> ['Einstein'].
pn --> ['Tesla'].

/*
write a simple chat bot program using DCG grammars
due May 1-st at noon
*/
