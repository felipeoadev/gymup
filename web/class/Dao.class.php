<?php
class Dao
{
    private $database;
    private $table;
    private $tipo;    
    private $primaryKey;
    private $properties;
    private $name;
    private $label;
    
    //Metodo construtor da classe
    public function __construct()
    {
        $this->data = array();
        $this->query = NULL;
        $this->is_new = TRUE;
        
        //verifica se existe o arquivo de configuração para este banco de dados
        if (file_exists(CONFIG))
        {
            //lê o INI e retorna o array
            $db = parse_ini_file(CONFIG, true);
            
            $this->database = $db['CONEXAO']['base'];
            $this->tipo     = $db['CONEXAO']['tipo'];
        }
        else
        {
            //se não existir, lança um erro
            throw new Exception(utf8_decode("Arquivo {CONFIG} não encontrado"));
        }        
    }    
    
    //Metodos Getters
    public function __get($property)
    {
        return utf8_encode($this->{$property});
    }

    //Metodos Setters
    public function __set($property, $value)
    {
        $this->{$property} = $value;
    }
    
    //Retorna os labels de cada campo da tabela
    public function getLabel() 
    {
        return $this->label;
    }  
    
    /* Retorno os atributos da classe
     * Parametro:
     * Retorno:
    */
    public function getProperties()
    {
        return $this->properties;
    }    
    
    /* Insere os atributos da classe automaticamente conforme a tabela
     * Parametro:
     * Retorno:
    */
    public function setProperties()
    {
        $select = new SelectSql();        
                
        if ($this->tipo == 'mysql')
        {
            $select->setEntidade('information_schema.COLUMNS');
            $select->addColuna('COLUMN_NAME AS Campos');
            $select->addColuna('IF(COLUMN_KEY = "PRI, "S", "N") AS Primary_Key');
            
            $criterio = new Criterio();
            $criterio->add("TABLE_SCHEMA", $this->database);
            $criterio->add("TABLE_NAME", $this->database);
            
            
            $sql = "SELECT COLUMN_NAME AS Campos, IF(COLUMN_KEY = 'PRI', 'S', 'N') AS Primary_Key"
                 . "FROM information_schema.COLUMNS"
                 . "WHERE TABLE_SCHEMA = '{$this->database}' AND "
                 . "TABLE_NAME = '{$this->table}';";                                      
        }
        else if ($this->tipo == 'mssql')
        {
            $sql = "SELECT properties=syscolumns.name "
                 . "FROM syscolumns LEFT JOIN sysobjects ON sysobjects.id = syscolumns.id "
                 . "WHERE sysobjects.name = {$this->table};";
        }
        $this->properties = $result;
        $this->primaryKey = $result[0];
    }    
    
}
