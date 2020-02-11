# Hobby

This repository contains some of my hobby projects.

Currently this includes:

- Telegram client for the Bot API (https://core.telegram.org/bots/api).
- A silly telegram bot with plugins to do all kinds of silly stuff.

## Repository Structure

- /lib - useful generic libraries
  - /repository - repository API
    - /repository/api - repository API for use in domain classes
    - /repository/mem - in memory repository API implementation, for use in domain tests
    - /repository/jpa - JPA based repository API implementation
  - /telegram - telegram Bot API
    - /telegram/botapi - telegram Bot API client
    - /telegram/botapi-impl - implementation of the Telegram Bot API client
- /hobbes - a silly telegram bot
- /3rd-party - bazel rules for 3rd party libraries
- /tools - custom bazel rules for spring, docker images, etc.