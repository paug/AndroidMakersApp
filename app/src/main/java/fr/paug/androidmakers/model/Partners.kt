package fr.paug.androidmakers.model

class Partners(val name: String?, val imageUrl: String?, val link: String?, val description: String?) {
    override fun toString(): String {
        return "Partners{" +
                "name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                '}'
    }
}