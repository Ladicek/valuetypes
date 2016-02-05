#!/bin/bash

JDK=/home/lthon/projects/openjdk/valhalla/build/linux-x86_64-normal-server-release/images/jdk

cd $(dirname $(readlink -f $0))

rm -rf out/
mkdir out/

$JDK/bin/javac -XDGenerateValueAsReference -d out/ ${1/.java/}.java
$JDK/bin/java -cp out ${1/.java/}

