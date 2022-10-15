package gcu.production.index.android.repository.features.storage

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import gcu.production.models.user.AuthEntity
import javax.inject.Inject

internal class DataPreferences @Inject constructor(
    @ApplicationContext context: Context
) {
    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(
            null, Context.MODE_PRIVATE
        )
    }

    fun actionWithAuth(
        dataID: String, newValue: String?
    ): String? =
        newValue?.let {
            this.sharedPreferences
                .edit()
                .putString(dataID, it)
                .apply()
            return@let it
        } ?: this.sharedPreferences.getString(dataID, null)

    fun anyAction(
        dataID: String, newValue: Boolean?
    ): Boolean =
        newValue?.let {
            this.sharedPreferences
                .edit()
                .putBoolean(dataID, it)
                .apply()
            return@let it
        } ?: this.sharedPreferences.getBoolean(dataID, false)

    fun removeAllData() =
        this.sharedPreferences.edit().clear().commit()

    fun getAuthModel() =
        actionWithAuth(
            LOGIN_ID, null
        )?.let { login ->
            actionWithAuth(
                PASSWORD_ID, null
            )?.let { password ->
                AuthEntity(
                    login = login,
                    password = password
                )
            }
        }

    companion object {
        const val LOGIN_ID = "login"
        const val PASSWORD_ID = "password"
    }
}