
Example using https://www.keycloak.org/docs/latest/authorization_services/#_enforcer_overview

* App A
~~~
export access_token=$(curl --insecure -X POST http://localhost:8080/auth/realms/quarkus/protocol/openid-connect/token --user backend-service-a:rheajQMedhNOAWDkxLeXuclU3CRfwUX7 -H 'content-type: application/x-www-form-urlencoded' -d 'username=usera&password=secret&grant_type=password' | jq --raw-output '.access_token')

curl -v -X GET http://localhost:8081/api/hello -H "Authorization: Bearer "$access_token
curl -v -X GET http://localhost:8081/api/me -H "Authorization: Bearer "$access_token
~~~

* App B
~~~
export access_token=$(curl --insecure -X POST http://localhost:8080/auth/realms/quarkus/protocol/openid-connect/token --user backend-service-b:sRRQYhBnnBMQGXIOPbFQmiz4jxUsd8aZ -H 'content-type: application/x-www-form-urlencoded' -d 'username=userb&password=secret&grant_type=password' | jq --raw-output '.access_token')

curl -v -X GET http://localhost:8084/api/hello -H "Authorization: Bearer "$access_token
curl -v -X GET http://localhost:8084/api/me -H "Authorization: Bearer "$access_token
~~~

* App C
~~~
export access_token=$(curl --insecure -X POST http://localhost:8080/auth/realms/quarkus/protocol/openid-connect/token --user backend-service-c:1qFKMNLOpIDfP3BpHOS1sWgu1UYZ6Cti -H 'content-type: application/x-www-form-urlencoded' -d 'username=userc&password=secret&grant_type=password' | jq --raw-output '.access_token')

curl -v -X GET http://localhost:8083/api/hello -H "Authorization: Bearer "$access_token
curl -v -X GET http://localhost:8083/api/me -H "Authorization: Bearer "$access_token
~~~