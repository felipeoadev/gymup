<?php
class Dao
{
    private $table;
    private $primaryKey;
    private $conexao;
    private $properties;
    private $name;
    private $label;
    private $tipo  = array('mysql', 'sqlserver');
    
    //Metodo construtor da classe
    public function __construct()
    {
        $this->data = array();
        $this->query = NULL;
        $this->is_new = TRUE;
        $this->bd = RADIO;
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
    
}
