package br.com.upbrtecnologia.gymup.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import br.com.upbrtecnologia.gymup.database.Banco;

/**
 * Created by UPBrTecnologia on 10/09/2015.
 * Classe Pessoa
 */
public class Pessoa {
    // Labels table name
    public static final String TABELA = "pessoa";

    // Labels Table Columns names
    public static final String CODIGO_PESSOA = "codigoPessoa";
    public static final String NOME_PESSOA = "nomePessoa";
    public static final String EMAIL_PESSOA = "emailPessoa";
    public static final String SENHA_PESSOA = "senhaPessoa";
    public static final String ATIVO_PESSOA = "ativoPessoa";

    // property help us to keep data
    private int codigoPessoa;
    private String nomePessoa;
    private String emailPessoa;
    private String senhaPessoa;
    private String ativoPessoa;

    private SQLiteDatabase db;
    private Banco banco;
    private Cursor cursor;

    public Pessoa(Context context) {
        //Verifica  se o banco exites
        banco = new Banco(context);
        banco.getDatabase();
    }

    public int getCodigoPessoa() {
        return codigoPessoa;
    }

    public void setCodigoPessoa(int codigoPessoa) {
        this.codigoPessoa = codigoPessoa;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public String getEmailPessoa() {
        return emailPessoa;
    }

    public void setEmailPessoa(String emailPessoa) {
        this.emailPessoa = emailPessoa;
    }

    public String getSenhaPessoa() {
        return senhaPessoa;
    }

    public void setSenhaPessoa(String senhaPessoa) {
        this.senhaPessoa = senhaPessoa;
    }

    public String getAtivoPessoa() {
        return ativoPessoa;
    }

    public void setAtivoPessoa(String ativoPessoa) {
        this.ativoPessoa = ativoPessoa;
    }

    public boolean verificaUsuario(String email, String senha)
    {
        try
        {
            //Consulta o webservice para ver se o usuario existe
            JSONObject jsonRootObject = new JSONObject("http://localhost/gymup/usuarios/login/"+email+"/"+this.gerarHashMD5(senha));

            //Verifica se retornou o nome do usuario
            JSONArray jsonArray = jsonRootObject.optJSONArray("usuario");

            if (jsonArray.length() > 0)
            {
                //Iterate the jsonArray and print the info of JSONObjects
                for(int i=0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    int codigoPessoa = Integer.parseInt(jsonObject.optString("codigoPessoa").toString());
                    String nomePessoa = jsonObject.optString("nomePessoa").toString();
                }


            }

            String[] campos = {this.EMAIL_PESSOA, this.SENHA_PESSOA, this.ATIVO_PESSOA};
            String where = this.EMAIL_PESSOA + " = '" + email + "' AND " + this.SENHA_PESSOA + " = '" + senha + "' AND "
                    + this.ATIVO_PESSOA + " =  'S'";

            db = banco.getReadableDatabase();
            cursor = db.query(this.TABELA, campos, where, null, null, null, null);

            //db.insert(this.TABELA, campos, where);

            if (cursor != null)
            {
                Log.i("dataops_", "Error :: Quant. retornada :: " + cursor.getCount()); //Print

                if (cursor.getCount() > 0)
                {
                    return true;
                }
            }

            cursor.close();
            db.close();*/
        }
        catch (android.database.sqlite.SQLiteException ex)
        {
            Log.i("dataops_", "Error :: verificaUsuario :: " + ex.getMessage()); //Print
        }
        catch (JSONException e)
        {
            Log.i("dataops_", "Error :: JSON :: " + e.getMessage().toString()); //Print
            e.printStackTrace();
        }

        return false;
    }

    //Gerar um hash MD5
    public String gerarHashMD5(String password)
    {
        try
        {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer MD5Hash = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                MD5Hash.append(h);
            }

            password = MD5Hash.toString();

        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return password;
    }
}

