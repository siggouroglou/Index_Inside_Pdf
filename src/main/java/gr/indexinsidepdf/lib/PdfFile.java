package gr.indexinsidepdf.lib;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import static gr.indexinsidepdf.lib.SoftawarePdfDocument.NEWLINE;
import static gr.indexinsidepdf.lib.SoftawarePdfDocument.TAB;
import gr.indexinsidepdf.lib.storage.CoverManager;
import gr.indexinsidepdf.lib.storage.SettingsManager;
import gr.indexinsidepdf.model.CoverModel;
import gr.indexinsidepdf.model.PdfNode;
import gr.softaware.java_1_0.data.mutable.IntegerMutable;
import gr.softaware.java_1_0.data.sequence.CountSequence;
import gr.softaware.java_1_0.data.sequence.LatinLowerCountSequence;
import gr.softaware.java_1_0.data.sequence.LatinUpperCountSequence;
import gr.softaware.java_1_0.data.sequence.NumberCountSequence;
import gr.softaware.java_1_0.data.sequence.RomanLowerCountSequence;
import gr.softaware.java_1_0.data.structure.tree.basic.BasicTree;
import gr.softaware.java_1_0.data.structure.tree.basic.BasicTreeNode;
import gr.softaware.java_1_0.data.types.FileType;
import gr.softaware.java_1_0.text.PropertiesUtilities;
import gr.softaware.java_1_0.text.TextStyleFormat;
import gr.softaware.java_1_0.text.TextStyleOutput;
import gr.softaware.javafx_1_0.controls.dialog.DialogHelper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 *
 * @author syggouroglou@gmail.com
 */
public class PdfFile extends SoftawarePdfDocument {

    private final BasicTree<PdfNode> tree;
    private final ProgressBar progressBar;
    private final Label progressLabel;
    private final Label progressResultLabel;

    private PdfFile(PdfFileBuilder builder) {
        this.tree = builder.tree;
        this.progressBar = builder.progressBar;
        this.progressLabel = builder.progressLabel;
        this.progressResultLabel = builder.progressResultLabel;
    }

    public static class PdfFileBuilder {

        private BasicTree<PdfNode> tree;
        private ProgressBar progressBar;
        private Label progressLabel;
        private Label progressResultLabel;

        public PdfFileBuilder() {
            this.tree = null;
            this.progressBar = null;
            this.progressLabel = null;
            this.progressResultLabel = null;
        }

        public PdfFileBuilder tree(BasicTree<PdfNode> tree) {
            this.tree = tree;
            return this;
        }

        public PdfFileBuilder progressBar(ProgressBar progressBar) {
            this.progressBar = progressBar;
            return this;
        }

        public PdfFileBuilder progressLabel(Label progressLabel) {
            this.progressLabel = progressLabel;
            return this;
        }

        public PdfFileBuilder progressResultLabel(Label progressResultLabel) {
            this.progressResultLabel = progressResultLabel;
            return this;
        }

        public PdfFile build() {
            return new PdfFile(this);
        }

    }

    private int progressDone = 0;
    private int progressMax;

    void create() {
        progressMax = 10 + 20 + tree.size(); // 10 for doc creation and 20 for cover page.

        // Create the task source.
        Task task = new Task<Void>() {
            private Document document;
            private BaseFont arialBaseFont;
            private BaseFont arialBoldBaseFont;
            private final CountSequence countSequence0 = new LatinUpperCountSequence();
            private final CountSequence countSequence1 = new NumberCountSequence();
            private final CountSequence countSequence2 = new LatinLowerCountSequence();
            private final CountSequence countSequence3 = new RomanLowerCountSequence();
            private final CountSequence countSequence4 = new RomanLowerCountSequence();

            @Override
            public Void call() {
                //=> State starting.
                updateResultLabel("Το ευρετήριο(pdf) ετοιμάζεται..");
                updateProgressLabel("Έναρξη διεργασίας");

                // Initialize the document.
                try {
                    createDocument();
                } catch (IOException | DocumentException ex) {
                    Platform.runLater(() -> DialogHelper.alertErrorGr("Ένα πρόβλημα προέκυψε με τις ρυθμίσεις του αρχείου ευρετηρίου. Παρακαλούμε ελέγξτε τις ρυθμίσεις που έχετε κάνει."));
                    return null;
                }

                try {
                    // Generate the cover.
                    generateCover();
                } catch (DocumentException | IOException ex) {
                    Platform.runLater(() -> DialogHelper.alertErrorGr("Ένα πρόβλημα προέκυψε κατά τη δημιουργία του εξώφυλλου."));
                    return null;
                }

                try {
                    // Generate the index.
                    generateIndex();
                } catch (DocumentException ex) {
                    Platform.runLater(() -> DialogHelper.alertErrorGr("Ένα πρόβλημα προέκυψε κατά τη δημιουργία του ευρετηρίου."));
                    return null;
                }

                // Close the document.
                document.close();
                updateResultLabel("Το ευρετήριο(pdf) είναι έτοιμο!");

                // Run the result method.
                Platform.runLater(() -> PdfProccessManager.getInstance().createPdfResult(null));

                return null;
            }

            private void updateProgressLabel(String message) {
                Platform.runLater(() -> progressLabel.textProperty().set(message));
            }

            private void updateResultLabel(String message) {
                Platform.runLater(() -> progressResultLabel.textProperty().set(message));
            }

            private void updateProgressPlus(int done) {
                progressDone += done;
                updateProgress(progressDone, progressMax);
            }

            private void createDocument() throws FileNotFoundException, DocumentException, IOException {
                //=> State starting.
                updateProgressLabel("Δημιουργία εγγράφου..");

                // Create the document.
                document = new Document(PageSize.A4, 40f, 40f, 40f, 40f);

                // Init the location and name of the pdf document.
                File indexFile;
                if (SettingsManager.getInstance().isDefaultLocation()) {
                    indexFile = new File(tree.getRoot().getData().getFile(), SettingsManager.getInstance().getFileName());
                } else {
                    indexFile = new File(SettingsManager.getInstance().getPdfFolderPath(), SettingsManager.getInstance().getFileName());
                }

                // Get a PdfWriter instanse.
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(indexFile));
                writer.setBoxSize("page", new Rectangle(10, 30, 540, 20));

                // Open the document.
                document.open();

                // Create Custom Font.
                arialBaseFont = BaseFont.createFont("files/fonts/arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                arialBoldBaseFont = BaseFont.createFont("files/fonts/arialunibd.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

                // Update process bar.
                updateProgressPlus(10);
            }

            private void generateCover() throws IOException, DocumentException {
                //=> State create cover.
                updateProgressLabel("Κατασκευή εξώφυλλου..");

                // Cover settings prefix.
                String coverProp = "cover." + SettingsManager.getInstance().getLanguage() + ".";
                String and = PropertiesUtilities.getInstance().getString(coverProp + "and");

                // One paragraph for the whole page.
                Paragraph coverParagraph = new Paragraph();
                coverParagraph.setAlignment(Element.ALIGN_CENTER);
                coverParagraph.setLeading(20);
                coverParagraph.setKeepTogether(true);

                // Add the logo.
                Image coverLogo = Image.getInstance(this.getClass().getResource("/files/images/cover_logo.jpg"));
                coverLogo.scaleToFit(170, 170);
                coverLogo.setAlignment(Element.ALIGN_CENTER);
                coverLogo.setSpacingBefore(-50);
                document.add(coverLogo);
                coverParagraph.add(NEWLINE[1]);

                // Get the Cover Model.
                CoverModel coverModel = CoverManager.getInstance().getCoverModel();
                Font normalFont = new Font(arialBaseFont, 12, Font.NORMAL);
                Font boldFont = new Font(arialBoldBaseFont, 12, Font.NORMAL);
                Font italicFont = new Font(arialBaseFont, 12, Font.ITALIC);
                Font boldAndItalicFont = new Font(arialBoldBaseFont, 12, Font.NORMAL);

                // title
                if (!coverModel.getStartTitle().isEmpty()) {
                    Font titleFont = new Font(arialBoldBaseFont, 16, Font.NORMAL);
                    coverParagraph.add(new Chunk(coverModel.getStartTitle(), titleFont));
                    coverParagraph.add(NEWLINE[1]);
                }

                // dated <date>
                if (!coverModel.getStartDate().isEmpty()) {
                    String date = PropertiesUtilities.getInstance().getString(coverProp + "date");
                    coverParagraph.add(new Chunk(date + " ", normalFont));
                    coverParagraph.add(new Chunk(coverModel.getStartDate(), boldFont));
                    coverParagraph.add(NEWLINE[1]);
                }

                // for an ammount of 
                if (!coverModel.getStartAmmount().isEmpty()) {
                    String amount = PropertiesUtilities.getInstance().getString(coverProp + "amount");
                    coverParagraph.add(new Chunk(amount + " ", normalFont));
                    coverParagraph.add(new Chunk(coverModel.getStartAmmount(), boldFont));
                    coverParagraph.add(NEWLINE[1]);
                }

                // between list
                if (!coverModel.getBetweenList().isEmpty()) {
                    final String between = PropertiesUtilities.getInstance().getString(coverProp + "between");
                    String borrower = PropertiesUtilities.getInstance().getString(coverProp + "borrower");
                    String borrowers = PropertiesUtilities.getInstance().getString(coverProp + "borrowers");
                    coverParagraph.add(NEWLINE[2]);
                    // Add the static containt.
                    coverParagraph.add(new Chunk(between, normalFont));
                    coverParagraph.add(NEWLINE[2]);
                    // Check the count of the between. Different format in case of one or more items.
                    if (coverModel.getBetweenList().size() == 1) {
                        coverParagraph.add(new Chunk(coverModel.getBetweenList().get(0), boldFont));
                        coverParagraph.add(NEWLINE[1]);
                        coverParagraph.add(new Chunk(borrower, normalFont));
                    } else {
                        // Create the roman sequence.
                        CountSequence betweenSequence = new RomanLowerCountSequence();
                        // Create a counter to not add the "and" word to the final item.
                        IntegerMutable counter = new IntegerMutable(0);
                        // Add the between items.
                        coverModel.getBetweenList().stream().forEach((betweenItem) -> {
                            counter.set(counter.intValue() + 1);
                            coverParagraph.add(new Chunk(betweenItem, boldFont));
                            coverParagraph.add(new Chunk(" (" + betweenSequence.nextValue() + ")", normalFont));
                            if (counter.intValue() < coverModel.getBetweenList().size()) {
                                coverParagraph.add(new Chunk(" " + and, normalFont));
                            }
                            coverParagraph.add(NEWLINE[1]);
                        });
                        coverParagraph.add(new Chunk(borrowers, normalFont));
                    }
                    coverParagraph.add(NEWLINE[1]);
                }

                // lender list
                if (!coverModel.getLenderList().isEmpty()) {
                    String lender = PropertiesUtilities.getInstance().getString(coverProp + "lender");
                    String lenders = PropertiesUtilities.getInstance().getString(coverProp + "lenders");
                    // Add the static containt.
                    coverParagraph.add(NEWLINE[1]);
                    coverParagraph.add(new Chunk(and, normalFont));
                    coverParagraph.add(NEWLINE[2]);
                    // Check the count of the lender. Different format in case of one or more items.
                    if (coverModel.getLenderList().size() == 1) {
                        coverParagraph.add(new Chunk(coverModel.getLenderList().get(0), boldFont));
                        coverParagraph.add(NEWLINE[1]);
                        coverParagraph.add(new Chunk(lender, normalFont));
                    } else {
                        // Create the roman sequence.
                        CountSequence lenderSequence = new RomanLowerCountSequence();
                        // Create a counter to not add the "and" word to the final item.
                        IntegerMutable counter = new IntegerMutable(0);
                        // Add the lender items.
                        coverModel.getLenderList().stream().forEach((lenderItem) -> {
                            counter.set(counter.intValue() + 1);
                            coverParagraph.add(new Chunk(lenderItem, boldFont));
                            coverParagraph.add(new Chunk(" (" + lenderSequence.nextValue() + ")", normalFont));
                            if (counter.intValue() < coverModel.getLenderList().size()) {
                                coverParagraph.add(new Chunk(" " + and, normalFont));
                            }
                            coverParagraph.add(NEWLINE[1]);
                        });
                        coverParagraph.add(new Chunk(lenders, normalFont));
                    }
                    coverParagraph.add(NEWLINE[1]);
                }

                // end title
                if (!coverModel.getEndTitle().isEmpty()) {
                    coverParagraph.add(NEWLINE[2]);
                    coverParagraph.add(new Chunk(coverModel.getEndTitle(), normalFont));
                    coverParagraph.add(NEWLINE[2]);
                }

                // end text.
                if (!coverModel.getEndText().isEmpty()) {
                    Paragraph footerParagraph = new Paragraph();
                    footerParagraph.setAlignment(Element.ALIGN_CENTER);
                    footerParagraph.setLeading(20);
                    TextStyleFormat textStyleFormat = new TextStyleFormat();
                    List<TextStyleOutput> formatBlocks = textStyleFormat.format(coverModel.getEndText());
                    for (TextStyleOutput current : formatBlocks) {
                        switch (current.getStyleType()) {
                            case NORMAL:
                                footerParagraph.add(new Chunk(current.getValue().toString(), normalFont));
                                break;
                            case BOLD:
                                footerParagraph.add(new Chunk(current.getValue().toString(), boldFont));
                                break;
                            case ITALIC:
                                footerParagraph.add(new Chunk(current.getValue().toString(), italicFont));
                                break;
                            case BOLD_ITALIC:
                                footerParagraph.add(new Chunk(current.getValue().toString(), boldAndItalicFont));
                                break;
                        }
                    }
                    coverParagraph.add(footerParagraph);
                }

                // Add the paragraph to the document.
                document.add(coverParagraph);
                document.newPage();

                // Update process bar.
                updateProgressPlus(20);
            }

            private void generateIndex() throws DocumentException {
                //=> State create cover.
                updateProgressLabel("Κατασκευή ευρετηρίου..");

                // Cover settings prefix.
                String coverProp = "cover." + SettingsManager.getInstance().getLanguage() + ".";
                String index = PropertiesUtilities.getInstance().getString(coverProp + "index");

                // Add the index title.
                Font titleFont = new Font(arialBaseFont, 25, Font.NORMAL, new BaseColor(23, 54, 93));
                final Paragraph titleParagraph = new Paragraph();
                titleParagraph.setAlignment(Element.ALIGN_LEFT);
                titleParagraph.add(new Chunk(index, titleFont));

                // Add the index text on the top.
                Font separatorFont = new Font(arialBaseFont, 20, Font.NORMAL, new BaseColor(79, 129, 189));
                LineSeparator lineSeparator = new LineSeparator(separatorFont);
                titleParagraph.add(lineSeparator);
                document.add(titleParagraph);

                // Create the paragraph.
                final Paragraph paragraph = new Paragraph();
                paragraph.setSpacingBefore(-30);
                paragraph.setAlignment(Element.ALIGN_LEFT);
                paragraph.setLeading(40);

                IntegerMutable level = new IntegerMutable(0);
                generateIndexRecursively(tree.getRoot(), tree.getRoot(), paragraph, level);

                // Add content.
                document.add(paragraph);
            }

            private void generateIndexRecursively(BasicTreeNode<PdfNode> parent, BasicTreeNode<PdfNode> current, Paragraph paragraph, IntegerMutable level) {
                // Argument validation.
                if (parent == null) {
                    throw new NullPointerException("Parent cannot be null. Maybe a bug exists here.");
                }
                // Argument validation.
                if (current == null) {
                    throw new NullPointerException("Child cannot be null. Maybe a bug exists here.");
                }

                // Get the pdfNOde.
                PdfNode node = (PdfNode) current.getData();

                // Check if i am not on  the root node.
                if (parent.equals(current)) {
                    // First run so refresh the level.
                    level.set(-1);
                } else {
                    generateIndexRow(node, level, paragraph);
                }

                // Update process bar.
                updateProgressPlus(1);

                // Continue with children of this node.
                for (BasicTreeNode<PdfNode> child : current.getChildren()) {
                    level.set(level.intValue() + 1);
                    generateIndexRecursively(current, child, paragraph, level);

                    // Reset the correct sequence.
                    if (level.intValue() == 1) {
                        countSequence2.reset();
                    } else if (level.intValue() == 2) {
                        countSequence3.reset();
                    } else if (level.intValue() == 3) {
                        countSequence4.reset();
                    }

                    // Reset the level to the correct number.
                    level.set(level.intValue() - 1);
                }
            }

            private void generateIndexRow(PdfNode node, IntegerMutable level, Paragraph paragraph) {
                // Get the corrent font.
                Font font;
                if (level.intValue() == 0) {
                    font = new Font(arialBoldBaseFont, 15, Font.NORMAL, new BaseColor(49, 132, 155));
                } else if (level.intValue() == 1) {
                    font = new Font(arialBoldBaseFont, 13, Font.NORMAL);
                } else {
                    font = new Font(arialBaseFont, 13, Font.ITALIC);
                }

                // Add new line.
                paragraph.add(NEWLINE[1]);
                // Add tab.
                paragraph.add(TAB[level.intValue() + 1]);

                // Get the correct count sequence.
                if (level.intValue() == 0) {
                    paragraph.add(new Chunk(countSequence0.nextValue() + ".  ", font));
                } else if (level.intValue() == 1) {
                    paragraph.add(new Chunk(countSequence1.nextValue() + ".  ", font));
                } else if (level.intValue() == 2) {
                    paragraph.add(new Chunk(countSequence2.nextValue() + ")  ", font));
                } else if (level.intValue() == 3) {
                    paragraph.add(new Chunk(countSequence3.nextValue() + ")  ", font));
                } else if (level.intValue() == 4) {
                    paragraph.add(new Chunk(countSequence4.nextValue() + ")  ", font));
                }

                // Create the element.
                Chunk element = new Chunk(node.getTitle(), font);

                // Check if this is a directory.
                if (node.getFileType() == FileType.DIRECTORY) {
                    paragraph.add(element);
                } else if (node.getFileType() == FileType.FILE) {
                    String url = getRelativePath(node.getFile().getAbsolutePath(), tree.getRoot().getData().getFile().getAbsolutePath());
//                        Anchor anchor = new Anchor(new Phrase(node.getTitle(), font));
//                        String reference;
//                        try {
//                            reference = URIUtil.encodeQuery(url);
//                            anchor.setReference(reference);
//                        } catch (URIException ex) {
//                        }
//                        Chunk element = new Chunk(node.getTitle(), font);
                    element.setAnchor(url);
                    paragraph.add(element);
                }
            }
        };

        // Run the task to a thread.
        progressBar.progressProperty().bind(task.progressProperty());
        Thread thread = new Thread(task);
        thread.start();
    }

    public String getRelativePath(String absolutePath, String root) {
        String relative = absolutePath.replace(root, "");
        relative = relative.replace("\\", "/");
        if (!relative.startsWith("/")) {
            relative = "./" + relative;
        } else {
            relative = "." + relative;
        }

        return relative;
    }
}
