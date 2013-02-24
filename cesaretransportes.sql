CREATE DATABASE  IF NOT EXISTS `cesaretransportes` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `cesaretransportes`;
-- MySQL dump 10.13  Distrib 5.5.29, for debian-linux-gnu (i686)
--
-- Host: localhost    Database: cesaretransportes
-- ------------------------------------------------------
-- Server version	5.5.29-0ubuntu0.12.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `veiculo`
--

DROP TABLE IF EXISTS `veiculo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `veiculo` (
  `idVeiculo` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `marca` varchar(50) NOT NULL,
  `cor` varchar(50) NOT NULL,
  `placa` varchar(7) NOT NULL,
  `localizacao` varchar(255) DEFAULT NULL,
  `dataExclusao` datetime DEFAULT NULL,
  PRIMARY KEY (`idVeiculo`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `veiculo`
--

LOCK TABLES `veiculo` WRITE;
/*!40000 ALTER TABLE `veiculo` DISABLE KEYS */;
/*!40000 ALTER TABLE `veiculo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empresa`
--

DROP TABLE IF EXISTS `empresa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `empresa` (
  `idEmpresa` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `cnpj` varchar(14) NOT NULL,
  `email` varchar(255) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `msn` varchar(255) NOT NULL,
  `mostrarMapa` varchar(5) NOT NULL,
  `localizacao` varchar(1500) NOT NULL,
  PRIMARY KEY (`idEmpresa`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empresa`
--

LOCK TABLES `empresa` WRITE;
/*!40000 ALTER TABLE `empresa` DISABLE KEYS */;
INSERT INTO `empresa` VALUES (1,'cesar de sousa junior','00000000000000','cesarsonline@gmail.com','cesarsousa','cesar_sj@hotmail.com','false','');
/*!40000 ALTER TABLE `empresa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `telefone`
--

DROP TABLE IF EXISTS `telefone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `telefone` (
  `idTelefone` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idEmpresa` int(10) unsigned NOT NULL,
  `idCliente` int(10) unsigned NOT NULL,
  `ddd` varchar(3) NOT NULL,
  `numero` varchar(15) NOT NULL,
  `complemento` varchar(50) NOT NULL,
  PRIMARY KEY (`idTelefone`),
  KEY `idCliente` (`idCliente`),
  KEY `idEmpresa` (`idEmpresa`)
) ENGINE=MyISAM AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `telefone`
--

LOCK TABLES `telefone` WRITE;
/*!40000 ALTER TABLE `telefone` DISABLE KEYS */;
INSERT INTO `telefone` VALUES (1,1,0,'41','99212513','TIM'),(2,1,0,'41','84889261','OI'),(3,1,0,'41','78136520','Nxl ID 82*26299');
/*!40000 ALTER TABLE `telefone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `endereco`
--

DROP TABLE IF EXISTS `endereco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `endereco` (
  `idEndereco` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idEmpresa` int(10) unsigned NOT NULL,
  `idCliente` int(10) unsigned NOT NULL,
  `idOrcamento` int(10) unsigned NOT NULL,
  `cidade` varchar(50) NOT NULL,
  `estado` varchar(2) NOT NULL,
  `localizacao` varchar(203) NOT NULL,
  `statusEndereco` int(10) unsigned NOT NULL,
  PRIMARY KEY (`idEndereco`),
  KEY `idOrcamento` (`idOrcamento`),
  KEY `idEmpresa` (`idEmpresa`),
  KEY `idCliente` (`idCliente`)
) ENGINE=MyISAM AUTO_INCREMENT=83 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `endereco`
--

LOCK TABLES `endereco` WRITE;
/*!40000 ALTER TABLE `endereco` DISABLE KEYS */;
INSERT INTO `endereco` VALUES (1,1,0,0,'niteroi','RJ','rua padre anchieta 142, centro',0);
/*!40000 ALTER TABLE `endereco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servico`
--

DROP TABLE IF EXISTS `servico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `servico` (
  `idServico` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idOrcamento` int(10) unsigned NOT NULL,
  `idVeiculo` int(10) unsigned NOT NULL,
  `valor` decimal(10,0) NOT NULL,
  `dataPrevEntrega` datetime NOT NULL,
  `dataEntrega` datetime DEFAULT NULL,
  `dataExclusao` datetime DEFAULT NULL,
  PRIMARY KEY (`idServico`,`idOrcamento`),
  KEY `idVeiculo` (`idVeiculo`),
  KEY `idOrcamento` (`idOrcamento`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servico`
--

LOCK TABLES `servico` WRITE;
/*!40000 ALTER TABLE `servico` DISABLE KEYS */;
/*!40000 ALTER TABLE `servico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
  `idCliente` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `tipoDoc` varchar(4) NOT NULL,
  `numDoc` varchar(14) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `dataCadastro` datetime NOT NULL,
  `statusCliente` varchar(5) NOT NULL,
  `dataExclusao` datetime DEFAULT NULL,
  `tipoCliente` varchar(1) NOT NULL,
  PRIMARY KEY (`idCliente`)
) ENGINE=MyISAM AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orcamento`
--

DROP TABLE IF EXISTS `orcamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orcamento` (
  `idOrcamento` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idCliente` int(10) unsigned NOT NULL,
  `peso` varchar(255) NOT NULL,
  `dimensao` varchar(255) NOT NULL,
  `mensagem` varchar(2000) DEFAULT NULL,
  `orcamentoLido` varchar(5) NOT NULL,
  `orcamentoRespondido` varchar(5) NOT NULL,
  `dataCadastro` datetime NOT NULL,
  `dataExclusao` datetime DEFAULT NULL,
  PRIMARY KEY (`idOrcamento`),
  KEY `idCliente` (`idCliente`)
) ENGINE=MyISAM AUTO_INCREMENT=45 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orcamento`
--

LOCK TABLES `orcamento` WRITE;
/*!40000 ALTER TABLE `orcamento` DISABLE KEYS */;
/*!40000 ALTER TABLE `orcamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'cesaretransportes'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-02-23 18:43:13