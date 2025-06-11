package com.example.tsgapp

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Connection
import org.jsoup.Jsoup
import java.net.URLEncoder

data class Producto(val nombre: String, val precio: String, val imagenUrl: String, val ofertaproducto: String, val ofertaprecio: String, val ofertaespe: String, val supermercado: String)
suspend fun getProductosDIA(query: String): List<Producto> {
    //<p data-test-id="search-product-card-dia-club" class="search-product-card__dia-club">Oferta exclusiva CLUB Dia</p>
    val encodedQuery = URLEncoder.encode(query, "UTF-8").replace("+", "%20")
    return try {
        val url = "https://www.dia.es/search?q=$encodedQuery"
        val document = withContext(Dispatchers.IO) {
            Jsoup.connect(url).get()
        }

        val items = document.select(".search-product-card")

        items.mapNotNull { element ->

            val nombre = element.selectFirst(".search-product-card__product-name")?.text().orEmpty()
            val precio = element.selectFirst(".search-product-card__active-price")?.text().orEmpty()
            val imgElement = element.selectFirst(".search-product-card__product-image")
            val imageUrl = imgElement?.absUrl("src").orEmpty()
            val ofertaprecio = element.selectFirst(".product-special-offer__strikethrough-price")?.text().orEmpty()
            val descuento = element.selectFirst(".product-special-offer__discount")?.text().orEmpty()
            val ofertaespe = element.selectFirst(".search-product-card__dia-club")?.text().orEmpty()
            val supermercado = "DIA"
            if (nombre.isNotBlank() && imageUrl.isNotBlank()) {
                Producto(nombre, precio, imageUrl, descuento, ofertaprecio, ofertaespe, supermercado)
            } else {
                null
            }
        }
    } catch (e: Exception) {
        Log.e("JsoupError", "Error parsing HTML", e)
        emptyList()
    }
}

suspend fun getProductosAhorramas(query: String): List<Producto> {
    val encodedQuery = URLEncoder.encode(query, "UTF-8").replace("+", "%20")
    return try {
        val url = "https://www.ahorramas.com/buscador?q=$encodedQuery"
        val document = withContext(Dispatchers.IO) {
            Jsoup.connect(url).get()
        }

        val items = document.select(".product-tile")

        items.mapNotNull { element ->
            val nombre = element.selectFirst(".product-name-gtm")?.text().orEmpty()

            val precio = element.selectFirst(".price .value")?.text().orEmpty()

            val imgElement = element.selectFirst(".tile-image")
            val imageUrl = imgElement?.absUrl("src").orEmpty()

            val ofertaproducto = element.selectFirst(".tile-promotions")?.text().orEmpty()
            val ofertaprecio = element.selectFirst(".unit-price-per-unit")?.text().orEmpty()
            val ofertaespe = ""
            val supermercado = "Ahorramas"
            if (nombre.isNotBlank() && imageUrl.isNotBlank()) {
                Producto(nombre, precio, imageUrl, ofertaproducto, ofertaprecio, ofertaespe, supermercado)
            } else {
                null
            }
        }
    } catch (e: Exception) {
        Log.e("JsoupError", "Error parsing HTML", e)
        emptyList()
    }
}

suspend fun getProductosMercadona(query: String): List<Producto> {
    val encodedQuery = URLEncoder.encode(query, "UTF-8").replace("+", "%20")

    return try {
        // 1. Enviar código postal y obtener cookies
        val postalCodeUrl = "https://tienda.mercadona.es/"
        val postalCode = "28823"

        val cookies = Jsoup.connect(postalCodeUrl)
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0 Safari/537.36")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .method(Connection.Method.POST)
            .data("postalCode", postalCode)
            .execute()
            .cookies()

        // 2. Realizar la búsqueda con las cookies
        val url = "https://tienda.mercadona.es/search-results?query=$encodedQuery"
        val document = withContext(Dispatchers.IO) {
            Jsoup.connect(url)
                .data("postalCode", postalCode)
                .userAgent("Mozilla/5.0...")
                .headers(mapOf(
                    "Accept" to "text/html,application/xhtml+xml...",
                    "Accept-Language" to "es-ES,es;q=0.9,en;q=0.8",
                    "Cookie" to cookies.entries.joinToString("; ") { "${it.key}=${it.value}" }
                ))
                .timeout(30_000)
                .get()
        }

        // 3. Parsear resultados
        val items = document.select("div.product-cell") // Usar la clase correcta
        items.mapNotNull { element ->
            val nombre = element.selectFirst("h4.subhead1-r.product-cell__description-name")?.text().orEmpty()
            val precio = element.selectFirst("p.product-price__unit-price")?.text().orEmpty()
            val imageUrl = element.selectFirst("img")?.absUrl("src").orEmpty()
            val ofertaproducto = ""
            val ofertaprecio = ""
            val ofertaespe = ""
            val supermercado = "Carrefour"
            if (nombre.isNotBlank() && imageUrl.isNotBlank()) {
                Producto(nombre, precio, imageUrl, ofertaproducto, ofertaprecio, ofertaespe, supermercado)
            } else {
                null
            }
        }
    } catch (e: Exception) {
        Log.e("JsoupError", "Error fetching data", e)
        emptyList()
    }
}//https://tienda.mercadona.es/search-results?query=
