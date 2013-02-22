pushd .
mkdir bin
cd src
%HOMEPATH%\scala\bin\scalac -cp ".;..\bin;%HOMEPATH%\akka\lib\akka-actor-2.0.1.jar" -optimize -explaintypes -deprecation -unchecked -feature -Yinline-warnings -d ..\bin prolog\*.scala prolog\interp\*.scala prolog\terms\*.scala prolog\fluents\*.scala prolog\io\*.scala prolog\builtins\*.scala prolog\acts\*.scala
echo "DONE"
popd
