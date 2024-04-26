package inc.blubz.fitsync.datasource.webservice

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import inc.blubz.fitsync.model.webservice.KtorClientDefaults.defaultSetup
import inc.blubz.fitsync.model.webservice.ResponseTimePlugin

object TestHttpClientProvider {
    fun getTestClient(
        engine: HttpClientEngine,
        locales: () -> String = { "en-US" },
    ): HttpClient {
        return HttpClient(engine) {
            install(ResponseTimePlugin)
            install(Logging) {
                defaultSetup()
            }
            install(ContentNegotiation) {
                defaultSetup(allowAnyContentType = false)
            }
            install(Resources)

            defaultRequest {
                val currentLocales = locales()
                if (currentLocales.isNotBlank()) {
                    header(HttpHeaders.AcceptLanguage, currentLocales)
                }
            }
        }
    }
}