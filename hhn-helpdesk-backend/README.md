# Local Development
## Initial steps (only needed once)
### Keycloak Docker Container
1. [Install docker](https://docs.docker.com/engine/install/)
2. [Start local keycloak as docker container](https://www.keycloak.org/getting-started/getting-started-docker)
   - Version should match the 
   - Change version as needed and be sure to **not** use port 8080)
```bash
docker run --name keycloak-local -p 8888:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:22.0.4 start-dev
```
3. Create new client in master realm with the following configuration:
   - `Client ID: helpdesk_admin` 
   - `Client authentication: True`
   - `Service accounts roles: True`
   - Leave `Root URL`, `Home URL`, `Valid redirect URIs`, `Web origins`, and everything else empty or with the standard 
   - Go to `Credentials` and copy `Client secret` into `application.properties` --> `hhn.keycloak.admin.client.secret`
   - Go to `Service accounts roles` and press 'Assign roles' --> Filter by clients --> Assign the following:
     - `(institution-realm) manage-users`
     - `(institution-realm) query-users`
     - `(institution-realm) view-users` 
   - Go to `Client scopes` --> `helpdesk_admin-dedicated` and verify that Client ID, Client Host, and Client IP Address token mappers exist 
5. Create realm `helpdesk` and `institution`
6. Create role `HHN_HELPDESK_ADMIN` in `helpdesk` realm
7. Create new client in `helpdesk` realm
   - `Client ID: helpdesk_user`
   - `Client authentication: True`
   - `Valid redirect URIs: http://localhost:3000/login/oauth2/code/keycloak`
   - Copy `Client secret` from the Credentials Tab into `application.properties` --> `keycloak.credentials.secret`
8. Go to `Client scopes` --> `helpdesk_user-dedicated` and configure a new predefined mapper from type "realm roles"
   - `Name: helpdesk-realm-role-mapper`
   - `Add to ID token: False`
   - `Add to access token: False`
   - `Add to userinfo: True`
9. Create user `example_admin` in realm `helpdesk`, assign the role `HHN_HELPDESK_ADMIN` and set the password to `example_admin`
10. Create users `example_user1` and `example_user2` in `institution` realm

### PostgreSQL Database 
1. Install postgres (or use the official docker container)
2. Create database `helpdesk`
3. If you do not use postgres:postgres as default login for your local PostgreSQL installation, change the the parameters inside `application.properties`

### Run first build with maven 
```bash
mvn clean package
mvn spring-boot:run
```
