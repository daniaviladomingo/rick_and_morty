package avila.daniel.rickmorty.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.CheckBoxPreference
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.di.qualifiers.RefreshData
import kotlinx.android.synthetic.main.activity_settings.*
import org.koin.android.ext.android.inject

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.settings,
                SettingsFragment()
            )
            .commit()
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        private val reloadData: () -> Unit by inject()
        private val refreshData: () -> Unit by inject(RefreshData)

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            val preferenceNameCharacter =
                findPreference<CheckBoxPreference>(getString(R.string.key_name_characters))
            val preferenceTypeCharacter =
                findPreference<CheckBoxPreference>(getString(R.string.key_type_characters))
            val preferenceSpecieCharacter =
                findPreference<CheckBoxPreference>(getString(R.string.key_specie))

            preferenceNameCharacter?.setOnPreferenceChangeListener { _, newValue ->
                if (newValue as Boolean) {
                    preferenceTypeCharacter?.isChecked = false
                    preferenceSpecieCharacter?.isChecked = false
                    refreshData()
                    true
                } else {
                    false
                }
            }

            preferenceTypeCharacter?.setOnPreferenceChangeListener { _, newValue ->
                if (newValue as Boolean) {
                    preferenceNameCharacter?.isChecked = false
                    preferenceSpecieCharacter?.isChecked = false
                    refreshData()
                    true
                } else {
                    false
                }
            }

            preferenceSpecieCharacter?.setOnPreferenceChangeListener { _, newValue ->
                if (newValue as Boolean) {
                    preferenceTypeCharacter?.isChecked = false
                    preferenceNameCharacter?.isChecked = false
                    refreshData()
                    true
                } else {
                    false
                }
            }

            val keyNameLocation =
                findPreference<CheckBoxPreference>(getString(R.string.key_name_location))
            val keyTypeLocation =
                findPreference<CheckBoxPreference>(getString(R.string.key_type_location))
            val keyDimension =
                findPreference<CheckBoxPreference>(getString(R.string.key_dimension))

            keyNameLocation?.setOnPreferenceChangeListener { _, newValue ->
                if (newValue as Boolean) {
                    keyTypeLocation?.isChecked = false
                    keyDimension?.isChecked = false
                    refreshData()
                    true
                } else {
                    false
                }
            }

            keyTypeLocation?.setOnPreferenceChangeListener { _, newValue ->
                if (newValue as Boolean) {
                    keyNameLocation?.isChecked = false
                    keyDimension?.isChecked = false
                    refreshData()
                    true
                } else {
                    false
                }
            }

            keyDimension?.setOnPreferenceChangeListener { _, newValue ->
                if (newValue as Boolean) {
                    keyNameLocation?.isChecked = false
                    keyTypeLocation?.isChecked = false
                    refreshData()
                    true
                } else {
                    false
                }
            }

            findPreference<ListPreference>(getString(R.string.key_gender))?.setOnPreferenceChangeListener { prefecence, newValue ->
                if ((prefecence as ListPreference).value != newValue) {
                    reloadData()
                    true
                } else {
                    false
                }
            }

            findPreference<ListPreference>(getString(R.string.key_status))?.setOnPreferenceChangeListener { prefecence, newValue ->
                if ((prefecence as ListPreference).value != newValue) {
                    reloadData()
                    true
                } else {
                    false
                }
            }
        }
    }
}