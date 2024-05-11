#!/bin/sh

set -e

function checkChangelog() {
  if [[ ! -f ./CHANGELOG.md ]]; then
    echo 'No Changelog.md file found creating new changelog'
    touch CHANGELOG.md
  fi
}

function addLastTagToChangeLog() {
  echo $(git tag -l -n1 $(git describe --tags --abbrev=0)) | cat - CHANGELOG.md > temp && mv temp CHANGELOG.md
}

function execute() {
    checkChangelog
    addLastTagToChangeLog
}

execute



