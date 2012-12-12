/*
 * os scripts abaixo geram um modelo não normalizado. Para gerar um modelo
 * normalizado utilize os scripts do arquivo cetrans2.sql
 */

/*
 * script gerado pelo DBDesigner.
 */

CREATE DATABASE `cesaretransportes`;

CREATE TABLE Veiculo (
  idVeiculo INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  marca VARCHAR NULL,
  cor VARCHAR NULL,
  placa VARCHAR NULL,
  localizacao VARCHAR NULL,
  dataExclusao DATETIME NULL,
  PRIMARY KEY(idVeiculo)
);

CREATE TABLE Empresa (
  idEmpresa INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  nome VARCHAR NULL,
  endereco VARCHAR NULL,
  cnpj VARCHAR NULL,
  email VARCHAR NULL,
  senha VARCHAR NULL,
  msn VARCHAR NULL,
  telefone1 VARCHAR NULL,
  telefone2 VARCHAR NULL,
  nextel VARCHAR NULL,
  PRIMARY KEY(idEmpresa)
);

CREATE TABLE Cliente (
  idCliente INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  nome VARCHAR NULL,
  tipoDoc VARCHAR NULL,
  numDoc VARCHAR NULL,
  telefone VARCHAR NULL,
  email VARCHAR NULL,
  senha VARCHAR NULL,
  dataCadastro DATETIME NULL,
  statusCliente VARCHAR NULL,
  dataExclusao DATETIME NULL,
  tipoCliente VARCHAR NULL,
  PRIMARY KEY(idCliente)
);

CREATE TABLE Orcamento (
  idOrcamento INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Veiculo_idVeiculo INTEGER UNSIGNED NOT NULL,
  Cliente_idCliente INTEGER UNSIGNED NOT NULL,
  nome VARCHAR NULL,
  email VARCHAR NULL,
  telefone VARCHAR NULL,
  origem VARCHAR NULL,
  enderecoOrigem VARCHAR NULL,
  destino VARCHAR NULL,
  enderecoDestino VARCHAR NULL,
  peso VARCHAR NULL,
  dimensão VARCHAR NULL,
  mensagem VARCHAR NULL,
  valor VARCHAR NULL,
  orcamentoLido VARCHAR NULL,
  orcamentoSalvo VARCHAR NULL,
  dataCadastro DATETIME NULL,
  dataEntrega DATETIME NULL,
  dataPrevEntrega DATETIME NULL,
  dataExclusao DATETIME NULL,
  PRIMARY KEY(idOrcamento),
  FOREIGN KEY(Cliente_idCliente)
    REFERENCES Cliente(idCliente)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Veiculo_idVeiculo)
    REFERENCES Veiculo(idVeiculo)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

/*
 * script gerado pelo phpMyAdmin
 */
-- phpMyAdmin SQL Dump
-- version 3.2.0.1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tempo de Geração: Dez 04, 2011 as 08:06 PM
-- Versão do Servidor: 5.1.36
-- Versão do PHP: 5.3.0

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Banco de Dados: `cesaretransportes`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `cliente`
--

CREATE TABLE IF NOT EXISTS `cliente` (
  `idCliente` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` varchar(10) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `telefone` varchar(10) NOT NULL,
  `email` varchar(255) NOT NULL,
  `statusCliente` varchar(5) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `tipoDoc` varchar(4) NOT NULL,
  `numDoc` varchar(14) NOT NULL,
  `dataCadastro` datetime NOT NULL,
  `dataExclusao` datetime DEFAULT NULL,
  PRIMARY KEY (`idCliente`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=33 ;

--
-- Extraindo dados da tabela `cliente`
--

INSERT INTO `cliente` (`idCliente`, `tipo`, `nome`, `telefone`, `email`, `statusCliente`, `senha`, `tipoDoc`, `numDoc`, `dataCadastro`, `dataExclusao`) VALUES
(3, 'cliente', 'cesar de sousa junior', '4198396616', 'cesarsonline@gmail.com', 'true', 'cesar', 'cpf', '02676563947', '2010-10-21 12:32:45', NULL),
(11, 'cliente', 'karoline sgode felisbino', '4132435678', 'karol@gmail.com', 'false', 'karol', 'cpf', '75643289087', '2011-01-01 00:00:01', NULL),
(27, 'cliente', 'cesar', '2198386616', 'cesar_sj@hotmail.com', 'true', 'c', 'cnpj', '47564388465984', '2011-11-18 00:00:00', '2011-11-26 00:00:00'),
(22, 'cliente', 'alcindo miguel', '2196011143', 'alcindomiguel@uol.com.br', 'true', 'miguel', 'cnpj', '85483476596488', '2011-10-23 00:00:00', '2011-11-26 00:00:00'),
(1, 'admin', 'administrador', '4199999999', 'c', 'true', 'c', 'cnpj', '06185954000100', '2010-11-01 00:00:00', NULL);

-- --------------------------------------------------------

--
-- Estrutura da tabela `contato`
--

CREATE TABLE IF NOT EXISTS `contato` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `mensagem` text NOT NULL,
  `data` varchar(20) NOT NULL,
  `emailLido` varchar(3) NOT NULL,
  `emailSalvo` varchar(3) NOT NULL,
  `idCliente` int(11) DEFAULT NULL,
  `dataExclusao` datetime DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `idCliente` (`idCliente`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=29 ;

--
-- Extraindo dados da tabela `contato`
--

INSERT INTO `contato` (`codigo`, `nome`, `email`, `mensagem`, `data`, `emailLido`, `emailSalvo`, `idCliente`, `dataExclusao`) VALUES
(9, 'cesar de sousa junior', 'cesarsonline@gmail.com', 'ola testando envio de mensagem dia 1 de setembro de 2011.', '01/09/2011 11:26:01', 'sim', 'sim', NULL, '2011-11-12 00:00:00'),
(11, 'joao de sousa', 'joaosousao@gmail.com', 'oi', '02/09/2011 10:01:26', 'sim', 'nao', NULL, NULL),
(12, 'cesar de sousa junior', 'cesarsonline@gmail.com', 'verifique se o id do cliente não esta null.', '15/10/2011 09:12:56', 'sim', 'nao', 3, NULL),
(25, 'cesar de sousa junior', 'cesarsonline@gmail.com', 'outro teste', '07/11/2011 03:18:15', 'nao', 'nao', 3, NULL),
(22, 'cesar de sousa junior', 'cesarsonline@gmail.com', 'Res: oi.\n\n Recebida de: cesar de sousa junior (cesarsonline@gmail.com). Em: 01/11/2011 06:12:18\n\nMENSAGEM ORIGINAL: 313', '01/11/2011 06:12:18', 'sim', 'nao', 3, '2011-11-12 00:00:00'),
(23, 'cesar de sousa junior', 'cesarsonline@gmail.com', 'ola', '02/11/2011 04:44:50', 'nao', 'nao', 3, NULL),
(24, 'cesar de sousa junior', 'cesarsonline@gmail.com', 'testando tela de espera', '07/11/2011 03:17:04', 'nao', 'sim', 3, NULL),
(18, 'cesar de sousa junior', 'cesarsonline@gmail.com', 'email enviado pelo android', '25/10/2011 11:40:46', 'nao', 'nao', 3, NULL),
(21, 'cesar', 'cesarsonline@gmail.com', 'Res: cdfvmkdlidoljdhfkjrwetfiw.\n\n Recebida de: cesar (cesarsonline@gmail.com). Em: 29/10/2011 08:00:35\n\nMENSAGEM ORIGINAL: fefwefew', '29/10/2011 08:00:35', 'sim', 'nao', NULL, '2011-11-12 00:00:00'),
(26, 'cesar de sousa junior', 'cesarsonline@gmail.com', 'teste com exclusão', '12/11/2011 12:39:39', 'nao', 'nao', 3, '2011-11-12 00:00:00'),
(27, 'cesar de sousa junior', 'cesarsonline@gmail.com', 'este email esta sendo enviado pela aplicação web...', '22/11/2011 02:02:24', 'nao', 'nao', 3, NULL),
(28, 'cesar de sousa junior', 'cesarsonline@gmail.com', 'Res: reposta de email teste de RequestWindow.\n\n Recebida de: cesar de sousa junior (cesarsonline@gmail.com). Em: 22/11/2011 02:06:26\n\nMENSAGEM ORIGINAL: este email esta sendo enviado pelo aplicativo mobile...', '22/11/2011 02:06:26', 'sim', 'nao', 3, NULL);

-- --------------------------------------------------------

--
-- Estrutura da tabela `empresa`
--

CREATE TABLE IF NOT EXISTS `empresa` (
  `idEmpresa` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `endereco` varchar(255) NOT NULL,
  `cnpj` varchar(14) NOT NULL,
  `msn` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `telefone1` varchar(10) NOT NULL,
  `telefone2` varchar(10) NOT NULL,
  `nextel` varchar(255) NOT NULL,
  PRIMARY KEY (`idEmpresa`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Extraindo dados da tabela `empresa`
--

INSERT INTO `empresa` (`idEmpresa`, `nome`, `endereco`, `cnpj`, `msn`, `email`, `senha`, `telefone1`, `telefone2`, `nextel`) VALUES
(1, 'cesare transportes ltda.', 'rua alzira miranda koerbel 140, afonso pena, são josé dos pinhais', '06185954000100', 'cesaretransportes@hotmail.com', 'cesaretransportes@gmail.com', '53340276', '2196011143', '2184841143', '41 7813-6520 ID 82*26299');

-- --------------------------------------------------------

--
-- Estrutura da tabela `orcamento`
--

CREATE TABLE IF NOT EXISTS `orcamento` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `telefone` varchar(15) NOT NULL,
  `origem` varchar(100) NOT NULL,
  `enderecoOrigem` varchar(255) NOT NULL,
  `destino` varchar(100) NOT NULL,
  `enderecoDestino` varchar(255) NOT NULL,
  `peso` varchar(100) NOT NULL,
  `dimensao` varchar(100) NOT NULL,
  `mensagem` text,
  `data` varchar(20) NOT NULL,
  `valor` varchar(100) DEFAULT NULL,
  `orcamentoLido` varchar(3) NOT NULL,
  `orcamentoSalvo` varchar(3) NOT NULL,
  `idCliente` int(11) DEFAULT NULL,
  `idVeiculo` int(11) DEFAULT NULL,
  `dataEntrega` datetime DEFAULT NULL,
  `dataPrevEntrega` datetime DEFAULT NULL,
  `dataExclusao` datetime DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `idCliente` (`idCliente`),
  KEY `idVeiculo` (`idVeiculo`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=47 ;

--
-- Extraindo dados da tabela `orcamento`
--

INSERT INTO `orcamento` (`codigo`, `nome`, `email`, `telefone`, `origem`, `enderecoOrigem`, `destino`, `enderecoDestino`, `peso`, `dimensao`, `mensagem`, `data`, `valor`, `orcamentoLido`, `orcamentoSalvo`, `idCliente`, `idVeiculo`, `dataEntrega`, `dataPrevEntrega`, `dataExclusao`) VALUES
(11, 'cesar de sousa junior', 'cesarsonline@gmail.com', '2198386616', 'rio de janeiro - RJ', '', 'curitiba - PR', '', '300 kilos', '2 x 3 metros', 'Res: 300 reais.\n\n Recebida de: cesar de sousa junior (cesarsonline@gmail.com). Em: 16/06/2011 07:22:48\n\nMESAGEM ORIGINAL: produto pesado', '16/06/2011 07:22:48', '100,00', 'sim', 'sim', NULL, 57, '2000-01-01 00:00:00', '2011-12-10 00:00:00', NULL),
(14, 'voe rapido ltda', 'voerapido@uol.com.br', '2176512309', 'guarulhos - SP', '', 'curitiba - PR', '', '1 ton', '1m', 'Res: 350 reais   pedagio   ajudante = 1000 reais..\n\n Recebida de: voe rapido ltda (voerapido@uol.com.br). Em: 02/09/2011 10:00:23\n\nMESAGEM ORIGINAL: valores de fretes', '02/09/2011 10:00:23', '280,00', 'sim', 'nao', NULL, 57, '2011-11-07 00:00:00', '2011-12-10 00:00:00', NULL),
(19, 'cesar de sousa junior', 'cesarsonline@gmail.com', '4198396616', 'curitiba - PR', '', 'palmas - TO', '', '2 ton', '3 m2', 'Res: 2 mil reais mais pedagio.\n\n Recebida de: cesar de sousa junior (cesarsonline@gmail.com). Em: 15/10/2011 09:00:38\n\nMESAGEM ORIGINAL: ', '15/10/2011 09:00:38', '2000,00', 'sim', 'nao', 3, 57, '2011-11-07 00:00:00', '2011-12-10 00:00:00', '2011-11-21 00:00:00'),
(21, 'fernnado rabelo', 'fer@gmail.com', '2158754378', 'ctba - PR', '', 'rio de janeiro - RJ', '', '300 k', ' 3 m2', 'Res: 2000 reais.\n\n Recebida de: fernnado rabelo (fer@gmail.com). Em: 18/10/2011 02:40:42\n\nMESAGEM ORIGINAL: quanto é tudo?', '18/10/2011 02:40:42', '110,00', 'sim', 'nao', NULL, 57, '2011-11-07 00:00:00', '2011-12-10 00:00:00', NULL),
(31, 'cesar de sousa junior', 'cesar_sj@hotmail.com', '2148758435', 'rio de janeiro - RJ', '', 'são paulo - SP', '', '350 k', '1m x 2m', 'Res: frete R$ 1000\r\npedágio R$ 100\r\najudante R$ 50 .\n\n Recebida de: cesar de sousa junior (cesar_sj@hotmail.com). Em: 28/10/2011 06:21:10\n\nMESAGEM ORIGINAL: preço do frete, pedágio e ajundante.', '28/10/2011 06:21:10', '200,00', 'sim', 'nao', NULL, 2, '2011-11-22 00:00:00', '2011-12-10 00:00:00', NULL),
(27, 'cesar de sousa junior', 'cesarsonline@gmail.com', '2198386616', 'rio de janeiro - RJ', '', 'curitiba - PR', '', '1 ton', '1m x 2m2', 'Res: 3000 reais.\n\n Recebida de: cesar de sousa junior (cesarsonline@gmail.com). Em: 26/10/2011 09:17:07\n\nMESAGEM ORIGINAL: ', '26/10/2011 09:17:07', '300,00', 'sim', 'nao', NULL, 2, NULL, '2011-12-10 00:00:00', NULL),
(29, 'cesar de sousa junior', 'cesarsonline@gmail.com', '2198386616', 'rio de janeiro - RJ', '', 'curitiba - PR', '', '1 ton', '1m x 2m2', 'Res: no mesage at all.\n\n Recebida de: cesar de sousa junior (cesarsonline@gmail.com). Em: 26/10/2011 09:20:59\n\nMESAGEM ORIGINAL: ', '26/10/2011 09:20:59', '100', 'sim', 'nao', NULL, 18, NULL, '2011-12-10 00:00:00', NULL),
(30, 'cesar de sousa junior', 'cesarsonline@gmail.com', '4198396616', 'curitiba - RJ', '', 'rio de janeiro - RJ', '', '1 ton', '2m2', 'Res: resposta enviada pelo site... .\n\n Recebida de: cesar de sousa junior (cesarsonline@gmail.com). Em: 26/10/2011 11:44:32\n\nMESAGEM ORIGINAL: orcamento de servico enviado pelo mobile', '26/10/2011 11:44:32', '2000', 'sim', 'nao', 3, 18, NULL, '2011-12-10 00:00:00', NULL),
(34, 'camponesa transportes', 'cesarsonline@gmail.com', '2157483694', 'curitiba - PR', 'rua tibagi 300', 'apucarana - PR', 'rua dos padres 27', '3k', '2m2', 'Res: 3000 reais.\n\n Recebida de: camponesa transportes (cesarsonline@gmail.com). Em: 01/11/2011 01:02:34\n\nMESAGEM ORIGINAL: valor total do frete', '01/11/2011 01:02:34', '234,00', 'sim', 'sim', NULL, 2, NULL, '2011-12-10 00:00:00', NULL),
(35, 'cesar de sousa junior', 'cesarsonline@gmail.com', '4198396616', 'congonhas - AC', 'av paulista 2342', 'rio de janeiro - AC', 'av nossa senhora de copacabana 32', '400 kilos', '2m2', 'Res: 2000 reais.\n\n Recebida de: cesar de sousa junior (cesarsonline@gmail.com). Em: 01/11/2011 01:26:55\n\nMESAGEM ORIGINAL: ', '01/11/2011 01:26:55', '500,00', 'sim', 'nao', 3, 2, '2011-11-24 00:00:00', '2011-12-10 00:00:00', NULL),
(36, 'cesar de sousa junior', 'cesarsonline@gmail.com', '4198396616', 'curitiba - RJ', 'rua parana 89', 'rio de janeiro - RJ', 'rua padre anchieta 142', '2 kilos', '2 m2', 'Res: 2000 reais.\n\n Recebida de: cesar de sousa junior (cesarsonline@gmail.com). Em: 02/11/2011 12:27:11\n\nMESAGEM ORIGINAL: ', '02/11/2011 12:27:11', '1050,00', 'sim', 'nao', 3, 2, NULL, '2011-12-10 00:00:00', NULL),
(39, 'alcindo miguel', 'alcindomiguel@uol.com.br', '2196011143', 'rio de janeiro - RJ', 'rua padre anchieta 142', 'curitiba - PR', 'rua parana 88', '300 kilos', '2m x 1.5m', 'Res: 300 reais.\n\n Recebida de: alcindo miguel (alcindomiguel@uol.com.br). Em: 05/11/2011 12:23:23\n\nMESAGEM ORIGINAL: valor do frete e dos pedágios.', '05/11/2011 12:23:23', NULL, 'sim', 'nao', 22, NULL, NULL, '2011-12-10 00:00:00', NULL),
(40, 'alcindo miguel', 'alcindomiguel@uol.com.br', '2196011143', 'santos - SP', 'rua araruama 39', 'tocantis - TO', 'rua da esperança 4234', '3k', '3m2', '', '05/11/2011 01:13:12', '400,00', 'sim', 'nao', 22, 2, '2011-11-22 00:00:00', '2011-12-10 00:00:00', NULL),
(41, 'alcindo miguel', 'alcindomiguel@uol.com.br', '2196011143', 'maringa - PR', 'rua das palmeiras queimadas 300', 'curitiba - PR', 'rua flores em chamas 233', '3 kilos', '1 metro quadrado', '', '07/11/2011 01:43:42', '300', 'sim', 'nao', 22, 18, '2011-11-10 00:00:00', '2011-12-10 00:00:00', NULL),
(42, 'cesar de sousa junior', 'cesarsonline@gmail.com', '4198396616', 'dfdgfu - AC', 'euggwut', 'rfwret - AC', 'gregter', 'tretrt', 'tertret', '', '18/11/2011 06:36:57', NULL, 'sim', 'nao', 3, NULL, NULL, '2011-12-10 00:00:00', '2011-11-21 00:00:00'),
(43, 'cesar de sousa junior', 'cesarsonline@gmail.com', '4198396616', 'teresopolis - PR', 'rua das palmeiras 123', 'curitiba - PR', 'rua parana 88', '200 kilos', '2m2va', 'valor do frete e dos pedagios', '21/11/2011 02:01:30', NULL, 'sim', 'nao', 3, NULL, NULL, '2011-12-10 00:00:00', NULL),
(44, 'cesar', 'cesar_sj@hotmail.com', '4348386463', 'mandaguari - RJ', 'rua joaquim ribeiro 23', 'rio de janeiro - RJ', 'rua padre anchieta 142', '1000 k', '1m2', 'Res: não efetuamos transportes de caixas de 250 cm2.\n\n Recebida de: cesar (cesar_sj@hotmail.com). Em: 21/11/2011 02:13:14\n\nMESAGEM ORIGINAL: 4 caixas de 250 cm2', '21/11/2011 02:13:14', NULL, 'sim', 'nao', 27, NULL, NULL, '2011-12-10 00:00:00', NULL),
(45, 'cesar de sousa junior', 'cesarsonline@gmail.com', '4198396616', 'niteroi - RJ', 'rua noronha torrezao 590, santa rosa', 'niteroi - RJ', 'rua padre anchieta 142, centro', '100kilos', '1m2', 'Res: 300 reais.\n\n Recebida de: cesar de sousa junior (cesarsonline@gmail.com). Em: 28/11/2011 09:58:58\n\nMESAGEM ORIGINAL: valor do frete.', '28/11/2011 09:58:58', '500', 'sim', 'nao', 3, 2, '2011-11-29 00:00:00', '2011-12-10 00:00:00', NULL),
(46, 'cesar de sousa junior', 'cesarsonline@gmail.com', '4198396616', 'rio de janeiro - PR', 'rua da conceicao, niteroi', 'curitiba - PR', 'rua sete de setembro, agua verde', '2200  k', '2m2', '', '01/12/2011 07:46:11', NULL, 'nao', 'nao', 3, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Estrutura da tabela `veiculo`
--

CREATE TABLE IF NOT EXISTS `veiculo` (
  `idVeiculo` int(11) NOT NULL AUTO_INCREMENT,
  `marca` varchar(255) NOT NULL,
  `cor` varchar(20) NOT NULL,
  `placa` varchar(10) NOT NULL,
  `localizacao` varchar(255) DEFAULT NULL,
  `dataExclusao` datetime DEFAULT NULL,
  PRIMARY KEY (`idVeiculo`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=60 ;

--
-- Extraindo dados da tabela `veiculo`
--

INSERT INTO `veiculo` (`idVeiculo`, `marca`, `cor`, `placa`, `localizacao`, `dataExclusao`) VALUES
(2, 'volks', 'branco', 'GET4324', 'rua padre anchieta 142, niteroi rj', NULL),
(18, 'honda', 'prata', 'FSD4342', 'rua parana 88, borda do campo, são josé dos pinhais pr', NULL),
(13, 'ford', 'amarela', 'GRG4534', 'rua parana 88, curitiba - pr', NULL),
(57, 'renault', 'branca', 'VHH1111', 'rua sorocaba, botafogo. rio de janeiro rj', NULL);

/*
 * script gerado pelo mysql workbench
 */
-- MySQL dump 10.13  Distrib 5.5.9, for Win32 (x86)
--
-- Host: localhost    Database: cesaretransportes
-- ------------------------------------------------------
-- Server version	5.1.36-community-log

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
-- Current Database: `cesaretransportes`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `cesaretransportes` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `cesaretransportes`;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
  `idCliente` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` varchar(10) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `telefone` varchar(10) NOT NULL,
  `email` varchar(255) NOT NULL,
  `statusCliente` varchar(5) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `tipoDoc` varchar(4) NOT NULL,
  `numDoc` varchar(14) NOT NULL,
  `dataCadastro` datetime NOT NULL,
  `dataExclusao` datetime DEFAULT NULL,
  PRIMARY KEY (`idCliente`)
) ENGINE=MyISAM AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (3,'cliente','cesar de sousa junior','4198396616','cesarsonline@gmail.com','true','cesar','cpf','02676563947','2010-10-21 12:32:45',NULL),(11,'cliente','karoline sgode felisbino','4132435678','karol@gmail.com','false','karol','cpf','75643289087','2011-01-01 00:00:01',NULL),(27,'cliente','cesar','2198386616','cesar_sj@hotmail.com','true','c','cnpj','47564388465984','2011-11-18 00:00:00','2011-11-26 00:00:00'),(22,'cliente','alcindo miguel','2196011143','alcindomiguel@uol.com.br','true','miguel','cnpj','85483476596488','2011-10-23 00:00:00','2011-11-26 00:00:00'),(1,'admin','administrador','4199999999','c','true','c','cnpj','06185954000100','2010-11-01 00:00:00',NULL);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contato`
--

DROP TABLE IF EXISTS `contato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contato` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `mensagem` text NOT NULL,
  `data` varchar(20) NOT NULL,
  `emailLido` varchar(3) NOT NULL,
  `emailSalvo` varchar(3) NOT NULL,
  `idCliente` int(11) DEFAULT NULL,
  `dataExclusao` datetime DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `idCliente` (`idCliente`)
) ENGINE=MyISAM AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contato`
--

LOCK TABLES `contato` WRITE;
/*!40000 ALTER TABLE `contato` DISABLE KEYS */;
INSERT INTO `contato` VALUES (9,'cesar de sousa junior','cesarsonline@gmail.com','ola testando envio de mensagem dia 1 de setembro de 2011.','01/09/2011 11:26:01','sim','sim',NULL,'2011-11-12 00:00:00'),(11,'joao de sousa','joaosousao@gmail.com','oi','02/09/2011 10:01:26','sim','nao',NULL,NULL),(12,'cesar de sousa junior','cesarsonline@gmail.com','verifique se o id do cliente não esta null.','15/10/2011 09:12:56','sim','nao',3,NULL),(25,'cesar de sousa junior','cesarsonline@gmail.com','outro teste','07/11/2011 03:18:15','nao','nao',3,NULL),(22,'cesar de sousa junior','cesarsonline@gmail.com','Res: oi.\n\n Recebida de: cesar de sousa junior (cesarsonline@gmail.com). Em: 01/11/2011 06:12:18\n\nMENSAGEM ORIGINAL: 313','01/11/2011 06:12:18','sim','nao',3,'2011-11-12 00:00:00'),(23,'cesar de sousa junior','cesarsonline@gmail.com','ola','02/11/2011 04:44:50','nao','nao',3,NULL),(24,'cesar de sousa junior','cesarsonline@gmail.com','testando tela de espera','07/11/2011 03:17:04','nao','sim',3,NULL),(18,'cesar de sousa junior','cesarsonline@gmail.com','email enviado pelo android','25/10/2011 11:40:46','nao','nao',3,NULL),(21,'cesar','cesarsonline@gmail.com','Res: cdfvmkdlidoljdhfkjrwetfiw.\n\n Recebida de: cesar (cesarsonline@gmail.com). Em: 29/10/2011 08:00:35\n\nMENSAGEM ORIGINAL: fefwefew','29/10/2011 08:00:35','sim','nao',NULL,'2011-11-12 00:00:00'),(26,'cesar de sousa junior','cesarsonline@gmail.com','teste com exclusão','12/11/2011 12:39:39','nao','nao',3,'2011-11-12 00:00:00'),(27,'cesar de sousa junior','cesarsonline@gmail.com','este email esta sendo enviado pela aplicação web...','22/11/2011 02:02:24','nao','nao',3,NULL),(28,'cesar de sousa junior','cesarsonline@gmail.com','Res: reposta de email teste de RequestWindow.\n\n Recebida de: cesar de sousa junior (cesarsonline@gmail.com). Em: 22/11/2011 02:06:26\n\nMENSAGEM ORIGINAL: este email esta sendo enviado pelo aplicativo mobile...','22/11/2011 02:06:26','sim','nao',3,NULL);
/*!40000 ALTER TABLE `contato` ENABLE KEYS */;
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
  `endereco` varchar(255) NOT NULL,
  `cnpj` varchar(14) NOT NULL,
  `msn` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `telefone1` varchar(10) NOT NULL,
  `telefone2` varchar(10) NOT NULL,
  `nextel` varchar(255) NOT NULL,
  PRIMARY KEY (`idEmpresa`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empresa`
--

LOCK TABLES `empresa` WRITE;
/*!40000 ALTER TABLE `empresa` DISABLE KEYS */;
INSERT INTO `empresa` VALUES (1,'cesare transportes ltda.','rua alzira miranda koerbel 140, afonso pena, são josé dos pinhais','06185954000100','cesaretransportes@hotmail.com','cesaretransportes@gmail.com','53340276','2196011143','2184841143','41 7813-6520 ID 82*26299');
/*!40000 ALTER TABLE `empresa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orcamento`
--

DROP TABLE IF EXISTS `orcamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orcamento` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `telefone` varchar(15) NOT NULL,
  `origem` varchar(100) NOT NULL,
  `enderecoOrigem` varchar(255) NOT NULL,
  `destino` varchar(100) NOT NULL,
  `enderecoDestino` varchar(255) NOT NULL,
  `peso` varchar(100) NOT NULL,
  `dimensao` varchar(100) NOT NULL,
  `mensagem` text,
  `data` varchar(20) NOT NULL,
  `valor` varchar(100) DEFAULT NULL,
  `orcamentoLido` varchar(3) NOT NULL,
  `orcamentoSalvo` varchar(3) NOT NULL,
  `idCliente` int(11) DEFAULT NULL,
  `idVeiculo` int(11) DEFAULT NULL,
  `dataEntrega` datetime DEFAULT NULL,
  `dataPrevEntrega` datetime DEFAULT NULL,
  `dataExclusao` datetime DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `idCliente` (`idCliente`),
  KEY `idVeiculo` (`idVeiculo`)
) ENGINE=MyISAM AUTO_INCREMENT=47 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orcamento`
--

LOCK TABLES `orcamento` WRITE;
/*!40000 ALTER TABLE `orcamento` DISABLE KEYS */;
INSERT INTO `orcamento` VALUES (11,'cesar de sousa junior','cesarsonline@gmail.com','2198386616','rio de janeiro - RJ','','curitiba - PR','','300 kilos','2 x 3 metros','Res: 300 reais.\n\n Recebida de: cesar de sousa junior (cesarsonline@gmail.com). Em: 16/06/2011 07:22:48\n\nMESAGEM ORIGINAL: produto pesado','16/06/2011 07:22:48','100,00','sim','sim',NULL,57,'2000-01-01 00:00:00','2011-12-10 00:00:00',NULL),(14,'voe rapido ltda','voerapido@uol.com.br','2176512309','guarulhos - SP','','curitiba - PR','','1 ton','1m','Res: 350 reais   pedagio   ajudante = 1000 reais..\n\n Recebida de: voe rapido ltda (voerapido@uol.com.br). Em: 02/09/2011 10:00:23\n\nMESAGEM ORIGINAL: valores de fretes','02/09/2011 10:00:23','280,00','sim','nao',NULL,57,'2011-11-07 00:00:00','2011-12-10 00:00:00',NULL),(19,'cesar de sousa junior','cesarsonline@gmail.com','4198396616','curitiba - PR','','palmas - TO','','2 ton','3 m2','Res: 2 mil reais mais pedagio.\n\n Recebida de: cesar de sousa junior (cesarsonline@gmail.com). Em: 15/10/2011 09:00:38\n\nMESAGEM ORIGINAL: ','15/10/2011 09:00:38','2000,00','sim','nao',3,57,'2011-11-07 00:00:00','2011-12-10 00:00:00','2011-11-21 00:00:00'),(21,'fernnado rabelo','fer@gmail.com','2158754378','ctba - PR','','rio de janeiro - RJ','','300 k',' 3 m2','Res: 2000 reais.\n\n Recebida de: fernnado rabelo (fer@gmail.com). Em: 18/10/2011 02:40:42\n\nMESAGEM ORIGINAL: quanto é tudo?','18/10/2011 02:40:42','110,00','sim','nao',NULL,57,'2011-11-07 00:00:00','2011-12-10 00:00:00',NULL),(31,'cesar de sousa junior','cesar_sj@hotmail.com','2148758435','rio de janeiro - RJ','','são paulo - SP','','350 k','1m x 2m','Res: frete R$ 1000\r\npedágio R$ 100\r\najudante R$ 50 .\n\n Recebida de: cesar de sousa junior (cesar_sj@hotmail.com). Em: 28/10/2011 06:21:10\n\nMESAGEM ORIGINAL: preço do frete, pedágio e ajundante.','28/10/2011 06:21:10','200,00','sim','nao',NULL,2,'2011-11-22 00:00:00','2011-12-10 00:00:00',NULL),(27,'cesar de sousa junior','cesarsonline@gmail.com','2198386616','rio de janeiro - RJ','','curitiba - PR','','1 ton','1m x 2m2','Res: 3000 reais.\n\n Recebida de: cesar de sousa junior (cesarsonline@gmail.com). Em: 26/10/2011 09:17:07\n\nMESAGEM ORIGINAL: ','26/10/2011 09:17:07','300,00','sim','nao',NULL,2,NULL,'2011-12-10 00:00:00',NULL),(29,'cesar de sousa junior','cesarsonline@gmail.com','2198386616','rio de janeiro - RJ','','curitiba - PR','','1 ton','1m x 2m2','Res: no mesage at all.\n\n Recebida de: cesar de sousa junior (cesarsonline@gmail.com). Em: 26/10/2011 09:20:59\n\nMESAGEM ORIGINAL: ','26/10/2011 09:20:59','100','sim','nao',NULL,18,NULL,'2011-12-10 00:00:00',NULL),(30,'cesar de sousa junior','cesarsonline@gmail.com','4198396616','curitiba - RJ','','rio de janeiro - RJ','','1 ton','2m2','Res: resposta enviada pelo site... .\n\n Recebida de: cesar de sousa junior (cesarsonline@gmail.com). Em: 26/10/2011 11:44:32\n\nMESAGEM ORIGINAL: orcamento de servico enviado pelo mobile','26/10/2011 11:44:32','2000','sim','nao',3,18,NULL,'2011-12-10 00:00:00',NULL),(34,'camponesa transportes','cesarsonline@gmail.com','2157483694','curitiba - PR','rua tibagi 300','apucarana - PR','rua dos padres 27','3k','2m2','Res: 3000 reais.\n\n Recebida de: camponesa transportes (cesarsonline@gmail.com). Em: 01/11/2011 01:02:34\n\nMESAGEM ORIGINAL: valor total do frete','01/11/2011 01:02:34','234,00','sim','sim',NULL,2,NULL,'2011-12-10 00:00:00',NULL),(35,'cesar de sousa junior','cesarsonline@gmail.com','4198396616','congonhas - AC','av paulista 2342','rio de janeiro - AC','av nossa senhora de copacabana 32','400 kilos','2m2','Res: 2000 reais.\n\n Recebida de: cesar de sousa junior (cesarsonline@gmail.com). Em: 01/11/2011 01:26:55\n\nMESAGEM ORIGINAL: ','01/11/2011 01:26:55','500,00','sim','nao',3,2,'2011-11-24 00:00:00','2011-12-10 00:00:00',NULL),(36,'cesar de sousa junior','cesarsonline@gmail.com','4198396616','curitiba - RJ','rua parana 89','rio de janeiro - RJ','rua padre anchieta 142','2 kilos','2 m2','Res: 2000 reais.\n\n Recebida de: cesar de sousa junior (cesarsonline@gmail.com). Em: 02/11/2011 12:27:11\n\nMESAGEM ORIGINAL: ','02/11/2011 12:27:11','1050,00','sim','nao',3,2,NULL,'2011-12-10 00:00:00',NULL),(39,'alcindo miguel','alcindomiguel@uol.com.br','2196011143','rio de janeiro - RJ','rua padre anchieta 142','curitiba - PR','rua parana 88','300 kilos','2m x 1.5m','Res: 300 reais.\n\n Recebida de: alcindo miguel (alcindomiguel@uol.com.br). Em: 05/11/2011 12:23:23\n\nMESAGEM ORIGINAL: valor do frete e dos pedágios.','05/11/2011 12:23:23',NULL,'sim','nao',22,NULL,NULL,'2011-12-10 00:00:00',NULL),(40,'alcindo miguel','alcindomiguel@uol.com.br','2196011143','santos - SP','rua araruama 39','tocantis - TO','rua da esperança 4234','3k','3m2','','05/11/2011 01:13:12','400,00','sim','nao',22,2,'2011-11-22 00:00:00','2011-12-10 00:00:00',NULL),(41,'alcindo miguel','alcindomiguel@uol.com.br','2196011143','maringa - PR','rua das palmeiras queimadas 300','curitiba - PR','rua flores em chamas 233','3 kilos','1 metro quadrado','','07/11/2011 01:43:42','300','sim','nao',22,18,'2011-11-10 00:00:00','2011-12-10 00:00:00',NULL),(42,'cesar de sousa junior','cesarsonline@gmail.com','4198396616','dfdgfu - AC','euggwut','rfwret - AC','gregter','tretrt','tertret','','18/11/2011 06:36:57',NULL,'sim','nao',3,NULL,NULL,'2011-12-10 00:00:00','2011-11-21 00:00:00'),(43,'cesar de sousa junior','cesarsonline@gmail.com','4198396616','teresopolis - PR','rua das palmeiras 123','curitiba - PR','rua parana 88','200 kilos','2m2va','valor do frete e dos pedagios','21/11/2011 02:01:30',NULL,'sim','nao',3,NULL,NULL,'2011-12-10 00:00:00',NULL),(44,'cesar','cesar_sj@hotmail.com','4348386463','mandaguari - RJ','rua joaquim ribeiro 23','rio de janeiro - RJ','rua padre anchieta 142','1000 k','1m2','Res: não efetuamos transportes de caixas de 250 cm2.\n\n Recebida de: cesar (cesar_sj@hotmail.com). Em: 21/11/2011 02:13:14\n\nMESAGEM ORIGINAL: 4 caixas de 250 cm2','21/11/2011 02:13:14',NULL,'sim','nao',27,NULL,NULL,'2011-12-10 00:00:00',NULL),(45,'cesar de sousa junior','cesarsonline@gmail.com','4198396616','niteroi - RJ','rua noronha torrezao 590, santa rosa','niteroi - RJ','rua padre anchieta 142, centro','100kilos','1m2','Res: 300 reais.\n\n Recebida de: cesar de sousa junior (cesarsonline@gmail.com). Em: 28/11/2011 09:58:58\n\nMESAGEM ORIGINAL: valor do frete.','28/11/2011 09:58:58','500','sim','nao',3,2,'2011-11-29 00:00:00','2011-12-10 00:00:00',NULL),(46,'cesar de sousa junior','cesarsonline@gmail.com','4198396616','rio de janeiro - PR','rua da conceicao, niteroi','curitiba - PR','rua sete de setembro, agua verde','2200  k','2m2','','01/12/2011 07:46:11',NULL,'nao','nao',3,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `orcamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `veiculo`
--

DROP TABLE IF EXISTS `veiculo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `veiculo` (
  `idVeiculo` int(11) NOT NULL AUTO_INCREMENT,
  `marca` varchar(255) NOT NULL,
  `cor` varchar(20) NOT NULL,
  `placa` varchar(10) NOT NULL,
  `localizacao` varchar(255) DEFAULT NULL,
  `dataExclusao` datetime DEFAULT NULL,
  PRIMARY KEY (`idVeiculo`)
) ENGINE=MyISAM AUTO_INCREMENT=60 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `veiculo`
--

LOCK TABLES `veiculo` WRITE;
/*!40000 ALTER TABLE `veiculo` DISABLE KEYS */;
INSERT INTO `veiculo` VALUES (2,'volks','branco','GET4324','rua padre anchieta 142, niteroi rj',NULL),(18,'honda','prata','FSD4342','rua parana 88, borda do campo, são josé dos pinhais pr',NULL),(13,'ford','amarela','GRG4534','rua parana 88, curitiba - pr',NULL),(57,'renault','branca','VHH1111','rua sorocaba, botafogo. rio de janeiro rj',NULL);
/*!40000 ALTER TABLE `veiculo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-12-04 18:09:36
