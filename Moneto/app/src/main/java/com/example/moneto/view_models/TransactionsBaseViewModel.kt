package com.example.moneto.view_models

import androidx.lifecycle.ViewModel
import com.example.moneto.data.Transaction

abstract class TransactionsBaseViewModel(): ViewModel() {
    abstract fun removeTransaction(tranToRemove: Transaction)
}