package gr.indexinsidepdf.lib.storage;

import gr.indexinsidepdf.model.CoverModel;

/**
 * A singleton class than manages the cover settings.
 * @author siggouroglou@gmail.com
 */
public class CoverManager {
    private static CoverManager INSTANCE;
    private final CoverModel coverModel;
    
    private CoverManager() {
        coverModel = new CoverModel();
    }
    
    public static CoverManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new CoverManager();
        }
        return INSTANCE;
    }

    public CoverModel getCoverModel() {
        return coverModel;
    }

}
