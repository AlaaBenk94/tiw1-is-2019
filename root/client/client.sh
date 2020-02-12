#!/usr/bin/env bash

# 0. demander une authorisation de keycloak
# 1. demande de création d'un emprunt sur une trottinette
curl -d '{"date": "2020-02-06T10:04:27.699Z","idAbonne": 5,"idTrottinette": 1}' -H "Content-Type: application/json" -X POST http://localhost:8081/emprunts

# 2. création d'une autorisation de transfert
URL=http://localhost:8082/compte/

function create-compte() {
  curl -s -X POST -H "Content-Type: application/json" --data-raw '{"valeur": "10.0"}' ${URL} | jq -e '.id'
}

function create-autorisation() {
  compte=$1
  shift
  echo ${compte}
  curl -s -X POST -H "Content-Type: application/json" --data-raw '{"montant": "5.0"}' ${URL}${compte}
}

cpt=$(create-compte)
echo "Compte ${cpt}"
create-autorisation ${cpt}

# 2.1 création d'un compte

# 2.2 création d'une autorisation

# 3. demande de transfert
curl -d @ -H "Content-Type: text/xml;charset=UTF-8" -X POST http://localhost:8081/emprunts

# 4. vérification de déblocage de la trottinette (i.e. emprunt activé) à intervalles réguliers jusqu'à ce que la trottinette soit effectivement activée