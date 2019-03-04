package fr.paug.androidmakers.flash_droid

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import android.graphics.Color
import fr.paug.androidmakers.R


class DessertAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val desserts = listOf(
            "cupcake",
            "donut",
            "eclair",
            "froyo",
            "gingerbread",
            "honeycomb",
            "ice_cream_sandwich",
            "jelly_bean",
            "kitkat",
            "lollipop",
            "marshmallow",
            "nougat",
            "oreo",
            "pie"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(fr.paug.androidmakers.R.layout.dessert_item, parent, false)

        return object : RecyclerView.ViewHolder(view) {}
    }

    override fun getItemCount(): Int {
        return desserts.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById(R.id.dessert) as ImageView

        val drawableResourceId = imageView.resources.getIdentifier(desserts[position], "drawable", imageView.context.packageName)
        imageView.setImageResource(drawableResourceId)
        if (User.desserts().keys.contains(desserts[position])) {
            imageView.setColorFilter(null)
        } else {
            imageView.setColorFilter(Color.rgb(128, 128, 128))
        }
    }

}
