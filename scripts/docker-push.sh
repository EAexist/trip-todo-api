./gradlew clean build -x test -Dspring.profiles.active=prod
docker build --build-arg JAR_FILE=build/libs/\*.jar -t eaexists/trip-todo-api:latest .
docker push eaexists/trip-todo-api:latest