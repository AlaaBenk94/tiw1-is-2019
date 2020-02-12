#!/usr/bin/env bash

echo "**************************************************************************************"
echo "*************************** keycloak initialisation script ***************************"
echo "**************************************************************************************"


RET=$(curl -o /dev/null -s -w "%{http_code}\n" http://localhost:8080)
while [[ ${RET} != "200" ]]; do
	echo "waiting for keycloak to be ready"
	sleep 10
	RET=$(curl -o /dev/null -s -w "%{http_code}\n" http://localhost:8080)
done
echo "keycloak service is ready"

# env variables
TP_REALM=realm-tiw1-tp3
TP_CLIENT=tp3-client
TP_USER_ROLE=user
TP_ADMIN_ROLE=admin
TP_USER=tp3-user
TP_ADMIN=tp3-admin
TP_PASSWORD=password

# loggin
echo "logging to keycloak server >>>"
/opt/jboss/keycloak/bin/kcadm.sh config credentials --server http://localhost:8080/auth --realm master --user ${KEYCLOAK_USER} --password ${KEYCLOAK_PASSWORD} && echo "<<< logged"

# creating realm
echo "creating realm >>>"
/opt/jboss/keycloak/bin/kcadm.sh create realms -s realm=${TP_REALM} -s enabled=true && echo "<<< done"

# creating client
echo "creating client >>>"
/opt/jboss/keycloak/bin/kcadm.sh create clients -r ${TP_REALM} -s clientId=${TP_CLIENT} -s publicClient=true -s directAccessGrantsEnabled=true -s 'webOrigins=[]' -s 'redirectUris=["http://localhost:8080/*"]' -i && echo "<<< done"

# creating roles
echo "creating roles >>>" 
/opt/jboss/keycloak/bin/kcadm.sh create roles -r ${TP_REALM} -s name=${TP_USER_ROLE}
/opt/jboss/keycloak/bin/kcadm.sh create roles -r ${TP_REALM} -s name=${TP_ADMIN_ROLE} && echo "<<< done"

# creating 
echo "creating users >>>"
/opt/jboss/keycloak/bin/kcadm.sh create users -r ${TP_REALM} -s username=${TP_ADMIN} -s enabled=true
/opt/jboss/keycloak/bin/kcadm.sh create users -r ${TP_REALM} -s username=${TP_USER} -s enabled=true && echo "<<< done"

# assigning roles 
echo "assigning roles >>>"
/opt/jboss/keycloak/bin/kcadm.sh add-roles -r ${TP_REALM} --uusername ${TP_USER} --rolename ${TP_USER_ROLE}
/opt/jboss/keycloak/bin/kcadm.sh add-roles -r ${TP_REALM} --uusername ${TP_ADMIN} --rolename ${TP_ADMIN_ROLE} && echo "<<< done"

# setting passwords
echo "setting user passwords >>"
/opt/jboss/keycloak/bin/kcadm.sh set-password -r ${TP_REALM} --username ${TP_USER} --new-password ${TP_PASSWORD} 
/opt/jboss/keycloak/bin/kcadm.sh set-password -r ${TP_REALM} --username ${TP_ADMIN} --new-password ${TP_PASSWORD} && echo "<<< done"

echo "**************************************************************************************"