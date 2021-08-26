package com.novatec.epitak.ui.map

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.graphhopper.GraphHopper
import com.novatec.core.Constants.mapFilesLocation
import com.novatec.core.Constants.uzbMapFilesLocation
import com.novatec.epitak.R
import com.novatec.epitak.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.oscim.core.GeoPoint
import org.oscim.event.Gesture
import org.oscim.event.Gesture.LongPress
import org.oscim.event.GestureListener
import org.oscim.event.MotionEvent
import org.oscim.layers.Layer
import org.oscim.layers.marker.ItemizedLayer
import org.oscim.layers.marker.MarkerItem
import org.oscim.layers.marker.MarkerSymbol
import org.oscim.layers.tile.buildings.BuildingLayer
import org.oscim.layers.tile.vector.VectorTileLayer
import org.oscim.layers.tile.vector.labeling.LabelLayer
import org.oscim.layers.vector.PathLayer
import org.oscim.layers.vector.geometries.Style
import org.oscim.map.Map
import org.oscim.tiling.source.mapfile.MapFileTileSource
import java.io.File

class MapActivity : BaseActivity() {

    private val viewModel: MapViewModel by viewModels()
    var hopper: GraphHopper? = null
    var mapsFolder: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        mapsFolder = File(applicationInfo.dataDir + mapFilesLocation)
        if (!mapsFolder!!.exists()) {
            mapsFolder!!.mkdirs()
        }

//        viewModel.getMapZip()
//
        loadMap(File(applicationInfo.dataDir + uzbMapFilesLocation))
    }

    var l: VectorTileLayer? = null

    fun loadMap(areaFolder: File?) {
        map.map().layers().add(MapEventsReceiver(map.map()))
        val tileSource = MapFileTileSource()
        tileSource.setMapFile(File(areaFolder, "uzbekistan.map").absolutePath)
        l = map.map().setBaseMap(tileSource)
        lifecycleScope.launch(IO) {
            val loadMap = async {
                val tmpHopp = GraphHopper().forMobile()
                tmpHopp.load(File(mapsFolder, "uzbekistan-latest").absolutePath + "-gh")
                hopper = tmpHopp
//            hopper.setPreferredLanguage("ru")
//            hopper.setEnableInstructions(true)
            }
            loadMap.await()
            withContext(Main){
                setupMap()
            }
        }

    }


    var directionsLayer: ItemizedLayer<MarkerItem>? = null
    var camsLayer: ItemizedLayer<MarkerItem>? = null

    //    var marksLayer: ItemizedLayer<MyMarkerItem>? = null
    var pathLayer: PathLayer? = null
    fun setupMap() {
        map.map().setMapPosition(41.310704, 69.280226, 150000.toDouble())
        map.map().viewport().setTilt(90f)
        val style = Style.builder().generalization(Style.GENERALIZATION_SMALL)
            .strokeColor(resources.getColor(R.color.colorAccent))
            .strokeWidth(2 * resources.displayMetrics.density).build()
        pathLayer = PathLayer(map.map(), style)
        map.map().layers().add(BuildingLayer(map.map(), l))
        map.map().layers().add(LabelLayer(map.map(), l))
        directionsLayer = ItemizedLayer<MarkerItem>(map.map(), null as MarkerSymbol?)
//        marksLayer = ItemizedLayer<MyMarkerItem>(map.map(), null as MarkerSymbol?)
        map.map().layers().add(pathLayer)
        map.map().layers().add(directionsLayer)
//        map.map().layers().add(marksLayer)
//        map.map().setTheme(AssetsRenderTheme(getActivity().getAssets(), "",
//                                             getContext().getSharedPreferences("myPrefs",
//                                                                               MODE_PRIVATE)
//                                                 .getString("THEME",
//                                                            Constants.THEME_LIGHT)))
    }


    override fun onDestroy() {
        super.onDestroy()
        hopper?.close()
        hopper = null
        if (map == null || map.map() == null) {
            return
        }
        map.map().destroy()
        map.onDestroy()
    }


    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    //  public void setService(MyLocationService service) {
    //    service.setCameraTargetReceiverCallbacks(this);
    //    service.setLocationReceiverCallbacks(this);
    //  }
    override fun onResume() {
        super.onResume()
        map.onResume()

    }


    internal class MapEventsReceiver(map: Map?) :
        Layer(map), GestureListener {
        override fun onGesture(g: Gesture, e: MotionEvent): Boolean {
            if (g is LongPress) {
//                return if (layout_route.getVisibility() == View.VISIBLE) {
//                    val p = mMap.viewport().fromScreenPoint(e.x, e.y)
//                    onTap(p)
//                } else {
//                    val p = mMap.viewport().fromScreenPoint(e.x, e.y)
//                    onLongPress(p)
//                }
            }
//            lastTapTime = System.currentTimeMillis()
            return false
        }
    }

}