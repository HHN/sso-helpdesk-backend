# Issue Reporting

Please report any issue here: [https://github.com/HHN/sso-helpdesk](https://github.com/HHN/sso-helpdesk)

# Local Development
## Keycloak 
### Installation
1. [Install docker](https://docs.docker.com/engine/install/)
2. [Start local keycloak as docker container](https://www.keycloak.org/getting-started/getting-started-docker)
   - Version should match the `keycloak.version` inside [pom.xml](pom.xml)
   - Be sure **not** to use port 8080
   
```bash
docker run --name keycloak-local -p 8888:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:22.0.4 start-dev
```

### Configuration
1. Go to http://localhost:8888/admin/, login with `admin:admin` and create the following realms
   - `helpdesk` 
   - `institution`

#### Realm Configuration: `institution`
1. Create the following users
   - Required user actions: `None`
   - Username: `example_user1`, `example_user2`, `...`
   - Email: `example_user1@test.de`, `...`
   - Email verified: `True`
   - First and Last name: `User1 Example`, `...`

#### Realm Configuration: `helpdesk`
1. Create a new realm role `HHN_HELPDESK_ADMIN`
2. Create new OpenID Connect client for the helpdesk realm
   - Client ID: `helpdesk_user`
   - Name: `Helpdesk User Client`
   - Client authentication: `True`
   - Authorization: `False`
   - Authentication Flow: Check only `Standard Flow` and turn everything else off 
   - Valid redirect URIs: `http://localhost:3000/login/oauth2/code/keycloak`
   - Leave everything else blank
3. Go to Credentials and copy client secret into [application.properties](src/main/resources/application.properties) as value for `spring.security.oauth2.client.registration.keycloak.client-secret`
4. Go to client scopes, choose `helpdesk_user-dedicated`, and configure a new predefined mapper from type "realm roles"
   - Add to ID token: `False`
   - Add to access token: `False`
   - Add to userinfo: `True`
5. Create user `example_admin`, assign the role `HHN_HELPDESK_ADMIN` in the Role mapping tab (after the creation), and set the password to `example_admin` by pressing "Set password" in the credentials tab (disable "Temporary password")

#### Realm Configuration: `master`

1. Create new client in the with the following configuration:
   - Client ID: `helpdesk_admin` 
   - Name: `Helpdesk Admin Client`
   - Client authentication: `True`
   - Authorization: `False`
   - Authentication Flow: Check only `Standard Flow` **and** `Service accounts roles`. Turn everything else off
   - Leave Root URL, Home URL, Valid redirect URIs, Web origins, and everything else empty or with the standard values
2. Go to Credentials and copy client secret into [application.properties](src/main/resources/application.properties) as value for `hhn.keycloak.admin.client.secret`
3. Go to Service accounts roles and press "Assign roles". Choose "Filter by clients" and assign the following:
     - `(institution-realm) manage-users`
     - `(institution-realm) query-users`
     - `(institution-realm) view-users`
4. Go to Client scopes, choose `helpdesk_admin-dedicated`, and verify that Client ID, Client Host, and Client IP Address token mappers exist

## PostgreSQL Database 
1. [Install postgres](https://www.postgresql.org/download/) (or use the official [docker container](https://hub.docker.com/_/postgres))
2. Create database `helpdesk`
3. If you do not use postgres:postgres as default login for your local PostgreSQL installation, change the parameters inside [application.properties](src/main/resources/application.properties)

## Run backend 

### Via Maven 

```bash
mvn clean package
mvn spring-boot:run
```

### Via IDE

Just start `de.hhn.rz.HelpDeskBackend`

## Run frontend with npm
To run the frontend please refer to the [frontend README](../sso-helpdesk-frontend/README.md)

# Troubleshooting
> Q: After authenticating the frontend keeps reloading the page  
> A: Be sure that your administrator account (e.g. example_admin) got the HHN_HELPDESK_ADMIN role assigned inside keycloak.
