#!/bin/bash
if ! [ -x "$(which mvn)" ]; then
          echo 'Error: maven is not installed.' >&2
          exit 1
fi
mvn clean install
java -jar ./target/messenger-0.0.1.jar