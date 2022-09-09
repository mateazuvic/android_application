package hr.algebra.my_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import hr.algebra.my_application.databinding.ActivityItemPagerBinding
import hr.algebra.my_application.framework.fetchItems
import hr.algebra.my_application.model.Item

const val ITEM_POSITION = "hr.algebra.my_application.item_position"

class ItemPagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemPagerBinding

    private var itemPosition = 0
    private lateinit var items: MutableList<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPager()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun initPager() {
        items = fetchItems()
        itemPosition = intent.getIntExtra(ITEM_POSITION, 0)
        binding.viewPager.adapter = ItemPagerAdapter(this, items)
        binding.viewPager.currentItem = itemPosition
    }
}