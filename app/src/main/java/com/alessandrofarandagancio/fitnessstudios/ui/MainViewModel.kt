package com.alessandrofarandagancio.fitnessstudios.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessandrofarandagancio.fitnessstudios.Page
import com.alessandrofarandagancio.fitnessstudios.R
import com.alessandrofarandagancio.fitnessstudios.repository.AppSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val appSettingsRepository: AppSettingsRepository) :
    ViewModel() {

    init {
        loadSettings()
    }

    private val _toShowPage = MutableLiveData<Int>()

    val toShowPage: LiveData<Int>
        get() = _toShowPage

    fun setLastPage(id: Int) {
        viewModelScope.launch {
            val value = when (id) {
                R.id.MapFragment -> Page.MAP
                R.id.ListFragment -> Page.LIST
                else -> throw java.lang.Exception("Not Happen Again.")
            }
            appSettingsRepository.setLastPage(value)
            _toShowPage.value = id
        }
    }

    private fun loadSettings() {
        viewModelScope.launch {
            appSettingsRepository.getLastPage().collect {
                _toShowPage.value = when (it) {
                    Page.MAP -> R.id.MapFragment
                    Page.LIST -> R.id.ListFragment
                    else -> throw java.lang.Exception("Not Happen Again.")
                }
            }
        }
    }

}