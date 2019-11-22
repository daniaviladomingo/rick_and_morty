package avila.daniel.rickmorty.ui.characters

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.base.InitialLoadFragment
import avila.daniel.rickmorty.ui.model.CharacterUI
import avila.daniel.rickmorty.ui.util.data.ResourceState
import kotlinx.android.synthetic.main.fragment_characters.*
import org.koin.android.viewmodel.ext.android.viewModel

class CharactersFragment : InitialLoadFragment() {

    private val charactersViewModel: CharactersViewModel by viewModel()

    private val characterList = mutableListOf<CharacterUI>()
    private val adapter = CharactersAdapter(characterList)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setListener()

        list_characters.adapter = adapter
        list_characters.layoutManager = GridLayoutManager(activity, 3)
        list_characters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                (list_characters.layoutManager as LinearLayoutManager).run {
                    charactersViewModel.listScrolled(
                        childCount,
                        findFirstVisibleItemPosition(),
                        itemCount
                    )
                }
            }
        })
    }

    private fun setListener() {
        charactersViewModel.charactersLiveData.observe(viewLifecycleOwner, Observer { resource ->
            resource?.run {
                managementResourceState(status, message)
                if (status == ResourceState.SUCCESS) {
                    data?.run {
                        characterList.addAll(this)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    override fun initialLoad() {
        charactersViewModel.loadMoreCharacteres()
    }

    override fun getLayoutId(): Int = R.layout.fragment_characters

    override fun checkAgain(): () -> Unit = {}

    companion object {
        @JvmStatic
        fun newInstance() = CharactersFragment()
    }
}