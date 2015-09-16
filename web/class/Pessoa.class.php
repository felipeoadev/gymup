<?php
class Pessoa extends Dao
{
    private $campos = array();
    private $label  = array();
    
    function getCampos() 
    {
        return $this->campos;
    }

    function setCampos($campos) 
    {
        $this->campos = $campos;
    }    
    
    function getLabel() 
    {
        return $this->label;
    }
    
    function setLabel($label) 
    {
        $this->label = $label;
    }


}