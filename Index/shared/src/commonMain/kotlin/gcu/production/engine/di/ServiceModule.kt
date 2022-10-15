package gcu.production.engine.di

import org.kodein.di.*
import kotlin.native.concurrent.ThreadLocal

val serviceModule =
    DI.Module(
        name = "serviceModule"
        , init = {
        //    bind<GeolocationListener>() with singleton { GeolocationListener() }
          //  bind<DataStorageService>() with singleton { DataStorageService() }
         }
    )

val serviceDI = DI {
    import(serviceModule)
}

@ThreadLocal
object ServiceRepository
{
   // val geolocationListener: GeolocationListener by serviceDI.instance()
  //  val dataStorageService: DataStorageService by serviceDI.instance()
}