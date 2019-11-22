package avila.daniel.searchview_test;

/**
 * @author Fernando A. H. Falkiewicz
 */
public abstract class SimpleOnQueryTextListener implements SimpleSearchView.OnQueryTextListener {
    @Override
    public boolean onQueryTextSubmit(String query) {
        // No action
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // No action
        return false;
    }

    @Override
    public boolean onQueryTextCleared() {
        // No action
        return false;
    }
}
