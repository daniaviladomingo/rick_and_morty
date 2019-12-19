package avila.daniel.rickmorty.ui.fragment.characters

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import avila.daniel.domain.model.Character
import avila.daniel.repository.cache.model.compose.CharacterFilterParameter
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.base.InitialLoadFragment
import avila.daniel.rickmorty.di.qualifiers.SearchFilterCharacters
import avila.daniel.rickmorty.ui.activity.character.CharacterActivity
import avila.daniel.rickmorty.ui.util.ISearch
import avila.daniel.rickmorty.ui.util.data.ResourceState
import kotlinx.android.synthetic.main.fragment_characters.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class CharactersFragment : InitialLoadFragment(), ISearch {

    private val charactersViewModel: CharactersViewModel by viewModel()

    private val characterList = mutableListOf<Character>()
    private val adapter: CharactersAdapter by inject()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setListener()

        adapter.onClickListener = { id ->
            charactersViewModel.openCharacterDetail(characterList.find { it.id == id }!!)
        }

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
    }

    private fun setListener() {
        charactersViewModel.itemsLiveData.observe(viewLifecycleOwner, Observer { resource ->
            resource?.run {
                managementResourceState(status, message)
                if (status == ResourceState.SUCCESS) {
                    data?.run {
                        if (this.first) {
                            characterList.clear()
                        }
                        characterList.addAll(this.second)
                        adapter.update(characterList)
                    }
                }
            }
        })

        charactersViewModel.refreshLiveData.observe(viewLifecycleOwner, Observer { resource ->
            resource?.run {
                managementResourceState(status, message)
                if (status == ResourceState.SUCCESS) {
                    data?.run {
                        adapter.refresh()
                    }
                }
            }
        })

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
                                    CharacterActivity::class.java
                                ).apply {
                                    putExtra(CharacterActivity.CHARACTER, it)
                                })
                        }
                    }
                }
            })
    }

    override fun initialLoad() {
        charactersViewModel.load()
    }

    override fun updateFilter(newFilter: String) {
        charactersViewModel.updateSearchFilter(newFilter)
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