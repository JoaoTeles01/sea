# Sea Adoption API

## Descrição

Este projeto é uma API para gerenciar adoções de animais. Ele é construído usando Spring Boot e inclui endpoints para criar, atualizar, deletar e listar adoções e animais.

## Tecnologias Utilizadas

- Java
- Spring Boot
- Gradle
- SQL
- Kotlin

## Estrutura do Projeto

- `src/main/java/com/challenge/sea/controller/AdoptionController.java`: Controlador para gerenciar adoções.
- `src/main/java/com/challenge/sea/controller/AnimalController.java`: Controlador para gerenciar animais.
- `src/main/java/com/challenge/sea/SeaApplication.java`: Classe principal para iniciar a aplicação.
- `src/main/java/com/challenge/sea/utils/AdoptionConverter.java`: Classe utilitária para converter entidades de adoção.

## Endpoints

### Adoções

- **POST /api/adoption**: Cria uma nova adoção.
- **GET /api/adoption**: Retorna todas as adoções.
- **GET /api/adoption/{id}**: Retorna uma adoção pelo ID.
- **PUT /api/adoption/{id}**: Atualiza uma adoção pelo ID.
- **DELETE /api/adoption/{id}**: Deleta uma adoção pelo ID.

### Animais

- **POST /api/animals**: Cria um novo animal.
- **GET /api/animals**: Retorna todos os animais.
- **GET /api/animals/{id}**: Retorna um animal pelo ID.
- **PUT /api/animals/{id}**: Atualiza um animal pelo ID.
- **DELETE /api/animals/{id}**: Deleta um animal pelo ID.
- **PUT /api/animals/{id}/adopt**: Marca um animal como adotado.

## Como Executar

1. Clone o repositório:
    ```sh
    git clone <URL_DO_REPOSITORIO>
    ```
2. Navegue até o diretório do projeto:
    ```sh
    cd sea-adoption-api
    ```
3. Execute o projeto usando Gradle:
    ```sh
    ./gradlew bootRun
    ```

## Contribuição

1. Faça um fork do projeto.
2. Crie uma nova branch para sua feature ou correção:
    ```sh
    git checkout -b minha-feature
    ```
3. Commit suas mudanças:
    ```sh
    git commit -m 'Minha nova feature'
    ```
4. Faça o push para a branch:
    ```sh
    git push origin minha-feature
    ```
5. Abra um Pull Request.

## Licença

Este projeto está licenciado sob a Licença MIT. Veja o arquivo `LICENSE` para mais detalhes.