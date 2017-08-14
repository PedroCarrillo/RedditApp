package com.pedrocarrillo.redditclient.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.pedrocarrillo.redditclient.domain.Preview;
import com.pedrocarrillo.redditclient.domain.RedditContentData;
import com.pedrocarrillo.redditclient.domain.RedditResponse;

import java.lang.reflect.Type;

/**
 * @author pedrocarrillo.
 */

public class RedditContentDataParser implements JsonDeserializer<RedditContentData> {

    private final Gson gson;

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String BODY_HTML = "body";
    private static final String REPLIES = "replies";
    private static final String OVER18 = "over_18";
    private static final String AUTHOR = "author";
    private static final String THUMBNAIL = "thumbnail";
    private static final String NUMCOMMENTS = "num_comments";
    private static final String PREVIEW = "preview";
    private static final String PERMALINK = "permalink";
    private static final String SUBREDDITNAMEPREFIXED = "subreddit_name_prefixed";
    private static final String CREATED_AT = "created_utc";
    private static final String SUBREDDIT = "subreddit";
    private static final String UPS = "ups";
    private static final String SCORES = "score";

    public RedditContentDataParser() {
        gson = new GsonBuilder()
                .registerTypeAdapter(RedditContentData.class, this)
                .create();
    }

    @Override
    public RedditContentData deserialize(JsonElement json,
                                         Type typeOfT,
                                         JsonDeserializationContext context) throws JsonParseException {
        RedditContentData redditContentData = new RedditContentData();
        JsonObject jsonObject = json.getAsJsonObject();
        redditContentData
                .setId(jsonObject.get("id").getAsString());
        if (jsonObject.has(TITLE)) {
            redditContentData
                    .setTitle(jsonObject.get(TITLE).getAsString());
        }
        if (jsonObject.has(SUBREDDITNAMEPREFIXED)) {
            redditContentData
                    .setSubredditNamePrefixed(jsonObject.get(SUBREDDITNAMEPREFIXED).getAsString());
        }
        if (jsonObject.has(OVER18)) {
            redditContentData
                    .setNsfw(jsonObject.get(OVER18).getAsBoolean());
        }
        if (jsonObject.has(CREATED_AT)) {
            redditContentData
                    .setCreatedAt(jsonObject.get(CREATED_AT).getAsLong());
        }
        if (jsonObject.has(THUMBNAIL)) {
            redditContentData
                    .setThumbnail(jsonObject.get(THUMBNAIL).getAsString());
        }
        if (jsonObject.has(AUTHOR)) {
            redditContentData
                    .setAuthor(jsonObject.get(AUTHOR).getAsString());
        }
        if (jsonObject.has(SUBREDDIT)) {
            redditContentData
                    .setSubreddit(jsonObject.get(SUBREDDIT).getAsString());
        }
        if (jsonObject.has(UPS)) {
            redditContentData
                    .setUpVotes(jsonObject.get(UPS).getAsInt());
        }
        if (jsonObject.has(SCORES)) {
            redditContentData
                    .setScore(jsonObject.get(SCORES).getAsInt());
        }
        if (jsonObject.has(NUMCOMMENTS)) {
            redditContentData
                    .setNumComments(jsonObject.get(NUMCOMMENTS).getAsInt());
        }
        if (jsonObject.has(BODY_HTML)) {
            redditContentData
                    .setBody(jsonObject.get(BODY_HTML).getAsString());
        }
        if (jsonObject.has(PERMALINK)) {
            redditContentData
                    .setPermalink(jsonObject.get(PERMALINK).getAsString());
        }
        if (jsonObject.has(PREVIEW)) {
            JsonElement previewJsonElement = jsonObject.get(PREVIEW);
            redditContentData
                    .setPreview(gson.fromJson(previewJsonElement, Preview.class));
        }
        if (jsonObject.has(REPLIES)) {
            JsonElement redditResponseJsonElement = jsonObject.get(REPLIES);
            if (redditResponseJsonElement.isJsonObject()) {
                redditContentData
                        .setReplies(gson.fromJson(redditResponseJsonElement, RedditResponse.class));
            }
        }
        return redditContentData;
    }

}
