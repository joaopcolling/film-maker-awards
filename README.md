# Film Maker Awards - API RESTful

## Visão Geral
Este projeto é uma API RESTful desenvolvida como parte de um processo seletivo técnico. Ele permite a leitura da lista de indicados e vencedores da categoria Pior Filme do Golden Raspberry Awards a partir de um arquivo CSV.

## Funcionalidades
- Importação automática do arquivo CSV ao iniciar a aplicação
- API para obter o produtor com maior intervalo entre dois prêmios consecutivos e o que obteve dois prêmios mais rápido
- Banco de dados em memória (H2), sem necessidade de instalação externa
- Testes de integração para validação dos dados fornecidos

## Pré-requisitos
Para rodar o projeto, é necessário ter instalado:
- [Git](https://git-scm.com/)
- [Java 17+](https://adoptium.net/)
- [Maven](https://maven.apache.org/)
- [Docker](https://www.docker.com/) (opcional, caso queira rodar via container)

## Clonando o Repositório
```sh
git clone https://github.com/joaopcolling/film-maker-awards.git
cd film-maker-awards
```

## Compilando o Projeto
Antes de executar, é necessário compilar o projeto com Maven:
```sh
mvn clean install
```

## Executando a Aplicação
A API pode ser executada de duas maneiras: utilizando o JAR localmente ou via Docker.

### Executar com JAR
```sh
java -jar target/film-maker-awards.war
```

Se desejar usar um arquivo CSV local específico:
```sh
java -jar target/film-maker-awards.war --csv.path="/caminho/para/movielist.csv"
```

### Executar com Docker
#### Construindo a Imagem Docker
```sh
docker build -t film_maker_awards .
```

#### Rodando o Contêiner
Para rodar com o arquivo padrão fornecido na proposta:
```sh
docker run -p 8080:8080 --rm film_maker_awards
```

Para rodar com um arquivo CSV local:
```sh
docker run -p 8080:8080 -v /caminho/para/movielist.csv:/app/csv/movielist.csv --rm film_maker_awards --csv.path=/app/csv/movielist.csv
```

## Banco de Dados H2
A aplicação utiliza o banco de dados H2 em memória, acessível pelo navegador em:
```
http://localhost:8080/h2-console
```
Credenciais padrão:
- **JDBC URL:** `jdbc:h2:mem:filmmakerawardsdb`
- **User:** `username`
- **Password:** `password`

## Consumindo a API
A API fornece um endpoint para obter os produtores com maior e menor intervalo entre prêmios:
```sh
GET http://localhost:8080/awards
```
Para testar via CLI:
```sh
curl -X GET http://localhost:8080/awards
```

## Testes de Integração
Os testes garantem que os dados importados do CSV correspondem aos fornecidos na proposta.
Também validam o retorno da API para os mesmos dados fornecidos.
Para rodar os testes:
```sh
mvn test
```

## Considerações Finais
Esta API foi desenvolvida seguindo os requisitos técnicos do desafio, implementando um serviço RESTful no nível 2 da maturidade de Richardson. O código-fonte está disponível no repositório GitHub para revisão e execução.

Caso tenha dúvidas ou sugestões, entre em contato!
