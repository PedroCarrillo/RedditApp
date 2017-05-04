package com.pedrocarrillo.redditclient.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pedrocarrillo on 5/4/17.
 */

public class RedditLurkerDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "RedditLurker.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String BOOLEAN_TYPE = " INTEGER";

    private static final String LONG_TYPE = " LONG";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PostsPersistenceContract.PostEntry.TABLE_NAME + " (" +
                    PostsPersistenceContract.PostEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + " PRIMARY KEY," +
                    PostsPersistenceContract.PostEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    PostsPersistenceContract.PostEntry.COLUMN_NAME_AUTHOR + TEXT_TYPE + COMMA_SEP +
                    PostsPersistenceContract.PostEntry.COLUMN_NAME_IS_NSFW + BOOLEAN_TYPE + COMMA_SEP +
                    PostsPersistenceContract.PostEntry.COLUMN_NAME_SUBREDDIT + TEXT_TYPE + COMMA_SEP +
                    PostsPersistenceContract.PostEntry.COLUMN_NAME_THUMBNAIL + TEXT_TYPE + COMMA_SEP +
                    PostsPersistenceContract.PostEntry.COLUMN_NAME_CREATED_AT + LONG_TYPE + COMMA_SEP +
                    PostsPersistenceContract.PostEntry.COLUMN_NAME_IS_FAVORITE + BOOLEAN_TYPE +
                    " )";

    public RedditLurkerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }
}
