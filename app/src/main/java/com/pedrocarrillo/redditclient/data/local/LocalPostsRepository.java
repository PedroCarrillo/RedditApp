package com.pedrocarrillo.redditclient.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.pedrocarrillo.redditclient.data.PostsDataSource;
import com.pedrocarrillo.redditclient.domain.RedditContentData;
import com.pedrocarrillo.redditclient.domain.RedditContent;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by pedrocarrillo on 5/4/17.
 */

public class LocalPostsRepository implements PostsDataSource {

    private final BriteDatabase mDatabaseHelper;

    private static LocalPostsRepository ourInstance;

    private Func1<Cursor, RedditContent> mTaskMapperFunction;

    private String[] projection = {
            PostsPersistenceContract.PostEntry.COLUMN_NAME_ENTRY_ID,
            PostsPersistenceContract.PostEntry.COLUMN_NAME_TITLE,
            PostsPersistenceContract.PostEntry.COLUMN_NAME_AUTHOR,
            PostsPersistenceContract.PostEntry.COLUMN_NAME_IS_NSFW,
            PostsPersistenceContract.PostEntry.COLUMN_NAME_SUBREDDIT,
            PostsPersistenceContract.PostEntry.COLUMN_NAME_THUMBNAIL,
            PostsPersistenceContract.PostEntry.COLUMN_NAME_CREATED_AT,
            PostsPersistenceContract.PostEntry.COLUMN_NAME_IS_FAVORITE
    };

    public static LocalPostsRepository getInstance(@NonNull Context context,
                                                   @NonNull Scheduler scheduler) {
        if (ourInstance == null) {
            ourInstance = new LocalPostsRepository(context, scheduler);
        }
        return ourInstance;
    }

    private LocalPostsRepository(@NonNull Context context,
                                 @NonNull Scheduler scheduler) {
        SqlBrite sqlBrite = SqlBrite.create();
        RedditLurkerDbHelper dbHelper = new RedditLurkerDbHelper(context);
        mDatabaseHelper = sqlBrite.wrapDatabaseHelper(dbHelper, scheduler);
        mTaskMapperFunction = this::getPost;
    }

    private RedditContent getPost(@NonNull Cursor c){
        String id = c.getString(c.getColumnIndexOrThrow(PostsPersistenceContract.PostEntry.COLUMN_NAME_ENTRY_ID));
        String author = c.getString(c.getColumnIndexOrThrow(PostsPersistenceContract.PostEntry.COLUMN_NAME_AUTHOR));
        String subreddit = c.getString(c.getColumnIndexOrThrow(PostsPersistenceContract.PostEntry.COLUMN_NAME_SUBREDDIT));
        String title = c.getString(c.getColumnIndexOrThrow(PostsPersistenceContract.PostEntry.COLUMN_NAME_TITLE));
        String thumbnail = c.getString(c.getColumnIndexOrThrow(PostsPersistenceContract.PostEntry.COLUMN_NAME_THUMBNAIL));
        boolean isNsfw = c.getInt(c.getColumnIndexOrThrow(PostsPersistenceContract.PostEntry.COLUMN_NAME_IS_NSFW)) == 1;
        boolean favorite = c.getInt(c.getColumnIndexOrThrow(PostsPersistenceContract.PostEntry.COLUMN_NAME_IS_FAVORITE)) == 1;
        long createdAt = c.getLong(c.getColumnIndexOrThrow(PostsPersistenceContract.PostEntry.COLUMN_NAME_CREATED_AT));
        RedditContentData redditContentData = new RedditContentData(id, title, subreddit, isNsfw, createdAt, thumbnail, author);
        RedditContent redditContent = new RedditContent(redditContentData);
        redditContent.setFavorite(favorite);
        return redditContent;
    }

    @Override
    public Observable<RedditContent> getPosts() {
        String sql = String.format("SELECT %s FROM %s WHERE %s LIKE ?", TextUtils.join(",", projection), PostsPersistenceContract.PostEntry.TABLE_NAME, PostsPersistenceContract.PostEntry.COLUMN_NAME_IS_FAVORITE);
        return mDatabaseHelper.createQuery(PostsPersistenceContract.PostEntry.TABLE_NAME, sql, "1")
                .mapToList(mTaskMapperFunction)
                .first()
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from);
    }

    @Override
    public Observable<RedditContent> getPaginatedPosts() {
        return Observable.empty();
    }

    @Override
    public void setFavorite(RedditContent redditContent, boolean favorite) {
        if (favorite) {
            ContentValues values = new ContentValues();
            values.put(PostsPersistenceContract.PostEntry.COLUMN_NAME_ENTRY_ID, redditContent.getRedditContentData().getId());
            values.put(PostsPersistenceContract.PostEntry.COLUMN_NAME_AUTHOR, redditContent.getRedditContentData().getAuthor());
            values.put(PostsPersistenceContract.PostEntry.COLUMN_NAME_CREATED_AT, redditContent.getRedditContentData().getCreatedAt());
            values.put(PostsPersistenceContract.PostEntry.COLUMN_NAME_IS_NSFW, redditContent.getRedditContentData().isNsfw());
            values.put(PostsPersistenceContract.PostEntry.COLUMN_NAME_SUBREDDIT, redditContent.getRedditContentData().getSubredditNamePrefixed());
            values.put(PostsPersistenceContract.PostEntry.COLUMN_NAME_THUMBNAIL, redditContent.getRedditContentData().getThumbnail());
            values.put(PostsPersistenceContract.PostEntry.COLUMN_NAME_TITLE, redditContent.getRedditContentData().getTitle());
            values.put(PostsPersistenceContract.PostEntry.COLUMN_NAME_IS_FAVORITE, redditContent.isFavorite() ? 1 : 0);
            mDatabaseHelper.insert(PostsPersistenceContract.PostEntry.TABLE_NAME, values, SQLiteDatabase.CONFLICT_IGNORE);
        } else {
            ContentValues values = new ContentValues();
            values.put(PostsPersistenceContract.PostEntry.COLUMN_NAME_IS_FAVORITE, 0);

            String selection = PostsPersistenceContract.PostEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
            String[] selectionArgs = {redditContent.getRedditContentData().getId()};
            mDatabaseHelper.update(PostsPersistenceContract.PostEntry.TABLE_NAME, values, selection, selectionArgs);
        }
    }

}
