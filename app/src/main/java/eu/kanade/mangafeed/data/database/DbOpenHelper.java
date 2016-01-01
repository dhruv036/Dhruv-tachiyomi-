package eu.kanade.mangafeed.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import eu.kanade.mangafeed.data.database.tables.CategoryTable;
import eu.kanade.mangafeed.data.database.tables.MangaCategoryTable;
import eu.kanade.mangafeed.data.database.tables.MangaSyncTable;
import eu.kanade.mangafeed.data.database.tables.ChapterTable;
import eu.kanade.mangafeed.data.database.tables.MangaTable;

public class DbOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mangafeed.db";
    public static final int DATABASE_VERSION = 2;

    public DbOpenHelper(@NonNull Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        db.execSQL(MangaTable.getCreateTableQuery());
        db.execSQL(ChapterTable.getCreateTableQuery());
        db.execSQL(MangaSyncTable.getCreateTableQuery());
        db.execSQL(CategoryTable.getCreateTableQuery());
        db.execSQL(MangaCategoryTable.getCreateTableQuery());

        // DB indexes
        db.execSQL(MangaTable.getCreateUrlIndexQuery());
        db.execSQL(MangaTable.getCreateFavoriteIndexQuery());
        db.execSQL(ChapterTable.getCreateMangaIdIndexQuery());
    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1) {
            db.execSQL("ALTER TABLE manga_sync RENAME TO tmp;");
            db.execSQL(MangaSyncTable.getCreateTableQuery());
            db.execSQL("INSERT INTO " + MangaSyncTable.TABLE + " SELECT * FROM tmp;");
            db.execSQL("DROP TABLE tmp;");
        }
    }

    @Override
    public void onConfigure(@NonNull SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

}