package co.harismiftahulhudha.imagecalculator.home.ui_home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.harismiftahulhudha.imagecalculator.home.domain.models.ImageCalculatorModel
import co.harismiftahulhudha.imagecalculator.home.domain.payloads.CreateImageCalculatorPayload
import co.harismiftahulhudha.imagecalculator.home.interactors.usecases.CreateImageCalculatorUseCase
import co.harismiftahulhudha.imagecalculator.home.interactors.usecases.GetListImageCalculatorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val createImageCalculatorUseCase: CreateImageCalculatorUseCase,
    private val getListImageCalculatorUseCase: GetListImageCalculatorUseCase,
    private val state: SavedStateHandle
): ViewModel() {

    private val channel = Channel<Event>()
    val event = channel.receiveAsFlow()

    var switchStorage = state.get<Int>("switchStorage") ?: -1
        set(value) {
            field = value
            state["switchStorage"] = value
        }
    
    fun setImageCalculatorFromFile(content: String) = viewModelScope.launch {
        val list: MutableList<ImageCalculatorModel> = mutableListOf()
        if (content.isNotBlank()) {
            content.split(";;").forEach { file ->
                if (file.isNotEmpty()) {
                    val splits = file.split("&&")
                    if (splits.isNotEmpty()) {
                        list.add(
                            ImageCalculatorModel(
                                id = "${splits[1]}${splits[2]}".hashCode().toLong(), image = splits[0], input = splits[1], result = splits[2]
                            )
                        )
                    }
                }
            }
        }
        channel.send(Event.OnGetListImageCalculator(list))
    }

    fun createImageCalculator(payload: CreateImageCalculatorPayload) = viewModelScope.launch {
        createImageCalculatorUseCase(payload).collect {
            it.fold(
                success = { data, _ ->
                    channel.send(Event.OnGetListImageCalculator(data))
                }
            )
        }
    }

    fun onCreateImageCalculator(payload: CreateImageCalculatorPayload) = viewModelScope.launch {
        channel.send(Event.OnCreateImageCalculator(payload))
    }

    fun getListImageCalculator() = viewModelScope.launch {
        getListImageCalculatorUseCase().collect {
            it.fold(
                success = { data, _ ->
                    channel.send(Event.OnGetListImageCalculator(data))
                }
            )
        }
    }

    sealed class Event {
        data class OnCreateImageCalculator(val payload: CreateImageCalculatorPayload): Event()
        data class OnGetListImageCalculator(val data: MutableList<ImageCalculatorModel>): Event()
    }
}