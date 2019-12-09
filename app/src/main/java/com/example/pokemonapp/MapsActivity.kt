package com.example.pokemonapp

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        checkPermision()
        loadPoke()
    }

var ACCESSLOCATION = 123
    fun checkPermision(){
        if(Build.VERSION.SDK_INT>=23){
            if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_DENIED){
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),ACCESSLOCATION)
                return
            }
        }
        getUserLocation()
    }


    fun getUserLocation(){
        //get location
            var myLocation = MylocationListener()
                var locationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3,3f,myLocation)
                var mythead = myThread()
                mythead.start()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            ACCESSLOCATION ->{
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    getUserLocation()
                }
                else{
                    Toast.makeText(this,"Permission not granted",Toast.LENGTH_LONG).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap

    }


    //get user location
        var location:Location?=null
    inner class MylocationListener:LocationListener{
      constructor(){

        location= Location("start")
          location!!.longitude=0.0
          location!!.latitude= 0.0
      }

        override fun onLocationChanged(p0: Location?) {
                    location=p0

        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
          //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderEnabled(provider: String?) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderDisabled(provider: String?) {
             //ODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }


    inner class myThread:Thread{
        constructor():super(){
            }
        override fun run() {
            while(true){
                try {
                    runOnUiThread{
                        mMap!!.clear()
                        val mario = LatLng(location!!.latitude, location!!.longitude)
                       //show me
                        mMap!!.addMarker(MarkerOptions()
                            .position(mario)
                            .title("YOO")
                            .snippet("around the world")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mario)))
                        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(mario,14f))
                        // show pokemons
                        for(i in 0..pokeList.size-1){
                            var newPoke = pokeList[i]
                            if(!pokeList[i].cought){
                                val poke = LatLng(pokeList[i].lat!!, pokeList[i].lng!!)
                                //show me
                                mMap!!.addMarker(MarkerOptions()
                                    .position(poke)
                                    .title(pokeList[i].name)
                                    .snippet(pokeList[i].des)
                                    .icon(BitmapDescriptorFactory.fromResource(pokeList[i].image!!)))
                                mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(mario,14f))
                            }
                        }

                    }

                }catch (ex:Exception){

                }
            }
        }
    }
    var pokeList = ArrayList<Pokemon>()
    fun loadPoke(){
        pokeList.add(Pokemon("charmander", "I am in Japan", R.drawable.charmander, 55.0,37.7789994893035, -122.401846647263))
        pokeList.add(Pokemon("Bulbasaur", "I am in USA", R.drawable.bulbasaur, 90.5,37.7949568502667, -122.410494089127))
        pokeList.add(Pokemon("Squirtle", "I am in ALGERIA", R.drawable.squirtle, 331.0,37.7816621152613, -122.41225361824))

    }

}
