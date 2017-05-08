package com.pedrocarrillo.redditclient.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.pedrocarrillo.redditclient.data.PostsDataSource;
import com.pedrocarrillo.redditclient.domain.RedditData;
import com.pedrocarrillo.redditclient.domain.RedditPost;
import com.pedrocarrillo.redditclient.domain.RedditPostMetadata;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

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

    private Func1<Cursor, RedditPostMetadata> mTaskMapperFunction;

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

    private RedditPostMetadata getPost(@NonNull Cursor c){
        String id = c.getString(c.getColumnIndexOrThrow(PostsPersistenceContract.PostEntry.COLUMN_NAME_ENTRY_ID));
        String author = c.getString(c.getColumnIndexOrThrow(PostsPersistenceContract.PostEntry.COLUMN_NAME_AUTHOR));
        String subreddit = c.getString(c.getColumnIndexOrThrow(PostsPersistenceContract.PostEntry.COLUMN_NAME_SUBREDDIT));
        String title = c.getString(c.getColumnIndexOrThrow(PostsPersistenceContract.PostEntry.COLUMN_NAME_TITLE));
        String thumbnail = c.getString(c.getColumnIndexOrThrow(PostsPersistenceContract.PostEntry.COLUMN_NAME_THUMBNAIL));
        boolean isNsfw = c.getInt(c.getColumnIndexOrThrow(PostsPersistenceContract.PostEntry.COLUMN_NAME_IS_NSFW)) == 1;
        boolean favorite = c.getInt(c.getColumnIndexOrThrow(PostsPersistenceContract.PostEntry.COLUMN_NAME_IS_FAVORITE)) == 1;
        long createdAt = c.getLong(c.getColumnIndexOrThrow(PostsPersistenceContract.PostEntry.COLUMN_NAME_CREATED_AT));
        RedditPost redditPost = new RedditPost(id, title, subreddit, isNsfw, createdAt, thumbnail, author);
        RedditPostMetadata redditPostMetadata = new RedditPostMetadata(redditPost);
        redditPostMetadata.setFavorite(favorite);
        return redditPostMetadata;
    }

    @Override
    public Observable<RedditPostMetadata> getPosts() {
        String sql = String.format("SELECT %s FROM %s WHERE %s LIKE ?", TextUtils.join(",", projection), PostsPersistenceContract.PostEntry.TABLE_NAME, PostsPersistenceContract.PostEntry.COLUMN_NAME_IS_FAVORITE);
        return mDatabaseHelper.createQuery(PostsPersistenceContract.PostEntry.TABLE_NAME, sql, "1")
                .mapToList(mTaskMapperFunction)
                .first()
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from);
    }

    @Override
    public Observable<RedditPostMetadata> getPaginatedPosts() {
        return Observable.empty();
    }

    @Override
    public void setFavorite(RedditPostMetadata redditPostMetadata, boolean favorite) {
        if (favorite) {
            ContentValues values = new ContentValues();
            values.put(PostsPersistenceContract.PostEntry.COLUMN_NAME_ENTRY_ID, redditPostMetadata.getPostData().getId());
            values.put(PostsPersistenceContract.PostEntry.COLUMN_NAME_AUTHOR, redditPostMetadata.getPostData().getAuthor());
            values.put(PostsPersistenceContract.PostEntry.COLUMN_NAME_CREATED_AT, redditPostMetadata.getPostData().getCreatedAt());
            values.put(PostsPersistenceContract.PostEntry.COLUMN_NAME_IS_NSFW, redditPostMetadata.getPostData().isNsfw());
            values.put(PostsPersistenceContract.PostEntry.COLUMN_NAME_SUBREDDIT, redditPostMetadata.getPostData().getSubredditNamePrefixed());
            values.put(PostsPersistenceContract.PostEntry.COLUMN_NAME_THUMBNAIL, redditPostMetadata.getPostData().getThumbnail());
            values.put(PostsPersistenceContract.PostEntry.COLUMN_NAME_TITLE, redditPostMetadata.getPostData().getTitle());
            values.put(PostsPersistenceContract.PostEntry.COLUMN_NAME_IS_FAVORITE, redditPostMetadata.isFavorite() ? 1 : 0);
            mDatabaseHelper.insert(PostsPersistenceContract.PostEntry.TABLE_NAME, values, SQLiteDatabase.CONFLICT_IGNORE);
        } else {
            ContentValues values = new ContentValues();
            values.put(PostsPersistenceContract.PostEntry.COLUMN_NAME_IS_FAVORITE, 0);

            String selection = PostsPersistenceContract.PostEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
            String[] selectionArgs = {redditPostMetadata.getPostData().getId()};
            mDatabaseHelper.update(PostsPersistenceContract.PostEntry.TABLE_NAME, values, selection, selectionArgs);
        }
    }

}
