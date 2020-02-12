#!/usr/bin/env bash

## Get authentication token
KEYCLOAK_URL=http://localhost:8080/auth/realms/realm-tiw1-tp3/protocol/openid-connect/token
KEYCLOAK_CLIENT=tp3-client
KEYCLOAK_USERNAME=tp3-admin
KEYCLOAK_PASSWORD=password
echo ${KEYCLOAK_URL}
KEYCLOAK_TOKEN=$(curl -d "client_id=${KEYCLOAK_CLIENT}&username=${KEYCLOAK_USERNAME}&password=${KEYCLOAK_PASSWORD}&grant_type=password" -H "Content-Type: application/x-www-form-urlencoded" -X POST ${KEYCLOAK_URL} | jq -r ".access_token")
echo ${KEYCLOAK_TOKEN}

## Requesting emprunt
EMPRUNTS_URL=http://localhost:8083/emprunts
curl -v --oauth2-bearer ${KEYCLOAK_TOKEN} ${EMPRUNTS_URL}
