package com.store.importacionesdominguez.ui.checkout.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.store.importacionesdominguez.data.model.FechaEnvio
import com.store.importacionesdominguez.databinding.ItemsFechaEnvioBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class FechaEnviosViewHolder(
    private val binding: ItemsFechaEnvioBinding,
) : RecyclerView.ViewHolder(binding.root) {


    @SuppressLint("SetTextI18n")
    fun bind(item: FechaEnvio, selectedDate: LocalDate?, onItemClick: ((fecha: LocalDate) -> Unit)?) {
        binding.textViewFecha.text = item.fecha.format(DateTimeFormatter.ofPattern("dd 'de' MMMM",Locale("es", "ES")))
        binding.textViewCostoEnvio.text = "S/. ${item.costoEnvio}"

        if (item.fecha === selectedDate) {
            binding.root.setBackgroundColor(0xFFE0E0E0.toInt())
        } else {
            binding.root.setBackgroundColor(0xFFFFFFFF.toInt())
        }

        binding.root.setOnClickListener {
            onItemClick?.invoke(item.fecha)
        }
    }
}