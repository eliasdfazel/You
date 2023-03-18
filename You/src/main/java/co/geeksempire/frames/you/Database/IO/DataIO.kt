/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/18/23, 11:39 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Database.IO

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot

class DataIO : ViewModel() {

    val allFrames: MutableLiveData<ArrayList<DocumentSnapshot>> by lazy {
        MutableLiveData<ArrayList<DocumentSnapshot>>()
    }


}