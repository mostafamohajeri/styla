Styla - a Prolog in Scala,

The name STYLA stands tentatively for "Scala Terms Yield Logic Agents" but hints
toward the fact that style (seen as "elegance" of implementation) has been a major
design principle behind it.

Styla is a fairly complete Prolog interpreter written in Scala, derived from Kernel Prolog 
(see Fluents: A Refactoring of Prolog for Uniform Reflection and 
Interoperation with External Objects CL'2000).

Sample programs are in directory progs.

Try bm, bm1, bm2 shell scripts for benchmarking.

Among the features not found in most Prologs, first class Logic Engines - 
and a generic view of everything as Fluents (TermSinks and TermSources)
that abstract away iteration over various data types, including answers
produced by Logic Engines.

Here are some limitations, most things not on this list
and expected from a Prolog system should work.

- toplevel goals that a not conjunctions should be parenthesized
- operators are all fixed and xfx, but they match the default priorities of
  most Prologs' operators - see examples in progs/
- no gui or networking - Scala can do all that better

Take a look at prolog.Main for the start-up sequence - that gives
a glimpse of how to embed it into a Scala or Java program.

To add a new built-ins, just clone the closest match 
in prolog.builtins and drop it in the same directory - the
runtime system it will instantly recognize it.

Use the script "compile" to recompile the system with scalac
"run" to run it with scala and jrun to run it with java (possibly
edit for path info in this case).

The file prolog/fluents/Lib.scala embeds Prolog code that is present at
start-up. New Prolog code that you want part of the default libraries
can be added there.

Enjoy,

Paul Tarau


