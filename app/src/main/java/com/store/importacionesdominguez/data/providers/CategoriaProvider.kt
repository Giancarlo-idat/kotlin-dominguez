package com.store.importacionesdominguez.data.providers

import com.store.importacionesdominguez.data.model.CategoriaModel

class CategoriaProvider {

    companion object {

        val categoriaList = listOf(
            CategoriaModel(
                id = "1",
                nombre = "Laptops",
                descripcion = "Laptops de las mejores marcas",
                imagen = "https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/mbp-spacegray-select-202011?wid=892&hei=820&&qlt=80&.v=1603406905000"
            ),
        )
    }

}