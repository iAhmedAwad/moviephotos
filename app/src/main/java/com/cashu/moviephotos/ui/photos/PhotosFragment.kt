package com.cashu.moviephotos.ui.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.HttpException
import com.cashu.moviephotos.R
import com.cashu.moviephotos.data.remote.APIRepository
import com.cashu.moviephotos.data.remote.APIServices
import com.cashu.moviephotos.data.remote.RetrofitClient
import com.cashu.moviephotos.data.remote.constants.APIQueries
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PhotosFragment : Fragment() {


    private lateinit var viewModel: PhotosViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var apiServices: APIServices
    private lateinit var apiRepository: APIRepository
    private val retrofitClient = RetrofitClient.getRetrofitClient()!!
    private var photosAdapter = PhotosAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =
            inflater.inflate(R.layout.photos_fragment, container, false)

        apiServices = retrofitClient.create(APIServices::class.java)
        apiRepository = APIRepository(apiServices)

        initViews(view)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PhotosViewModel::class.java)
        // TODO: Use the ViewModel
       CoroutineScope(IO).launch {
           getData()
       }
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = photosAdapter
        }
    }

    private suspend fun getData() {
        CoroutineScope(IO).launch {

            val response = apiRepository.getPhotos(
                APIQueries.METHOD_PHOTOS_SEARCH, APIQueries.FORMAT_VALUE, APIQueries.TEXT_VALUE,
                1, 20
            )
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        //Do something with response e.g show to the UI.
                        val photos = response.body()?.photos?.photo
                        if (photos != null) {
                            photosAdapter.setData(photos)
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: HttpException) {
                    Toast.makeText(requireContext(), "Exception ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                } catch (e: Throwable) {
                    Toast.makeText(
                        requireContext(),
                        "Ooops: Something else went wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }

}
