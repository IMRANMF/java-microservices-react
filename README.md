# java-microservices-react

## Overview

This repository contains a Java microservices-based e-commerce application with a React frontend. The services are orchestrated using Docker Compose and include:

- `gateway-service`
- `order-service`
- `inventory-service`
- `notification-service`
- `mongodb`
- `rabbitmq`
- `frontend`

## Prerequisites

- Docker installed and running
- Docker Compose plugin available (`docker compose`) or Docker Compose v1 (`docker-compose`)

## Run locally

1. Open a terminal in the repository root:
   ```bash
   cd /dev-data/imran/java-microservices-react
   ```

2. Build and start all services:
   ```bash
   docker compose up --build
   ```

   If your system still uses the older Compose command:
   ```bash
   docker-compose up --build
   ```

3. Access the application and supporting services:
   - Frontend: `http://localhost:3000`
   - Gateway / API: `http://localhost:8080`
   - Order service: `http://localhost:8081`
   - Inventory service: `http://localhost:8082`
   - Notification service: `http://localhost:8083`
   - RabbitMQ management: `http://localhost:15672`

## Troubleshooting

- If Compose fails because a container name already exists, remove the conflicting container and try again:
  ```bash
  docker rm -f mongodb
  docker rm -f rabbitmq
  ```

- If you need to stop and remove containers created by Compose:
  ```bash
  docker compose down
  ```

- To remove volumes as well:
  ```bash
  docker compose down -v
  ```

- To rebuild images from scratch:
  ```bash
  docker compose build --no-cache
  ```

## Recommended workflow

1. Start services:
   ```bash
   docker compose up --build -d
   ```
2. Check container status:
   ```bash
   docker compose ps
   ```
3. Stop services when finished:
   ```bash
   docker compose down
   ```

## Notes

- The Compose configuration lives in `docker-compose.yml`.
- The frontend sources are under `e-commerce-frontend/`.
- Each Java microservice is defined in its own subdirectory.
