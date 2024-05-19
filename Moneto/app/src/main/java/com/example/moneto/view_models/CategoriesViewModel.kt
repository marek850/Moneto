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
/**
 * Reprezentuje stav obrazovky kategórií, obsahujúci zoznam dostupných kategórií a názov novej kategórie pri vytváraní.
 *
 * @param categoryName Názov novej kategórie, ktorá sa má pridať.
 * @param categories Zoznam všetkých kategórií získaných z databázy.
 */
data class CategoriesState(
    val categoryName: String = "",
    val categories: List<Category> = listOf()
)
/**
 * ViewModel pre obrazovku kategórií, zodpovedný za správu kategórií vrátane ich vytvárania a mazania.
 * Táto trieda spravuje a aktualizuje zoznam kategórií na základe interakcií užívateľa a zmeny v databáze.
 */
class CategoriesViewModel : ViewModel(){
    private val _state = MutableStateFlow(CategoriesState())
    val state: StateFlow<CategoriesState> = _state.asStateFlow()

    init {
        // Načíta aktuálne kategórie z databázy pri inicializácii ViewModelu.
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
    /**
     * Nastaví názov novej kategórie v stave.
     * @param name Názov novej kategórie, ktorý sa má použiť.
     */
    fun setNewCategoryName(name: String) {
        _state.update { currentState ->
            currentState.copy(
                categoryName = name
            )
        }
    }
    /**
     * Vytvorí novú kategóriu podľa názvu uloženého v stave a uloží ju do databázy.
     */
    fun createNewCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            monetoDb.write {
                this.copyToRealm(Category(
                    _state.value.categoryName,
                ))
            }
            // Resetuje názov kategórie po jej vytvorení.
            _state.update { currentState ->
                currentState.copy(
                    categoryName = ""
                )
            }
        }
    }
    /**
     * Vymaže kategóriu z databázy.
     * @param category Kategória, ktorá sa má vymazať.
     */
    fun deleteCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            monetoDb.write {
                val deletingCategory = this.query<Category>("_id == $0", category._id).find().first()
                delete(deletingCategory)
            }
        }
    }
}
