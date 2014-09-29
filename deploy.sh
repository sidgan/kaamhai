#!/bin/sh

echo 'building'
mvn clean install


echo 'deploying war in tomcat'
cp kaamhai-webapp/target/*SNAPSHOT.war kaamhai-webapp/target/test
rm -f kaamhai-webapp/target/kaamhai.war
mv kaamhai-webapp/target/test kaamhai-webapp/target/kaamhai.war
rm -rf ${TOMCAT_HOME}/webapps/kaamhai*
cp kaamhai-webapp/target/kaamhai.war ${TOMCAT_HOME}/webapps/kaamhai.war
