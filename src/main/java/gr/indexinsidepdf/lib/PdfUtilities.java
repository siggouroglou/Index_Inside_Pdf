package gr.indexinsidepdf.lib;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author gbirbilis
 */
class PdfUtilities extends PdfPageEventHelper {

    // Geberic Fonts.
    public static final Font BOLD = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    public static final Font UNDERLINE = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.UNDERLINE);
    public static final Font NORMAL = new Font(Font.FontFamily.TIMES_ROMAN, 12);
    public static final Font ITALIC = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.ITALIC);
    public static final Font BOLD_UNDERLINE = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD | Font.UNDERLINE);
    public static final Font URL = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD | Font.UNDERLINE, new BaseColor(22, 51, 242));
    // Colored Fonts.
    public static final Font ORANGE = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(225, 166, 39));
    public static final Font BLUE = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 3, 176));
    // New Line, Tab.
    public static final Chunk[] NEWLINE = new Chunk[] {new Chunk(""), new Chunk("\n"), new Chunk("\n\n"), new Chunk("\n\n\n"), new Chunk("\n\n\n\n"), new Chunk("\n\n\n\n\n"), new Chunk("\n\n\n\n\n\n")};
    public static final Chunk[] TAB = new Chunk[] {new Chunk(""), new Chunk("    "), new Chunk("        "), new Chunk("            "), new Chunk("                "), new Chunk("                    "), new Chunk("                        ")};
    // Custom Fonts.
    public static final Font FONT_52 = new Font(Font.FontFamily.HELVETICA, 52, Font.BOLD, new GrayColor(0.75f));
    public static final Font FONT_12 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, new GrayColor(0.75f));
    // Custom Embeded Fonts For Unicode Support.
    public static Font NORMAL_UNC = new Font();
    public static Font BOLD_UNC = new Font();
    // General Symbols.
    public static final Chunk DOT = new Chunk(".");
    public static final Chunk COMMA = new Chunk(",");

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        // Create pagging.
        Rectangle rect = writer.getBoxSize("page");
        Phrase page = new Phrase(Integer.toString(writer.getPageNumber()), FONT_12);
        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_RIGHT, page,
                rect.getRight(), rect.getTop(), 0);
    }

    // One Page watermark. (Manual called method.)
    public void watermark(PdfWriter writer, Document document, String text) {
        // Create PdfUtilities.
        ColumnText.showTextAligned(writer.getDirectContentUnder(),
                Element.ALIGN_CENTER, new Phrase(text, FONT_52),
                297.5f, 421, writer.getPageNumber() % 2 == 1 ? 45 : -45);

//        ColumnText.showTextAligned(writer.getDirectContentUnder(),
//                Element.ALIGN_CENTER, new Phrase("de-code.gr", FONT_52),
//                297.5f, 421, 45);
    }

    // Create pagging (Manual called method.)
    public void pagging(PdfWriter writer, Document document, int pageNumber) {
        Rectangle rect = writer.getBoxSize("page");
        Phrase header = new Phrase(Integer.toString(writer.getPageNumber()), FONT_12);
        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_RIGHT, header,
                rect.getRight(), rect.getTop(), 0);
        pageNumber++;
    }

    // Create a Link.
    public Chunk createUrl(String name, String link) throws MalformedURLException {
        // Create Url.
        Chunk url = new Chunk(name, URL);
        url.setAction(new PdfAction(new URL(link)));

        return url;
    }

    // Create an image.
    public Image createImage(String path, int aligment, int border, int borderWidth, BaseColor borderColor, int fitWidth, int fitHeigth) throws BadElementException, IOException {
        // Create the image object.
        Image image = Image.getInstance(path);
        image.setAlignment(aligment);
        image.setBorder(border);
        image.setBorderWidth(borderWidth);
        image.setBorderColor(borderColor);
        image.scaleToFit(fitWidth, fitHeigth);

        return image;
    }
}
