#!/bin/sh

set -e

# GIT_LAB_BASE="https://api.github.com/"
# GIT_LAB_USER="snowball-leixia"
# GIT_LAB_PASSWORD="Ib1818216841?@"
# BLACKLIST="black.txt"

# BLACK_ITEMS=()

# function loadWhiteList() {
#     echo "Loading white list ..."
#     while IFS= read -r line; do
#         BLACK_ITEMS+=(line)
#     done < "$BLACKLIST"
#     echo "Loaded total ${#BLACK_ITEMS[@]} tickets to skip!"
#     sleep 3
# }

# function testChangelog() {
#   if [[ ! -f ./CHANGELOG.txt ]]; then
#     touch CHANGELOG.txt
#   fi
# }

# function getChangelog() {
#   local currentTag previousTag prevChangelogContents
#   # currentTag=$(git describe --abbrev=0 --tags "$(git describe --abbrev=0)"^)
#   previousTag=$(git describe --abbrev=0)
#   prevChangelogContents=$(cat ./CHANGELOG.txt)

#   {
#     echo "## $currentTag";
#     echo "";
#     git log-short --no-merges "$currentTag...$previousTag" | grep -v "Upgrade to";
#     echo "";
#   } > CHANGELOG.txt
#   echo "$prevChangelogContents" >> CHANGELOG.txt
# }

# testChangelog
# getChangelog

# echo "============= Release Manifest Wizard =============="

# read -p "Enter repository name e.g. mma-movement-data-service: " REPO_NAME

# read -p "Enter release ${REPO_NAME} tag version e.g. 1.0.0: " TAG_VER

# loadWhiteList

# echo "Fetching ${REPO_NAME}:${TAG_VER} details from gitlab please wait..."

# sleep 5
# RESPONSE=$(curl -L \
#   -H "Accept: application/vnd.github+json" \
#   -H "Authorization: Bearer ${GITLAB_PRIVATE_TOKEN}" \
#   -H "X-GitHub-Api-Version: 2022-11-28" \
#   https://api.github.com/repos/${GIT_LAB_USER}/${REPO_NAME}/git/refs/tags)
  
# URL=$(jq -r '.[].url' <<< "${RESPONSE}")

# echo "Release Tag url: ${URL}"

# sleep 5

# RESPONSE=$(curl -v -L \
#   -H "Accept: application/vnd.github+json" \
#   -H "Authorization: Bearer ${GITLAB_PRIVATE_TOKEN}" \
#   -H "X-GitHub-Api-Version: 2022-11-28" \
#   https://api.github.com/repos/${GIT_LAB_USER}/${REPO_NAME}/compare/${TAG_VER}...head)


#!/bin/bash
## Author LinkinStar

# solve the space by IFS
IFS=`echo -en "\n\b"`
echo -en $IFS

if [ -f "CHANGELOG.md" ];then
    rm -f CHANGELOG.md
    touch CHANGELOG.md
else
    touch CHANGELOG.md
fi

function printFeat(){  
    for i in ${feat[@]}
    do
        echo "- "$i >> CHANGELOG.md
    done
    echo >> CHANGELOG.md
}

function printFix(){  
    for i in ${fix[@]}
    do
        echo "- "$i >> CHANGELOG.md
    done
    echo >> CHANGELOG.md
}

function printOther(){  
    for i in ${other[@]}
    do
        echo "- "$i >> CHANGELOG.md
    done
    echo >> CHANGELOG.md
}

function checkLog(){
    if [[ $1 == "feat"* ]]
    then
        feat[featIndex]=$1
        let featIndex++
    elif [[ $1 == "fix"* ]]
    then
        fix[fixIndex]=$1
        let fixIndex++
    else
        other[otherIndex]=$1
        let otherIndex++
    fi 
}

function printLog(){
    if [[ $featIndex -ne 0 ]]; then
        echo "### Features" >> CHANGELOG.md
        printFeat
    fi
    
    if [[ $fixIndex -ne 0 ]]; then
        echo "### Bug Fixes" >> CHANGELOG.md
        printFix
    fi
    
    if [[ $otherIndex -ne 0 ]]; then
        echo "### Other Changes" >> CHANGELOG.md
        printOther
    fi
    
    feat=()
    featIndex=0
    
    fix=()
    fixIndex=0
    
    other=()
    otherIndex=0
}

curDate=""
function checkDate()
{
    if [[ $curDate = $1 ]]; then
        return
    fi
    curDate=$1
    
    printLog
    
    echo >> CHANGELOG.md
    echo "## "$curDate >> CHANGELOG.md
}

commitMessageList=`git log --date=format:'%Y-%m-%d' --pretty=format:'%cd%n%s'`

index=0

for i in ${commitMessageList[@]}
do
    if [[ $index%2 -eq 0 ]]
    then
        checkDate $i
    else
        #echo "- "$i >> CHANGELOG.md
        checkLog $i
    fi

    let index++
done

printLog

