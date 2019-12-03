package avila.daniel.rickmorty.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.CheckBoxPreference
import androidx.preference.PreferenceFragmentCompat
import avila.daniel.rickmorty.R
import kotlinx.android.synthetic.main.activity_settings.*

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
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            val keyNameCharacter =
                findPreference<CheckBoxPreference>(getString(R.string.key_name_characters))
            val keyTypeCharacter =
                findPreference<CheckBoxPreference>(getString(R.string.key_type_characters))
            val keySpecie = findPreference<CheckBoxPreference>(getString(R.string.key_specie))



            keyNameCharacter?.setOnPreferenceChangeListener { preference, newValue ->
                if (newValue as Boolean) {
                    keyTypeCharacter?.isChecked = false
                    keySpecie?.isChecked = false
                }
                true
            }

            keyTypeCharacter?.setOnPreferenceChangeListener { preference, newValue ->
                if (newValue as Boolean) {
                    keyNameCharacter?.isChecked = false
                    keySpecie?.isChecked = false
                }
                true
            }

            keySpecie?.setOnPreferenceChangeListener { preference, newValue ->
                if (newValue as Boolean) {
                    keyTypeCharacter?.isChecked = false
                    keyNameCharacter?.isChecked = false
                }
                true
            }

            val keyNameLocation =
                findPreference<CheckBoxPreference>(getString(R.string.key_name_location))
            val keyTypeLocation =
                findPreference<CheckBoxPreference>(getString(R.string.key_type_location))
            val keyDimension =
                findPreference<CheckBoxPreference>(getString(R.string.key_dimension))

            keyNameLocation?.setOnPreferenceChangeListener { preference, newValue ->
                if (newValue as Boolean) {
                    keyTypeLocation?.isChecked = false
                    keyDimension?.isChecked = false
                }
                true
            }

            keyTypeLocation?.setOnPreferenceChangeListener { preference, newValue ->
                if (newValue as Boolean) {
                    keyNameLocation?.isChecked = false
                    keyDimension?.isChecked = false
                }
                true
            }

            keyDimension?.setOnPreferenceChangeListener { preference, newValue ->
                if (newValue as Boolean) {
                    keyNameLocation?.isChecked = false
                    keyTypeLocation?.isChecked = false

                }
                true
            }
        }
    }
}