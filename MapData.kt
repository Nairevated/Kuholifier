package com.example.kuholifier

data class MapData(
    val name: String,
    val snail1: String,
    val snail2: String
)
object MapDetail {
    val locationData = mapOf(
        R.id.Dinalupihanbtn to MapData ("Dinalupihan","Crowded","Moderate"),
        R.id.Hermosabtn to MapData ("Hermosa","Moderate","Sparse"),
        R.id.Oranibtn to MapData ("Orani","Moderate","Moderate"),
        R.id.Samalbtn to MapData ("Samal","Crowded","Crowded"),
        R.id.Abucaybtn to MapData ("Abucay","Sparse","Sparse"),
        R.id.Balangabtn to MapData ("Balanga","Sparse","Sparse"),
        R.id.Pilarbtn to MapData ("Pilar", "Moderate","Moderate"),
        R.id.Orionbtn to MapData ("Orion","Crowded","Moderate"),
        R.id.Limaybtn to MapData ("Limay","Crowded","Crowded"),
        R.id.Marivelesbtn to MapData ("Mariveles","Sparse","Moderate"),
        R.id.Bagacbtn to MapData ("Bagac","Moderate","Sparse"),
        R.id.Morongbtn to MapData ("Morong","Sparse","Moderate")
    )
}