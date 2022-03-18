// package avila.daniel.rickmorty.base
//
// abstract class BaseAdmobActivity : BaseActivity() {
//     private lateinit var adView: AdView
//     private var interstitial: InterstitialAd? = null
//
//     private var initialLayoutComplete = false
//
//     private val adSize: AdSize
//         get() {
//             val display = windowManager.defaultDisplay
//             val outMetrics = DisplayMetrics()
//             display.getMetrics(outMetrics)
//
//             val density = outMetrics.density
//
//             var adWidthPixels = adViewContainer().width.toFloat()
//             if (adWidthPixels == 0f) {
//                 adWidthPixels = outMetrics.widthPixels.toFloat()
//             }
//
//             val adWidth = (adWidthPixels / density).toInt()
//             return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
//         }
//
//     override fun onCreate(savedInstanceState: Bundle?) {
//         super.onCreate(savedInstanceState)
//
//         adView = AdView(this)
//         adViewContainer().addView(adView)
//         adViewContainer().viewTreeObserver.addOnGlobalLayoutListener {
//             if (!initialLayoutComplete) {
//                 initialLayoutComplete = true
//                 loadBanner()
//             }
//         }
//
//         loadInterstitialAd()
//     }
//
//     public override fun onPause() {
//         adView.pause()
//         super.onPause()
//     }
//
//     /** Called when returning to the activity  */
//     public override fun onResume() {
//         super.onResume()
//         adView.resume()
//     }
//
//     /** Called before the activity is destroyed  */
//     public override fun onDestroy() {
//         adView.destroy()
//         super.onDestroy()
//     }
//
//     private fun loadBanner() {
//         adView.adUnitId = adUnitIDBanner()
//         adView.adSize = adSize
//         adView.loadAd(AdRequest.Builder().build())
//         adView.adListener = object : AdListener() {
//             override fun onAdLoaded() {
//                 // Code to be executed when an ad finishes loading.
//             }
//
//             override fun onAdFailedToLoad(adError: LoadAdError) {
//                 // Code to be executed when an ad request fails.
//             }
//
//             override fun onAdOpened() {
//                 // Code to be executed when an ad opens an overlay that
//                 // covers the screen.
//             }
//
//             override fun onAdClicked() {
//                 // Code to be executed when the user clicks on an ad.
//             }
//
//             override fun onAdClosed() {
//                 // Code to be executed when the user is about to return
//                 // to the app after tapping on an ad.
//             }
//         }
//     }
//
//     private fun initAds(adUnit: String) {
//         InterstitialAd.load(
//             this,
//             adUnit,
//             AdRequest.Builder().build(),
//             object : InterstitialAdLoadCallback() {
//                 override fun onAdLoaded(interstitialAd: InterstitialAd) {
//                     interstitial = interstitialAd
//                     interstitial?.fullScreenContentCallback = object : FullScreenContentCallback() {
//                         override fun onAdDismissedFullScreenContent() {
//                         }
//
//                         override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
//                         }
//
//                         override fun onAdShowedFullScreenContent() {
//                             interstitial = null
//                             onInterstitialAdShowed()
//                         }
//                     }
//                 }
//
//                 override fun onAdFailedToLoad(p0: LoadAdError) {
//                     interstitial = null
//                 }
//             })
//     }
//
//     protected fun showInterstitial() {
//         interstitial?.show(this)
//         loadInterstitialAd()
//     }
//
//     private fun loadInterstitialAd() {
//         adUnitIDInterstitial()?.run {
//             initAds(this)
//         }
//     }
//
//     abstract fun adViewContainer(): FrameLayout
//
//     abstract fun adUnitIDBanner(): String
//
//     open fun adUnitIDInterstitial(): String? = null
//
//     protected open var onInterstitialAdShowed: () -> Unit = {}
// }