mkdir bin
pushd .
cd src
scalac -deprecation -optimize -d ../bin prolog/*.scala prolog/terms/* prolog/fluents/*.scala  prolog/io/*.scala prolog/builtins/*.scala prolog/*.scala
popd
