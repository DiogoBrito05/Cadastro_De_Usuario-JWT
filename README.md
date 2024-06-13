# API do Projeto de Cadastro de Usuários e lançamentos de vendas de Produtos.

Esse é um projeto de cadastro de usuários e lançamento de vendas de produtos desenvolvido em Java com Spring Boot. O objetivo principal do projeto é fornecer um sistema para gerenciar o controle dos usuários,Onde usuários com apenas a ROLES de USER poderão usar o sisetma para gerenciar suas tarefas do dia a dia. Os usuários com as ROLES de permissão DEFAULT poderão apenas visualizar e os usuários ADMIN terão todo o acesso do sistema, assim fazendo o lançamento de vendas do produto que o cliente adquiriu.

## O projeto é dividido em várias partes, cada uma responsável por uma área específica do sistema. Vou deixar uma visão geral das diferentes partes do projeto:

## 1. Modelos (Entidades)
- Os modelos representam as entidades do sistema e são utilizados para mapear os dados que são armazenados no banco de dados.
## 2. Repositórios (Repository)
- Os repositórios são responsáveis por fornecer métodos para acessar e manipular os dados no banco de dados. Eles estendem as interfaces fornecidas pelo Spring Data JPA.
## 3. Serviços (Services)
- Os serviços são responsáveis por implementar a lógica de negócios da aplicação. Eles interagem com os repositórios para acessar e manipular os dados.
## 4. Exceções Personalizadas
- O projeto inclui exceções personalizadas para lidar com erros específicos que podem ocorrer durante a execução da aplicação. As exceções são lançadas em diferentes partes do código e podem ser capturadas e tratadas adequadamente.
## 5. Controladores (controller)
Os controladores são responsáveis por receber as requisições HTTP e chamar os métodos apropriados nos serviços para processar essas requisições. Eles também são responsáveis por retornar as respostas HTTP adequadas. Os controladores estão presentes em diferentes partes do projeto para lidar com diferentes tipos de operações.
## 6. Configuração
O projeto inclui configurações relacionadas ao Spring Boot, como configurações do banco de dados, segurança, mapeamento de URLs, entre outras.

## :rocket: Tecnologias Utilizadas

<table>
<tr>
  <td>Linguagem de Programação</td>
  <td>Framework Backend</td>
  <td>Persistência de Dados</td>
  <td>Segurança</td>
  <td>Banco de Dados</td>
  <td>Gerenciamento de Dependências</td>
</tr>
<tr>
  <td>Java 17.0</td>
  <td>Spring Boot 2.7.0</td>
  <td>Spring Data JPA</td>
  <td>Spring Security</td>
  <td>MySQLWorkbench 8.0</td>
  <td>Maven</td>
</tr>
</table>

Como Executar o Projeto
Para executar o projeto, siga estas etapas:

Clone o repositório do projeto para o seu ambiente local.
Certifique-se de ter o Java e o Maven instalados em seu sistema.
Configure o banco de dados conforme as configurações definidas no arquivo de propriedades do Spring.
Execute a aplicação usando o Maven ou importe o projeto em sua IDE favorita e execute-o a partir de lá.

## Link Front End:
- https://github.com/SRQCarlosAraujo/SRQ-Authentication
