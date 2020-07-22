build-cqrs-app:
	cd cqrs_aplication && make build && cd -

build-crud-app:
	cd crud_aplication && make build && cd -

build-client:
	cd client && make build && cd -


run: build-crud-app build-cqrs-app build-client
	docker-compose up --build