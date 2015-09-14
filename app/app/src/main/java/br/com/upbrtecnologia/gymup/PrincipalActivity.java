package br.com.upbrtecnologia.gymup;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import android.view.View;

import br.com.upbrtecnologia.gymup.controller.Pessoa;
import br.com.upbrtecnologia.gymup.database.Banco;

public class PrincipalActivity extends AppCompatActivity {

    private EditText editTextUsuario;
    private EditText editTextSenha;
    private Button buttonLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        editTextUsuario = (EditText) findViewById(R.id.editTextEmail);
        editTextSenha   = (EditText)findViewById(R.id.editTextSenha);
        buttonLogar     = (Button)findViewById(R.id.buttonLogar);

        buttonLogar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Pessoa pessoa = new Pessoa(getBaseContext());
                AlertDialog.Builder aviso = new AlertDialog.Builder(PrincipalActivity.this);

                if (editTextUsuario.getText().toString().equals("") && editTextSenha.getText().toString().equals(""))
                {
                    aviso.setMessage("Campos vazios");
                    aviso.setNegativeButton("OK", null);
                    aviso.show();
                }
                else
                {
                    if (pessoa.verificaUsuario(editTextUsuario.getText().toString(), editTextSenha.getText().toString()) == true)
                    {
                        aviso.setMessage("Login efetuado com sucesso");
                        aviso.setNeutralButton("OK", null);
                        aviso.show();
                    }
                    else
                    {
                        aviso.setMessage("Usuário/Senha incorretos");
                        aviso.setNegativeButton("OK", null);
                        aviso.show();
                    }
                }


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
