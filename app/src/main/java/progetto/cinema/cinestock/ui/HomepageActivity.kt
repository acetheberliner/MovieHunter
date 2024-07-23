//package progetto.cinema.cinestock.ui
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.google.android.material.progressindicator.CircularProgressIndicator
//import progetto.cinema.cinestock.R
//import progetto.cinema.cinestock.ui.adapter.FilmAdapter
//import progetto.cinema.cinestock.ui.viewmodel.HomepageViewModel
//import progetto.cinema.cinestock.ui.viewmodel.HomepageViewModelListener
//
//class HomepageActivity : AppCompatActivity(){
//
//    private val TAG = ""
//    private lateinit var recyclerView: RecyclerView
//    private var progressIndicator: CircularProgressIndicator? = null
//
//    private var viewModel: HomepageViewModel? = null
//
//    override fun onCreate(savedInstanceState: Bundle?){
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        recyclerView = findViewById(R.id.recycler_view)
//        progressIndicator = findViewById(R.id.progressIndicator)
//
//        val adapter = FilmAdapter(mutableListOf())
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(this@HomepageActivity)
//
//        viewModel = ViewModelProvider(this)[HomepageViewModel::class.java]
//        viewModel?.setupListener(
//            listener = object : HomepageViewModelListener {
//                override fun onLocationListRetrieved(
//                    filmModelList: List<FilmModel>
//                ) {
//                    // ho lista dei film aggiornata
//                }
//
//            }
//        )
//        viewModel?.filmSearch()
//    }
//}