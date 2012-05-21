:-consult(a).

b(X):-a(X).

:-foreach(b(X),println(X)),println(done).

