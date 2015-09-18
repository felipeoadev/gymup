<?php
    require 'vendor/autoload.php';
    require 'templates/autoload.php';
         
    //instancie o objeto
    $app = new \Slim\Slim();
        
    //Cria um grupo usuarios onde todas as consultas relacionadas ao usuario 
    //deverá vir precedida do /usuarios
    $app->group('/usuarios', function() use($app)
    {
        //rota para a home
        /*$app->get('/',function() use ($app)
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
        });*/
 
        //rota para login
        $app->post('/login/', function() use ($app)
        {
            if(isset($_POST))
            {
                $data = $_POST;
                        
                $pessoa = new Pessoa();
                $pessoa->efetuaLogin($data['email'], $data['senha']);
                $app->render('default.php', $data, 200);
                echo 'Aqui';
            }
            else
            {
                $app->render(404);
                echo 'Aqui erro';
            }
        });
    });
    
        
    //rode a aplicação Slim 
    $app->run();