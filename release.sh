#!/bin/sh

set -e

SEMVER_REGEX="^(v)?(0|[1-9][0-9]*)\\.(0|[1-9][0-9]*)\\.(0|[1-9][0-9]*)(\\-[0-9A-Za-z-]+(\\.[0-9A-Za-z-]+)*)?(\\+[0-9A-Za-z-]+(\\.[0-9A-Za-z-]+)*)?$"

function checkChangelog() {
  if [[ ! -f ./CHANGELOG.md ]]; then
    echo 'No Changelog.md file found creating new changelog'
    touch CHANGELOG.md
  fi
}

function addLastTagToChangeLog() {
  echo $(git for-each-ref --format '%(refname:short): %(subject) | %(taggername) | [%(taggerdate)]' refs/tags/$(git describe --tags --abbrev=0)) | cat - CHANGELOG.md > temp && mv temp CHANGELOG.md 
  echo "Changelog updated successfully, please add and commit"
}

function tagWithAnnotation() {
  echo "Fetching tags from remote"
  git fetch --tags
  LATEST_TAG=$(git describe --tags --abbrev=0)
  echo "Latest tag = $LATEST_TAG"
  read -p "Please enter tag name: " NEW_TAG_VER
  validateTagVersion $NEW_TAG_VER
  read -p "Please enter tag annotation message: " ANN_MSG
  validateAnnotationMessage $ANN_MSG
  git tag $NEW_TAG_VER -m "$ANN_MSG"
  echo "Tagged: ${NEW_TAG_VER}"
}

function validateAnnotationMessage() {
  local msg=$1
  if [[ "${#msg}" = 0 ]]; then 
    echo "Annotation should not be empty to exceed 140 characters"
    exit 1
  fi    
}

function validateTagVersion() {
  local version=$1
  if [[ "$version" =~ $SEMVER_REGEX ]]; then
    if [ "$#" -eq "2" ]; then
      local major=${BASH_REMATCH[1]}
      local minor=${BASH_REMATCH[2]}
      local patch=${BASH_REMATCH[3]}
      local prere=${BASH_REMATCH[4]}
      local build=${BASH_REMATCH[5]}
      eval "$2=(\"$major\" \"$minor\" \"$patch\" \"$prere\" \"$build\")"
    else
      echo "$version"
    fi
  else
    echo "Version $version does not match the semver scheme 'X.Y.Z(-PRERELEASE)(+BUILD)'"
    exit 1
  fi
}
function execute() {
  echo "============= Release Manifest Wizard =============="
  echo "1) Update Changelog"
  echo "2) Tag from master"
  read -p ">> " OPTION
  if [[ $OPTION = "1" ]]; then
      updateChangeLog
  elif [[ $OPTION = "2" ]]; then
      tagWithAnnotation
  else 
    echo "Option $OPTION not supported"    
  fi
}

function updateChangeLog() {
    checkChangelog
    addLastTagToChangeLog
}

execute



