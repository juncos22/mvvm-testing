package dev.demo.mvvmtesting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar
import dev.demo.mvvmtesting.adapters.CocktailDataAdapter
import dev.demo.mvvmtesting.listeners.OnItemClickListener
import dev.demo.mvvmtesting.models.Drink
import dev.demo.mvvmtesting.states.DataState
import dev.demo.mvvmtesting.viewmodels.CocktailViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerCocktails: RecyclerView
    private lateinit var drinkSearch: SearchView
    private val cocktailViewModel: CocktailViewModel by lazy {
        ViewModelProvider(this).get(CocktailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.drinkListToolbar))

        recyclerCocktails = findViewById(R.id.recyclerCocktails)
        recyclerCocktails.layoutManager = LinearLayoutManager(this)

        drinkSearch = findViewById(R.id.drinkSearch)
        drinkSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    Handler().postDelayed({
                        cocktailViewModel.searchCocktail(query)
                    }, 2000)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null) {
                    Handler().postDelayed({
                        cocktailViewModel.searchCocktail(query)
                    }, 2000)
                }
                return true
            }

        })
        cocktailViewModel.drinkLiveData.observe(this, { state ->
            handleDataState(state)
        })
    }

    private fun handleDataState(state: DataState<List<Drink>>) {
        val progressCircular = findViewById<CircularProgressIndicator>(R.id.progress_circular)

        when(state) {
            is DataState.Loading -> {
                recyclerCocktails.visibility = View.GONE
                progressCircular.visibility = View.VISIBLE
            }
            is DataState.Success -> {
                progressCircular.visibility = View.GONE
                recyclerCocktails.visibility = View.VISIBLE

                if (state.data != null) {
                    recyclerCocktails.adapter = CocktailDataAdapter(this, state.data,
                        object : OnItemClickListener{
                        override fun onClick(drink: Drink) {
                            val intent = Intent(this@MainActivity, DrinkDetailsActivity::class.java)
                            intent.putExtra("drinkItem", drink)
                            startActivity(intent)
                        }
                    })
                }
            }
            is DataState.Error -> {
                Log.e("ERROR", state.message!!)

                progressCircular.visibility = View.GONE
                val view = progressCircular.rootView
                Snackbar.make(view, state.message, Snackbar.LENGTH_LONG).show()
            }
        }
    }
}