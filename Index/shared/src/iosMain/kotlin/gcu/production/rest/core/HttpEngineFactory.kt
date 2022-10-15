package gcu.production.rest.core

import io.ktor.client.engine.*
import io.ktor.client.engine.ios.*

actual class HttpEngineFactory actual constructor() {
    actual fun createEngine(): HttpClientEngineFactory<HttpClientEngineConfig> = Ios
}