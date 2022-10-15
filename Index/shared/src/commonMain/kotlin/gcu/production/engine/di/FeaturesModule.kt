package gcu.production.engine.di

import kotlinx.serialization.ExperimentalSerializationApi
import org.kodein.di.DI

@OptIn(ExperimentalSerializationApi::class)
@Suppress("DEPRECATION")
val featuresModule =
    DI.Module {
        importAll(restModule)
    }