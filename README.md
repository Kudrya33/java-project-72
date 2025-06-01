[![Actions Status](https://github.com/Kudrya33/java-project-72/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/Kudrya33/java-project-72/actions)
[![Java CI](https://github.com/Kudrya33/java-project-72/actions/workflows/build.yml/badge.svg)](https://github.com/Kudrya33/java-project-72/actions/workflows/build.yml)
[![Maintainability](https://qlty.sh/badges/df299474-4790-4bf1-af0e-49d715cfa1f1/maintainability.svg)](https://qlty.sh/gh/Kudrya33/projects/java-project-72)
[![Code Coverage](https://qlty.sh/badges/df299474-4790-4bf1-af0e-49d715cfa1f1/test_coverage.svg)](https://qlty.sh/gh/Kudrya33/projects/java-project-72)

[Website](https://java-project-72-zy72.onrender.com/) – сайт, который анализирует указанные страницы на SEO пригодность.
Это веб-приложение на Java с использованием Javalin и JTE-шаблонов, реализующее функциональность "анализатора сайтов".

Инструкция по запуску:

1. Склонируйте/скачайте проект
2. Соберите проект с помощью Maven или Gradle
3. Запустите приложение
4. Перейдите в браузер по адресу http://localhost:7070/

Инструкция по использованию сайта:

1. Введите в строку поиска URL адрес сайта.
2. Сайт добавится в общий список и станет доступный для проверок.
3. Перейдите в списке на интересующий вас URL адрес.
4. Нажмите кнопку проверки для получения информации о сайте.

Структура приложения
App.java — точка входа, конфигурирует Javalin, подключение к БД и маршруты.
UrlsController — основной контроллер обработки логики сайта и проверок.
JTE-шаблоны находятся в папке resources/templates.
schema.sql — SQL-скрипт для инициализации схемы базы данных.
