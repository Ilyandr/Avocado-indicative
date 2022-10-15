package gcu.production.qr_check.RestAPI.Features.RestInteraction.Repository

import gcu.production.rest.features.source.AccountDataSourceActions
import gcu.production.rest.features.source.RestAccountDataSource

class RestAccountRepository(private val restAccountDataSource: RestAccountDataSource)
{
    suspend fun changeAccountSingleSetting(
        accountDataSourceActions: AccountDataSourceActions
        , userLoginKey: String?
        , newParameterData: String
    ): Boolean =
        this.restAccountDataSource.changeAccountSingleSetting(
            accountDataSourceActions
            , userLoginKey
            , newParameterData
        )
}