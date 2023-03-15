package co.geeksempire.frames.you

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.frames.you.Utils.NetworkConnections.NetworkCheckpoint
import co.geeksempire.frames.you.Utils.NetworkConnections.NetworkConnectionListener
import co.geeksempire.frames.you.Utils.NetworkConnections.NetworkConnectionListenerInterface
import co.geeksempire.frames.you.databinding.EntryLayoutBinding

class EntryConfigurations : AppCompatActivity(), NetworkConnectionListenerInterface {

    val networkCheckpoint: NetworkCheckpoint by lazy {
        NetworkCheckpoint(applicationContext)
    }

    private val networkConnectionListener: NetworkConnectionListener by lazy {
        NetworkConnectionListener(this@EntryConfigurations, entryLayoutBinding.rootView, networkCheckpoint)
    }

    lateinit var entryLayoutBinding: EntryLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        entryLayoutBinding = EntryLayoutBinding.inflate(layoutInflater)
        setContentView(entryLayoutBinding.root)

        networkConnectionListener.networkConnectionListenerInterface = this@EntryConfigurations

    }

    override fun networkAvailable() {
        TODO("Not yet implemented")
    }

    override fun networkLost() {
        TODO("Not yet implemented")
    }

}