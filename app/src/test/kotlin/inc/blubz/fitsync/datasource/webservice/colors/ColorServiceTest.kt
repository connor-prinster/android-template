package inc.blubz.fitsync.datasource.webservice.colors

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import inc.blubz.fitsync.datasource.webservice.TestHttpClientProvider
import inc.blubz.fitsync.model.webservice.colors.ColorService
import inc.blubz.fitsync.util.ext.ApiResponse
import kotlin.test.Test

class ColorServiceTest {
    @Test
    fun getColors() = runTest {
        val mockEngine = MockEngine { request ->
            respond(
                content = ByteReadChannel(COLORS_RESPONSE),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val colorService = ColorService(TestHttpClientProvider.getTestClient(mockEngine))
        val response = colorService.getColorsBySafeArgs()
        check(response is ApiResponse.Success)

        val colors = response.data
        assertThat(colors.colors.size).isEqualTo(1)

        val color = colors.colors.first()
        assertThat(color.colorName).isEqualTo("White")
        assertThat(color.hexValue).isEqualTo("#FFFFFF")
    }

    @Test
    fun failedNetwork() = runTest {
        val mockEngine = MockEngine { request ->
            respond(
                content = ByteReadChannel("""{"error": "Oh No!" }"""),
                status = HttpStatusCode.InternalServerError,
            )
        }
        val colorService = ColorService(TestHttpClientProvider.getTestClient(mockEngine))
        val response = colorService.getColorsBySafeArgs()
        check(response is ApiResponse.Failure)
    }

    companion object {
        const val COLORS_RESPONSE = """
            {
                "colors": [
                    {
                       "colorName": "White",
                       "hexValue": "#FFFFFF"
                    }
                ]
            }
        """
    }
}
