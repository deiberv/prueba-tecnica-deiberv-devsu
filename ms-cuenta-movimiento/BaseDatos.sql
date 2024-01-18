CREATE DATABASE `api_cuenta_movimiento`;
USE `api_cuenta_movimiento`;

CREATE TABLE `clientes` (
  `cliente_id` bigint NOT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB;

CREATE TABLE `cuentas` (
  `saldo` decimal(38,2) DEFAULT NULL,
  `cliente_id` bigint NOT NULL,
  `cuenta_id` bigint NOT NULL,
  `numero_cuenta` varchar(10) NOT NULL,
  `estado` enum('True','False') DEFAULT NULL,
  `tipo_cuenta` enum('CORRIENTE','AHORRO') NOT NULL
) ENGINE=InnoDB;

CREATE TABLE `movimientos` (
  `movimiento_id` bigint NOT NULL,
  `fecha` datetime(6) DEFAULT NULL,
  `cuenta_id` bigint DEFAULT NULL,
  `saldo_inicial` decimal(38,2) DEFAULT NULL,
  `valor` decimal(38,2) DEFAULT NULL,
  `saldo` decimal(38,2) DEFAULT NULL
) ENGINE=InnoDB;


--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`cliente_id`);

ALTER TABLE `cuentas`
  ADD PRIMARY KEY (`cuenta_id`),
  ADD UNIQUE KEY `UK_NROCUENTA` (`numero_cuenta`),
  ADD KEY `FK_CTA_CLIENTE_ID` (`cliente_id`);

ALTER TABLE `movimientos`
  ADD PRIMARY KEY (`movimiento_id`),
  ADD KEY `FK_MOV_CUENTA_ID` (`cuenta_id`);

ALTER TABLE `cuentas`
  MODIFY `cuenta_id` bigint NOT NULL AUTO_INCREMENT;

ALTER TABLE `movimientos`
  MODIFY `movimiento_id` bigint NOT NULL AUTO_INCREMENT;

ALTER TABLE `cuentas`
  ADD CONSTRAINT `FK_CTA_CLIENTE_ID` FOREIGN KEY (`cliente_id`) REFERENCES `clientes` (`cliente_id`);

ALTER TABLE `movimientos`
  ADD CONSTRAINT `FK_MOV_CUENTA_ID` FOREIGN KEY (`cuenta_id`) REFERENCES `cuentas` (`cuenta_id`);