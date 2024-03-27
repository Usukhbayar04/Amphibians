package com.example.amphibians.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibians.AmphibiansApplication
import com.example.amphibians.data.AmphibiansRepository
import com.example.amphibians.model.Amphibian
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// Үндсэн дэлгэуийн төлөв нь success,error,loading гэсэн дэлгэуүүдийг хэрэгжүүлхээр битүүмжилнэ.
sealed interface AmphibiansUiState {
    data class Success(val amphibians: List<Amphibian>) : AmphibiansUiState
    object Error : AmphibiansUiState
    object Loading : AmphibiansUiState
}

// Программын өгөгдөл болон хадгалах method-уудыг агуулсан viewModel
class AmphibiansViewModel(private val amphibiansRepository: AmphibiansRepository) : ViewModel() {
    var amphibiansUiState: AmphibiansUiState by mutableStateOf(AmphibiansUiState.Loading)
        private set
    // Классыг эхлүүлэх хэсэг болгон гүйцэтгэдэг.
    init {
        getAmphibians()
    }

    // эхлэсэн үед доорх функцийг дууддаг.
    fun getAmphibians() {
        viewModelScope.launch {
            // Өгөгдөл дуудаж байгаа
            amphibiansUiState = AmphibiansUiState.Loading
            amphibiansUiState = try {
                AmphibiansUiState.Success(amphibiansRepository.getAmphibians())
            } catch (e: IOException) {
                AmphibiansUiState.Error
            } catch (e: HttpException) {
                AmphibiansUiState.Error
            }
        }
    }

    // Хамтрагч объект нь класстай холбоотой статик гишүүн эсвэл ашигтай method-уудыг тодорхойлдог.
    companion object {
        // AmphibiansViewModel-ийн жишээ үүсгэх Factory-ийг ашигладаг.
        // ViewModel-той ажиллах үйлдвэрлэлийг бий болгодог кодын блок
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            // Initializer block
            initializer {
                // AmphibiansApplication-аас жишээг дамжуулна
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as AmphibiansApplication)
                val amphibiansRepository = application.container.amphibiansRepository
                AmphibiansViewModel(amphibiansRepository = amphibiansRepository)
            }
        }
    }
}
