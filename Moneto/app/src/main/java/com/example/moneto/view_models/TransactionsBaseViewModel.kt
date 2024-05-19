package com.example.moneto.view_models

import androidx.lifecycle.ViewModel
import com.example.moneto.data.TimeRange
import com.example.moneto.data.Transaction
import com.example.moneto.data.TransactionType

/**
 * Abstraktná základná trieda ViewModel pre transakcie, poskytuje základné rozhranie pre operácie s transakciami.
 * Táto trieda je určená na dedenie ďalšími ViewModel triedami, ktoré implementujú špecifické správanie
 * pre rôzne typy transakcií.
 */
abstract class TransactionsBaseViewModel(): ViewModel() {
    /**
     * Abstraktná metóda na odstránenie transakcie z databázy. Táto metóda musí byť implementovaná
     * v odvodených triedach, ktoré špecifikujú, ako sa má transakcia odstrániť.
     *
     * @param tranToRemove Objekt transakcie, ktorá má byť odstránená.
     */
    abstract fun removeTransaction(tranToRemove: Transaction)
    abstract fun updateTimeRange(range: TimeRange, transactionType: TransactionType)
}