#!/usr/bin/env bash

RET=$(curl -o /dev/null -s -w "%{http_code}\n" http://localhost:8080)
while [[ ${RET} != "200" ]]; do
	echo "not ready yet"
	sleep 5
	RET=$(curl -o /dev/null -s -w "%{http_code}\n" http://localhost:8080)
done
