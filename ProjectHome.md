A fairly complete and fast Prolog interpreter written is Scala, using Paul Tarau's Java-based Kernel Prolog as starting point (also available as an open source project at http://code.google.com/p/kernel-prolog/ ).

The name "Styla", besides being a rhyme to Scala :-) stands tentatively for "Scala Terms Yield Logic Agents" (a future development target), but hints toward the fact that style (seen as elegance and simplicity of implementation) has been a major design principle behind it.

Among the features not found in most Prologs, first class Logic Engines -
and a generic view of everything as Fluents (TermSinks and TermSources)
that abstract away iteration over various data types, including answers
produced by Logic Engines.

Here are some limitations, most things not on this list
and expected from a Prolog system should work.

> - toplevel goals that are not conjunctions should be parenthesized
> - operators are all fixed and "xfx" associativity, but they match the
> > default priorities of most Prologs' operators - see examples in "progs"

> - no gui or networking - Scala can do all that better

Take a look at "prolog.Main" for the start-up sequence - that gives
a glimpse of how to embed it into a Scala or Java program.

To add a new built-in, just clone the closest match
in prolog.builtins and drop it in the same directory - the
runtime system it will instantly recognize it.

Just type "styla" to run the system from its precompiled
"styla.jar" file.

Use the scripts

"compile" to recompile the system with scalac
"run" to run it with scala and
"jrun" to run it with java (possibly edit for path info in this case).

The file "prolog/fluents/Lib.scala" embeds Prolog code that is present at
start-up. New Prolog code that you want part of the default libraries
can be added there.

A new feature - interclausal variables has been added.