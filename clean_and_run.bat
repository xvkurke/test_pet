@echo off
setlocal

REM Clean and build the project
call gradlew.bat clean
call gradlew.bat build

REM Bring down any running containers
docker-compose down

REM Clean up unused Docker resources
docker system prune -f
docker image prune -a -f

REM Start the Docker containers
docker-compose up --build

endlocal
