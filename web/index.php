<?php
    error_reporting(E_ALL & ~ E_NOTICE);
    require 'vendor/autoload.php';
    require 'templates/autoload.php';
            
    //instancie o objeto
    $app = new \Slim\Slim(array('debug' => true));
        
    //Cria um grupo usuarios onde todas as consultas relacionadas ao usuario 
    //deverá vir precedida do /usuarios
    $app->group('/usuarios', function() use($app)
    {
        //rota para a home
        $app->get('/',function() use ($app)
        {
            //exemplo de lista de usuarios
            $users = array
            (
                'users'=>array
                (
                    'jo'=>'senhadejo',
                    'luca'=>'senhaluca',
                    'yasmin'=>'senhayasmin',
                    'eric'=>'seric'
                )
            );
            
            $data = array
            (
                'data'=>$users
            );
            
            $app->render('default.php', $data, 200);
        });
 
        //rota para login
        $app->get('/login/:email/:hash', function($email, $hash) use ($app)
        {
            $pessoa = new Pessoa();
            $resultado = $pessoa->efetuaLogin($email, $hash);
            
            $login = array
            (
                'usuario' => $resultado                
            );
                          
            $data = array
            (
                'data' => $login
            );            
            
            $app->render('default.php', $data, 200);
            
            /*if(isset($_POST))
            {
                $data = $_POST;
                        
                //$pessoa = new Pessoa();
                //$pessoa->efetuaLogin($data['email'], $data['senha']);
                
                $app->render('default.php', $data, 200);
                echo 'Aqui';
            }
            else
            {
                $app->render(404);
                echo 'Aqui erro';
            }*/
        });
    });
    
        
    //rode a aplicação Slim 
    $app->run();