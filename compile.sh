rm -r -f bin
mkdir bin
$HOME/scala/bin/scalac -classpath "$HOME/akka/lib/akka-actor-2.0.1.jar" -optimize -explaintypes -deprecation -unchecked -feature -Yinline-warnings -d bin  src/prolog/*.scala src/prolog/*/*.scala
