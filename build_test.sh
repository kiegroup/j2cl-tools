#!/usr/bin/env sh
# sh script to build maven sub modules

# build all modules
set -e
# read revision from the file
revision=$(cat revision.txt)

modules=(
      jakarta-spec
     )

for module in "${modules[@]}"
do
    echo "Building $module"
    cd $module
    mvn clean install -Drevision=$revision
    cd ..
    # stop on error
    if [ $? -ne 0 ]; then
        echo "Error building $module"
        exit 1
    fi

done
