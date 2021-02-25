#!/bin/bash

cd XmemeBackend
mvn clean package
cd target
java -jar XmemeBackend.jar