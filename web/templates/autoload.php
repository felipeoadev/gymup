<?php
    /* Carrega as classes que serão utilizadas automaticamente*/
    define('CONFIG', "{$_SERVER['DOCUMENT_ROOT']}/gymup/class/config.ini");

    spl_autoload_register('__autoload');

    function __autoload($classe)
    {
        if (file_exists("{$_SERVER['DOCUMENT_ROOT']}/gymup/class/{$classe}.class.php"))
        {
            require "{$_SERVER['DOCUMENT_ROOT']}/gymup/class/{$classe}.class.php";
        }    
    }   