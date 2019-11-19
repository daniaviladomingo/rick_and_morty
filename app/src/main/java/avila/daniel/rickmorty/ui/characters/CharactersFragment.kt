package avila.daniel.rickmorty.ui.characters


import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.base.BaseFragment
import avila.daniel.rickmorty.ui.data.ResourceState
import avila.daniel.rickmorty.ui.episodes.EpisodesFragment
import avila.daniel.rickmorty.ui.episodes.EpisodesViewModel
import avila.daniel.rickmorty.ui.model.CharacterUI
import kotlinx.android.synthetic.main.fragment_characters.*
import org.koin.android.viewmodel.ext.android.viewModel

class CharactersFragment : BaseFragment() {

    private val charactersViewModel: CharactersViewModel by viewModel()

    private val characterList = mutableListOf<CharacterUI>()
    private val adapter = CharactersAdapter(characterList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setListener()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        list_characters.adapter = adapter
        list_characters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.d("aaa", "Escrolleo")
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

    override fun getLayoutId(): Int = R.layout.fragment_characters

    override fun checkAgain(): () -> Unit = {}

    companion object {
        @JvmStatic
        fun newInstance() = CharactersFragment()

        const val TAG = "charactersFragment"
    }
}