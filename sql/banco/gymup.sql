/*
SQLyog Ultimate v9.50 
MySQL - 5.6.26 : Database - gymup
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`gymup` /*!40100 DEFAULT CHARACTER SET latin1 COLLATE latin1_general_ci */;

USE `gymup`;

/*Table structure for table `pessoa` */

DROP TABLE IF EXISTS `pessoa`;

CREATE TABLE `pessoa` (
  `codigoPessoa` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Código do registro',
  `nomePessoa` varchar(100) COLLATE latin1_general_ci DEFAULT NULL COMMENT 'Nome da pessoa',
  `emailPessoa` varchar(100) COLLATE latin1_general_ci DEFAULT NULL COMMENT 'Email da pessoa',
  `senhaPessoa` char(32) COLLATE latin1_general_ci DEFAULT NULL COMMENT 'Hash MD5 da senha da pessoa',
  `ativoPessoa` char(1) COLLATE latin1_general_ci DEFAULT NULL COMMENT 'Se a pessoa está ativa ou não',
  PRIMARY KEY (`codigoPessoa`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

/*Data for the table `pessoa` */

insert  into `pessoa`(`codigoPessoa`,`nomePessoa`,`emailPessoa`,`senhaPessoa`,`ativoPessoa`) values (1,'Felipe','f3lipeoa@gmail.com','698dc19d489c4e4db73e28a713eab07b','S');

/* Procedure structure for procedure `SPexecutaLogin` */

/*!50003 DROP PROCEDURE IF EXISTS  `SPexecutaLogin` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `SPexecutaLogin`(in email Varchar(100), in senha char(32))
BEGIN
	SELECT P.codigoPessoa, P.nomePessoa
	from `pessoa` P
	where P.emailPessoa = email and P.senhaPessoa = senha and P.ativoPessoa = 'S';
    END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
