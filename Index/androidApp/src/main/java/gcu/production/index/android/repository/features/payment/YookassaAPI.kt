package gcu.production.index.android.repository.features.payment

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import gcu.production.engine.EngineSDK
import gcu.production.engine.di.restAPI
import gcu.production.index.android.presentation.view.custom.CustomDialogBuilder
import gcu.production.index.android.repository.features.dataPaymentAction
import gcu.production.index.android.repository.features.unitAction
import gcu.production.index.android.repository.source.usage.UsageDialogSource
import gcu.production.other.Constants.CURRENCY_APP_RU
import gcu.production.other.Constants.GENERAL_CLIENT_API_ID
import gcu.production.other.Constants.GENERAL_PAYMENT_SUBTITLE
import gcu.production.other.Constants.GENERAL_PAYMENT_TITLE
import gcu.production.other.Constants.GENERAL_SHOP_API_PASSWORD
import gcu.production.other.Constants.GENERAL_SHOP_ID
import gcu.production.other.Constants.GENERAL_SHOP_PASSWORD
import gcu.production.models.payment.PaymentModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Credentials
import ru.yoomoney.sdk.kassa.payments.Checkout
import ru.yoomoney.sdk.kassa.payments.Checkout.createTokenizeIntent
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.Amount
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.PaymentMethodType
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.PaymentParameters
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.SavePaymentMethod
import java.math.BigDecimal
import java.util.Currency
import java.util.Random

internal class YookassaAPI(
    private val resultRegister: Fragment,
    private val successAction: dataPaymentAction,
    private val faultAction: unitAction
) {
    private var price: Double = 1.0
    private var paymentId: String? = null

    private val loadingDialog: UsageDialogSource by lazy {
        CustomDialogBuilder(
            resultRegister.requireActivity()
        ).setWaitingDialog().build()
    }

    private val launchTokenize =
        resultRegister.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == AppCompatActivity.RESULT_OK)
                it.data?.run {
                    loadingDialog.show()

                    sendRequestWithToken(
                        Checkout.createTokenizationResult(
                            this
                        ).paymentToken
                    )
                } ?: run {
                    loadingDialog.hide()
                    faultAction.invoke()
                }
            else {
                loadingDialog.hide()
                faultAction.invoke()
            }
        }

    infix fun showPaymentDialog(price: Double) {

        this.price = price

        launchTokenize.launch(
            createTokenizeIntent(
                resultRegister.requireContext(),
                PaymentParameters(
                    amount = Amount(
                        BigDecimal.valueOf(price),
                        Currency.getInstance(CURRENCY_APP_RU)
                    ),
                    title = GENERAL_PAYMENT_TITLE,
                    subtitle = GENERAL_PAYMENT_SUBTITLE,
                    clientApplicationKey = GENERAL_SHOP_API_PASSWORD,
                    shopId = GENERAL_SHOP_ID,
                    savePaymentMethod = SavePaymentMethod.OFF,
                    paymentMethodTypes = setOf(
                        PaymentMethodType.BANK_CARD,
                        PaymentMethodType.SBERBANK
                    ),
                    authCenterClientId = GENERAL_CLIENT_API_ID
                )
            )
        )
    }

    private infix fun sendRequestWithToken(
        token: String
    ) =
        CoroutineScope(Dispatchers.Main).launch {

            withContext(Dispatchers.IO) {

                EngineSDK.restAPI.restOrgRepository.sendTokenizeData(
                    headerAuth = requireHeaderAuth(),
                    uniqueOperationKey = "${Random().nextInt(10000810) + 12}",
                    sendPaymentDataEntity = PaymentModel(
                        token, gcu.production.models.payment.Amount(
                            BigDecimal.valueOf(
                                price
                            ).toString(),
                            Currency.getInstance(
                                CURRENCY_APP_RU
                            ).toString()
                        ), true
                    )
                )
            }?.apply {
                sendConfirmPayment()
            } ?: run {
                loadingDialog.hide()
                faultAction.invoke()
            }

        }

    private fun requireHeaderAuth() =
        Credentials.basic(
            GENERAL_SHOP_ID,
            GENERAL_SHOP_PASSWORD
        )

    private fun sendConfirmPayment() =
        CoroutineScope(Dispatchers.Main).launch {

            withContext(Dispatchers.IO) {

                paymentId?.let {

                    EngineSDK.restAPI.restOrgRepository.putPaymentResult(
                        headerAuth = requireHeaderAuth(),
                        singlePaymentID = it
                    )
                }
            }?.apply {
                loadingDialog.hide()
                if (this)
                    successAction.invoke(price)
                else faultAction.invoke()

            }
        }
}
