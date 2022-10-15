package gcu.production.engine.di

import org.kodein.di.DI

@Suppress("DEPRECATION")
val coreModule =
    DI.Module(
        init = {
            importAll(
                ktorModule, serializationModule
            )
        })