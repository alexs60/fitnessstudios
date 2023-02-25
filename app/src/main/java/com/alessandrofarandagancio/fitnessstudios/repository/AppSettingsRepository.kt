package com.alessandrofarandagancio.fitnessstudios.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.alessandrofarandagancio.fitnessstudios.Page
import com.alessandrofarandagancio.fitnessstudios.Settings
import com.alessandrofarandagancio.fitnessstudios.datastore.SettingsSerializer
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppSettingsRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val settingsSerializer: SettingsSerializer
) {

    private val Context.settingsDataStore: DataStore<Settings> by dataStore(
        fileName = "settings.pb",
        serializer = settingsSerializer
    )

    suspend fun setLastPage(page: Page) {
        Log.d("setLastPage", "${page.name}")
        context.settingsDataStore.updateData { current: Settings ->
            Log.d("setLastPage.updateData", "${page.name}")
            current.toBuilder().setLastPage(page).build()
        }
    }

    fun getLastPage(): Flow<Page> = context.settingsDataStore.data.map { settings: Settings ->
        Log.d("getLastPage.data.map", "${settings.lastPage}")
        settings.lastPage
    }

}