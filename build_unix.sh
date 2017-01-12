#!/usr/bin/env bash

rm -r dist
mkdir -p dist
cp -a src/. dist
find dist -name "*.java" > sources.txt
javac @sources.txt