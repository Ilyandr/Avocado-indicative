package gcu.production.rest.features.source

interface RestAccountDataSource
{
    suspend fun changeAccountSingleSetting(
        accountDataSourceActions: AccountDataSourceActions
        , userLoginKey: String?
        , newParameterData: String): Boolean
}

enum class AccountDataSourceActions
{
    changePhone
    , changeOrganization
    , deleteAccount
    , changePassword
}