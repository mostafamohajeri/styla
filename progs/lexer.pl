number_token(Codes,number(N)):-
  number(Codes,[]),
  number_codes(N,Codes).

number --> float.
number --> int.

float --> digits,".",digits.
int --> digits.

digits --> digit.
digits --> digit,digits.

% digit --> "0";"1";"2";"3".

digit --> [X],{member(X,"0123456789")}.

% digit --> [X],{"09"=[Zero,Nine],X>=Zero,X=<Nine}.
