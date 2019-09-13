

export access_token=$(\
    curl -X POST http://localhost:8180/auth/realms/quarkus/protocol/openid-connect/token \
    --user quarkus-app:secret \
    -H 'content-type: application/x-www-form-urlencoded' \
    -d 'username=user1&password=1234&grant_type=password' | jq --raw-output '.access_token' \

 )

export access_token=$(curl -X POST http://localhost:8180/auth/realms/master/protocol/openid-connect/token -H 'content-type: application/x-www-form-urlencoded' -d 'username=pedro&password=123&grant_type=password&client_id=estoque-app' | jq --raw-output '.access_token')

curl -v -X PUT localhost:8080/api/estoque/livro/remover/ABC100/quantidade/1 -H "Authorization: Bearer "$access_token

export access_token=$(curl -X POST http://localhost:8180/auth/realms/master/protocol/openid-connect/token -H 'content-type: application/x-www-form-urlencoded' -d 'username=pedro&password=123&grant_type=password&client_id=livraria-app' | jq --raw-output '.access_token')



curl -v -X PUT localhost:8580/api/livraria/livro/ABC100/vender/2 -H "Authorization: Bearer "$access_token
