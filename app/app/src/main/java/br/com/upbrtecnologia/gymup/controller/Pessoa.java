package br.com.upbrtecnologia.gymup.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import br.com.upbrtecnologia.gymup.database.Banco;

/**
 * Created by UPBrTecnologia on 10/09/2015.
 * Classe Pessoa
 */
public class Pessoa
{
    // Labels table name
    public static final String TABELA = "pessoa";

    // Labels Table Columns names
    public static final String CODIGO_PESSOA = "codigoPessoa";
    public static final String NOME_PESSOA   = "nomePessoa";
    public static final String EMAIL_PESSOA  = "emailPessoa";
    public static final String SENHA_PESSOA  = "senhaPessoa";
    public static final String ATIVO_PESSOA  = "ativoPessoa";

    // property help us to keep data
    private int codigoPessoa;
    private String nomePessoa;
    private String emailPessoa;
    private String senhaPessoa;
    private String ativoPessoa;

    private SQLiteDatabase db;
    private Banco banco;
    private Cursor cursor;

    public Pessoa(Context context)
    {
        //Verifica  se o banco exites
        banco = new Banco(context);
        banco.getDatabase();
    }

    public int getCodigoPessoa()
    {
        return codigoPessoa;
    }

    public void setCodigoPessoa(int codigoPessoa)
    {
        this.codigoPessoa = codigoPessoa;
    }

    public String getNomePessoa()
    {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa)
    {
        this.nomePessoa = nomePessoa;
    }

    public String getEmailPessoa()
    {
        return emailPessoa;
    }

    public void setEmailPessoa(String emailPessoa)
    {
        this.emailPessoa = emailPessoa;
    }

    public String getSenhaPessoa()
    {
        return senhaPessoa;
    }

    public void setSenhaPessoa(String senhaPessoa)
    {
        this.senhaPessoa = senhaPessoa;
    }

    public String getAtivoPessoa()
    {
        return ativoPessoa;
    }

    public void setAtivoPessoa(String ativoPessoa)
    {
        this.ativoPessoa = ativoPessoa;
    }

    public boolean verificaUsuario(String email, String senha)
    {
        try
        {
            String[] campos = {this.EMAIL_PESSOA, this.SENHA_PESSOA, this.ATIVO_PESSOA};
            String where = this.EMAIL_PESSOA + " = '" + email + "' AND " + this.SENHA_PESSOA + " = '"+ senha + "' AND "
                    + this.ATIVO_PESSOA + " =  'S'";

            db = banco.getReadableDatabase();
            cursor = db.query(this.TABELA, campos, where, null, null, null, null);

            if (cursor != null)
            {
                Log.i("dataops_", "Error :: Quant. retornada :: " + cursor.getCount()); //Print

                if (cursor.getCount() > 0)
                {
                    return true;
                }
            }

            cursor.close();
            db.close();
        }
        catch(android.database.sqlite.SQLiteException ex)
        {
            Log.i("dataops_", "Error :: verificaUsuario :: " + ex.getMessage()); //Print
        }

        return false;
    }
}

