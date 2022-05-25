package com.example.chapter7latihan.manager

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(context: Context) {
    // dua data store, data user dan data status user
    private val dataStore : DataStore<Preferences> = context.createDataStore(name = "user_prefs2")
    private val dataStore2 : DataStore<Preferences> = context.createDataStore(name = "status")


    companion object {
        //object data
        val USERNAME = preferencesKey<String>("USERNAME")
        val PASSWORD = preferencesKey<String>("PASSWORD")

        val ADDRESS = preferencesKey<String>("ADDRESS")
        val IMAGE = preferencesKey<String>("IMAGE")
        val NAME = preferencesKey<String>("NAME")
        val UMUR = preferencesKey<String>("UMUR")

        val EMAIL = preferencesKey<String>("EMAIL")
        val ID = preferencesKey<String>("ID")
        val STATUS = preferencesKey<String>("STATUS")


    }
    //fungsi login insert data ke datastore
    suspend fun setDataUser( id : String, username : String, password : String, address : String, umur : String, image : String, name : String){
        dataStore.edit {
            it[USERNAME] = username
            it[PASSWORD] = password
            it[ID] = id
            it[IMAGE] = image
            it[UMUR] = umur
            it[ADDRESS]= address
            it [NAME] = name


        }
    }
    suspend fun setID(id : String){
        dataStore.edit {
            it[ID] = id
        }
    }


    // menghapus data yang ada di datastore
    suspend fun logout(){
        dataStore.edit {
            it.clear()
        }
    }
    //status user di datastore2 (sebagai pengecekan auth)
    suspend fun setStatus(status : String){
        dataStore2.edit {
            it[STATUS] = status
        }
    }
    // sebagai pengakses data yang ada di datastore via livedatya
    val userNAME : Flow<String> = dataStore.data.map {
        it[UserManager.USERNAME] ?: ""
    }
    val userADDRESS : Flow<String> = dataStore.data.map {
        it[UserManager.ADDRESS] ?: ""
    }
    val userUMUR : Flow<String> = dataStore.data.map {
        it[UserManager.UMUR] ?: ""
    }
    val userNAME2 : Flow<String> = dataStore.data.map {
        it[UserManager.NAME] ?: ""
    }
    val userIMAGE : Flow<String> = dataStore.data.map {
        it[UserManager.IMAGE] ?: ""
    }
    val userID : Flow<String> = dataStore.data.map {
        it[UserManager.ID] ?: ""
    }

    val userPASS : Flow<String> = dataStore.data.map {
        it[UserManager.PASSWORD] ?: ""
    }
    val userSTATUS : Flow<String> = dataStore2.data.map {
        it[UserManager.STATUS] ?: "no"
    }

}