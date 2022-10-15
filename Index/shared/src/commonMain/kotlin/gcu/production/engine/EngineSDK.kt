package gcu.production.engine

import gcu.production.engine.di.coreModule
import gcu.production.engine.di.engineModule
import gcu.production.engine.di.featuresModule
import org.kodein.di.*
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object EngineSDK {
    internal val di: DirectDI
        get() = directDI

    private var directDI: DirectDI = DI {
        importAll(
            engineModule, featuresModule, coreModule
        )
    }.direct
}