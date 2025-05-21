package com.example.houssem_souari.data

import com.example.houssem_souari.R

class CountryRepository {
    fun getCountries(): List<Country> {
        return listOf(
            Country("USA", R.drawable.usa, "Washington D.C.", "Grand Canyon"),
            Country("France", R.drawable.france, "Paris", "Eiffel Tower"),
            Country("Japan", R.drawable.japan, "Tokyo", "Mount Fuji"),
            Country("Brazil", R.drawable.brazil, "Brasília", "Amazon Rainforest"),
            Country("Australia", R.drawable.australia, "Canberra", "Great Barrier Reef"),
            Country("Egypt", R.drawable.egypt, "Cairo", "Pyramids of Giza"),
            Country("Italy", R.drawable.italy, "Rome", "Colosseum"),
            Country("South Africa", R.drawable.south_africa, "Pretoria", "Kruger National Park"),
            Country("India", R.drawable.india, "New Delhi", "Taj Mahal"),
            Country("Canada", R.drawable.canada, "Ottawa", "Niagara Falls"),
            Country("China", R.drawable.china, "Beijing", "Great Wall"),
            Country("Mexico", R.drawable.mexico, "Mexico City", "Chichen Itza"),
            Country("United Kingdom", R.drawable.uk, "London", "Big Ben"),
            Country("Germany", R.drawable.germany, "Berlin", "Brandenburg Gate"),
            Country("Russia", R.drawable.russia, "Moscow", "Red Square")
        )
    }
}