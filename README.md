# employees-api

Servicio REST de **Empleados** implementado con **Spring Boot 3.2**, **Java 21**, **Arquitectura Hexagonal**, **JPA**, **Flyway** y **Oracle**. Expone operaciones CRUD y búsqueda, documentadas con **OpenAPI** y protegidas con **JWT (OAuth2 Resource Server)**.

---

## 🚀 Requisitos

- **Java 21** (Temurin/Oracle/OpenJDK)
- **Maven 3.9+**
- **Oracle XE / Oracle DB** en ejecución
  - Ejemplo: `localhost:1521/XEPDB1`
  - Usuario: `APP`, Password: `App_1234`
- (Opcional) **Docker** para levantar Oracle XE

---

## ⚙️ Configuración

El perfil por defecto es `oracle`.  
Configura la conexión y el secreto JWT en `application-oracle.yml`.

```yaml
spring:
  datasource:
    url: jdbc:oracle:thin:@//localhost:1521/XEPDB1
    username: APP
    password: App_1234
    driver-class-name: oracle.jdbc.OracleDriver

  security:
    jwt:
      secret: "dev-secret-32bytes-minimo-para-hmac........"

  flyway:
    enabled: true
    url: jdbc:oracle:thin:@localhost:1521/XEPDB1
    user: APP
    password: App_1234
    locations: classpath:db/migration
    baseline-on-migrate: true
    validate-on-migrate: true
    clean-disabled: true
    baseline-version: 0

  jpa:
    hibernate:
      ddl-auto: validate
    properties:
      hibernate:
        dialect: org.hibernate.dialect.OracleDialect
        default_schema: APP
```

Variables de entorno soportadas:

```bash
SPRING_PROFILES_ACTIVE=oracle
SECURITY_JWT_SECRET=dev-secret-32bytes-minimo-para-hmac........
```

---

## 🗄️ Base de datos y migraciones

Las migraciones Flyway están en `src/main/resources/db/migration`:

- `V1__create_employee.sql`
- `V2__seed_employees.sql`

Se ejecutan automáticamente al arrancar.

---

## ▶️ Ejecución

### Compilar

```bash
mvn clean package
```

### Ejecutar con perfil Oracle

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=oracle
```

O bien:

```bash
SPRING_PROFILES_ACTIVE=oracle java -jar target/employees-api.jar
```

---

## 📡 Endpoints principales

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`
- **Actuator Health**: `http://localhost:8080/actuator/health`

---

## 🔐 Seguridad

El API requiere **JWT Bearer Token** (HS256) con scopes:

- Lectura → `employees.read`
- Escritura → `employees.write`

Ejemplo de payload para generar un token en [jwt.io](https://jwt.io):

```json
{
  "iss": "employees-api-local",
  "sub": "local-user",
  "scope": "employees.read employees.write",
  "iat": 1710000000,
  "exp": 1890000000,
  "jti": "demo-token"
}
```

---

## 🛠️ Ejemplos de uso

### cURL

```bash
TOKEN=<tu-jwt>
curl -H "Authorization: Bearer $TOKEN" http://localhost:8080/employees
```

### Endpoints REST

- `GET /employees` → Lista de empleados
- `GET /employees/{id}` → Detalle
- `POST /employees` → Crear (lista de empleados)
- `PUT /employees/{id}` → Actualizar
- `PATCH /employees/{id}` → Actualización parcial
- `DELETE /employees/{id}` → Eliminar
- `GET /employees/search?name={texto}` → Buscar por nombre

---

## ✅ Pruebas

El proyecto incluye **JUnit 5**, **Mockito**, **Spring Security Test** y **WebMvcTest**.  

### Ejecutar pruebas

```bash
mvn test
```

### Reporte de cobertura (JaCoCo)

```bash
mvn test jacoco:report
open target/site/jacoco/index.html
```

---

## 🗂️ Estructura del proyecto

```
src
 ├─ main/java/com/demo/employees
 │   ├─ business/...        # puertos y casos de uso
 │   ├─ domain/...          # entidades y excepciones
 │   ├─ infrastructure
 │   │   ├─ adapters
 │   │   │   ├─ inbound/...  # DTOs, mappers, command handlers
 │   │   │   └─ outbound/... # repositorios JPA
 │   │   ├─ interfaces/rest  # controladores y exception handler
 │   │   └─ config/...       # seguridad y configuración
 │   └─ Application.java
 └─ test/java/com/demo/employees/unit/tests/... # Unit tests
```

---

## ⚠️ Problemas comunes

- `Could not resolve placeholder 'security.jwt.secret'` → Falta definir `SECURITY_JWT_SECRET`.
- `401 Unauthorized` → Token inválido o ausente.
- `403 Forbidden` → Token válido pero sin scopes necesarios.
- `400 Bad Request` → Validación fallida (campos obligatorios o formato de fecha incorrecto).
- Error con Oracle → Verifica `jdbc:oracle:thin:@//host:1521/SERVICE` y credenciales.

---

## 📦 Build

```bash
mvn clean package
# genera target/employees-api.jar
```

---

## 📄 Licencia

Proyecto de ejemplo para fines educativos y de referencia. Ajusta a tu licencia corporativa si aplica.
