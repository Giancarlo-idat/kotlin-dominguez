package com.store.importacionesdominguez.data.providers

import com.store.importacionesdominguez.data.model.ProductModel

class ProductsProvider {

    companion object {

        val productList = listOf(
            ProductModel("1","Laptop Asus Intel Core I3 8GB 256GB SSD Vivobook GO 15 12° Generación", "S/ 3,999", "https://falabella.scene7.com/is/image/FalabellaPE/883096366_1?wid=340&hei=340&qlt=70&fmt=webp", "Laptop Asus", "Laptop", "Asus"),
            ProductModel("2","Laptop Asus Intel Core I3 8GB 256GB SSD nVivobook GO 15 12° Generación", "S/ 3,999", "https://falabella.scene7.com/is/image/FalabellaPE/882926982_1?wid=340&hei=340&qlt=70&fmt=webp", "Laptop HP", "Laptop", "HP"),
            ProductModel("3","Laptop Asus Intel Core I3 8GB 256GB SSD Vivobook GO 15 12° Generación", "S/ 3,999", "https://falabella.scene7.com/is/image/FalabellaPE/882959594_1?wid=340&hei=340&qlt=70&fmt=webp", "Laptop Lenovo", "Laptop", "Lenovo"),
            ProductModel("4","Laptop Asus Intel Core I3 8GB 256GB SSD Vivobook GO 15 12° Generación", "S/ 3,999", "https://s7d2.scene7.com/is/image/TottusPE/43186407_1?wid=340&hei=340&qlt=70&fmt=webp", "Laptop Dell", "Laptop", "Dell"),
            ProductModel("5","Laptop Asus Intel Core I3 8GB 256GB SSD Vivobook GO 15 12° Generación", "S/ 3,999", "https://falabella.scene7.com/is/image/FalabellaPE/883085764_1?wid=340&hei=340&qlt=70&fmt=webp", "Laptop Acer", "Laptop", "Acer"),
            ProductModel("6","Laptop Asus Intel Core I3 8GB 256GB SSD Vivobook GO 15 12° Generación", "S/ 3,999", "https://falabella.scene7.com/is/image/FalabellaPE/883099615_1?wid=340&hei=340&qlt=70&fmt=webp", "Laptop Samsung", "Laptop", "Samsung"),
            ProductModel("7","Laptop Asus Intel Core I3 8GB 256GB SSD Vivobook GO 15 12° Generación", "S/ 3,999", "https://falabella.scene7.com/is/image/FalabellaPE/883024537_1?wid=340&hei=340&qlt=70&fmt=webp", "Laptop Apple", "Laptop", "Apple"),
            ProductModel("8","Laptop Asus Intel Core I3 8GB 256GB SSD Vivobook GO 15 12° Generación", "S/ 3,999", "https://falabella.scene7.com/is/image/FalabellaPE/882958605_2?wid=340&hei=340&qlt=70&fmt=webp", "Laptop Xiaomi", "Laptop", "Xiaomi"),
            ProductModel("9","Laptop Asus Intel Core I3 8GB 256GB SSD Vivobook GO 15 12° Generación", "S/ 3,999", "https://falabella.scene7.com/is/image/FalabellaPE/883024529_1?wid=340&hei=340&qlt=70&fmt=webp", "Laptop Huawei", "Laptop", "Huawei"),

        )

        fun getProductById(productId: String): ProductModel? {
            return productList.find { it.id == productId }
        }
    }
}