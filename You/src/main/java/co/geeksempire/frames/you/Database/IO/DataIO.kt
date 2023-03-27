/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/27/23, 6:27 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Database.IO

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.geeksempire.frames.you.Database.Structure.DataStructure
import co.geeksempire.frames.you.Utils.Colors.primaryColors
import co.geeksempire.frames.you.Utils.Colors.uniqueGradient
import co.geeksempire.frames.you.Utils.Display.displayRatio
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async

class DataIO : ViewModel() {

    private object Keys {

        const val frameAuthorLink = "frameAuthorLink"
        const val frameAuthorNickname = "frameAuthorNickname"

        const val frameTags = "frameTags"

        const val frameUrl = "frameUrl"
        const val frameUrlHorizontal = "frameUrlHorizontal"

        const val frameTrend = "frameTrend"

        const val frameTime = "frameTime"

    }

    val allFrames: MutableLiveData<ArrayList<DataStructure>> by lazy {
        MutableLiveData<ArrayList<DataStructure>>()
    }

    fun retrieveFrames(context: Context) {

        val firestoreDirectory = "/You/Frames/${displayRatio(context)}"
        Log.d(this@DataIO.javaClass.simpleName, "${firestoreDirectory}")

        Firebase.firestore
            .collection(firestoreDirectory)
            .get(Source.DEFAULT)
            .addOnSuccessListener { querySnapshot ->

                processFramesSnapshots(context, querySnapshot)

            }.addOnFailureListener {
                it.printStackTrace()

            }

    }

    private fun processFramesSnapshots(context: Context, querySnapshot: QuerySnapshot) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        val framesItems = ArrayList<DataStructure>()

        val primaryColors = primaryColors(context)

        if (!querySnapshot.isEmpty
            && querySnapshot.documents.isNotEmpty()) {

            querySnapshot.documents.forEach { documentSnapshot ->

                framesItems.add(DataStructure(
                    frameAuthorLink = documentSnapshot.getString(DataIO.Keys.frameAuthorLink).toString(),
                    frameAuthorNickname = documentSnapshot.getString(DataIO.Keys.frameAuthorNickname).toString(),

                    frameName = documentSnapshot.id,

                    frameTags = documentSnapshot.getString(DataIO.Keys.frameTags).toString(),

                    frameUrl = documentSnapshot.getString(DataIO.Keys.frameUrl).toString(),
                    frameUrlHorizontal = documentSnapshot.getString(DataIO.Keys.frameUrlHorizontal).toString(),

                    frameTrend = documentSnapshot.getDouble(DataIO.Keys.frameTrend)?.toInt()?:1,
                    frameTime = documentSnapshot.getLong(DataIO.Keys.frameTime)?:1,
                    backgroundColors = uniqueGradient(primaryColors)
                ))

            }

        }

        allFrames.postValue(framesItems)

    }

    fun filterHotFrames(allFramesInput: ArrayList<DataStructure>) {

        allFramesInput.sortByDescending {

            it.frameTrend
        }

        allFrames.postValue(allFramesInput)

    }

    fun filterNewFrames(allFramesInput: ArrayList<DataStructure>) {

        allFramesInput.sortByDescending {

            it.frameTime
        }

        allFrames.postValue(allFramesInput)

    }

    fun filterFavoriteFrames(context: Context, allFramesInput: ArrayList<DataStructure>) {

        val favoriteIO = FavoriteIO(context)

        val filteredContent = allFramesInput.filter {

            favoriteIO.favorited(it.frameName)
        }

        allFramesInput.clear()

        allFramesInput.addAll(filteredContent)

        allFrames.postValue(allFramesInput)

    }

    fun searchFrames(allFramesInput: ArrayList<DataStructure>, searchQuery: String) {
        Log.d(this@DataIO.javaClass.simpleName, "Search: ${searchQuery}")

        val filteredContent = allFramesInput.filter {

            (it.frameAuthorNickname.lowercase().contains(searchQuery.lowercase()))
                    || (it.frameTags.lowercase().contains(searchQuery.lowercase()))
        }

        allFramesInput.clear()

        allFramesInput.addAll(filteredContent)

        allFrames.postValue(allFramesInput)

    }

}