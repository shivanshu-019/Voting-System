#!/bin/sh
mkdir -p out
javac -encoding UTF-8 -cp "lib/*" -d out src/*.java || exit 1
java -cp "out:lib/*" Main
