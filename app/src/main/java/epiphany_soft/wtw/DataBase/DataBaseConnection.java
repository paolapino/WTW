package epiphany_soft.wtw.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

import static epiphany_soft.wtw.DataBase.DataBaseContract.*;

/**
 * Created by Camilo on 23/03/2016.
 */
public class DataBaseConnection {
    DataBaseHelper miDBHelper;
    public DataBaseConnection (Context context){
        miDBHelper = new DataBaseHelper(context);
    }

    public Cursor consultarAllGeneros(){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query =
                    "SELECT " + GeneroContract.COLUMN_NAME_GENERO_ID+","+
                            GeneroContract.COLUMN_NAME_GENERO_NOMBRE+" ";
            query+= "FROM "+ GeneroContract.TABLE_NAME;
            Cursor c=db.rawQuery(query,null);
            return c;
        }
        return null;
    }
    public Cursor consultarPeliculaLikeNombre(String nombre){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query =
                    "SELECT " + ProgramaContract.COLUMN_NAME_PROGRAMA_ID + "," +
                            ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE + "," +
                            ProgramaContract.COLUMN_NAME_PROGRAMA_SINOPSIS + ","+
                            ProgramaContract.COLUMN_NAME_PROGRAMA_ANIO_ESTRENO + "," +
                            ProgramaContract.COLUMN_NAME_PROGRAMA_PAIS_ORIGEN + " ";
            query +=
                    "FROM " + ProgramaContract.TABLE_NAME + " JOIN " +
                            PeliculaContract.TABLE_NAME +
                            " ON " + PeliculaContract.COLUMN_NAME_PELICULA_ID + "="
                            + ProgramaContract.COLUMN_NAME_PROGRAMA_ID + " ";
           query +=
                    "WHERE " + ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE + " LIKE \'%"+nombre+"%\'";
            Cursor c = db.rawQuery(query, null);
            return c;
        }
        else return null;
    }
    /**@param nombre es el nombre con el que se realizará la búsqueda de la película
    * @return la pelicula cuyo nombre es nombre*/
    public Cursor consultarPeliculaPorNombre(String nombre){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query =
                    "SELECT " + ProgramaContract.COLUMN_NAME_PROGRAMA_ID + "," +
                    ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE + "," +
                    ProgramaContract.COLUMN_NAME_PROGRAMA_SINOPSIS + ","+
                            GeneroContract.COLUMN_NAME_GENERO_NOMBRE +","+
                    ProgramaContract.COLUMN_NAME_PROGRAMA_ANIO_ESTRENO + "," +
                    ProgramaContract.COLUMN_NAME_PROGRAMA_PAIS_ORIGEN + " ";
            query +=
                    "FROM " + ProgramaContract.TABLE_NAME+" NATURAL JOIN "+
                            GeneroContract.TABLE_NAME+ " JOIN " +
                            PeliculaContract.TABLE_NAME +
                            " ON " + PeliculaContract.COLUMN_NAME_PELICULA_ID + "="
                            + ProgramaContract.COLUMN_NAME_PROGRAMA_ID + " ";
            query +=
                    "WHERE " + ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE +"=\'"+nombre+"\'";
            Cursor c = db.rawQuery(query, null);
            return c;
        }
        else return null;
    }

    public boolean insertarGenero(String nombre){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(GeneroContract.COLUMN_NAME_GENERO_NOMBRE, nombre);
            long rowid=db.insert(GeneroContract.TABLE_NAME, null, values);
            if (rowid>0) return true;
        }
        return false;
    }

    public Cursor getGenerosNoUsados(){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query =
                    "SELECT "+ GeneroContract.COLUMN_NAME_GENERO_ID+","+
                            GeneroContract.COLUMN_NAME_GENERO_NOMBRE+" ";
            query+=
                    "FROM "+ GeneroContract.TABLE_NAME+" ";
            query+=
                    "WHERE "+ GeneroContract.COLUMN_NAME_GENERO_ID + " NOT IN ("+
                            "SELECT "+ ProgramaContract.COLUMN_NAME_GENERO_ID+" FROM "+
                            ProgramaContract.TABLE_NAME+")";
            Cursor c = db.rawQuery(query, null);
            return c;
        }
        else return null;
    }

    public boolean eliminarGenero(int id){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            String query= GeneroContract.COLUMN_NAME_GENERO_ID+"=?";
            int numDel=db.delete(GeneroContract.TABLE_NAME, query, new String[]{Integer.toString(id)});
            if (numDel>0) return true;
        }
        return false;
    }

    public int consultarId_Programa (String nombre ) {
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if (miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query =
                    "SELECT " + ProgramaContract.COLUMN_NAME_PROGRAMA_ID +" ";
            query +=
                    "FROM " + ProgramaContract.TABLE_NAME+" ";

            query +=
                    "WHERE " + ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE + "=\'" + nombre + "\'";
            Cursor c = db.rawQuery(query, null);
            if (c.moveToNext()){
                return c.getInt(c.getColumnIndex(ProgramaContract.COLUMN_NAME_PROGRAMA_ID));
            }
        }
        return -1;
    }

    public boolean insertarPrograma( int id_gen, String nombre, String sinopsis, int anio, String pais) {
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if (miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ProgramaContract.COLUMN_NAME_GENERO_ID, id_gen);
            values.put(ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE, nombre);
            values.put(ProgramaContract.COLUMN_NAME_PROGRAMA_SINOPSIS, sinopsis);
            values.put(ProgramaContract.COLUMN_NAME_PROGRAMA_ANIO_ESTRENO, anio);
            values.put(ProgramaContract.COLUMN_NAME_PROGRAMA_PAIS_ORIGEN, pais);
            long rowide= db.insert(ProgramaContract.TABLE_NAME, null, values);
            if (rowide > 0) return true;
        }
        return false;

    }

    public boolean insertarPelicula(int  id) {
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if (miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(PeliculaContract.COLUMN_NAME_PELICULA_ID, id);
            long rowide= db.insert(PeliculaContract.TABLE_NAME, null, values);
            if (rowide > 0) return true;
        }
        return false;
    }

    public boolean insertarSerie(int  id) {
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if (miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(SerieContract.COLUMN_NAME_SERIE_ID, id);
            long rowide= db.insert(SerieContract.TABLE_NAME, null, values);
            if (rowide > 0) return true;
        }
        return false;
    }

    public boolean actualizarPrograma(int id_gen, String nombre, String sinopsis, int anio, String pais){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if (miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ProgramaContract.COLUMN_NAME_GENERO_ID, id_gen);
            values.put(ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE,nombre);
            values.put(ProgramaContract.COLUMN_NAME_PROGRAMA_SINOPSIS, sinopsis);
            values.put(ProgramaContract.COLUMN_NAME_PROGRAMA_ANIO_ESTRENO, anio);
            values.put(ProgramaContract.COLUMN_NAME_PROGRAMA_PAIS_ORIGEN, pais);

            String query= ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE+"=?";
            int rowid=db.update(ProgramaContract.TABLE_NAME, values, query, new String[]{nombre});
            if (rowid>0) return true;
        }
        return false;
    }

    public Cursor consultarSerieLikeNombre(String nombre){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query =
                    "SELECT " + ProgramaContract.COLUMN_NAME_PROGRAMA_ID + "," +
                            ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE + "," +
                            ProgramaContract.COLUMN_NAME_PROGRAMA_SINOPSIS + ","+
                            ProgramaContract.COLUMN_NAME_PROGRAMA_ANIO_ESTRENO + "," +
                            ProgramaContract.COLUMN_NAME_PROGRAMA_PAIS_ORIGEN + " ";
            query +=
                    "FROM " + ProgramaContract.TABLE_NAME + " JOIN " +
                            SerieContract.TABLE_NAME +
                            " ON " + SerieContract.COLUMN_NAME_SERIE_ID + "="
                            + ProgramaContract.COLUMN_NAME_PROGRAMA_ID + " ";
            query +=
                    "WHERE " + ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE + " LIKE \'%"+nombre+"%\'";
            Cursor c = db.rawQuery(query, null);
            return c;
        }
        else return null;
    }

    public Cursor consultarSeriePorNombre(String nombre){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query =
                    "SELECT " + ProgramaContract.COLUMN_NAME_PROGRAMA_ID + "," +
                            ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE + "," +
                            ProgramaContract.COLUMN_NAME_PROGRAMA_SINOPSIS + ","+
                            GeneroContract.COLUMN_NAME_GENERO_NOMBRE +","+
                            ProgramaContract.COLUMN_NAME_PROGRAMA_ANIO_ESTRENO + "," +
                            ProgramaContract.COLUMN_NAME_PROGRAMA_PAIS_ORIGEN + " ";
            query +=
                    "FROM " + ProgramaContract.TABLE_NAME+" NATURAL JOIN "+
                            GeneroContract.TABLE_NAME+ " JOIN " +
                            SerieContract.TABLE_NAME +
                            " ON " + SerieContract.COLUMN_NAME_SERIE_ID + "="
                            + ProgramaContract.COLUMN_NAME_PROGRAMA_ID + " ";
            query +=
                    "WHERE " + ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE +"=\'"+nombre+"\'";
            Cursor c = db.rawQuery(query, null);
            return c;
        }
        else return null;
    }

    public boolean insertarCapitulo(int id_cap, String nombreCap, int id_temp, int id_ser){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if (miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CapituloContract.COLUMN_NAME_CAPITULO_ID, id_cap);
            values.put(CapituloContract.COLUMN_NAME_CAPITULO_NOMBRE,nombreCap);
            values.put(CapituloContract.COLUMN_NAME_TEMPORADA_ID, id_temp);
            values.put(CapituloContract.COLUMN_NAME_SERIE_ID, id_ser);

            long rowide= db.insert(CapituloContract.TABLE_NAME, null, values);
            if (rowide > 0) return true;
        }
        return false;
    }
/*
    public Cursor getTemporadasDeSerie(int idSerie){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query =
                    "SELECT " + TemporadaContract.COLUMN_NAME_TEMPORADA_ID + " ";
            query +=
                    "FROM " + TemporadaContract.TABLE_NAME+" ";
            query +=
                    "WHERE " + TemporadaContract.COLUMN_NAME_PROGRAMA_ID +"=? ";
            Cursor c = db.rawQuery(query, new String[]{Integer.toString(idSerie)});
            return c;
        }
        else return null;
    }*/

    public Cursor getTemporadasDeSerie(int idSerie){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query =
                    "SELECT " +TemporadaContract.TABLE_NAME+"."+ TemporadaContract.COLUMN_NAME_TEMPORADA_ID + ","
                    +"COUNT("+CapituloContract.TABLE_NAME+"."+ CapituloContract.COLUMN_NAME_TEMPORADA_ID+") AS cuenta ";
            query +=
                    "FROM " + TemporadaContract.TABLE_NAME+" LEFT OUTER JOIN "+CapituloContract.TABLE_NAME +
                            " ON "+TemporadaContract.TABLE_NAME+"."+ TemporadaContract.COLUMN_NAME_TEMPORADA_ID
                            +"="+CapituloContract.TABLE_NAME+"."+CapituloContract.COLUMN_NAME_TEMPORADA_ID+" AND "
                            +TemporadaContract.TABLE_NAME+"."+TemporadaContract.COLUMN_NAME_PROGRAMA_ID+"="
                            +CapituloContract.TABLE_NAME+"."+CapituloContract.COLUMN_NAME_SERIE_ID+" ";
            query +=
                    "WHERE " +TemporadaContract.TABLE_NAME+"."+ TemporadaContract.COLUMN_NAME_PROGRAMA_ID +"=? ";
            query+=
                    "GROUP BY "+TemporadaContract.TABLE_NAME+"."+TemporadaContract.COLUMN_NAME_TEMPORADA_ID;
            Cursor c = db.rawQuery(query, new String[]{Integer.toString(idSerie)});
            return c;
        }
        else return null;
    }


    public boolean actualizarCapitulo(int id_cap_old, int id_cap_new, String nombreCap, int id_temp, int id_ser){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if (miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CapituloContract.COLUMN_NAME_CAPITULO_ID, id_cap_new);
            values.put(CapituloContract.COLUMN_NAME_CAPITULO_NOMBRE,nombreCap);
            values.put(CapituloContract.COLUMN_NAME_TEMPORADA_ID, id_temp);
            values.put(CapituloContract.COLUMN_NAME_SERIE_ID, id_ser);

            String query= CapituloContract.COLUMN_NAME_SERIE_ID+"=? AND "
                    +CapituloContract.COLUMN_NAME_TEMPORADA_ID+"=? AND "
                    +CapituloContract.COLUMN_NAME_CAPITULO_ID+"=?";
            String[] compare = new String[]{Integer.toString(id_ser),Integer.toString(id_temp),Integer.toString(id_cap_old)};
            int rowid=db.update(CapituloContract.TABLE_NAME, values, query, compare);
            if (rowid>0) return true;
        }
        return false;
    }

    public Cursor consultarCapitulosPorTemporada(int idTemporada, int idSerie){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query = "SELECT " + CapituloContract.COLUMN_NAME_CAPITULO_ID +","+
                    CapituloContract.COLUMN_NAME_CAPITULO_NOMBRE+" ";
            query +=
                    "FROM " + CapituloContract.TABLE_NAME+" ";
            query +=
                    "WHERE "+CapituloContract.COLUMN_NAME_TEMPORADA_ID+"=? AND " + CapituloContract.COLUMN_NAME_SERIE_ID +"=? ";
            query += "ORDER BY "+CapituloContract.COLUMN_NAME_CAPITULO_ID;

            Cursor c = db.rawQuery(query, new String[]{Integer.toString(idTemporada),Integer.toString(idSerie)});
            return c;
        }
        else return null;
    }

    public Cursor consultarInfoCapitulo(int idTemporada, int idSerie, int idCap){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query = "SELECT " + CapituloContract.COLUMN_NAME_CAPITULO_ID +","+
                    CapituloContract.COLUMN_NAME_CAPITULO_NOMBRE+" ";
            query +=
                    "FROM " + CapituloContract.TABLE_NAME+" ";
            query +=
                    "WHERE "+CapituloContract.COLUMN_NAME_TEMPORADA_ID+"=? AND "
                            + CapituloContract.COLUMN_NAME_SERIE_ID +"=? AND "
                            + CapituloContract.COLUMN_NAME_CAPITULO_ID+"=?";
            Cursor c = db.rawQuery(query, new String[]{Integer.toString(idTemporada),Integer.toString(idSerie),
            Integer.toString(idCap)});
            return c;
        }
        else return null;
    }

    public boolean insertarTemporada(int id_serie, int  idTemporada){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if (miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(TemporadaContract.COLUMN_NAME_PROGRAMA_ID, id_serie);
            values.put(TemporadaContract.COLUMN_NAME_TEMPORADA_ID, idTemporada);

            long rowide= db.insert(TemporadaContract.TABLE_NAME, null, values);
            if (rowide > 0) return true;
        }
        return false;
    }

    public boolean AgregarUsuario(String nombre, String Contrasenia){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(UsuarioContract.COLUMN_NAME_USUARIO_NOMBRE, nombre);
            values.put(UsuarioContract.COLUMN_NAME_USUARIO_PASSWORD, Contrasenia);
            long rowid=db.insert(UsuarioContract.TABLE_NAME, null, values);
            if (rowid>0) return true;
        }
        return false;
    }


    public boolean actualizarUsuario(String nombre, String contrasenia){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if (miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(UsuarioContract.COLUMN_NAME_USUARIO_NOMBRE, nombre);
            values.put(UsuarioContract.COLUMN_NAME_USUARIO_PASSWORD,contrasenia);
            String query= UsuarioContract.COLUMN_NAME_USUARIO_NOMBRE+"=?";
            int rowid=db.update(UsuarioContract.TABLE_NAME, values, query, new String[]{nombre});
            if (rowid>0) return true;
        }
        return false;
    }

}
