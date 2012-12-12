-- script de criação gerado pelo DBDesigner

CREATE TABLE Veiculo (
  idVeiculo INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  marca VARCHAR(50) NOT NULL,
  cor VARCHAR(50) NOT NULL,
  placa VARCHAR(7) NOT NULL,
  localizacao VARCHAR(255) NULL,
  dataExclusao DATETIME NULL,
  PRIMARY KEY(idVeiculo)
);

-- executar comando de criação da tabela acima

CREATE TABLE Empresa (
  idEmpresa INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  nome VARCHAR(255) NOT NULL,
  cnpj VARCHAR(14) NOT NULL,
  email VARCHAR(255) NOT NULL,
  senha VARCHAR(255) NOT NULL,
  msn VARCHAR(255) NOT NULL,
  PRIMARY KEY(idEmpresa)
);

-- executar comando de criação da tabela acima

CREATE TABLE Cliente (
  idCliente INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  nome VARCHAR(255) NOT NULL,
  tipoDoc VARCHAR(4) NOT NULL,
  numDoc VARCHAR(14) NOT NULL,
  email VARCHAR(255) NOT NULL,
  senha VARCHAR(255) NOT NULL,
  dataCadastro DATETIME NOT NULL,
  statusCliente VARCHAR(5) NOT NULL,
  dataExclusao DATETIME NULL,
  tipoCliente VARCHAR(1) NOT NULL,
  PRIMARY KEY(idCliente)
);

-- executar comando de criação da tabela acima

CREATE TABLE Orcamento (
  idOrcamento INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  idCliente INTEGER UNSIGNED NOT NULL,
  peso VARCHAR(255) NOT NULL,
  dimensao VARCHAR(255) NOT NULL,
  mensagem VARCHAR(255) NULL,
  orcamentoLido VARCHAR(5) NOT NULL,
  orcamentoRespondido VARCHAR(5) NOT NULL,
  dataCadastro DATETIME NOT NULL,
  dataExclusao DATETIME NULL,
  PRIMARY KEY(idOrcamento),
  FOREIGN KEY(idCliente)
    REFERENCES Cliente(idCliente)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

-- executar comando de criação da tabela acima

CREATE TABLE Telefone (
  idTelefone INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  idEmpresa INTEGER UNSIGNED NOT NULL,
  idCliente INTEGER UNSIGNED NOT NULL,
  ddd VARCHAR(3) NOT NULL,
  numero VARCHAR(8) NOT NULL,
  complemento VARCHAR(50) NOT NULL,
  PRIMARY KEY(idTelefone),
  FOREIGN KEY(idCliente)
    REFERENCES Cliente(idCliente)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(idEmpresa)
    REFERENCES Empresa(idEmpresa)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

-- executar comando de criação da tabela acima

CREATE TABLE Servico (
  idServico INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  idOrcamento INTEGER UNSIGNED NOT NULL,
  idVeiculo INTEGER UNSIGNED NOT NULL,
  valor DECIMAL NOT NULL,
  dataPrevEntrega DATETIME NOT NULL,
  dataEntrega DATETIME NULL,
  PRIMARY KEY(idServico, idOrcamento),
  FOREIGN KEY(idVeiculo)
    REFERENCES Veiculo(idVeiculo)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(idOrcamento)
    REFERENCES Orcamento(idOrcamento)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

-- executar comando de criação da tabela acima

CREATE TABLE Endereco (
  idEndereco INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  idEmpresa INTEGER UNSIGNED NOT NULL,
  idCliente INTEGER UNSIGNED NOT NULL,
  idOrcamento INTEGER UNSIGNED NOT NULL,
  cidade VARCHAR(50) NOT NULL,
  estado VARCHAR(2) NOT NULL,
  localizacao VARCHAR(203) NOT NULL,
  statusEndereco INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(idEndereco),
  FOREIGN KEY(idOrcamento)
    REFERENCES Orcamento(idOrcamento)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(idEmpresa)
    REFERENCES Empresa(idEmpresa)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(idCliente)
    REFERENCES Cliente(idCliente)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

-- executar comando de criação da tabela acima

