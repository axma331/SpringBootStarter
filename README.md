# HTTP Logging Spring Boot Starter

Данное приложение предоставляет стартер для автоматической настройки логирования HTTP-запросов и ответов в приложениях на базе Spring Boot. Стартер конфигурирует интерцептор, который записывает важные детали каждого HTTP-запроса и ответа, такие как метод, URL, заголовки, статус ответа, время выполнения и многое другое.

## Технологии

Проект использует следующие технологии и инструменты:

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white "Java")
![Maven](https://img.shields.io/badge/Maven-green.svg?style=for-the-badge&logo=mockito&logoColor=white "Maven")
![Spring](https://img.shields.io/badge/Spring-blueviolet.svg?style=for-the-badge&logo=spring&logoColor=white "Spring")
![GitHub](https://img.shields.io/badge/git-%23121011.svg?style=for-the-badge&logo=github&logoColor=white "Git")
+ Язык программирования: *Java 17*
+ Автоматизация сборки: *Maven*
+ Фреймворк: *Spring*
+ Контроль версий: *Git*

## HTTP Logging Spring Boot Starter

<details>
<summary>Полный текст задания</summary>
Задание: Создание Spring Boot Starter для логирования HTTP запросов

### Описание:
Ваша задача - разработать Spring Boot Starter, который предоставит возможность логировать HTTP запросы в вашем приложении на базе Spring Boot.

### Требования:

#### Функциональность:
Ваш Spring Boot Starter должен предоставлять возможность логировать все входящие и исходящие HTTP запросы и ответы вашего приложения.
Логирование должно включать в себя метод запроса, URL, заголовки запроса и ответа, код ответа, время обработки запроса и т.д.

#### Реализация:
- Создайте проект Maven для вашего Spring Boot Starter.
- Используйте Spring Boot для автоконфигурации вашего Starter.
- Реализуйте механизм перехвата и логирования HTTP запросов с помощью фильтров или интерцепторов Spring, или Spring AOP.
- Обеспечьте настройку уровня логирования и формата вывода логов.

#### Документация:
- Напишите подробное описание вашего Spring Boot Starter, включая его функциональность и способы использования.
- Обеспечьте хорошую документацию по API и конфигурации вашего Starter.

#### Тестирование:
- Напишите unit-тесты для проверки корректности работы вашего Spring Boot Starter.
- Покройте тестами основные сценарии использования и краевые случаи.

</details>

## Установка и настройка

### Предварительные требования

Убедитесь, что у вас установлены следующие инструменты:
- Java 17
- Maven

### Шаги установки

1. Добавьте следующую зависимость в ваш проект:

   **Maven**
   ```xml
   <dependency>
       <groupId>t1.ismailov</groupId>
       <artifactId>http-logging-spring-boot-starter</artifactId>
       <version>1.0.0</version>
   </dependency>
    ```
### Настройка логирования

Для включения логирования HTTP-запросов и ответов добавьте следующие свойства в `application.properties` или `application.yml`:

- Включение логирования:
  ```properties
  http.logging.enabled=true
  ```
- Указание формата логов (доступные значения: json или text):
  ```properties
  http.logging.format=json
  ```
- Настройка уровня логирования для интерцептора:
  ```properties
  logging.level.t1.ismailov.springbootstarter.config.HttpLoggingInterceptor=INFO
  ```
- Настройка уровня логирования для всего пакета:
  ```properties
  logging.level.t1.ismailov.springbootstarter=DEBUG
  ```
## Использование

После настройки логирования, интерцептор автоматически регистрируется в контексте Spring и начинает логировать все HTTP-запросы и ответы в соответствии с указанными настройками.

## Примеры логов

### Формат JSON

```json
{
  "executionTime": 150,
  "method": "GET",
  "uri": "/api/v1/resource",
  "requestHeaders": {
    "Content-Type": "application/json"
  },
  "responseHeaders": {
    "Content-Type": "application/json"
  },
  "statusCode": 200
}
```
### Формат Text
```bash
HttpLogEntity{executionTime=150, method='GET', uri='/api/v1/resource', requestHeaders={Content-Type=application/json}, responseHeaders={Content-Type=application/json}, statusCode=200}
```