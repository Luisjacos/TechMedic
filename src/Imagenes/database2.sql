CREATE TABLE IF NOT EXISTS `Events` (
  
`idEvents` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  
`tipoLlamado` VARCHAR(15) NOT NULL,
  
`habitacion` VARCHAR(4) NOT NULL,
  
`fechaInicio` DATE NOT NULL,
  
`fechaFinal` DATE NOT NULL,
  
`tiempoInicio` TIME NOT NULL,
  
`tiempoFinal` TIME NOT NULL,
  
PRIMARY KEY (`idEvents`))

ENGINE = InnoDB;