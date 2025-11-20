# authService

This small service validates user credentials by calling `userService` and returns a JWT token.

Defaults (can be changed in `src/main/resources/application.properties`):

- `server.port=8085`
- `userservice.url=http://localhost:8083`
- `jwt.secret=verySecretChangeMe`
- `jwt.expiration-ms=3600000`

Run (from `authService` folder):

```powershell
mvnw.cmd spring-boot:run
```

Example login request (PowerShell):

```powershell
$body = @{ userName = 'alice'; password = 'password' } | ConvertTo-Json
Invoke-RestMethod -Method Post -Uri http://localhost:8085/api/auth/login -Body $body -ContentType 'application/json'
```

What it does:

- Calls `POST {userservice.url}/api/internal/users/validate-login` with the login payload.
- If valid, returns `{ token: "<jwt>" , tokenType: "Bearer" }`.
