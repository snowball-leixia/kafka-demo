#!/bin/sh

set -e

commits=$(git cherry -v main | tr -d '\n')
length=${#commits}-1
IFS='+' read -ra array <<< "${commits:1:$length}"
for line in "${array[@]}"; do
    echo $line;
done