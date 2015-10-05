package gr.indexinsidepdf.lib;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import static gr.indexinsidepdf.lib.PdfUtilities.NEWLINE;
import static gr.indexinsidepdf.lib.PdfUtilities.TAB;
import gr.indexinsidepdf.lib.storage.SettingsManager;
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
import gr.softaware.javafx_1_0.controls.dialog.DialogHelper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 *
 * @author syggouroglou@gmail.com
 */
public class PdfFile extends PdfUtilities {

    private final BasicTree<PdfNode> tree;
    private final ProgressBar progressBar;
    private final Label progressLabel;

    private PdfFile(PdfFileBuilder builder) {
        this.tree = builder.tree;
        this.progressBar = builder.progressBar;
        this.progressLabel = builder.progressLabel;
    }

    public static class PdfFileBuilder {

        private BasicTree<PdfNode> tree;
        private ProgressBar progressBar;
        private Label progressLabel;

        public PdfFileBuilder() {
            this.tree = null;
            this.progressBar = null;
            this.progressLabel = null;
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

        public PdfFile build() {
            return new PdfFile(this);
        }

    }

    private int progressDone = 0;
    private int progressMax;
    private Document document;
    private BaseFont arialBaseFont;
    private BaseFont arialBoldBaseFont;

    void create() {
        progressMax = 10 + 20 + tree.size(); // 10 for doc creation and 20 for cover page.

        // Create the task source.
        Task task = new Task<Void>() {
            private final CountSequence countSequence0 = new LatinUpperCountSequence();
            private final CountSequence countSequence1 = new NumberCountSequence();
            private final CountSequence countSequence2 = new LatinLowerCountSequence();
            private final CountSequence countSequence3 = new RomanLowerCountSequence();
            private final CountSequence countSequence4 = new RomanLowerCountSequence();

            @Override
            public Void call() {
                // Initialize the document.
                try {
                    createDocument();
                } catch (IOException | DocumentException ex) {
                    DialogHelper.alertErrorGr("Ένα πρόβλημα προέκυψε με τις ρυθμίσεις του αρχείου ευρετηρίου. Παρακαλούμε ελέγξτε τις ρυθμίσεις που έχετε κάνει.");
                    return null;
                }

                try {
                    // Generate the cover.
                    generateIndex();
                } catch (DocumentException ex) {
                    DialogHelper.alertErrorGr("Ένα πρόβλημα προέκυψε κατά τη δημιουργία του ευρετηρίου.");
                    return null;
                }

                // Close the document.
                document.close();

                // Run the result method.
                Platform.runLater(() -> PdfProccessManager.getInstance().createPdfResult(null));

                return null;
            }

            private void updateProgressPlus(int done) {
                progressDone += done;
                updateProgress(progressDone, progressMax);
            }

            private void createDocument() throws FileNotFoundException, DocumentException, IOException {
                // Create the document.
                document = new Document(PageSize.A4, 50f, 50f, 20f, 80f);

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
                arialBaseFont = BaseFont.createFont("files/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                arialBoldBaseFont = BaseFont.createFont("files/fonts/arialbd.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

                // Update process bar.
                updateProgressPlus(10);
            }

            private void generateIndex() throws DocumentException {

                // Create the paragraph.
                final Paragraph paragraph = new Paragraph();
                paragraph.setAlignment(Element.ALIGN_LEFT);
                paragraph.setLeading(50);
                paragraph.add(NEWLINE[0]);

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
                    font = new Font(arialBoldBaseFont, 14, Font.NORMAL);
                } else {
                    font = new Font(arialBaseFont, 14, Font.ITALIC);
                }

                // Add new line.
                paragraph.add(NEWLINE[1]);
                // Add tab.
                paragraph.add(TAB[level.intValue()]);

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
//                        Anchor anchor = new Anchor(new Phrase(node.getTitle(), font));
                    String url = relativePath(node.getFile().getAbsolutePath(), tree.getRoot().getData().getFile().getAbsolutePath());
//                        String reference;
//                        try {
//                            reference = URIUtil.encodeQuery(url);
//                            anchor.setReference(reference);
//                        } catch (URIException ex) {
//                        }
//                        Chunk chunk = new Chunk(node.getTitle(), font);
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

    public String relativePath(String absolutePath, String root) {
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
