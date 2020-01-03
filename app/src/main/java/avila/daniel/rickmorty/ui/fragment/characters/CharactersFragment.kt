package avila.daniel.rickmorty.ui.fragment.characters

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.base.BaseFragment
import avila.daniel.rickmorty.databinding.FragmentCharactersBinding
import avila.daniel.rickmorty.ui.activity.character.CharacterDetailActivity
import avila.daniel.rickmorty.ui.util.ISearchText
import avila.daniel.rickmorty.ui.util.data.ResourceState
import kotlinx.android.synthetic.main.fragment_characters.*
import org.koin.android.viewmodel.ext.android.viewModel

class CharactersFragment : BaseFragment(), ISearchText {

    private val charactersViewModel: CharactersViewModel by viewModel()

    private lateinit var adapter: CharactersAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (binding as FragmentCharactersBinding).viewModel = charactersViewModel

        setListener()

        adapter = CharactersAdapter(charactersViewModel)
        list_characters.adapter = adapter
        list_characters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    (list_characters.layoutManager as LinearLayoutManager).run {
                        if (childCount + findFirstVisibleItemPosition() >= itemCount) {
                            charactersViewModel.scrollEnd()
                        }
                    }
                }
            }
        })

        charactersViewModel.load()
    }

    private fun setListener() {
        charactersViewModel.itemsLiveData.observe(
            viewLifecycleOwner,
            Observer { resource -> resource?.run { managementResourceState(status, message) } })

        charactersViewModel.refreshLiveData.observe(
            viewLifecycleOwner,
            Observer { adapter.refresh() })

        charactersViewModel.characterParcelabeLiveData.observe(
            viewLifecycleOwner,
            Observer { resource ->
                resource?.run {
                    managementResourceState(status, message)
                    if (status == ResourceState.SUCCESS) {
                        data?.let {
                            startActivity(
                                Intent(
                                    activity,
                                    CharacterDetailActivity::class.java
                                ).apply {
                                    putExtra(CharacterDetailActivity.CHARACTER, it)
                                })
                        }
                    }
                }
            })
    }

    override fun searchText(searchText: String) {
        charactersViewModel.searchText(searchText)
    }

    override fun getLayoutId(): Int = R.layout.fragment_characters

    override fun checkAgain(): () -> Unit = {
        charactersViewModel.load()
    }

    override fun tryAgain(): () -> Unit = {
        charactersViewModel.load()
    }

    companion object {
        @JvmStatic
        fun newInstance() = CharactersFragment()
    }
}