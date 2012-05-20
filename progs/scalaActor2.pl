handle(Msg):-
  get_last_sender(Sender),
  !,
  call(Msg),
  scala_actor_send(Sender,println(thank_you2(Sender))).
handle(Msg):-
  call(Msg).
     
 