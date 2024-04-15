package com.store.importacionesdominguez.ui.checkout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.store.importacionesdominguez.data.model.FechaEnvio
import com.store.importacionesdominguez.databinding.ItemsFechaEnvioBinding
import java.time.LocalDate

class FechaEnviosAdapter(
    private var fechas: List<FechaEnvio>,
    private var selectedDate: LocalDate? = null,
    private val onItemClick: ((fecha: LocalDate) -> Unit)? = null
) : RecyclerView.Adapter<FechaEnviosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FechaEnviosViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemsFechaEnvioBinding.inflate(layoutInflater, parent, false)
        return FechaEnviosViewHolder(binding)
    }

    fun updateFechas(newFechas: List<FechaEnvio>) {
        fechas = newFechas
        notifyDataSetChanged()
    }

    fun updateSelectedDate(date: LocalDate?) {
        selectedDate = date
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = fechas.size
    override fun onBindViewHolder(holder: FechaEnviosViewHolder, position: Int) {
        val fecha: FechaEnvio = fechas[position]
        holder.bind(fecha, selectedDate) { fechaSeleccionada ->
            onItemClick?.invoke(fechaSeleccionada)
        }
    }
}