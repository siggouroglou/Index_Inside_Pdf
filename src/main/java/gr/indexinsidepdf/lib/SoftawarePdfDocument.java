package gr.indexinsidepdf.lib;

import com.itextpdf.text.Chunk;

/**
 *
 * @author gbirbilis
 */
public abstract class SoftawarePdfDocument {

    // New Line, Tab.
    public static final Chunk[] NEWLINE = new Chunk[]{new Chunk(""), new Chunk("\n"), new Chunk("\n\n"), new Chunk("\n\n\n"), new Chunk("\n\n\n\n"), new Chunk("\n\n\n\n\n"), new Chunk("\n\n\n\n\n\n")};
    public static final Chunk[] TAB = new Chunk[]{new Chunk(""), new Chunk("    "), new Chunk("        "), new Chunk("            "), new Chunk("                "), new Chunk("                    "), new Chunk("                        ")};
    
}
