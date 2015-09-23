package br.com.upbrtecnologia.gymup.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URI;

/**
 * Created by UPBrTecnologia on 10/09/2015.
 */
public class Banco extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "gymup.db";

    private static final String DATABASE_PATH = "/data/data/br.com.upbrtecnologia.gymup/databases/";

    private Context context;

    public Banco(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    /**
     * Método auxiliar que verifica a existencia do banco
     * da aplicação.
     */
    private boolean checkDataBase()
    {
        SQLiteDatabase db = null;

        try
        {
            String path = DATABASE_PATH + DATABASE_NAME;
            db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
            db.close();
        }
        catch (SQLiteException e)
        {
            // O banco não existe
        }

        // Retorna verdadeiro se o banco existir, pois o ponteiro irá existir,
        // se não houver referencia é porque o banco não existe
        return db != null;
    }

    private void createDataBase() throws Exception
    {
        // Primeiro temos que verificar se o banco da aplicação
        // já foi criado
        boolean exists = checkDataBase();

        if(!exists)
        {
            // Chamaremos esse método para que o android
            // crie um banco vazio e o diretório onde iremos copiar
            // no banco que está no assets.
            this.getReadableDatabase();

            // Se o banco de dados não existir iremos copiar o nosso
            // arquivo em /assets para o local onde o android os salva
            try
            {
                copyDatabase();
            }
            catch (IOException e)
            {
                throw new Error("Não foi possível copiar o arquivo");
            }
        }
    }

    /**
     * Esse método é responsável por copiar o banco do diretório
     * assets para o diretório padrão do android.
     */
    private void copyDatabase() throws IOException
    {

        String dbPath = DATABASE_PATH + DATABASE_NAME;

        // Abre o arquivo o destino para copiar o banco de dados
        OutputStream dbStream = new FileOutputStream(dbPath);

        // Abre Stream do nosso arquivo que esta no assets
        InputStream dbInputStream = context.getAssets().open(DATABASE_NAME);

        byte[] buffer = new byte[1024];
        int length;
        while((length = dbInputStream.read(buffer)) > 0)
        {
            dbStream.write(buffer, 0, length);
        }

        dbInputStream.close();

        dbStream.flush();
        dbStream.close();
    }

    public SQLiteDatabase getDatabase()
    {
        try
        {
            // Verificando se o banco já foi criado e se não foi o
            // mesmo é criado.
            createDataBase();

            // Abrindo database
            String path = DATABASE_PATH + DATABASE_NAME;

            return SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        }
        catch (Exception e)
        {
            // Se não conseguir copiar o banco um novo será retornado
            return getWritableDatabase();
        }
    }

}
