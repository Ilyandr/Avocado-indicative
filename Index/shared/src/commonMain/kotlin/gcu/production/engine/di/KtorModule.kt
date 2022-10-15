package gcu.production.engine.di

import gcu.production.other.Constants.APP_HOST
import gcu.production.other.Constants.PAYMENTS_HOST
import gcu.production.rest.core.HttpEngineFactory
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val ktorModule =
    DI.Module(
        name = "KtorModule", init = {

            bind<HttpEngineFactory>() with singleton {
                HttpEngineFactory()
            }

            bind<HttpClient>() with singleton {
                HttpClient(
                    instance<HttpEngineFactory>().createEngine()
                )
                {
                    install(Logging)
                    {
                        level = LogLevel.ALL
                        logger = Logger.SIMPLE
                    }

                    defaultRequest {
                        host = APP_HOST
                        url {
                            protocol = URLProtocol.HTTP
                        }
                    }
                }
            }

            bind<HttpClient>(tag = "paymentsClient") with singleton {
                HttpClient(
                    instance<HttpEngineFactory>().createEngine()
                )
                {
                    install(Logging)
                    {
                        level = LogLevel.ALL
                        logger = Logger.SIMPLE
                    }

                    defaultRequest {
                        host = PAYMENTS_HOST
                        url {
                            protocol = URLProtocol.HTTPS
                        }
                    }
                }
            }
        })