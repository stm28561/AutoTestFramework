## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)

## General info
Это мой учебный проект, со времён изучения автоматизированного тестирования

## Technologies
Project is created with:
* Maven
* JUnit
* Selenium

## Setup
To run this project, install it locally using npm:

```
$ cd ../FirstTest_2021_09
$ mvn compile
$ mvn test -Dtest=TestForLeague#leagueTestTask test
Для корректного прогона теста необходимо, чтобы chrome driver лежал
лежал по пути: C:\tmp\chromedriver.exe