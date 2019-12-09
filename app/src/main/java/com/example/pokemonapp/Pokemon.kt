package com.example.pokemonapp

class Pokemon{
    var name:String? =null
    var des:String? =null
    var image:Int? =null
    var power:Double? =null
    var lat:Double? =null
    var lng:Double? =null
    var cought:Boolean? = null

    constructor(name:String,des:String,image:Int,power:Double,lat:Double,lng:Double){
        this.name = name
        this.des = des
        this.image = image
        this.power = power
        this.lat = lat
        this.lng = lng
        this.cought = false
    }

}