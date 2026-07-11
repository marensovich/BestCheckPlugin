# 🔍 BestCheckPlugin

A Minecraft plugin for **server administrators** to check and verify players with database logging.

## 📋 Description

Provides staff with tools to conduct player checks (anti-cheat investigations). Supports both MySQL and SQLite for logging check history, making it suitable for servers of any size.

## 🛠️ Tech Stack

- **Java** — core language
- **Bukkit / Paper API** — Minecraft plugin framework (1.21)
- **MySQL** — production database
- **SQLite** — lightweight alternative database
- **mysql-connector-j / sqlite-jdbc** — JDBC drivers

## ✨ Features

- Player check system for administrators
- MySQL and SQLite database support
- Check history logging
- Configurable check workflows
- CheckCommand with rich feedback
- Compatible with modern Paper 1.21

## 🚀 Installation

1. Place JAR in `plugins/` folder
2. Configure database in `config.yml` (MySQL or SQLite)
3. Restart server
4. Assign permissions to admin staff

> Requires: Paper 1.21+
