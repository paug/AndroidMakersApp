package fr.paug.androidmakers.flash_droid

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.ar.core.*
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.rendering.ViewSizer
import com.google.ar.sceneform.ux.ArFragment
import fr.paug.androidmakers.R
import kotlinx.android.synthetic.main.help_view.*
import kotlinx.android.synthetic.main.overlay.*
import java.io.IOException
import java.util.concurrent.CompletableFuture

@RequiresApi(Build.VERSION_CODES.N)
class HuntFragment : ArFragment() {

    class ImageState (var completableFuture: CompletableFuture<Any>? = null, val node: AnchorNode? = null)
    private val trackedImages = mutableMapOf<Int, ImageState>()

    override fun onResume() {
        super.onResume()
        arSceneView.scene.addOnUpdateListener(this::onUpdateFrame)
        User.init(context!!) {
            updateCount()
        }
        activity?.window?.statusBarColor = Color.BLACK

        help.setOnClickListener {
            showHelp()
        }

        count.setOnClickListener {
            showDessertDex()
        }
    }

    private fun showDessertDex() {
        val frameLayout = view as FrameLayout? ?: return

        LayoutInflater.from(context!!).inflate(R.layout.dessert_dex, frameLayout, true)
        val dessertDex = frameLayout.findViewById<View>(R.id.dessert_dex)
        dessertDex.findViewById<View>(R.id.close).setOnClickListener {
            frameLayout.removeView(dessertDex)
        }

        val recyclerView = dessertDex.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(context!!, 3)
        recyclerView.adapter = DessertAdapter(context!!)
    }

    override fun onPause() {
        super.onPause()
        arSceneView.scene.removeOnUpdateListener(this::onUpdateFrame)
    }

    fun getRelaseView(name: String): View {
        val drawableResourceId = resources.getIdentifier(name, "drawable", context!!.packageName)

        val releaseView = LayoutInflater.from(context).inflate(R.layout.release, null, false)
        releaseView?.findViewById<ImageView>(R.id.imageView)?.setImageResource(drawableResourceId)
        releaseView?.findViewById<TextView>(R.id.title)?.setText("Android ${name}")
        releaseView?.findViewById<TextView>(R.id.description)?.setText("${14 - User.desserts().count()} desserts to go!")

        return releaseView

    }

    fun getTshirtView(): View {
        val imageView = ImageView(context)
        imageView.setImageResource(R.drawable.tshirt)
        return imageView
    }
    private fun onUpdateFrame(frameTime: FrameTime) {
        val frame = arSceneView.arFrame

        // If there is no frame, renderable not loaded, or ARCore is not tracking yet, just return.
        if (frame == null
                || frame.camera.trackingState != TrackingState.TRACKING) {
            return
        }

        if (!User.isReady()) {
            // wait until we get the firebase data
            return
        }

        val updatedAugmentedImages = frame.getUpdatedTrackables(AugmentedImage::class.java)
        for (augmentedImage in updatedAugmentedImages) {
            when (augmentedImage.trackingState!!) {
                TrackingState.PAUSED -> {
                    // When an image is in PAUSED state, but the camera is not PAUSED, it has been detected,
                    // but not yet tracked.
                    Log.d("HuntFragment", "Detected Image " + augmentedImage.getIndex())
                }

                TrackingState.TRACKING -> {
                    // Create a new anchor for newly found images.
                    val state = trackedImages.get(augmentedImage.index)
                    if (state == null) {
                        val base = AnchorNode()
                        base.anchor = augmentedImage.createAnchor(augmentedImage.centerPose)

                        val node = Node()
                        node.setParent(base)

                        val view = if (augmentedImage.index < 14) {
                            node.localPosition = Vector3(0f, 0f, augmentedImage.extentZ)
                            node.localRotation = Quaternion.axisAngle(Vector3(1.0f, 0f, 0f), -90f)
                            node.localScale = Vector3(augmentedImage.extentX, augmentedImage.extentX, 0f)
                            getRelaseView(augmentedImage.name)
                        } else {
                            node.localPosition = Vector3(0f, 0f, augmentedImage.extentZ/2)
                            node.localRotation = Quaternion.axisAngle(Vector3(1.0f, 0f, 0f), -90f)
                            node.localScale = Vector3(augmentedImage.extentX, augmentedImage.extentZ, 0f)
                            getTshirtView()
                        }
                        val completableFuture = ViewRenderable.builder().setView(context, view).build().handle { renderable, throwable ->
                            if (throwable != null) {
                                Log.e("HuntFragment", "Ooops")
                            } else {
                                node.renderable = renderable
                                trackedImages.put(augmentedImage.index, ImageState(null, base))
                                arSceneView.scene.addChild(base)
                            }
                        }

                        trackedImages.put(augmentedImage.index, ImageState(completableFuture, null))

                        if (augmentedImage.index < 14) {
                            User.addDessert(context!!, augmentedImage.name)
                            updateCount()
                            if (User.desserts().count() == 14) {
                                showVictory()
                            }
                        }

                        this.view!!.findViewById<View>(R.id.fit).visibility = View.GONE
                        this.view!!.findViewById<View>(R.id.scanPastry).visibility = View.GONE
                    }
                }

                TrackingState.STOPPED -> {
                    val state = trackedImages.get(augmentedImage.index)
                    if (state != null) {
                        if (state.completableFuture != null) {
                            state.completableFuture!!.cancel(true)
                        }
                        if (state.node != null) {
                            arSceneView.scene.removeChild(state.node)
                        }
                        trackedImages.remove(augmentedImage.index)
                    }
                }
            }
        }
    }

    private fun updateCount() {
        val desserts = User.desserts()
        view?.findViewById<TextView>(R.id.count)?.setText("${desserts.count()}/14")
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val frameLayout = FrameLayout(container!!.context)
        val view = super.onCreateView(inflater, frameLayout, savedInstanceState)

        // Turn off the plane discovery since we're only looking for images
        planeDiscoveryController.hide()
        planeDiscoveryController.setInstructionView(null)
        arSceneView.planeRenderer.isEnabled = false

        frameLayout.addView(view)

        inflater.inflate(R.layout.overlay, frameLayout, true)

        return frameLayout
    }

    private fun showHelp() {
        val frameLayout = view as FrameLayout? ?: return

        LayoutInflater.from(context!!).inflate(R.layout.help_view, frameLayout, true)
        val help_view = frameLayout.findViewById<ScrollView>(R.id.help_view)
        help_view.isFillViewport = true
        frameLayout.findViewById<View>(R.id.close).setOnClickListener {
            frameLayout.removeView(help_view)
        }
    }

    private fun showVictory() {
        val frameLayout = view as FrameLayout? ?: return

        val view = LayoutInflater.from(context!!).inflate(R.layout.victory, frameLayout, false)
        view.findViewById<View>(R.id.close).setOnClickListener {
            frameLayout.removeView(view)
        }
        frameLayout.addView(view)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        // do nothing, that disables fullscreen mode
    }

    override fun getSessionConfiguration(session: Session): Config {
        val config = super.getSessionConfiguration(session)
        if (!setupAugmentedImageDatabase(config, session)) {
            Log.e("HuntFragment", "Could not setup augmented image database")
        }
        config.setFocusMode(Config.FocusMode.AUTO);
        return config
    }

    private fun setupAugmentedImageDatabase(config: Config, session: Session): Boolean {
        val assetManager = if (context != null) context!!.assets else null
        if (assetManager == null) {
            Log.e("ArAmFragment", "Context is null, cannot intitialize image database.")
            return false
        }

        try {
            context!!.assets.open("images.imgdb").use {
                config.augmentedImageDatabase = AugmentedImageDatabase.deserialize(session, it)
            }
        } catch (e: IOException) {
            Log.e("HuntFragment", "IO exception loading augmented image database.", e)
            return false
        }

        return true
    }

    fun backPressed(): Boolean {
        val frameLayout = view as FrameLayout? ?: return false

        val helpView = frameLayout.findViewById<ScrollView>(R.id.help_view)
        if (helpView != null) {
            frameLayout.removeView(helpView)
            return true
        }

        val dessertDex = frameLayout.findViewById<View>(R.id.dessert_dex)
        if (dessertDex != null) {
            frameLayout.removeView(dessertDex)
            return true
        }

        val victory = frameLayout.findViewById<View>(R.id.victory)
        if (victory != null) {
            frameLayout.removeView(victory)
            return true
        }


        return false
    }
}