#!/usr/bin/env bash

exec &> out.txt

set -x

clojure --version

clojure -Srepro -M script.clj
