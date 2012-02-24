Styla - a Prolog in Scala,

The name STYLA stands tentatively for "Scala Terms Yield Logic Agents" but hints
toward the fact that style (seen as "elegance" of implementation) has been a major
design principle behind it.

Styla is a fairly complete Prolog interpreter written in Scala, derived from 
Kernel Prolog (see Fluents: A Refactoring of Prolog for Uniform Reflection 
and Interoperation with External Objects CL'2000).

Just type "styla" to run the system from its precompiled
"styla.jar" file. 

If you do not have Scala installed, and just want to embed Styla in
a Java or Android application, just download the self-contained
JavaCallsStyla.zip file, unzip it and type "go" inside the resulting folder,
then customize JavaMain.java as needed. the same directory contains also
the scripts "jstyla" and "jstyla.bat" that can run the interactive 
Styla toplevel, even if you do not have Scala installed. 

Use the scripts 

"compile" to recompile the system with scalac
"run" to run it with scala and 
"jrun" to run it with java (possibly edit for path info in this case).

The file "prolog/fluents/Lib.scala" embeds Prolog code that is present at
start-up. New Prolog code that you want part of the default libraries
can be added there.

Sample programs are in directory "progs".

Try "bm", "bm1", "bm2" shell scripts for benchmarking.

Among the features not found in most Prologs, first class Logic Engines - 
and a generic view of everything as Fluents (TermSinks and TermSources)
that abstract away iteration over various data types, including answers
produced by Logic Engines.

Here are some limitations, most things not on this list
and expected from a Prolog system should work.

- toplevel goals that are not conjunctions should be parenthesized
- operators are all fixed and "xfx" associativity, but they match the
  default priorities of most Prologs' operators - see examples in "progs"
- no gui or networking - Scala can do all that better

Take a look at "prolog.Main" for the start-up sequence - that gives
a glimpse of how to embed it into a Scala or Java program.

To add a new built-in, just clone the closest match 
in prolog.builtins and drop it in the same directory - the
runtime system it will instantly recognize it.

Enjoy,

Paul Tarau

P.S. Styla uses a few interesting Scala goodies, not available in Java:

- higher order functions, maps, folds, case classes etc.
- combinator parsers - "poor man's DCGs" :-)
- Scala's elegant implicit conversions between
  lists, arrays, sequences etc.
- Scala's arbitrary length integers and decimals (with
  a natural syntax, in contrast to Java)
- Scala's """...""" strings - for regexps and to embed 
  Prolog code directly in Scala classes
- a few IO abstractions available in Scala that
  view things like file operations as iterators -
  a natural match to the original Fluents of
  Kernel Prolog from which Styla was derived
  
- a few IO abstractions available in Scala that
  view things like file operations as iterators -
  a natural match to the original Fluents of the
  Java-based Kernel Prolog from which Styla was derived

Note that he latest version of Kernel Prolog is now an 
open source Google project at:

http://code.google.com/p/kernel-prolog/
  
