CREATE DATABASE  IF NOT EXISTS `cesaretransportes` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `cesaretransportes`;
-- MySQL dump 10.13  Distrib 5.5.28, for debian-linux-gnu (i686)
--
-- Host: localhost    Database: cesaretransportes
-- ------------------------------------------------------
-- Server version	5.5.28-0ubuntu0.12.04.2

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
INSERT INTO `veiculo` VALUES (1,'renault','branca','AJR2732','rua padre anchieta 142, são domingos, niteroi rj',NULL),(2,'honda','azul','APP1234','av visconde do rio branco 123, centro, niteroi rj',NULL),(3,'fiat','preta','AAA1111','rua chile 300, centro, rio de janeiro rj',NULL);
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
  PRIMARY KEY (`idEmpresa`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empresa`
--

LOCK TABLES `empresa` WRITE;
/*!40000 ALTER TABLE `empresa` DISABLE KEYS */;
INSERT INTO `empresa` VALUES (1,'cesare transportes ltda','06185954000100','cesaretransportes@gmail.com','53340276','cesaretransportes@hotmail.com'),(2,'cesar de sousa junior','00000000000000','cesarsonline@gmail.com','cesarsousa','cesar_sj@hotmail.com');
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
  `numero` varchar(8) NOT NULL,
  `complemento` varchar(50) NOT NULL,
  PRIMARY KEY (`idTelefone`),
  KEY `idCliente` (`idCliente`),
  KEY `idEmpresa` (`idEmpresa`)
) ENGINE=MyISAM AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `telefone`
--

LOCK TABLES `telefone` WRITE;
/*!40000 ALTER TABLE `telefone` DISABLE KEYS */;
INSERT INTO `telefone` VALUES (1,1,0,'41','99212513','TIM'),(2,1,0,'41','84889261','OI'),(3,1,0,'41','78136520','Nxl ID 82*26299'),(11,0,10,'56','89698436',''),(4,0,1,'21','98386616','24 HRS'),(12,0,11,'41','64578786',''),(13,0,12,'21','53426265','das 12 as 17 hs'),(14,0,13,'21','54758356',''),(15,0,14,'21','90789478',''),(16,0,15,'21','54454545',''),(5,2,0,'21','98386616','OI'),(6,2,0,'21','26224088','OI'),(7,2,0,'21','26224088','OI'),(17,0,17,'21','98386616',' ');
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
) ENGINE=MyISAM AUTO_INCREMENT=75 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `endereco`
--

LOCK TABLES `endereco` WRITE;
/*!40000 ALTER TABLE `endereco` DISABLE KEYS */;
INSERT INTO `endereco` VALUES (1,1,0,0,'são josé dos pinhais','PR','rua alzira miranda koerbel 140, afonso pena',0),(70,0,0,38,'rio de janeiro','RJ','rua chile 100, centro',2),(69,0,0,38,'niteroi','RJ','rua amaral peixoto 43, centro',1),(68,0,15,0,'rio de janeiro','RJ','rua do ouvidor 333, centro',0),(67,0,14,0,'curitiba','PR','rua das flores 22, centro',0),(66,0,0,37,'rio de janeiro','RJ','rua do ouvidor 88, centro',2),(65,0,0,37,'niteroi','RJ','rua padre anchieta 142, centro',1),(64,0,0,36,'rio de janeiro','RJ','rua chile 300, centro',2),(63,0,0,36,'niteroi','RJ','rua padre anchieta 142, centro',1),(62,0,13,0,'niteroi','AC','rua  padre anchieta 146, centro',0),(61,0,12,0,'rio de janeiro','RJ','rua da ribeira 300, centro',0),(60,0,0,35,'niteroi','RJ','rua padre anchieta 142, centro',2),(59,0,0,35,'rio de janeiro','RJ','rua xv de novembro, centro',1),(58,0,0,34,'londrina','PR','rua salgado morreira 321, centro',2),(57,0,0,34,'maringa','AC','rua das bandeiras queimadas 234, jardim da saudade',1),(56,0,0,33,'curitiba','PR','rua joão negrão 342, centro',2),(55,0,0,33,'curitiba','PR','rua emili furnam 88, borda do campo',1),(54,0,11,0,'curitba','PR','emilio furnam 88, borda do campo',0),(53,0,0,32,'maringa','PR','rua salgado morreira 321, centro',2),(52,0,0,32,'curitiba','PR','rua parana 25, agua verde',1),(51,0,0,31,'niteroi','RJ','av do cotorno 1250, barreto',2),(3,0,10,0,'niteroi','PR','rua padre anchieta, 142, centro',0),(50,0,0,31,'niteroi','RJ','rua padre anchieta 142, centro',1),(2,0,1,0,'niteroi','PR','rua padre anchieta, 142, centro',0),(10,2,0,0,'niteroi','RJ','rua padre anchieta, 142, centro',0),(71,0,0,39,'niteroi','RJ','rua padre anchieta, 142 centro',1),(72,0,0,39,'curitiba','PR','estrada da roseira 432, borda do campo',2),(73,0,0,40,'curitiba','AC','rua padre anchieta, 142 centro',1),(74,0,0,40,'rio de janeiro','AC','estrada da roseira 432, borda do campo',2);
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
INSERT INTO `servico` VALUES (1,31,1,300,'2011-12-20 00:00:00',NULL,NULL);
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
) ENGINE=MyISAM AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'admin','cpf','06534589754','c','c','2011-12-04 00:00:00','true',NULL,'A'),(14,'pamela da silva','cnpj','09567856789567','pamela@ig.com.br','pam','2011-12-18 00:00:00','false',NULL,'C'),(13,'fernanda limeira','cnpj','56708967689478','fernada@pop.com.br','fer','2011-12-18 00:00:00','false',NULL,'C'),(12,'carlos cavalcanti de paula','cnpj','65465776765578','carlosCavalcanti@hotmail.com','ca','2011-12-18 00:00:00','true',NULL,'C'),(11,'karoline sgode felisbino','cpf','76852345213','karol@gmail.com','karol','2011-12-18 00:00:00','true',NULL,'C'),(10,'cesar martins','cnpj','76986785686789','cesar_sj@hotmail.com','cesar','2011-12-18 00:00:00','true',NULL,'C'),(15,'joao da silva','cnpj','43235545544545','jj@gmail.com','joao','2011-12-19 00:00:00','true',NULL,'C'),(16,'joao maria jose da silva','na',NULL,'cesar_sj@hotmail.com',NULL,'2012-12-14 00:00:00','false',NULL,'U'),(17,'joao maria jose da silva','na',NULL,'cesar_sj@hotmail.com',NULL,'2012-12-14 00:00:00','false',NULL,'U');
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
  `mensagem` varchar(255) DEFAULT NULL,
  `orcamentoLido` varchar(5) NOT NULL,
  `orcamentoRespondido` varchar(5) NOT NULL,
  `dataCadastro` datetime NOT NULL,
  `dataExclusao` datetime DEFAULT NULL,
  PRIMARY KEY (`idOrcamento`),
  KEY `idCliente` (`idCliente`)
) ENGINE=MyISAM AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orcamento`
--

LOCK TABLES `orcamento` WRITE;
/*!40000 ALTER TABLE `orcamento` DISABLE KEYS */;
INSERT INTO `orcamento` VALUES (31,10,'200 k','2m2','','true','false','2011-12-18 00:00:00',NULL),(32,10,'200k','2m2','valor total do serviço','true','false','2011-12-18 00:00:00',NULL),(33,11,'2k','2m2','custo do frete, e tempo de percurso','true','false','2011-12-18 00:00:00',NULL),(34,11,'300k','2m x 50cm','','true','false','2011-12-18 00:00:00',NULL),(35,10,'1000 kilos','300 cm2','10 caixas de 30 cm cada.','true','false','2011-12-18 00:00:00','2011-12-18 00:00:00'),(36,12,'100 kilos.','1m2','Res: valor do frete 200 reais.\n\n Recebida de: carlos cavalcanti de paula (carlosCavalcanti@hotmail.com). Em: 18/12/2011\n\nMESAGEM ORIGINAL: ','true','true','2011-12-18 00:00:00',NULL),(37,10,'50 kilos','50 cm2','material de teste para selecao de candidatos a emprego','true','false','2011-12-18 00:00:00',NULL),(38,15,'20 k','1m2','','false','false','2011-12-19 00:00:00',NULL),(39,0,'1000 kilos','10 x 10 cm','Total de 10 caixas.','false','false','2012-12-14 00:00:00',NULL),(40,17,'1000 kilos','10 x 10 cm','total de 10 caixas','false','false','2012-12-14 00:00:00',NULL);
/*!40000 ALTER TABLE `orcamento` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-12-14 23:19:37
