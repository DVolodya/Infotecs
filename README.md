# Реализация FTP клиента

## Описание

Данное консольное приложение представляет из себя реализацию SFTP клиента для взаимодействия с SFTP сервером.

### Запуск приложения

*Чтобы запустить приложение необходимо:*
1. Склонировать репозиторий с GitHub на компьютер
   ```sh
   https://github.com/DVolodya/Infotecs
    ```
2. Перейти в корневую директорию проекта (Infotecs)
   
3. Запустить исполняемый **jar** файл проекта
   ```sh
   java -jar target\Infotecs.jar
   ```
----
## Работа с приложением
При запуске приложения пользователя встречает стартовое меню

(![image](https://github.com/user-attachments/assets/6e769a3e-5054-4391-955c-3c66d193daee)
")

Пользователю будет предложено ввести корректные данные для подключения по ssh.

После ввода корректный данный, пользователю будет предоставлен выбор действий
(![image](https://github.com/user-attachments/assets/03e95a83-172f-424d-9016-b9603f349b8f)
)

>[!IMPORTANT]
>расположен файл с информацией о доменных адресах в виде JSON подобной
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

### Jперации

Ниже приведены примеры 

1. Получение списка пар "домен – адрес" из файла в ***алфавитном порядке***

   ![Домены](![image](https://github.com/user-attachments/assets/fdf3cac9-8892-4f70-9898-d51a84b3b9ef)
)

2. Получение домена по его **id**

    ![Домен по id](![image](https://github.com/user-attachments/assets/162ba1a4-f682-41a9-96ad-13bcc0ae49d8)
)

3. Получение id по его **Домену**

   ![Домен но ip](![image](https://github.com/user-attachments/assets/24e88703-4760-4a1a-ac95-1c0784ce5fa2)
")


5. Пары "Домен - адрес" по его **id** или **домену**

    ![Удаление по id](![image](https://github.com/user-attachments/assets/499868ac-c741-4ba1-b51a-80bbb01a6e66)
)

>[!NOTE]
 >В случаях, если пользователь ввёл несуществующий id,
 >когда захотел получить или удалить информацию, будет
 >отображено соответствующее оповещение


![Некорректный id](![image](https://github.com/user-attachments/assets/8919205b-168d-4aa5-960c-3b2c743c3e9c)
)
