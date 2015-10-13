#
# Jetty GCloudDatastore Session Manager module
#

[depend]
annotations
webapp

[files]

maven://com.google.gcloud/gcloud-java-datastore/0.0.7|lib/gcloud/gcloud-java-datastore-0.0.7.jar
maven://com.google.gcloud/gcloud-java-core/0.0.7|lib/gcloud/gcloud-java-core-0.0.7.jar
maven://com.google.auth/google-auth-library-credentials/0.1.0|lib/gcloud/google-auth-library-credentials-0.1.0.jar
maven://com.google.auth/google-auth-library-oauth2-http/0.1.0|lib/gcloud/google-auth-library-oauth2-http-0.1.0.jar
maven://com.google.http-client/google-http-client-jackson2/1.19.0|lib/gcloud/google-http-client-jackson2-1.19.0.jar
maven://com.fasterxml.jackson.core/jackson-core/2.1.3|lib/gcloud/jackson-core-2.1.3.jar
maven://com.google.http-client/google-http-client/1.20.0|lib/gcloud/google-http-client-1.20.0.jar
maven://com.google.code.findbugs/jsr305/1.3.9|lib/gcloud/jsr305-1.3.9.jar
maven://org.apache.httpcomponents/httpclient/4.0.1|lib/gcloud/httpclient-4.0.1.jar
maven://org.apache.httpcomponents/httpcore/4.0.1|lib/gcloud/httpcore-4.0.1.jar
maven://commons-logging/commons-logging/1.1.1|lib/gcloud/commons-logging-1.1.1.jar
maven://commons-codec/commons-codec/1.3|lib/gcloud/commons-codec-1.3.jar
maven://com.google.oauth-client/google-oauth-client/1.20.0|lib/gcloud//google-oauth-client-1.20.0.jar
maven://com.google.guava/guava/18.0|lib/gcloud/guava-18.0.jar
maven://com.google.api-client/google-api-client-appengine/1.20.0|lib/gcloud/google-api-client-appengine-1.20.0.jar
maven://com.google.oauth-client/google-oauth-client-appengine/1.20.0|lib/gcloud/google-oauth-client-appengine-1.20.0.jar
maven://com.google.oauth-client/google-oauth-client-servlet/1.20.0|lib/gcloud/google-oauth-client-servlet-1.20.0.jar
maven://com.google.http-client/google-http-client-jdo/1.20.0|lib/gcloud/google-http-client-jdo-1.20.0.jar
maven://com.google.api-client/google-api-client-servlet/1.20.0|lib/gcloud/google-api-client-servlet-1.20.0.jar
maven://javax.jdo/jdo2-api/2.3-eb|lib/gcloud/jdo2-api-2.3-eb.jar
maven://javax.transaction/transaction-api/1.1|lib/gcloud/transaction-api-1.1.jar
maven://com.google.http-client/google-http-client-appengine/1.20.0|lib/gcloud/google-http-client-appengine-1.20.0.jar
maven://com.google.http-client/google-http-client-jackson/1.20.0|lib/gcloud/google-http-client-jackson-1.20.0.jar
maven://org.codehaus.jackson/jackson-core-asl/1.9.11|lib/gcloud/jackson-core-asl-1.9.11.jar
maven://joda-time/joda-time/2.8.2|lib/gcloud/joda-time-2.8.2.jar
maven://org.json/json/20090211|lib/gcloud/json-20090211.jar
maven://com.google.apis/google-api-services-datastore-protobuf/v1beta2-rev1-2.1.2|lib/gcloud/google-api-services-datastore-protobuf-v1beta2-rev1-2.1.2.jar
maven://com.google.protobuf/protobuf-java/2.5.0|lib/gcloud/protobuf-java-2.5.0.jar
maven://com.google.http-client/google-http-client-protobuf/1.15.0-rc|lib/gcloud/google-http-client-protobuf-1.15.0-rc.jar
maven://com.google.api-client/google-api-client/1.15.0-rc|lib/gcloud/google-api-client-1.15.0-rc.jar
maven://com.google.apis/google-api-services-datastore/v1beta2-rev23-1.19.0|lib/gcloud/google-api-services-datastore-v1beta2-rev23-1.19.0.jar

[lib]
lib/gcloud-session-manager-${jetty.version}.jar
lib/gcloud/*.jar

[xml]
etc/jetty-gcloud-sessions.xml

[license]
GCloudDatastore is an open source project hosted on Github and released under the Apache 2.0 license.
https://github.com/GoogleCloudPlatform/gcloud-java
http://www.apache.org/licenses/LICENSE-2.0.html

[ini-template]
## GCloudDatastore Session config

## Unique identifier for this node in the cluster
# jetty.gcloudSession.workerName=node1

## Name of properties files containing gcloud config
#jetty.gcloudSession.configFilet=etc/gcloud.props

##Alternative to properties file, individual properties
##  the gcloud projectId
#jetty.gcloudSession.projectId=

##  the p12 file associated with the project
#jetty.gcloudSession.p12File=

##  the serviceAccount for the Datastore
#jetty.gcloudSession.serviceAccount=

##  the password (can be obfuscated)
#jetty.gcloudSession.password=
