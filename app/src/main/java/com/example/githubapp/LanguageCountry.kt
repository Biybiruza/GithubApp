package com.example.githubapp

data class LanguageCountry (val image: Int, val name: String)

object Countries {

    private val images = intArrayOf(
        R.drawable.language,
        R.drawable.english,
        R.drawable.karakalpak,
        R.drawable.karakalpak,
        R.drawable.russian,
        R.drawable.uzbek
    )

     val countries = arrayOf(
        "All Language",
        "English",
        "Karakalpak latin",
        "Karakalpak kirill",
        "Russian",
        "Uzbek"
    )

    var list: ArrayList<LanguageCountry>? = null
        get() {
            if (field != null)
                return field

            field = ArrayList()
            for (i in images.indices){
                val imageId = images[i]
                val countryName = countries[i]

                val country = LanguageCountry(imageId, countryName)
                field!!.add(country)
            };
            return field
        }
}
