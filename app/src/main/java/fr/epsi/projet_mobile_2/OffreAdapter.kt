package fr.epsi.projet_mobile_2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class OffreAdapter (val offres : ArrayList<Offre>) : RecyclerView.Adapter<OffreAdapter.ViewHolder>() {

    class ViewHolder(view: View) :RecyclerView.ViewHolder(view){

        // Init component
        val imageViewOffre : ImageView = view.findViewById<ImageView>(R.id.imageViewProduit)
        val textViewTitre : TextView = view.findViewById<TextView>(R.id.nomProduit)
        val textViewDesc : TextView = view.findViewById<TextView>(R.id.descriptionProduit)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.cell_offres, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // assigner value
        val offre = offres[position]
        holder.textViewTitre.text = offre.name
        holder.textViewDesc.text = offre.description
        Picasso.get().load(offre.picture_url).into(holder.imageViewOffre)

    }

    override fun getItemCount(): Int {
        return offres.size
    }

}