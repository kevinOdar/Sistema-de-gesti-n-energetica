-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: equipo-01
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `actuador`
--

DROP TABLE IF EXISTS `actuador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `actuador` (
  `tipo` varchar(31) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dispositivo_id` int(11) DEFAULT NULL,
  `regla_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_a2cnpvifjgs768w0sysk58dsx` (`dispositivo_id`),
  KEY `FK_6ixvjnbsk4t6430txdwovi1av` (`regla_id`),
  CONSTRAINT `FK_6ixvjnbsk4t6430txdwovi1av` FOREIGN KEY (`regla_id`) REFERENCES `regla` (`id`),
  CONSTRAINT `FK_a2cnpvifjgs768w0sysk58dsx` FOREIGN KEY (`dispositivo_id`) REFERENCES `inteligente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actuador`
--

LOCK TABLES `actuador` WRITE;
/*!40000 ALTER TABLE `actuador` DISABLE KEYS */;
INSERT INTO `actuador` VALUES ('prender',1,2,1);
/*!40000 ALTER TABLE `actuador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrador`
--

DROP TABLE IF EXISTS `administrador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `administrador` (
  `fecha_alta` tinyblob,
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_lhjsinmexq43y65t1ffmjya1d` FOREIGN KEY (`id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrador`
--

LOCK TABLES `administrador` WRITE;
/*!40000 ALTER TABLE `administrador` DISABLE KEYS */;
INSERT INTO `administrador` VALUES (_binary '¬\í\0sr\0\rjava.time.Ser•]„º\"H²\0\0xpw\0\0\â\nx',5);
/*!40000 ALTER TABLE `administrador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `caracteristica`
--

DROP TABLE IF EXISTS `caracteristica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `caracteristica` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coeficienteDeConsumo` double DEFAULT NULL,
  `esDeBajoConsumo` bit(1) DEFAULT NULL,
  `esInteligente` bit(1) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `restriccionMayorIgual` double DEFAULT NULL,
  `restriccionMenorIgual` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `caracteristica`
--

LOCK TABLES `caracteristica` WRITE;
/*!40000 ALTER TABLE `caracteristica` DISABLE KEYS */;
INSERT INTO `caracteristica` VALUES (1,1.2,_binary '\0',_binary '','Aire acondicionado de 3500 frigorias',90,360),(2,1.013,_binary '',_binary '','Aire acondicionado de 2200 frigorias',90,360),(3,0.075,_binary '\0',_binary '\0','TV Tubo fluorescente 21\" ',90,360),(4,0.175,_binary '\0',_binary '\0','TV Tuvo fluorescente 29\" a 34\"',90,360),(5,0.18,_binary '\0',_binary '\0','LCD de 40\"',90,360),(6,0.04,_binary '',_binary '','LED 24\"',90,360),(7,0.055,_binary '',_binary '','LED 32\"',90,360),(8,0.08,_binary '',_binary '','LED 40\"',90,360),(9,0.09,_binary '',_binary '','Heladera con freezer',0,0),(10,0.075,_binary '',_binary '','Heladera sin freezer',0,0),(11,0.875,_binary '\0',_binary '\0','Lavarropas automatico de 5 Kg con calentamiento de agua',6,30),(12,0.175,_binary '',_binary '','Lavarropas automatico de 5 Kg',6,30),(13,0.1275,_binary '',_binary '\0','Lavarropas semi-automatico de 5 Kg',6,30),(14,0.09,_binary '',_binary '\0','Ventilador de pie',120,360),(15,0.06,_binary '',_binary '','Ventilador de techo',120,360),(16,0.04,_binary '\0',_binary '','Lampara halogena de 40W',90,360),(17,0.06,_binary '\0',_binary '','Lampara halogena 60W',90,360),(18,0.015,_binary '\0',_binary '','Lampara halogena de 100W',90,360),(19,0.011,_binary '',_binary '','Lampara de 11W',90,360),(20,0.015,_binary '',_binary '','Lampara de 15W',90,360),(21,0.02,_binary '',_binary '','Lampara de 20W',90,360),(22,0.4,_binary '',_binary '','PC de escritorio',60,360),(23,0.64,_binary '',_binary '\0','Microondas convencional',3,15),(24,0.75,_binary '',_binary '\0','Plancha a vapor',3,30);
/*!40000 ALTER TABLE `caracteristica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoria_consumo`
--

DROP TABLE IF EXISTS `categoria_consumo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `categoria_consumo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cargoFijo` double NOT NULL,
  `cargoVariable` double NOT NULL,
  `consumoMaximo` double NOT NULL,
  `consumoMinimo` double NOT NULL,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_4x72yjufc1iac99pydyp12bio` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria_consumo`
--

LOCK TABLES `categoria_consumo` WRITE;
/*!40000 ALTER TABLE `categoria_consumo` DISABLE KEYS */;
INSERT INTO `categoria_consumo` VALUES (1,18.76,0.644,150,0,'R1'),(2,35.32,0.644,325,150,'R2'),(3,60.71,0.681,400,325,'R3'),(4,71.74,0.738,450,400,'R4'),(5,110.38,0.794,500,450,'R5'),(6,220.75,0.832,600,500,'R6'),(7,443.59,0.851,700,600,'R7'),(8,545.96,0.851,1400,700,'R8'),(9,887.19,0.851,1400,1400,'R9');
/*!40000 ALTER TABLE `categoria_consumo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente_residencial`
--

DROP TABLE IF EXISTS `cliente_residencial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `cliente_residencial` (
  `consumo_en_automatico` bit(1) DEFAULT NULL,
  `fechaAltaServicio` datetime DEFAULT NULL,
  `puntos_acumulados` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `categoria_id` int(11) DEFAULT NULL,
  `transformador_id` int(11) DEFAULT NULL,
  `zona_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_rdu9568kwp23lw74j033aq090` (`categoria_id`),
  KEY `FK_qk0dg5d7ixjwjr7v0r7im5gs6` (`transformador_id`),
  KEY `FK_o9cqwmi3c6vsjvklmt792duu7` (`zona_id`),
  CONSTRAINT `FK_lt3wy505fwvl5sfn80lg2b0by` FOREIGN KEY (`id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `FK_o9cqwmi3c6vsjvklmt792duu7` FOREIGN KEY (`zona_id`) REFERENCES `zona_geografica` (`id`),
  CONSTRAINT `FK_qk0dg5d7ixjwjr7v0r7im5gs6` FOREIGN KEY (`transformador_id`) REFERENCES `transformador` (`id`),
  CONSTRAINT `FK_rdu9568kwp23lw74j033aq090` FOREIGN KEY (`categoria_id`) REFERENCES `categoria_consumo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente_residencial`
--

LOCK TABLES `cliente_residencial` WRITE;
/*!40000 ALTER TABLE `cliente_residencial` DISABLE KEYS */;
INSERT INTO `cliente_residencial` VALUES (_binary '','2018-02-21 21:31:07',15,1,1,NULL,NULL),(_binary '',NULL,15,3,NULL,NULL,NULL);
/*!40000 ALTER TABLE `cliente_residencial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coordenada`
--

DROP TABLE IF EXISTS `coordenada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `coordenada` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `latitud` double DEFAULT NULL,
  `longitud` double DEFAULT NULL,
  `ubicable_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_qdibx011bnc9lam0qi2ybfx6t` (`ubicable_id`),
  CONSTRAINT `FK_qdibx011bnc9lam0qi2ybfx6t` FOREIGN KEY (`ubicable_id`) REFERENCES `ubicable` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coordenada`
--

LOCK TABLES `coordenada` WRITE;
/*!40000 ALTER TABLE `coordenada` DISABLE KEYS */;
INSERT INTO `coordenada` VALUES (1,3.3,4.4,1),(2,-34.1857985,-58.2993031,NULL),(3,-34.1857989,-58.2993033,4);
/*!40000 ALTER TABLE `coordenada` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `criterio`
--

DROP TABLE IF EXISTS `criterio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `criterio` (
  `tipo` varchar(31) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `valorComparacion` int(11) DEFAULT NULL,
  `valorMaximo` int(11) DEFAULT NULL,
  `valorMinimo` int(11) DEFAULT NULL,
  `regla_id` int(11) DEFAULT NULL,
  `sensor_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_dgel3tlqympyx6wjo1d2rm120` (`regla_id`),
  KEY `FK_dy370y2k9abnu4j8tayrx3g5i` (`sensor_id`),
  CONSTRAINT `FK_dgel3tlqympyx6wjo1d2rm120` FOREIGN KEY (`regla_id`) REFERENCES `regla` (`id`),
  CONSTRAINT `FK_dy370y2k9abnu4j8tayrx3g5i` FOREIGN KEY (`sensor_id`) REFERENCES `sensor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `criterio`
--

LOCK TABLES `criterio` WRITE;
/*!40000 ALTER TABLE `criterio` DISABLE KEYS */;
INSERT INTO `criterio` VALUES ('igualdad',1,40,0,0,1,1);
/*!40000 ALTER TABLE `criterio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dispositivo`
--

DROP TABLE IF EXISTS `dispositivo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `dispositivo` (
  `tipo` varchar(31) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(512) DEFAULT NULL,
  `apagabilidad` varchar(255) DEFAULT NULL,
  `caracteristica_id` int(11) DEFAULT NULL,
  `cliente_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_2x0a9bi8yu3uhvtvytnojluhl` (`caracteristica_id`),
  KEY `FK_6m7rbebu37l1f66s4y5y8ccsq` (`cliente_id`),
  CONSTRAINT `FK_2x0a9bi8yu3uhvtvytnojluhl` FOREIGN KEY (`caracteristica_id`) REFERENCES `caracteristica` (`id`),
  CONSTRAINT `FK_6m7rbebu37l1f66s4y5y8ccsq` FOREIGN KEY (`cliente_id`) REFERENCES `cliente_residencial` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dispositivo`
--

LOCK TABLES `dispositivo` WRITE;
/*!40000 ALTER TABLE `dispositivo` DISABLE KEYS */;
INSERT INTO `dispositivo` VALUES ('inteligente',1,'tv','Apagable',6,NULL),('inteligente',2,'luz living','Apagable',21,NULL),('inteligente',3,NULL,NULL,1,1);
/*!40000 ALTER TABLE `dispositivo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `elemento_reporte`
--

DROP TABLE IF EXISTS `elemento_reporte`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `elemento_reporte` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fecha_inicio` datetime DEFAULT NULL,
  `idObjeto` int(11) DEFAULT NULL,
  `tipo` varchar(512) DEFAULT NULL,
  `valor` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `elemento_reporte`
--

LOCK TABLES `elemento_reporte` WRITE;
/*!40000 ALTER TABLE `elemento_reporte` DISABLE KEYS */;
/*!40000 ALTER TABLE `elemento_reporte` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estadodispositivo`
--

DROP TABLE IF EXISTS `estadodispositivo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `estadodispositivo` (
  `tipo` varchar(31) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fechaFin` datetime DEFAULT NULL,
  `fechaInicio` datetime DEFAULT NULL,
  `historial_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7rfrsncgu7ay14iy84l14y8x9` (`historial_id`),
  CONSTRAINT `FK_7rfrsncgu7ay14iy84l14y8x9` FOREIGN KEY (`historial_id`) REFERENCES `historialdeestados` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estadodispositivo`
--

LOCK TABLES `estadodispositivo` WRITE;
/*!40000 ALTER TABLE `estadodispositivo` DISABLE KEYS */;
INSERT INTO `estadodispositivo` VALUES ('encendido',1,'2018-08-21 21:31:24','2018-07-21 21:31:24',1),('encendido',2,'2018-09-28 21:31:24','2018-09-27 21:31:24',1),('apagado',3,'2018-10-19 21:31:24','2018-09-29 21:31:24',1),('encendido',4,'2018-10-21 11:31:24','2018-10-20 21:31:24',1),('apagado',5,NULL,'2018-10-21 21:31:50',NULL),('encendido',6,'2018-10-21 20:32:05','2018-10-21 18:32:05',NULL),('encendido',7,'2018-10-21 15:32:05','2018-10-12 21:32:05',3);
/*!40000 ALTER TABLE `estadodispositivo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historialdeestados`
--

DROP TABLE IF EXISTS `historialdeestados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `historialdeestados` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dispositivo_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_jdefapj7bujfh1yjrnu7fxi0f` (`dispositivo_id`),
  CONSTRAINT `FK_jdefapj7bujfh1yjrnu7fxi0f` FOREIGN KEY (`dispositivo_id`) REFERENCES `inteligente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historialdeestados`
--

LOCK TABLES `historialdeestados` WRITE;
/*!40000 ALTER TABLE `historialdeestados` DISABLE KEYS */;
INSERT INTO `historialdeestados` VALUES (1,1),(2,2),(3,3);
/*!40000 ALTER TABLE `historialdeestados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inteligente`
--

DROP TABLE IF EXISTS `inteligente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `inteligente` (
  `consumoEnModoAutomatico` bit(1) DEFAULT NULL,
  `intensidad` int(11) DEFAULT NULL,
  `puntos` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `estadoActual_id` int(11) DEFAULT NULL,
  `historialEstados_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_4n6mta3owm4smp3yeqn0ighpm` (`estadoActual_id`),
  KEY `FK_1rgc91wlqv1jxwfiypsncal8p` (`historialEstados_id`),
  CONSTRAINT `FK_1rgc91wlqv1jxwfiypsncal8p` FOREIGN KEY (`historialEstados_id`) REFERENCES `historialdeestados` (`id`),
  CONSTRAINT `FK_4n6mta3owm4smp3yeqn0ighpm` FOREIGN KEY (`estadoActual_id`) REFERENCES `estadodispositivo` (`id`),
  CONSTRAINT `FK_9vamfxcxxy2t8midlvf5jtuak` FOREIGN KEY (`id`) REFERENCES `dispositivo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inteligente`
--

LOCK TABLES `inteligente` WRITE;
/*!40000 ALTER TABLE `inteligente` DISABLE KEYS */;
INSERT INTO `inteligente` VALUES (_binary '',24,15,1,NULL,1),(_binary '',30,15,2,5,2),(_binary '\0',0,15,3,6,3);
/*!40000 ALTER TABLE `inteligente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicion`
--

DROP TABLE IF EXISTS `medicion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `medicion` (
  `sensor_id` int(11) NOT NULL,
  `medicion` int(11) DEFAULT NULL,
  KEY `FK_hhv73vkt9t1g6i49xi3dxycce` (`sensor_id`),
  CONSTRAINT `FK_hhv73vkt9t1g6i49xi3dxycce` FOREIGN KEY (`sensor_id`) REFERENCES `sensor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicion`
--

LOCK TABLES `medicion` WRITE;
/*!40000 ALTER TABLE `medicion` DISABLE KEYS */;
/*!40000 ALTER TABLE `medicion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parametro_sistema`
--

DROP TABLE IF EXISTS `parametro_sistema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `parametro_sistema` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(512) NOT NULL,
  `valor` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parametro_sistema`
--

LOCK TABLES `parametro_sistema` WRITE;
/*!40000 ALTER TABLE `parametro_sistema` DISABLE KEYS */;
INSERT INTO `parametro_sistema` VALUES (1,'cantidad mediciones sensor',20),(2,'intervalo optimizador',10),(3,'tiempo optimizador',1000),(4,'retardo reporte',10),(5,'tiempo reporte',1000),(6,'retardo tabla',10),(7,'tiempo tabla',1000);
/*!40000 ALTER TABLE `parametro_sistema` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `regla`
--

DROP TABLE IF EXISTS `regla`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `regla` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(512) DEFAULT NULL,
  `dispositivo_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fp84ol1y2u4o8kwtalpsjwv72` (`dispositivo_id`),
  CONSTRAINT `FK_fp84ol1y2u4o8kwtalpsjwv72` FOREIGN KEY (`dispositivo_id`) REFERENCES `inteligente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regla`
--

LOCK TABLES `regla` WRITE;
/*!40000 ALTER TABLE `regla` DISABLE KEYS */;
INSERT INTO `regla` VALUES (1,'prender luz',2);
/*!40000 ALTER TABLE `regla` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sensor`
--

DROP TABLE IF EXISTS `sensor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sensor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensor`
--

LOCK TABLES `sensor` WRITE;
/*!40000 ALTER TABLE `sensor` DISABLE KEYS */;
INSERT INTO `sensor` VALUES (1);
/*!40000 ALTER TABLE `sensor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sensor_regla`
--

DROP TABLE IF EXISTS `sensor_regla`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sensor_regla` (
  `sensores_id` int(11) NOT NULL,
  `reglas_id` int(11) NOT NULL,
  KEY `FK_o527tfgc3gs3fqgcrhw5xyt77` (`reglas_id`),
  KEY `FK_6sj72vw4ftrm13y4ktxvi5ktg` (`sensores_id`),
  CONSTRAINT `FK_6sj72vw4ftrm13y4ktxvi5ktg` FOREIGN KEY (`sensores_id`) REFERENCES `sensor` (`id`),
  CONSTRAINT `FK_o527tfgc3gs3fqgcrhw5xyt77` FOREIGN KEY (`reglas_id`) REFERENCES `regla` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensor_regla`
--

LOCK TABLES `sensor_regla` WRITE;
/*!40000 ALTER TABLE `sensor_regla` DISABLE KEYS */;
INSERT INTO `sensor_regla` VALUES (1,1),(1,1);
/*!40000 ALTER TABLE `sensor_regla` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `standard`
--

DROP TABLE IF EXISTS `standard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `standard` (
  `consumo_por_hora` double DEFAULT NULL,
  `esta_adaptado` bit(1) DEFAULT NULL,
  `horas_uso_diario` double DEFAULT NULL,
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_q3e8cr46nn6r90iutl6c2frsc` FOREIGN KEY (`id`) REFERENCES `dispositivo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `standard`
--

LOCK TABLES `standard` WRITE;
/*!40000 ALTER TABLE `standard` DISABLE KEYS */;
/*!40000 ALTER TABLE `standard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transformador`
--

DROP TABLE IF EXISTS `transformador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `transformador` (
  `id` int(11) NOT NULL,
  `zona_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_4eqbelrsm9wg0a9enalbqettt` (`zona_id`),
  CONSTRAINT `FK_4eqbelrsm9wg0a9enalbqettt` FOREIGN KEY (`zona_id`) REFERENCES `zona_geografica` (`id`),
  CONSTRAINT `FK_fbvfnxwrucvsy63vsn62um66w` FOREIGN KEY (`id`) REFERENCES `ubicable` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transformador`
--

LOCK TABLES `transformador` WRITE;
/*!40000 ALTER TABLE `transformador` DISABLE KEYS */;
INSERT INTO `transformador` VALUES (2,NULL),(4,NULL);
/*!40000 ALTER TABLE `transformador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ubicable`
--

DROP TABLE IF EXISTS `ubicable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ubicable` (
  `tipo` varchar(31) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ubicable`
--

LOCK TABLES `ubicable` WRITE;
/*!40000 ALTER TABLE `ubicable` DISABLE KEYS */;
INSERT INTO `ubicable` VALUES ('clienteResidencial',1),('transformador',2),('clienteResidencial',3),('transformador',4),('administrador',5);
/*!40000 ALTER TABLE `ubicable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `usuario` (
  `aliasUsuario` varchar(255) DEFAULT NULL,
  `apellido` varchar(255) DEFAULT NULL,
  `contrasenia` varchar(255) DEFAULT NULL,
  `domicilio` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `numeroDocumento` int(11) DEFAULT NULL,
  `salt` tinyblob,
  `telefono` int(11) DEFAULT NULL,
  `tipoDocumento` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_d944kg5rje93f4os2a76qypyy` (`aliasUsuario`),
  CONSTRAINT `FK_ky4fsf2p8cggggirm1b0cajes` FOREIGN KEY (`id`) REFERENCES `ubicable` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES ('kodar','odar','a3349aff8aaaa8efb8ffbd1836bf46fc1741845beda50717c93bfa0a675c663e','cucha cucha 2137','kevin',34325,_binary '€\æŽ\ï\Ó]ðÁŸ¾:Hpq\é}',45883110,'dni',1),(NULL,NULL,NULL,'cucha cucha 2137','cosmeFulanito',0,NULL,4323,'dni',3),('admin','admin','cb86215cb7fd7b2aadd72f03f4fececa529a605dea37eb4a9fa582c5c38fd314',NULL,NULL,0,_binary '\×\èj\êˆ\ÅOÀ¯¯Á—6',0,NULL,5);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zona_geografica`
--

DROP TABLE IF EXISTS `zona_geografica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `zona_geografica` (
  `id` int(11) NOT NULL,
  `cliente_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_i3efwmvrni9mim5apu8qsglaf` (`cliente_id`),
  CONSTRAINT `FK_f9soxabdsrds7mh1oh2glxlqc` FOREIGN KEY (`id`) REFERENCES `ubicable` (`id`),
  CONSTRAINT `FK_i3efwmvrni9mim5apu8qsglaf` FOREIGN KEY (`cliente_id`) REFERENCES `cliente_residencial` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zona_geografica`
--

LOCK TABLES `zona_geografica` WRITE;
/*!40000 ALTER TABLE `zona_geografica` DISABLE KEYS */;
/*!40000 ALTER TABLE `zona_geografica` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-10-21 18:33:25
