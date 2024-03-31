package com.example.moneto.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneto.data.Category
import com.example.moneto.data.monetoDb
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CategoriesState(
    val categoryName: String = "",
    val categories: List<Category> = listOf()
) {


}

class CategoriesViewModel(): ViewModel(){
    private val _state = MutableStateFlow(CategoriesState())
    val uiState: StateFlow<CategoriesState> = _state.asStateFlow()

    init {
        _state.update { currentState ->
            currentState.copy(
                categories = monetoDb.query<Category>().find()
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            monetoDb.query<Category>().asFlow().collect { changes ->
                _state.update { currentState ->
                    currentState.copy(
                        categories = changes.list
                    )
                }
            }
        }
    }



    fun setNewCategoryName(name: String) {
        _state.update { currentState ->
            currentState.copy(
                categoryName = name
            )
        }
    }





    fun createNewCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            monetoDb.write {
                this.copyToRealm(Category(
                    _state.value.categoryName,
                ))
            }
            _state.update { currentState ->
                currentState.copy(
                    categoryName = ""
                )
            }
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            monetoDb.write {
                val deletingCategory = this.query<Category>("_id == $0", category._id).find().first()
                delete(deletingCategory)
            }
        }
    }

    fun hideColorPicker() {
        TODO("Not yet implemented")
    }

    fun setNewCategoryColor(color: androidx.compose.ui.graphics.Color) {

    }
}
