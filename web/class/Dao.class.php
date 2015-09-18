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
    private $query;
    private $conexao;
    
    //Metodo construtor da classe
    public function __construct()
    {
        $this->data  = array();
        $this->query  = NULL;
        $this->is_new = TRUE;
        $this->table  = 'pessoa';
        
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
        try 
        {
            $this->query = new SelectSql();     

            if ($this->tipo == 'mysql')
            {
                $this->query->setEntidade('information_schema.COLUMNS');
                $this->query->addColuna('COLUMN_NAME AS campos');
                $this->query->addColuna('IF(COLUMN_KEY = "PRI", "S", "N") AS primary_key');

                $criterio = new Criterio; 
                $criterio->add(new Filtro('TABLE_SCHEMA', '=',  $this->database));
                $criterio->add(new Filtro('TABLE_NAME',   '=',  $this->table));

                /*$sql = "SELECT COLUMN_NAME AS Campos, IF(COLUMN_KEY = 'PRI', 'S', 'N') AS Primary_Key"
                     . "FROM information_schema.COLUMNS"
                     . "WHERE TABLE_SCHEMA = '{$this->database}' AND "
                     . "TABLE_NAME = '{$this->table}';";  */                                    
            }
            else if ($this->tipo == 'mssql')
            {
                /*$sql = "SELECT properties=syscolumns.name "
                     . "FROM syscolumns LEFT JOIN sysobjects ON sysobjects.id = syscolumns.id "
                     . "WHERE sysobjects.name = {$this->table};";*/

                $this->query->setEntidade('syscolumns LEFT JOIN sysobjects ON sysobjects.id = syscolumns.id');
                $this->query->addColuna('properties=syscolumns.name');

                $criterio = new Criterio; 
                $criterio->add(new Filtro('sysobjects.name', '=', $this->table));                 
            }

            $this->query->setCriterio($criterio);            
                        
            $this->conexao = Conexao::abrir(CONFIG);
            $resultado = $this->conexao->query($this->query->getInstrucao());
            
            if ($resultado)
            {                               
                while($linhas = $resultado->fetch(PDO::FETCH_ASSOC))
                {
                    if ($linhas['primary_key'] == 'S')
                    {
                        $this->primaryKey = $linhas['campos'];
                    }
                    else
                    {
                        $this->properties[] = $linhas['campos'];
                    }     
                }
            }
            
            unset($this->conexao); 
            unset($this->query); 
        } 
        catch (PDOException $erro) 
        {
            echo "{$erro->getMessage()} :: {$this->query->getInstrucao()}";
            exit;
        }
       
    }    
    
}
