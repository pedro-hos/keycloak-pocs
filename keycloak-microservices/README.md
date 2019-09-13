export access_token=$(\
    curl -X POST http://localhost:8180/auth/realms/quarkus/protocol/openid-connect/token \
        --user quarkus-app:secret \
            -H 'content-type: application/x-www-form-urlencoded' \
                -d 'username=user1&password=1234&grant_type=password' | jq --raw-output '.access_token' \

                 )
