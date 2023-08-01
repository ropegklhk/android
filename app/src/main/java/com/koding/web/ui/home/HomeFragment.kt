package com.koding.web.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.koding.web.R
import com.koding.web.databinding.FragmentHomeBinding
import com.koding.web.viewmodel.ViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance()
    }

    private val sliderAdapter by lazy {
        SliderAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUi()
        setObserver()

    }
    // untuk mengambil data dari viewmodel
    private fun setObserver() {
        viewModel.slider.observe(viewLifecycleOwner) {
            sliderAdapter.submitList(it)
        }
    }

    // menampilkan pertama ui
    private fun setUi() = with(binding) {
        vpBanner.adapter = sliderAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}