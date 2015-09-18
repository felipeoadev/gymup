DELIMITER $$

CREATE
    /*[DEFINER = { user | CURRENT_USER }]*/
    PROCEDURE `gymup`.`SPexecutaLogin`(IN email VARCHAR(100), IN senha CHAR(32))
    /*LANGUAGE SQL
    | [NOT] DETERMINISTIC
    | { CONTAINS SQL | NO SQL | READS SQL DATA | MODIFIES SQL DATA }
    | SQL SECURITY { DEFINER | INVOKER }
    | COMMENT 'string'*/
    BEGIN
	SELECT COUNT(P.codigoPessoa) AS total
	FROM `pessoa` P
	WHERE P.emailPessoa = email AND P.senhaPessoa = MD5(senha) AND P.ativoPessoa = 'S';
    END$$

DELIMITER ;