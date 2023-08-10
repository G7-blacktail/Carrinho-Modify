#!/bin/bash

docker stack deploy -c docker-compose.yml acnacional --with-registry-auth
