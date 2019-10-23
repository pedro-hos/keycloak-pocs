* Busca Chave para app de estoque:

```
export access_token=$(curl -X POST http://localhost:8180/auth/realms/master/protocol/openid-connect/token -H 'content-type: application/x-www-form-urlencoded' -d 'username=pedro&password=123&grant_type=password&client_id=estoque-app' | jq --raw-output '.access_token')
```
* Remove livro do estoque:

```
curl -v -X PUT localhost:8080/api/estoque/livro/remover/ABC100/quantidade/1 -H "Authorization: Bearer "$access_token
```

* Busca Chave para app de livraria:

```
export access_token=$(curl -X POST http://localhost:8180/auth/realms/master/protocol/openid-connect/token -H 'content-type: application/x-www-form-urlencoded' -d 'username=pedro&password=123&grant_type=password&client_id=livraria-app' | jq --raw-output '.access_token')
```

* Remove livro pela livraria:

```
curl -v -X PUT localhost:8580/api/livraria/livro/ABC100/vender/2 -H "Authorization: Bearer "$access_token
```

```
curl -v -X GET localhost:8580/api/livraria/vendas -H "Authorization: Bearer "$access_token
```
