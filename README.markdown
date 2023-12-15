# Mire

It's a nonviolent MUD. (Multi-User Dungeon)

## Usage

First make sure that you have `java` installed on your
machine. [OpenJDK](https://adoptopenjdk.net) is recommended. It should
be at least version 8, but newer versions (tested up to 17) should work too.

Do `./lein run` inside the Mire directory to launch the Mire
server. Then players can connect by telnetting to port 3333.

## Motivation

The primary purpose of this codebase is as a demonstration of how to
build a simple multithreaded server in Clojure.

Mire is built up step-by-step, where each step introduces one or two
small yet key Clojure principles and builds on the last step. The
steps each exist in separate git branches. To get the most out of
reading Mire, you should start reading in the branch called
[step-01-echo-server](http://github.com/technomancy/mire/tree/01-echo-server)
and continue from there.

While you can learn from Mire on its own, it has been written
specifically for the [PluralSight screencast on
Clojure](https://www.pluralsight.com/courses/functional-programming-clojure).
A [blog post](https://technomancy.us/136) steps through the codebase
and shows how to make minor updates for a more recent version of Clojure.

Copyright © 2009-2021 Phil Hagelberg
Licensed under the same terms as Clojure.

# Group Project

Участники проекта:

1. [Меликов Роман](https://github.com/MelikovMan)
2. [Брезицкий Сергей](https://github.com/SergeiB2002)
3. [Илья Заяц](https://github.com/IlyaZayats)
4. [Ростислав Орлов](https://github.com/RostislavOrlov)

## Расширяемый функционал

Командой для решения вопроса были выбран следующий функционал для реализации:

Новый функционал:
- Модификация команды look - выводит список игроков в комнате
- Новая команда yell, чтобы услышали все игроки в любой комнате крик
- Модификация команды say - выводит имя игрока, который сказал
- Новая команда whisper - приватный чат - сообщение одному игроку
- Изменить поведение при входе игрока - при входе просить помимо имени описание персонажа
- Изменить поведение при входе игрока - при входе просить помимо имени статы
- Бот на prolog, который бегает и кричит, используя команду yell
- Контейнеризация, CI/CD, изменение поведения бота с новым функционалом

## Процесс разработки

Командой было принято решение разбивать реализцаию функционала по неделям.

### Спринт 1

Начало: 03.12
- Модификация команды look - выводит список игроков в комнате - Сергей Брезицкий
- Модификация команды say - выводит имя игрока, который сказал - Илья Заяц
- Новая команда yell, чтобы услышали все игроки в любой комнате крик - Ростислав Орлов
- Бот на prolog, который бегает и кричит, используя команду yell - Меликов Роман
Окончание: 10.12

Результат работы спринта:
- Модификация команды look - выводит список игроков в комнате - Сергей Брезицкий:
  - Завершена, описана в PR#4
- Модификация команды say - выводит имя игрока, который сказал - Илья Заяц:
  - Завершена, описана в PR#1
- Новая команда yell, чтобы услышали все игроки в любой комнате крик - Ростислав Орлов
  - Завершена, описана в PR#5
- Новая команда yell, чтобы услышали все игроки в любой комнате крик - Меликов Роман
  - Завершена, описана в commit с сообщением AS

### Спринт 2
СПРИНТ 2:
Начало: 10.12

- Изменить поведение при входе игрока - при входе просить помимо имени статы - Сергей Брезицкий
- Новая команда whisper - приватный чат - сообщение одному игроку - Илья Заяц
- Изменить поведение при входе игрока - при входе просить помимо имени описание персонажа - Ростислав Орлов
- Контейнеризация, CI/CD, изменение поведения бота с новым функционалом - Меликов Роман

Окончание: 17.12

Результат работы спринта:
- Изменить поведение при входе игрока - при входе просить помимо имени статы - Сергей Брезицкий:
  - Завершена, описана в PR#17
- Новая команда whisper - приватный чат - сообщение одному игроку:
  - Завершена, описана в PR#6
- Изменить поведение при входе игрока - при входе просить помимо имени описание персонажа - Ростислав Орлов:
  - Завершена, описана в PR#15
- Новая команда yell, чтобы услышали все игроки в любой комнате крик - Меликов Роман
  - Завершена, описана в PR #7-#12, а также в коммите Update bot.pl.
