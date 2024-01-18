CREATE DATABASE `api_clientes`;
USE `api_clientes`;

CREATE TABLE `clientes` (
  `cliente_id` bigint NOT NULL,
  `edad` bigint DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `identificacion` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `estado` enum('TRUE','FALSE') DEFAULT NULL,
  `genero` enum('MASCULINO','FEMENINO','OTRO') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE `clientes`
  ADD PRIMARY KEY (`cliente_id`);

ALTER TABLE `clientes`
  MODIFY `cliente_id` bigint NOT NULL AUTO_INCREMENT;
