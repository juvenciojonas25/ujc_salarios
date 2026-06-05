-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jun 05, 2026 at 12:53 AM
-- Server version: 9.1.0
-- PHP Version: 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gestao_salarios`
--

-- --------------------------------------------------------

--
-- Table structure for table `carga_horaria`
--

DROP TABLE IF EXISTS `carga_horaria`;
CREATE TABLE IF NOT EXISTS `carga_horaria` (
  `id` varchar(36) NOT NULL,
  `data_aula` date NOT NULL,
  `horas_lecionadas` double DEFAULT NULL,
  `docente_id` varchar(36) NOT NULL,
  `disciplina_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `docente_id` (`docente_id`),
  KEY `disciplina_id` (`disciplina_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `carga_horaria`
--

INSERT INTO `carga_horaria` (`id`, `data_aula`, `horas_lecionadas`, `docente_id`, `disciplina_id`) VALUES
('3f3862e4-6077-11f1-b9e0-98e7f4333119', '2025-04-01', 3, '3f10ec3c-6077-11f1-b9e0-98e7f4333119', '3f2118dd-6077-11f1-b9e0-98e7f4333119'),
('3f386a40-6077-11f1-b9e0-98e7f4333119', '2025-04-08', 3, '3f10ec3c-6077-11f1-b9e0-98e7f4333119', '3f2118dd-6077-11f1-b9e0-98e7f4333119'),
('3f386dca-6077-11f1-b9e0-98e7f4333119', '2025-04-15', 2.5, '3f10ec3c-6077-11f1-b9e0-98e7f4333119', '3f2118dd-6077-11f1-b9e0-98e7f4333119'),
('3f386f4e-6077-11f1-b9e0-98e7f4333119', '2025-04-05', 4, '3f10f59a-6077-11f1-b9e0-98e7f4333119', '3f2120f5-6077-11f1-b9e0-98e7f4333119'),
('3f38709e-6077-11f1-b9e0-98e7f4333119', '2025-04-12', 4, '3f10f59a-6077-11f1-b9e0-98e7f4333119', '3f2120f5-6077-11f1-b9e0-98e7f4333119');

-- --------------------------------------------------------

--
-- Table structure for table `contrato`
--

DROP TABLE IF EXISTS `contrato`;
CREATE TABLE IF NOT EXISTS `contrato` (
  `id` varchar(36) NOT NULL,
  `numero` varchar(191) NOT NULL,
  `data_inicio` date NOT NULL,
  `data_fim` date DEFAULT NULL,
  `valor_pago_por_hora` double DEFAULT NULL,
  `carga_horaria_prevista` int DEFAULT NULL,
  `docente_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `numero` (`numero`),
  KEY `docente_id` (`docente_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `contrato`
--

INSERT INTO `contrato` (`id`, `numero`, `data_inicio`, `data_fim`, `valor_pago_por_hora`, `carga_horaria_prevista`, `docente_id`) VALUES
('3f266a68-6077-11f1-b9e0-98e7f4333119', 'CT-001', '2025-01-01', '2025-12-31', 500, 150, '3f10ec3c-6077-11f1-b9e0-98e7f4333119'),
('3f267417-6077-11f1-b9e0-98e7f4333119', 'CT-002', '2025-01-01', NULL, 750, 120, '3f10f59a-6077-11f1-b9e0-98e7f4333119');

-- --------------------------------------------------------

--
-- Table structure for table `disciplina`
--

DROP TABLE IF EXISTS `disciplina`;
CREATE TABLE IF NOT EXISTS `disciplina` (
  `id` varchar(36) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `codigo` varchar(191) NOT NULL,
  `curso` varchar(255) NOT NULL,
  `carga_horaria` int DEFAULT NULL,
  `docente_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigo` (`codigo`),
  KEY `docente_id` (`docente_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `disciplina`
--

INSERT INTO `disciplina` (`id`, `nome`, `codigo`, `curso`, `carga_horaria`, `docente_id`) VALUES
('3f2118dd-6077-11f1-b9e0-98e7f4333119', 'Programação Java', 'INF101', 'Informática', 60, '3f10ec3c-6077-11f1-b9e0-98e7f4333119'),
('3f2120f5-6077-11f1-b9e0-98e7f4333119', 'Bases de Dados', 'INF102', 'Informática', 45, '3f10f59a-6077-11f1-b9e0-98e7f4333119');

-- --------------------------------------------------------

--
-- Table structure for table `docente`
--

DROP TABLE IF EXISTS `docente`;
CREATE TABLE IF NOT EXISTS `docente` (
  `id` varchar(36) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `apelido` varchar(255) NOT NULL,
  `nuit` varchar(191) NOT NULL,
  `genero` varchar(1) NOT NULL,
  `email` varchar(191) NOT NULL,
  `valor_pago_por_hora` double DEFAULT NULL,
  `banco` varchar(100) DEFAULT NULL,
  `nib` varchar(191) DEFAULT NULL,
  `categoria` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nuit` (`nuit`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `nib` (`nib`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `docente`
--

INSERT INTO `docente` (`id`, `nome`, `apelido`, `nuit`, `genero`, `email`, `valor_pago_por_hora`, `banco`, `nib`, `categoria`) VALUES
('3f10ec3c-6077-11f1-b9e0-98e7f4333119', 'João', 'Silva', '123456789', 'M', 'joao@ujc.mz', 500, 'BCI', '1234567890123', 'Assistente'),
('3f10f59a-6077-11f1-b9e0-98e7f4333119', 'Maria', 'Santos', '987654321', 'F', 'maria@ujc.mz', 750, 'BIM', '3210987654321', 'Coordenador');

-- --------------------------------------------------------

--
-- Table structure for table `pagamento`
--

DROP TABLE IF EXISTS `pagamento`;
CREATE TABLE IF NOT EXISTS `pagamento` (
  `id` varchar(36) NOT NULL,
  `referencia_pagamento` varchar(191) NOT NULL,
  `mes` varchar(7) NOT NULL,
  `total_horas` double DEFAULT NULL,
  `valor_hora` double DEFAULT NULL,
  `salario_bruto` double DEFAULT NULL,
  `desconto_irps` double DEFAULT NULL,
  `salario_liquido` double DEFAULT NULL,
  `data_processamento` datetime(6) NOT NULL,
  `estado_pagamento` enum('PROCESSADO','PAGO','PENDENTE') DEFAULT NULL,
  `docente_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `referencia_pagamento` (`referencia_pagamento`),
  KEY `docente_id` (`docente_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE IF NOT EXISTS `usuario` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(191) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('ADMIN','FINANCEIRO','DOCENTE') NOT NULL,
  `docente_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `docente_id` (`docente_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
