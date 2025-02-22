# Реализация SFTP клиента

## Описание

Данное консольное приложение представляет из себя реализацию SFTP клиента для взаимодействия с SFTP сервером.

### Запуск приложения

*Чтобы запустить приложение необходимо:*
1. Склонировать репозиторий с GitHub на компьютер
   ```sh
   https://github.com/DVolodya/Infotecs
    ```
2. Перейти в корневую директорию проекта SFTPClient.jar
   
3. Запустить исполняемый **jar** файл проекта
   ```sh
   java -jar out/artifacts/Infotecs_jar/SFTPClient.jar
   ```
----
## Работа с приложением
При запуске приложения пользователя встречает стартовое меню

![image](https://github.com/user-attachments/assets/6e769a3e-5054-4391-955c-3c66d193daee)


Пользователю будет предложено ввести корректные данные для подключения по ssh.

После ввода корректный данный, пользователю будет предоставлен выбор действий

![image](https://github.com/user-attachments/assets/03e95a83-172f-424d-9016-b9603f349b8f)


>[!IMPORTANT]
>Расположен файл с информацией о доменных адресах в виде JSON подобной
>структуры с уникальными значениями доменов и IP-адресов уже лежит в корневом каталоге.
>

### Структура файла

```json
{
  "adresses": [
    {
      "domain": "Example",
      "ip": "192.168.25.1"
    },
    {
      "domain": "Test.com",
      "ip": "192.166.54.2"
    }
   ]
}
```

----

### Операции

Ниже приведены примеры 

1. Получение списка пар "домен – адрес" из файла в ***алфавитном порядке***

   ![image](https://github.com/user-attachments/assets/5e84c6f0-06ae-487d-8620-71374c58a2fa)


2. Получение id по его **Домену**

    ![image](https://github.com/user-attachments/assets/162ba1a4-f682-41a9-96ad-13bcc0ae49d8)


3. Получение домена по его **id**

   ![image](https://github.com/user-attachments/assets/cd8b3313-ec9d-40af-98b0-b604130d9e23)


4. Добавление пары ***Домен - адрес*

   ![image](https://github.com/user-attachments/assets/c162318a-74fa-4a98-a053-8e55c4dfac3f)

   
   ![image](https://github.com/user-attachments/assets/33fbff68-b2b7-44d7-be3a-353b0914aaa1)

6. Удаление пары "Домен - адрес" по его **id** или **домену**

    ![image](https://github.com/user-attachments/assets/499868ac-c741-4ba1-b51a-80bbb01a6e66)

7. Завершение программы
   
   ![image](https://github.com/user-attachments/assets/a2f5fb0e-a6ef-4b80-b5d7-31ccdcf7e6a6)

 >В случаях, если пользователь ввёл несуществующий id,
 >будет
 >отображено соответствующее оповещение


![image](https://github.com/user-attachments/assets/8919205b-168d-4aa5-960c-3b2c743c3e9c)

## Инструкция по запуску тестов и кратким обоснованием тестов

1.Перейти в корневую директорию проекта Infotecs_jar_tests
   
2. Запустить исполняемый **jar** файл проекта
   ```sh
   java -jar out/artifacts/Infotecs_jar_tests/SFTPClientTest.jar
   ```
