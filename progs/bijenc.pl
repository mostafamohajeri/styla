first_bit(N,Bit):- Bit is 1 /\ N.
times_exp2(N,K,R):-R is N << K.
div_by_exp2(N,K,R):-R is N >> K.
predecessor(N,R):-R is N-1.
successor(N,R):-R is N+1.

k_deflate(_,0,0).
k_deflate(K,N,R):-N>0,
  div_by_exp2(N,K,A),
  k_deflate(K,A,B),
  times_exp2(B,1,C),
  first_bit(N,D),
  R is C\/D.

k_inflate(_,0,0).
k_inflate(K,N,R):-N>0,
  div_by_exp2(N,1,A),
  k_inflate(K,A,B),
  times_exp2(B,K,C),
  first_bit(N,D),
  R is C\/D.

to_tuple(K,N,Ns):-K>0,
  predecessor(K,K1),
  numlist(0,K1,Ks),
  maplist(div_by_exp2(N),Ks,Ys),
  maplist(k_deflate(K),Ys,Ns).

from_tuple(Ns,N):-
  length(Ns,K),K>0,
  predecessor(K,K1),
  maplist(k_inflate(K),Ns,Xs),
  numlist(0,K1,Ks),
  maplist(times_exp2,Xs,Ks,Ys),
  sumlist(Ys,N).

to_pair(N,A,B):-to_tuple(2,N,[A,B]).

from_pair(X,Y,Z):-from_tuple([X,Y],Z).

term2nat(Vs,CSyms,FSyms,T, X):-
  length(CSyms,LC),
  length(FSyms,LF),
  length(Vs,LV),
  LVC is LV+LC,
  LVC>0,
  t2n(LV,LC,LF,LVC,Vs,CSyms,FSyms,T, X).

t2n(LV,_LC,_LF,_LVC,Vs,_CSyms,_FSyms,V, X):-var(V),!,
  lookup_var(I,Vs,V),
  I>=0,I<LV,X=I.
t2n(LV,_LC,_LF,_LVC,_Vs,CSyms,_FSyms,C, X):-atomic(C),!,
  nth0(I,CSyms,C),
  X is I+LV.
t2n(LV,LC,LF,LVC,Vs,CSyms,FSyms,T, X):-compound(T),
  T=..[F|Ts],
  nth0(L,FSyms,F/K),
  K>0,
  length(Args,K),
  P=..[t2n,LV,LC,LF,LVC,Vs,CSyms,FSyms],
  maplist(P,Ts,Args),
  from_tuple(Args,N),
  X is (LVC+(LF*N))+L.

lookup_var(N,Xs,X):-lookup_var(X,Xs,0,N).

lookup_var(X,[Y|_],N,N):-X==Y.
lookup_var(X,[_|Xs],N1,N3):-
  N2 is N1+1,
  lookup_var(X,Xs,N2,N3).

nat2term(Vs,CSyms,FSyms,X, T):-X>=0,
  length(CSyms,LC),
  length(FSyms,LF),
  length(Vs,LV),
  LVC is LV+LC,LVC>0,
  n2t(LV,LC,LF,LVC,Vs,CSyms,FSyms,X, T).

n2t(LV,_LC,_LF,_LVC,Vs,_CSyms,_FSyms,X, V):-X<LV,!,
  nth0(X,Vs,V).
n2t(LV,_LC,_LF,LVC,_Vs,CSyms,_FSyms,X, C):-LV=<X,X<LVC,!,
  X0 is X-LV,
  nth0(X0,CSyms,C).
n2t(LV,LC,LF,LVC,Vs,CSyms,FSyms,X, T):-X>=LVC,
  X0 is X-LVC,
  N is X0 // LF,
  L is X0 mod LF,
  nth0(L,FSyms,F/K),
  K>0,
  to_tuple(K,N,Args),
  P=..[n2t,LV,LC,LF,LVC,Vs,CSyms,FSyms],
  maplist(P,Args,Ts),
  T=..[F|Ts].

% number to term test
t(N,Vs-T):-Vs=[_,_],nat2term(Vs,[0],['->'/2],N,T).

% term to number test
n(Vs-T,N):-term2nat(Vs,[0],['->'/2],T,N).

% number to term test
t1(N,Vs-T):-length(Vs,3),nat2term(Vs,[0,1],['+'/2,'*'/2],N,T).

% term to number test
n1(Vs-T,N):-term2nat(Vs,[0,1],['+'/2,'*'/2],T,N).

% number to term test
t2(N,Vs-T):-length(Vs,2),nat2term(Vs,[a,b,c],['f'/2,'g'/3,'h'/1],N,T).

% term to number test
n2(Vs-T,N):-term2nat(Vs,[a,b,c],['f'/2,'g'/3,'h'/1],T,N).

test:-between(0,100,I),t(I,X),n(X,N),portray_clause([I=N,X]),fail.


ranterm(Bits,Vs,Cs,Fs, T):-
  N is random(2^Bits),
  nat2term(Vs,Cs,Fs,N,T).

from_bbase(Base,Xs,R):-
  maplist(successor,Xs,Xs1),
  from_base1(Base,Xs1,R).

from_base1(_Base,[],0).
from_base1(Base,[X|Xs],R):-X>0,X=<Base,
  from_base1(Base,Xs,R1),
  R is X+Base*R1.

to_bbase(Base,N,Xs):-
  to_base1(Base,N,Xs1),
  maplist(predecessor,Xs1,Xs).

to_base1(_,0,[]).
to_base1(Base,N,[D1|Ds]):-N>0,
   Q is N//Base,
   D is N mod Base,
   (D==0->D1=Base;D1=D),
   (D==0->Q1 is Q-1;Q1=Q),
   (Q1==0->Ds=[];to_base1(Base,Q1,Ds)).

c0(A):-[A]="a".
c1(Z):-[Z]="z".

base(B):-c0(A),c1(Z),B is 1+(Z-A).

string2nat(Cs,N):-
  base(B), 
  maplist(chr2ord,Cs,Ns),
  from_bbase(B,Ns,N).

nat2string(N,Cs):-N >= 0,
  base(B),
  to_bbase(B,N,Xs),
  maplist(ord2chr,Xs,Cs).

chr2ord(C,O):-c0(A),C>=A,c1(Z),C=<Z,O is C-A.
ord2chr(O,C):-O>=0,base(B),O<B,c0(A),C is A+O.

atom2nat(Atom,Nat):-atom_codes(Atom,Cs),string2nat(Cs,Nat).

nat2atom(Nat,Atom):-nat2string(Nat,Cs),atom_codes(Atom,Cs).

term2bitpars(T,[0,1],[T]):-var(T).
term2bitpars(T,[0,1],[T]):-atomic(T).
term2bitpars(T,Ps,As):-compound(T),term2bitpars(T,Ps,[],As,[]).

term2bitpars(T,Ps,Ps)-->{var(T)},[T].
term2bitpars(T,Ps,Ps)-->{atomic(T)},[T].
term2bitpars(T,[0|Ps],NewPs)-->{compound(T),T=..Xs},
  args2bitpars(Xs,Ps,NewPs).

args2bitpars([],[1|Ps],Ps)-->[].
args2bitpars([X|Xs],[0|Ps],NewPs)-->
  term2bitpars(X,Ps,[1|XPs]),
  args2bitpars(Xs,XPs,NewPs).  

bitpars2term([0,1],[T],T).
bitpars2term([P,Q,R|Ps],As,T):-bitpars2term(T,[P,Q,R|Ps],[],As,[]).

bitpars2term(T,Ps,Ps)-->[T].
bitpars2term(T,[0|Ps],NewPs)-->
  bitpars2args(Xs,Ps,NewPs),{T=..Xs}.

bitpars2args([],[1|Ps],Ps)-->[].
bitpars2args([X|Xs],[0|Ps],NewPs)-->
  bitpars2term(X,Ps,[1|XPs]),
  bitpars2args(Xs,XPs,NewPs).  

term2inj_code(T,N,As):-
  term2bitpars(T,Ps,As),
  from_bbase(2,Ps,N).

inj_code2term(N,As,T):-
  to_bbase(2,N,Ps),
  bitpars2term(Ps,As,T).

cons(X,Y, Z):-Z is ((Y<<1)+1)<<X.

decons(Z, X,Y):-Z>0, X is lsb(Z), Y is Z>>(X+1).

nat2nats(0,[]).
nat2nats(N,Ns):-N>0,
  decons(N,L1,N1),
  L is L1+1,
  to_tuple(L,N1,Ns).

nats2nat([],0).
nats2nat(Ns,N):-
  length(Ns,L),
  L1 is L-1,
  from_tuple(Ns,N1),
  cons(L1,N1,N).

pars2nat(Xs,T):-pars2nat(0,1,T,Xs,[]).

pars2nat(L,R,N) --> [L],pars2nats(L,R,Xs),{nats2nat(Xs,N)}.

pars2nats(_,R,[]) --> [R].
pars2nats(L,R,[X|Xs])-->pars2nat(L,R,X),pars2nats(L,R,Xs).

nat2pars(N,Xs):-nat2pars(0,1,N,Xs,[]).

nat2pars(L,R,N) --> {nat2nats(N,Xs)},[L],nats2pars(L,R,Xs).

nats2pars(_,R,[]) --> [R].
nats2pars(L,R,[X|Xs])-->nat2pars(L,R,X),nats2pars(L,R,Xs).

term2code(T,N,As):-
  term2bitpars(T,Ps,As),
  pars2nat(Ps,N).

code2term(N,As,T):-
  nat2pars(N,Ps),
  bitpars2term(Ps,As,T).

