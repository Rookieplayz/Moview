import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ali on 22/05/2018.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="Moview";
            public static int DB_VERSION=1;
        SQLiteDatabase sqLiteDatabase;

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /**Create Tables*/
        String create_table_authTable="CREATE TABLE AUTH(EMAIL VARCHAR(100) NOT NULL, USERNAME VARCHAR(100) NOT NULL, PASSWORD TEXT NOT NULL)";

        String create_table_USERS="CREATE TABLE USERS(UID INTEGER PRIMARY KEY AUTO INCREMENTAL, EMAIL VARCHAR(100), USERNAME VARCHAR(50));"+
                "FOREIGN KEY(EMAIL) REFERENCE ON AUTH(EMAIL) CASCADE ON UPDATE CASCADE ON DELETE;" +
                "FOREIGN KEY(USERNAME) REFERENCE ON AUTH(USERNAME) CASCADE ON UPDATE CASCADE ON DELETE;";

        String create_table_MOVIES="CREATE TABLE MOVIES(MID INTEGER PRIMARY KEY, TITLE VARCHAR(100),DESCRIPTION TEXT,RELEASE_ON DATE,LIKES INT, LIKER VARCHAR(100),LIKED BOOLEAN, UPLOADER VARCHAR(100),LINK TEXT);" +
                "FOREIGN KEY(LIKER) REFERENCE ON USERS(UID) CASCADE ON UPDATE CASCADE ON DELETE;" +
                "FOREIGN KEY(UPLOADER) REFERENCE ON USERS(UID) CASCADE ON UPDATE CASCADE ON DELETE;";


        String create_table_USER_INFORMATION="CREATE TABLE USER_INFORMATION(UID INTEGER PRIMARY KEY, FAV_VIDS VARCHAR(100),FOLLOWERS VARCHAR(100),FOLLOWING VARCHAR(100),LIKED_VIDS VARCHAR(100));" +
                "FOREIGN KEY(FAV_VIDS) REFERENCE ON MOVIE(MID) CASCADE ON UPDATE CASCADE ON DELETE;" +
                "FOREIGN KEY(LIKED_VIDS) REFERENCE ON MOVIE(MID) CASCADE ON UPDATE CASCADE ON DELETE;" +
                "FOREIGN KEY(FOLLOWERS) REFERENCE ON USERS(UID) CASCADE ON UPDATE CASCADE ON DELETE;" +
                "FOREIGN KEY(FOLLOWING) REFERENCE ON USERS(UID) CASCADE ON UPDATE CASCADE ON DELETE;";
        sqLiteDatabase.execSQL(create_table_authTable);
        sqLiteDatabase.execSQL(create_table_USERS);
        sqLiteDatabase.execSQL(create_table_MOVIES);
        sqLiteDatabase.execSQL(create_table_USER_INFORMATION);
        this.sqLiteDatabase=sqLiteDatabase;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    String query="DROP TABLE IF EXISTS create_table_authTable";
        String query2="DROP TABLE IF EXISTS create_table_USERS";
        String query3="DROP TABLE IF EXISTS create_table_MOVIES";
        String query4="DROP TABLE IF EXISTS create_table_USER_INFORMATION";

        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.execSQL(query2);
        sqLiteDatabase.execSQL(query3);
        sqLiteDatabase.execSQL(query4);
        this.onCreate(sqLiteDatabase);
    }
}
