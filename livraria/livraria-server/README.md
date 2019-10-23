RESULT='curl \
  -d "client_id=swarm-livraria" \
  -d "username=pedro-hos" \
  -d "password=Q1w2e3r4@" \
  -d "grant_type=password" \
  "http://localhost:8180/auth/realms/master/protocol/openid-connect/token"'
  
RESULT=`curl --data "grant_type=password&client_id=swarm-livraria&username=pedro-hos&password=Q1w2e3r4@" http://localhost:8180/auth/realms/master/protocol/openid-connect/token`

TOKEN=`echo $RESULT | sed 's/.*access_token":"//g' | sed 's/".*//g'`

curl -H "Content-Type: application/json" -H "Authorization: bearer $TOKEN" -X POST -d '{"nome":"Pedro"}' localhost:8080/autores