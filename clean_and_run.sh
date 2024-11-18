#!/bin/bash

set -e  # Exit immediately if a command exits with a non-zero status

# Clean and build the Gradle project
./gradlew clean build

# Bring down any running containers
docker-compose down

# Clean up unused Docker resources
docker system prune -f
docker image prune -a -f  # Force prune without confirmation

# Start the Docker containers
docker-compose up --build
