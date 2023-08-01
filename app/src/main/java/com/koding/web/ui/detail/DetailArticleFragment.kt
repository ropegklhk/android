package com.koding.web.ui.detail

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.RoundedCornersTransformation
import com.koding.web.R
import com.koding.web.data.Resource
import com.koding.web.databinding.FragmentDetailArticleBinding
import com.koding.web.utils.withDateFormat
import com.koding.web.viewmodel.ViewModelFactory


class DetailArticleFragment : Fragment() {
    private var _binding: FragmentDetailArticleBinding? = null
    private val binding get() = _binding!!
    private val args: DetailArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUi()
    }

    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance()
    }

    private fun setObserver() {
        viewModel.getDetailArticle(args.slug).observe(viewLifecycleOwner) { resources ->
            when (resources) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    val article = resources.data
                    binding.imageArticle.load(article.image) {
                        transformations(RoundedCornersTransformation(12f))
                    }
                    binding.tvTitle.text = article.title
                    binding.tvCategory.text = article.category.name
                    binding.tvAuthor.text = article.user.name
                    binding.tvDate.text = article.createdAt.withDateFormat()
                    binding.tvView.text = article.viewsCount
                    binding.wvContent.text =
                        Html.fromHtml(article.content, Html.FROM_HTML_MODE_LEGACY)
                }
                is Resource.Error -> {

                }
            }

        }

    }

    private fun setUi() = with(binding) {
        toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            inflateMenu(R.menu.menu_article)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.bookmark -> {
                        true
                    }
                    R.id.share -> {
                        true
                    }
                    else -> false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}