run-dev: docker
	./mvnw quarkus:dev

docker:
	docker-compose up --build

build:
	./mvnw clean package -Dquarkus.container-image.build=true
	docker build -f src/main/docker/Dockerfile.jvm -t fabriciogoncalves/quarkus_crud .


run: build docker