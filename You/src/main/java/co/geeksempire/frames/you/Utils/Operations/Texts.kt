/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/3/23, 7:10 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Utils.Operations

fun generateCreatorIcon(creatorUrl: String) : String {

    return if (creatorUrl.contains("facebook")) {

        "https://firebasestorage.googleapis.com/v0/b/frames-you.appspot.com/o/You%2FAssets%2FSocialMedia%2FIcons%2Ffacebook.png?alt=media"

    } else if (creatorUrl.contains("instagram")) {

        "https://firebasestorage.googleapis.com/v0/b/frames-you.appspot.com/o/You%2FAssets%2FSocialMedia%2FIcons%2Finstagram.png?alt=media"

    } else if (creatorUrl.contains("pinterest")) {

        "https://firebasestorage.googleapis.com/v0/b/frames-you.appspot.com/o/You%2FAssets%2FSocialMedia%2FIcons%2Fpinterest.png?alt=media"

    } else if (creatorUrl.contains("tiktok")) {

        "https://firebasestorage.googleapis.com/v0/b/frames-you.appspot.com/o/You%2FAssets%2FSocialMedia%2FIcons%2Ftiktok.png?alt=media"

    }
    else if (creatorUrl.contains("twitter")) {

        "https://firebasestorage.googleapis.com/v0/b/frames-you.appspot.com/o/You%2FAssets%2FSocialMedia%2FIcons%2Ftwitter.png?alt=media"

    }
    else if (creatorUrl.contains("youtube")) {

        "https://firebasestorage.googleapis.com/v0/b/frames-you.appspot.com/o/You%2FAssets%2FSocialMedia%2FIcons%2Fyoutube.png?alt=media"

    } else {

        "https://firebasestorage.googleapis.com/v0/b/frames-you.appspot.com/o/You%2FAssets%2FSocialMedia%2FIcons%2Fwebsite.png?alt=media"

    }
}