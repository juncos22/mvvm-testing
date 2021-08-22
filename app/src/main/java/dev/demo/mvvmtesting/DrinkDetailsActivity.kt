package dev.demo.mvvmtesting

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import dev.demo.mvvmtesting.models.Drink
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.widget.ScrollView
import androidx.appcompat.widget.Toolbar


class DrinkDetailsActivity : AppCompatActivity() {
    private lateinit var drinkImg: CircleImageView
    private lateinit var drinkName: TextView
    private lateinit var drinkCategory: TextView
    private lateinit var drinkAlcoholic: TextView
    private lateinit var drinkGlass: TextView
    private lateinit var drinkIngredient1: TextView
    private lateinit var drinkIngredient2: TextView
    private lateinit var drinkIngredient3: TextView
    private lateinit var drinkIngredient4: TextView
    private lateinit var drinkIngredient5: TextView
    private lateinit var drinkIngredient6: TextView
    private lateinit var drinkInstructions: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_details)
        setSupportActionBar(findViewById(R.id.drinkDetailsToolbar))

        configureToolbar()
        initViewItems()
        loadDrinkItem()

        val toolbar = findViewById<Toolbar>(R.id.drinkDetailsToolbar)
        val scrollIngredients = findViewById<ScrollView>(R.id.scrollIngredients)
        scrollIngredients.layoutParams.height =
            getScreenHeight(applicationContext) - toolbar.layoutParams.height
    }

    private fun initViewItems() {
        drinkImg = findViewById(R.id.drinkImg)
        drinkName = findViewById(R.id.drinkName)
        drinkCategory = findViewById(R.id.drinkCategory)
        drinkAlcoholic = findViewById(R.id.drinkAlcoholic)
        drinkGlass = findViewById(R.id.drinkGlass)
        drinkIngredient1 = findViewById(R.id.drinkIngredient1)
        drinkIngredient2 = findViewById(R.id.drinkIngredient2)
        drinkIngredient3 = findViewById(R.id.drinkIngredient3)
        drinkIngredient4 = findViewById(R.id.drinkIngredient4)
        drinkIngredient5 = findViewById(R.id.drinkIngredient5)
        drinkIngredient6 = findViewById(R.id.drinkIngredient6)
        drinkInstructions = findViewById(R.id.drinkInstructions)
    }

    private fun loadDrinkItem() {
        try {
            val drinkItem = intent.getSerializableExtra("drinkItem") as Drink
            Glide.with(applicationContext)
                .load(drinkItem.strDrinkThumb)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .into(drinkImg)

            drinkName.text = drinkItem.strDrink
            drinkCategory.text = drinkItem.strCategory
            drinkAlcoholic.text = drinkItem.strAlcoholic
            drinkGlass.text = drinkItem.strGlass
            drinkInstructions.text = drinkItem.strInstructions

            if (drinkItem.strIngredient1 != null) {
                drinkIngredient1.text = drinkItem.strIngredient1
            } else {
                drinkIngredient1.visibility = View.GONE
            }
            if (drinkItem.strIngredient2 != null) {
                drinkIngredient2.text = drinkItem.strIngredient2
            } else {
                drinkIngredient2.visibility = View.GONE
            }
            if (drinkItem.strIngredient3 != null) {
                drinkIngredient3.text = drinkItem.strIngredient3
            } else {
                drinkIngredient3.visibility = View.GONE
            }
            if (drinkItem.strIngredient4 != null) {
                drinkIngredient4.text = drinkItem.strIngredient4
            } else {
                drinkIngredient4.visibility = View.GONE
            }
            if (drinkItem.strIngredient5 != null) {
                drinkIngredient5.text = drinkItem.strIngredient5
            } else {
                drinkIngredient5.visibility = View.GONE
            }
            if (drinkItem.strIngredient6 != null) {
                drinkIngredient6.text = drinkItem.strIngredient6
            } else {
                drinkIngredient6.visibility = View.GONE
            }

        } catch (e: Exception) {
            AlertDialog.Builder(applicationContext)
                .setTitle("Unable to load drink item")
                .setMessage(e.message!!)
                .setIcon(R.drawable.ic_baseline_error_24)
                .setNegativeButton("OK") { dialog, _ ->
                    dialog.cancel()
                }
                .create()
                .show()
        }

    }

    private fun configureToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic__back_24)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun getScreenHeight(context: Context): Int {
        val wm = context.getSystemService(WINDOW_SERVICE) as WindowManager
        val display: Display = wm.defaultDisplay
        val metrics = DisplayMetrics()
        display.getMetrics(metrics)
        return metrics.heightPixels
    }

    override fun onBackPressed() {
    }
}