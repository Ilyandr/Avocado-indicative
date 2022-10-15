package gcu.production.engine.di

import gcu.production.rest.features.data.KtorRestAccountDataSource
import gcu.production.qr_check.RestAPI.Features.RestInteraction.Repository.RestAccountRepository
import gcu.production.engine.EngineSDK
import gcu.production.rest.features.source.RestAuthSource
import gcu.production.rest.features.source.RestPointSource
import gcu.production.rest.features.data.KtorRestAuthSource
import gcu.production.rest.features.data.KtorRestPointSource
import gcu.production.qrcheck.RestAPI.Features.RestInteraction.KtorAPI.KtorRestRecordSource
import gcu.production.rest.features.data.KtorOrgDataSource
import gcu.production.rest.features.repository.RestAuthRepository
import gcu.production.rest.features.repository.RestOrgRepository
import gcu.production.rest.features.repository.RestPointRepository
import gcu.production.rest.features.repository.RestRecordRepository
import kotlinx.serialization.ExperimentalSerializationApi
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import kotlin.native.concurrent.ThreadLocal

@ExperimentalSerializationApi
internal val restModule =
    DI.Module(
        name = "RestModule", init = {
            bind<RestAuthSource>() with singleton {
                KtorRestAuthSource(
                    httpClient = instance()
                )
            }
            bind<RestAuthRepository>() with singleton {
                RestAuthRepository(
                    restAuthSource = instance()
                )
            }

            bind<RestPointSource>() with singleton {
                KtorRestPointSource(
                    httpClient = instance()
                )
            }

            bind<RestPointRepository>() with singleton {
                RestPointRepository(
                    restPointSource = instance()
                )
            }


            bind<KtorRestRecordSource>() with singleton {
                KtorRestRecordSource(
                    httpClient = instance()
                )
            }

            bind<RestRecordRepository>() with singleton {
                RestRecordRepository(
                    restRecordDataSource = instance()
                )
            }

            bind<KtorRestAccountDataSource>() with singleton {
                KtorRestAccountDataSource(
                    httpClient = instance()
                )
            }

            bind<RestAccountRepository>() with singleton {
                RestAccountRepository(
                    restAccountDataSource = instance()
                )
            }

            bind<KtorOrgDataSource>() with singleton {
                KtorOrgDataSource(
                    paymentHttpClient = instance(tag = "paymentsClient"),
                    defaultHttpClient = instance()
                )
            }

            bind<RestOrgRepository>() with singleton {
                RestOrgRepository(
                    source = instance()
                )
            }
        })

@ThreadLocal
object RestModule {
    val restAuthRepository: RestAuthRepository
        get() = EngineSDK.di.instance()

    val restOrgRepository: RestOrgRepository
        get() = EngineSDK.di.instance()

    val restPointRepository: RestPointRepository
        get() = EngineSDK.di.instance()

    val restRecordRepository: RestRecordRepository
        get() = EngineSDK.di.instance()

    val restAccountRepository: RestAccountRepository
        get() = EngineSDK.di.instance()
}

val EngineSDK.restAPI: RestModule
    get() = RestModule