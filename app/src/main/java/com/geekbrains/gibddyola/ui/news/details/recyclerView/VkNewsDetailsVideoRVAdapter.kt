package com.geekbrains.gibddyola.ui.news.details.recyclerView

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.data.news.entity.VkNewsEntity
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class VkNewsDetailsVideoRVAdapter :
    RecyclerView.Adapter<VkNewsDetailsVideoRVAdapter.VkNewsDetailsViewHolder>() {

    private val videos = mutableListOf<String>()

    private var player: YouTubePlayer? = null

    fun setData(resultData: VkNewsEntity.Response.Item) {
        videos.clear()
        resultData.attachments?.forEach { attachment ->
            val tempVideoUrl =
                "Myb25gw6hwE"
//                "https://vk.com/video${attachment.video?.owner_id}_${attachment.video?.id}"
            videos.add(tempVideoUrl)
            Log.i("MY_TAG", videos[0])
        }
        notifyDataSetChanged()
    }

    fun releasePlayer() {
//        if (player == null) {
//            return
//        }
//        player!!.release()
//        player = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VkNewsDetailsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_vk_details_video, parent, false)
        return VkNewsDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: VkNewsDetailsViewHolder, position: Int) {
        holder.bind(videos[position])
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    inner class VkNewsDetailsViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val playerView: YouTubePlayerView =
            itemView.findViewById(R.id.vk_news_details_rv_item_video)


        fun bind(videoUrl: String) {
            playerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    youTubePlayer.cueVideo(videoUrl, 0F)

                }
            })
//            initPlayer(itemView.context, playerView, videoUrl)
        }
    }

    private fun initPlayer(context: Context, playerView: PlayerControlView, videoUrl: String) {
//        player = ExoPlayer.Builder(context).build()
//        playerView.player = player
//        player!!.playWhenReady = true
//        player!!.setMediaSource(buildMediaSource(videoUrl))
//        player!!.prepare()

    }

    private fun buildMediaSource(videoUrl: String): MediaSource {
        val dataSourceFactory: DataSource.Factory =
            DefaultHttpDataSource.Factory()

        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(videoUrl))
    }
}