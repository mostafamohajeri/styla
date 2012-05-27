% nothing special about handle/1 - except that it happens to
% be called and sends back a message to sender
handle(Msg):-
  get_last_sender(Sender),
  !,
  call(Msg),
  actor_send(Sender,println(thank_you2(Sender))).
handle(Msg):-
  call(Msg).
     
 