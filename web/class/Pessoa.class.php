<?php
class Pessoa extends Dao
{
    function __construct() 
    {
        $this->table      = 'ESTAGIO_ALUNOGR';
        $this->primaryKey = 'codigoEstagioAlunogr';
        $this->label      = array('');
        $this->name       = 'Pessoa';
        parent::__construct();  
        parent::setProperties();        
    }
    
    public function efetuaLogin($email, $senha) 
    {
        try
        {
            //Monta a procedure
            $procedure = new ProcedureSql();           
            $procedure->setEntidade('SPexecutaLogin');
            $procedure->addParametro($email);  
            $procedure->addParametro($senha);            
           
            $conn = Conexao::abrir(CONFIG);
            $resultado = $conn->query($procedure->getInstrucao(0));
            return $resultado->fetch(PDO::FETCH_ASSOC);
        }
        catch (PDOException $erro) 
        {          
            echo $erro->getMessage()." :: SQL ".$sql;
        }              
    }









}