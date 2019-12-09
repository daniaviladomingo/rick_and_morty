package avila.daniel.rickmorty.ui.fragment.characters

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.base.InitialLoadFragment
import avila.daniel.rickmorty.ui.model.CharacterUI
import avila.daniel.rickmorty.ui.util.ISearch
import avila.daniel.rickmorty.ui.util.data.ResourceState
import kotlinx.android.synthetic.main.fragment_characters.*
import org.koin.android.viewmodel.ext.android.viewModel

class CharactersFragment : InitialLoadFragment(), ISearch {

    private val charactersViewModel: CharactersViewModel by viewModel()

    private val characterList = mutableListOf<CharacterUI>()
    private val adapter = CharactersAdapter(characterList)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setListener()

        list_characters.adapter = adapter
        list_characters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    (list_characters.layoutManager as LinearLayoutManager).run {
                        charactersViewModel.listScrolled(
                            childCount,
                            findFirstVisibleItemPosition(),
                            itemCount
                        )
                    }
                }
            }
        })
    }

    private fun setListener() {
        charactersViewModel.charactersLiveData.observe(viewLifecycleOwner, Observer { resource ->
            Log.d("fff", "more")
            resource?.run {
                managementResourceState(status, message)
                if (status == ResourceState.SUCCESS) {
                    data?.run {
                        Log.d("fff", "sss ${this.size}")
                        characterList.addAll(this)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })

        charactersViewModel.clearCharactersLiveData.observe(
            viewLifecycleOwner,
            Observer { resource ->
                resource?.run {
                    managementResourceState(status, message)
                    if (status == ResourceState.SUCCESS) {
                        characterList.clear()
                        adapter.notifyDataSetChanged()
                    }
                }
            })
    }

    override fun initialLoad() {
        charactersViewModel.loadCharacteres()
    }

    override fun updateFilter(newFilter: String) {
        charactersViewModel.updateSearchFilter(newFilter)
    }

    override fun getLayoutId(): Int = R.layout.fragment_characters

    override fun checkAgain(): () -> Unit = {
        charactersViewModel.loadCharacteres()
    }

    override fun tryAgain(): () -> Unit = {
        charactersViewModel.loadCharacteres()
    }

    companion object {
        @JvmStatic
        fun newInstance() = CharactersFragment()
    }
}