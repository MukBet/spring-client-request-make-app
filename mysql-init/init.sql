-- docker logs mysql-container  для просмотра логов контейнера MySQL


-- Логирование начала работы скрипта
SELECT 'Начало выполнения init.sql' AS 'Инициализация';

-- Создание базы данных и пользователя
CREATE DATABASE IF NOT EXISTS democlient_db;
CREATE USER IF NOT EXISTS 'democlient_user'@'%' IDENTIFIED WITH mysql_native_password BY 'yourpassword1';

-- Логирование этапа назначения привилегий
SELECT 'Пользователь создан, назначаем привилегии' AS 'Инициализация';

-- Назначение привилегий
GRANT ALL PRIVILEGES ON democlient_db.* TO 'democlient_user'@'%';
FLUSH PRIVILEGES;

-- Логирование завершения работы скрипта
SELECT 'Завершение выполнения init.sql' AS 'Инициализация';
