<h1 align="left">Vireya - Hydrocore Api Mongo</h1>

###

<p align="left">Esse projeto foi desenvolvido por alunos do Instituto J&F, do curso Germinare Tech. Vireya é um projeto  que tem a proposta de simplificar e ajudar no gerenciamento de ETAs (Empresas de Tratamento de Água) e diminuir erros humanos. Trazendo soluções a problemas reais dentro de ETAs desde superdosagem ou subdosagem até o gerenciamento de estoque e geração de relatórios exigidos por lei e periódicos. Esta versão da API utiliza o **MongoDB** como banco de dados NoSQL.</p>

###

<h2 align="left">📂 Estrutura do projeto</h2>

###

<p align="left">📦 hydrocore-api-mongo<br> ┣ 📂 src<br> ┃ ┣ 📂 main<br> ┃ ┣ 📂 test<br> ┣ 📂 .github<br> ┣ 📄 README.md<br> ┣ 📄 Dockerfile<br> ┣ 📄 pom.xml<br> ┣ 📄 application.yml<br> ┣ 📄 .env</p>

###

<h2 align="left">⚙️ Como configurar o projeto</h2>

###

<h4 align="left">Configurações necessárias para inicializar o projeto</h4>

###

<p align="left">- Java 17<br>- Git<br>- Docker (Necessário apenas para o build e deploy final)<br>- **Acesso ao MongoDB Atlas** (Ou a instância de MongoDB configurada no <code>.env</code>)</p>

###

<h4 align="left">Para inicializar o projeto localmente (sem container de banco)</h4>

###

```bash
# Clone o projeto (assumindo o nome do repositório)
$ git clone [https://github.com/Vireya-Hydrocore/hydrocore-api-mongo.git](https://github.com/Vireya-Hydrocore/hydrocore-api-mongo.git)
cd hydrocore-api-mongo

# Certifique-se de que o arquivo .env está configurado com as credenciais do MongoDB e Redis

# Limpe e instale as dependências
$ mvn clean install

# Execute a aplicação
# Nota: O Spring Boot fará a conexão direta com o MONGO_URL definido no seu .env
$ mvn spring-boot:run

````
###
Ao iniciar, ele rodará em
###

http://localhost:8080/swagger-ui/index.html

<h2 align="left"> 📃 Documentação da api de PRD</h2>

| Documentação             | Links                                                                                |
|--------------------------|--------------------------------------------------------------------------------------|
| Hydrocore-Api-Mongo-prod | [Swagger PROD](https://hydrocore-api-mongo-prod.onrender.com/swagger-ui/index.html#/) |

###

<h2 align="left">✏️ Linguagens utilizadas</h2>

###

<div align="left">
    <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" height="40" alt="java logo"/>
    <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" height="40" alt="spring logo"/>
    <img src="https://cdn.simpleicons.org/docker/2496ED" height="40" alt="docker logo"  />
    <img src="https://cdn.simpleicons.org/mongodb/47A248" height="40" alt="mongodb logo"  />
</div>


### 

<h2 align="left"> 👤 Responsáveis por este repositório </h2>

###

- [@Clara Bartolini](https://github.com/clarabartolini)
- [@Leonardo Lins](https://github.com/leonardolinsz)

###

<p align="center">Este projeto está sob a licença <a href="https://opensource.org/licenses/MIT">MIT</a> – veja o arquivo LICENSE para detalhes.</p>

