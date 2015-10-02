package gr.indexinsidepdf.lib.storage;

import gr.indexinsidepdf.model.PdfConfiguration;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class PdfConfigurationManager {
    private static PdfConfigurationManager INSTANCE;
    private final PdfConfiguration pdfConfiguration;
    
    private PdfConfigurationManager() {
        pdfConfiguration = new PdfConfiguration();
    }
    
    public static PdfConfigurationManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new PdfConfigurationManager();
        }
        return INSTANCE;
    }

    public PdfConfiguration getPdfConfiguration() {
        return pdfConfiguration;
    }

}
