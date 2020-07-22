Projeto feito para testar o comportamento de uma plicação no "modelo" CQRS (Command Query Responsibility Segregatio) sob constante inserção de dados e leitura de dados.

# Ferramentas

Aqui vamos ter um pouco de Angular, Nginx, Docker, Postgres, Mongo, Quarkus, SmallRye, Artemis(ActiveMQ), vertx... mas nada muito sofisticado, somente o mínimo para fazer o experimento. 

# Executando

    $ make run

# Iterações

Você pode alterar `MAX_ITERATIONS` em `client/src/app/tester/tester.component.ts` 