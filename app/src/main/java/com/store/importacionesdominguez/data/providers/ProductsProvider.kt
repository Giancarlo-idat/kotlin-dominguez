package com.store.importacionesdominguez.data.providers

import com.store.importacionesdominguez.data.model.ProductModel
import java.math.BigDecimal

class ProductsProvider {

    companion object {

        val productList = listOf(
            ProductModel(
                id = "1",
                modelo = "Laptop Lenovo",
                descripcion = "Laptop Lenovo 15.6\" Ideapad 3 4GB RAM 1TB HDD",
                precio = BigDecimal(999.99),
                marca = "Lenovo",
                stock = 10,
                imagen = "https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/mbp-spacegray-select-202011?wid=892&hei=820&&qlt=80&.v=1603406905000",
                categoria = CategoriaProvider.categoriaList[0],
                fichaTecnica = mapOf(
                    "Procesador" to "Intel Core i3",
                    "Memoria RAM" to "4GB",
                    "Almacenamiento" to "1TB HDD",
                    "Sistema Operativo" to "Windows 10",
                    "Pantalla" to "15.6\"",
                    "Resolución" to "1366 x 768",
                    "Tarjeta Gráfica" to "Intel UHD Graphics 620",
                    "Puertos" to "2 x USB 3.0, 1 x USB 2.0, 1 x HDMI, 1 x RJ-45, 1 x Jack 3.5mm",
                    "Conectividad" to "WiFi 802.11ac, Bluetooth 4.1",
                    "Batería" to "2 celdas 35Wh",
                    "Dimensiones" to "36.22 x 25.15 x 1.99 cm",
                    "Peso" to "1.85 kg",
                ),
            ),
        )

        fun getProductById(productId: String): ProductModel? {
            return productList.find { it.id == productId }
        }
    }
}