package com.darwin.photolandhk

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.darwin.photolandhk.main_fragments.home.HomeOverviewAdapter
import com.darwin.photolandhk.network.ApiStatus
import com.darwin.photolandhk.posts.PostContent
import com.darwin.photolandhk.posts_overview.PostsOverviewAdapter

@BindingAdapter("imageUrl", "overview", "isAuthorThumbnail", requireAll = false)
fun bindImage(
    imgView: ImageView,
    imgUrl: String?,
    isHomeOverview: Boolean = false,
    isAuthorThumbnail: Boolean = false
) {
    imgUrl?.let {
//        val requestOptions = when {
//            isHomeOverview -> RequestOptions().override(240, 135)
//            isAuthorThumbnail -> RequestOptions().override(48,48)
//            else -> RequestOptions().override(360, 180)
//        }
        val reqOpt = RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .override(imgView.width/2, imgView.height/2).priority(Priority.IMMEDIATE)
        Glide.with(imgView.context)
            .load(it)
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(reqOpt)
            .placeholder(R.drawable.logo_only_scaled).error(R.drawable.ic_broken_image_scaled)
            .into(imgView)
    }
}

@RequiresApi(Build.VERSION_CODES.M)
@BindingAdapter("homeRecyclerViewTitle")
fun bindRecyclerViewTitle(card: CardView, title: String) {
    val textView = card.findViewById<TextView>(R.id.home_card_title)
    val button = card.findViewById<Button>(R.id.button_more)
    val context = card.context
    when (title) {
        context.getString(R.string.title_news) -> {
            val theColor = context.getColor(R.color.colorNews)
            textView.setBackgroundColor(theColor)
            textView.setTextColor(context.getColor(R.color.colorHomeWhiteCardBackground))
            textView.text = title
            button.setTextColor(theColor)
        }
        context.getString(R.string.title_report) -> {
            val theColor = context.getColor(R.color.colorReport)
            textView.setBackgroundColor(theColor)
            textView.setTextColor(context.getColor(R.color.colorHomeWhiteCardBackground))
            textView.text = title
            button.setTextColor(theColor)
        }
        context.getString(R.string.title_discussion) -> {
            val theColor = context.getColor(R.color.colorDiscussion)
            textView.setBackgroundColor(theColor)
            textView.setTextColor(context.getColor(R.color.colorHomeDarkCardBackground))
            textView.text = title
            button.setTextColor(theColor)
        }
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<PostContent>?) {
    when (recyclerView.adapter) {
        is HomeOverviewAdapter -> (recyclerView.adapter as HomeOverviewAdapter).submitList(data)
        is PostsOverviewAdapter -> (recyclerView.adapter as PostsOverviewAdapter).submitList(data)
//        is ReportOverviewAdapter -> (recyclerView.adapter as ReportOverviewAdapter).submitList(data)
//        is NewsOverviewAdapter -> (recyclerView.adapter as NewsOverviewAdapter).submitList(data)
        else -> null
    }
}

@BindingAdapter("onPulledToRefresh")
fun setOnSwipeRefreshListener(swipeRefreshLayout: SwipeRefreshLayout, onPulledToRefresh: Runnable) {
    swipeRefreshLayout.setOnRefreshListener { onPulledToRefresh.run() }
}

@SuppressLint("SetJavaScriptEnabled")
@BindingAdapter("postContent")
fun bindPostContent(webView: WebView, data: String) {
    webView.settings.javaScriptEnabled = true
    webView.settings.loadWithOverviewMode = true
//    webView.settings.minimumFontSize = 15
    var styles = "<!DOCTYPE html><html><head>"
    for (link in CSSs) {
        styles += "<link href=\"${link}\" rel=\"stylesheet\">"
    }
    webView.loadDataWithBaseURL(
        null,
        styles + "</head><body>" + data + "</body></html>",
        "text/HTML",
        "UTF-8",
        null
    )
}

@BindingAdapter("text")
fun bindText(textView: TextView, data: String) {
    textView.text = HtmlCompat.fromHtml(data, HtmlCompat.FROM_HTML_MODE_LEGACY)
}

@BindingAdapter("apiStatus")
fun bindStatus(statusImageView: ImageView, status: ApiStatus?) {
    when (status) {
        ApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_img)
        }
        ApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        ApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

/*
 * List of CSS styles
 */
private val CSSs = listOf(
    "https://photolandhk.com/wp-includes/css/dashicons.min.css?ver=5.5.3",
    "https://photolandhk.com/wp-content/themes/jannah/assets/css/plugins/buddypress.min.css?ver=5.0.9",
    "https://photolandhk.com/wp-includes/css/dist/block-library/style.min.css?ver=5.5.3",  // listing in on line
    "https://photolandhk.com/wp-includes/css/dist/block-library/theme.min.css?ver=5.5.3",
    "https://photolandhk.com/wp-content/plugins/buddypress/bp-members/css/blocks/member.min.css?ver=6.3.0",
    "https://photolandhk.com/wp-content/plugins/woocommerce/packages/woocommerce-blocks/build/vendors-style.css?ver=3.6.0",
    "https://photolandhk.com/wp-content/plugins/woocommerce/packages/woocommerce-blocks/build/style.css?ver=3.6.0",
    "https://photolandhk.com/wp-content/plugins/block-options/build/style.build.css?ver=1.29.3",
    "https://photolandhk.com/wp-content/themes/jannah/assets/css/plugins/woocommerce.min.css?ver=5.0.9",
    "https://photolandhk.com/wp-content/plugins/aps-products/css/aps-styles.css?ver=2.6",
    "https://photolandhk.com/wp-content/plugins/aps-products/css/imageviewer.css?ver=2.6",
    "https://photolandhk.com/wp-content/plugins/aps-products/css/nivo-lightbox.css?ver=2.6",
    "https://photolandhk.com/wp-content/plugins/aps-products/css/owl-carousel.css?ver=2.6",
    "https://photolandhk.com/wp-content/themes/jannah/assets/css/base.min.css?ver=5.0.9",
    "https://photolandhk.com/wp-content/plugins/jetpack/css/jetpack.css?ver=9.1",
    "https://photolandhk.com/wp-content/themes/jannah/assets/css/style.min.css",
    "https://photolandhk.com/wp-content/themes/jannah/assets/css/single.min.css",
    "https://photolandhk.com/wp-content/themes/jannah/assets/css/plugins/shortcodes.min.css",
    "https://photolandhk.com/wp-content/themes/jannah/assets/css/widgets.min.css",
    "https://photolandhk.com/wp-content/themes/jannah/assets/css/helpers.min.css",
    "https://photolandhk.com/wp-content/themes/jannah/assets/ilightbox/dark-skin/skin.css",
    "https://photolandhk.com/wp-content/themes/jannah/assets/css/fontawesome.css",
    "https://photolandhk.com/wp-content/themes/jannah/assets/css/print.css?ver=5.0.9",
    "https://www.youtube.com/s/player/408be03a/www-player-webp.css",
    "https://fonts.googleapis.com/css?family=Noto+Sans+TC:300,regular%7CNoto+Sans+TC:700,regular%7CNoto+Sans+TC:600,regular&subset=latin,latin,latin&display=swap",
    "https://www.youtube.com/s/player/408be03a/www-player-webp.css",
//    "https://www.youtube.com/yts/cssbin/www-subscribe-embed-webp-vflHrs6dn.css",
//    "https://www.youtube.com/yts/cssbin/www-subscribe-embed-card-webp-vfljgQue1.css",
    "https://www.facebook.com/rsrc.php/v3/y3/l/1,cross/khLib2ZYeaz.css?_nc_x=Ij3Wp8lg5Kz"
)

private val JSs = listOf(
    "https://photolandhk.com/wp-content/plugins/aps-products/js/aps-main-script-min.js?ver=2.6",
    "https://photolandhk.com/wp-content/plugins/aps-products/js/imageviewer.min.js?ver=2.6",
    "https://photolandhk.com/wp-content/plugins/aps-products/js/nivo-lightbox.min.js?ver=2.6",
    "https://photolandhk.com/wp-content/plugins/aps-products/js/owl.carousel.min.js?ver=2.6",
    "https://photolandhk.com/wp-content/plugins/ultimate-blocks/src/blocks/content-toggle/front.build.js?ver=2.4.1",
    "https://photolandhk.com/wp-content/themes/jannah/assets/ilightbox/lightbox.js?ver=5.0.9",
    "https://photolandhk.com/wp-content/themes/jannah/assets/js/br-news.js?ver=5.0.9",
    "https://photolandhk.com/wp-content/themes/jannah/assets/js/desktop.min.js?ver=5.0.9",
    "https://photolandhk.com/wp-content/themes/jannah/assets/js/live-search.js?ver=5.0.9",
    "https://photolandhk.com/wp-content/themes/jannah/assets/js/scripts.min.js?ver=5.0.9",
    "https://photolandhk.com/wp-content/themes/jannah/assets/js/shortcodes.js?ver=5.0.9",
    "https://photolandhk.com/wp-content/themes/jannah/assets/js/single.min.js?ver=5.0.9",
    "https://photolandhk.com/wp-content/themes/jannah/assets/js/sliders.min.js?ver=5.0.9",
    "https://photolandhk.com/wp-includes/js/wp-embed.min.js?ver=5.5.3",
    "https://photolandhk.com/wp-includes/js/wp-emoji-release.min.js?ver=5.5.3",
    "https://apis.google.com/js/platform.js?ver=5.5.3"
)