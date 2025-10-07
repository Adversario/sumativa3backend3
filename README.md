# cloud-banking

Sistema de microservicios listo para nube usando Spring Cloud (Config, Eureka, Gateway), OAuth2.0 + JWT,
Resilience4j, Kafka y Docker Compose.

## Requisitos
- Java 17
- Maven 3.9+
- Docker + Docker Compose

## Pasos
```bash
mvn -q -DskipTests clean package
docker compose up --build
```

### Obtener token OAuth2 (client_credentials)
```bash
curl -u gateway-client:gateway-secret   -d "grant_type=client_credentials&scope=accounts.read"   http://localhost:9000/oauth2/token
```

### Usar token
```bash
TOKEN=<PEGAR_ACCESS_TOKEN>
curl -H "Authorization: Bearer $TOKEN" http://localhost:8080/api/accounts/acc-1
curl -H "Authorization: Bearer $TOKEN" http://localhost:8080/api/customers/c-100
```

### Probar Kafka
```bash
curl -X PUT -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json"   -d '{"name":"Jane Legacy","segment":"GOLD"}'   http://localhost:8080/api/customers/c-100
```

### Ver fallback (Resilience4j)
```bash
docker compose stop customers
curl -H "Authorization: Bearer $TOKEN" http://localhost:8080/api/accounts/acc-1
```

### Parar
```bash
docker compose down -v
```
