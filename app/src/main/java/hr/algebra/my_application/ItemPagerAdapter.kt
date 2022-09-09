package hr.algebra.my_application

import android.app.AlertDialog
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.view.Gravity.apply
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.algebra.my_application.framework.startActivity
import hr.algebra.my_application.model.Item
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.io.File

class ItemPagerAdapter(private val context: Context, private val items: MutableList<Item>)
    : RecyclerView.Adapter<ItemPagerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ivItem = itemView.findViewById<ImageView>(R.id.ivItem)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvExplanation = itemView.findViewById<TextView>(R.id.tvExplanation)
        private val tvDate = itemView.findViewById<TextView>(R.id.tvDate)
        val ivRead = itemView.findViewById<ImageView>(R.id.ivRead)
        fun bind(item: Item) {
            Picasso.get()
                .load(File(item.picturePath))
                .error(R.drawable.nasa)
                .transform(RoundedCornersTransformation(50,5))
                .into(ivItem)
            tvTitle.text = item.title
            tvExplanation.text = item.explanation
            tvDate.text = item.date
            ivRead.setImageResource(if (item.read) R.drawable.green_flag else R.drawable.red_flag)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pager, parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.ivRead.setOnClickListener{
            item.read = !item.read //promijeni status na klik
            val uri = ContentUris.withAppendedId(NASA_PROVIDER_URI, item._id!!)
            val values = ContentValues().apply{
                put(Item::read.name, item.read)
            }
            context.contentResolver.update(uri, values, null, null)
            notifyItemChanged(position)
        }
        holder.bind(item)
    }


    override fun getItemCount() = items.size
}