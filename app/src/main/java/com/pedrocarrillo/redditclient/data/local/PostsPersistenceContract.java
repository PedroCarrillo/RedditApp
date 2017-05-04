package com.pedrocarrillo.redditclient.data.local;

import android.provider.BaseColumns;

/**
 * Created by pedrocarrillo on 5/4/17.
 */

public final class PostsPersistenceContract {

    private PostsPersistenceContract() {}

    /* Inner class that defines the table contents */
    public static abstract class PostEntry implements BaseColumns {
        public static final String TABLE_NAME = "posts";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBREDDIT = "subreddit";
        public static final String COLUMN_NAME_IS_NSFW = "is_nsfw";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_CREATED_AT = "created_at";
        public static final String COLUMN_NAME_THUMBNAIL = "thumbnail";
        public static final String COLUMN_NAME_IS_FAVORITE = "isFavorite";
    }

}
