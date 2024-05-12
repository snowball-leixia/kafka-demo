#!/bin/sh

set -e

function getCommitMessagesFromBranch() {
    commits=$(git cherry -v --abbrev main | tr -d '\n')
    length=${#commits}-1
    IFS='+' read -ra array <<< "${commits:1:$length}"
    for line in "${array[@]}"; do
        echo ${line:9:${#line}};
    done
}

getCommitMessagesFromBranch