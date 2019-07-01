package mainView.tools;

import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.File;
import java.io.IOException;

public class PdfHandler {
    private static PDPageContentStream getContent(PDDocument doc, PDPage page,
                                                  String title, int amount, String percents) throws IOException {
        PDFont font = PDType0Font.load(doc, new File("fonts" + File.separator + "font.ttf"));
        PDFont fontItalic = PDType0Font.load(doc, new File("fonts" + File.separator + "fontItalic.ttf"));

        doc.addPage(page);

        PDPageContentStream content = new PDPageContentStream(doc, page);

        content.beginText();

        content.setFont(font, 26);
        content.newLineAtOffset(100, 700);
        content.setLeading(20.5f);

        content.showText(title);
        content.newLine();
        content.setFont(fontItalic, 16);
        content.showText(amount + "ml  " + percents);
        content.newLine();

        content.setFont(font, 16);

        return content;
    }

    public static void saveAmounts(String title, int amount, String percents, VBox mainContainer, File file) throws IOException {
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        PDPageContentStream content = getContent(doc, page, title, amount, percents);

        for (Node n : mainContainer.getChildren()) {
            HBox el = (HBox) n;
            content.newLine();

            if(el.lookup(".infoInput") != null)
                content.showText(((TextField)el.lookup(".infoInput")).getText());
            else {
                content.showText(((Spinner)el.lookup(".amountSpinner")).getValue() + " ml   ");
                content.showText(((TextField)el.lookup(".nameInput")).getText() + "    ");
                if(el.lookup(".percentsSpinner") != null)
                    content.showText(((Spinner)el.lookup(".percentsSpinner")).getValue() + " %");
            }
        }

        content.endText();
        content.close();
        doc.save(file);
        doc.close();
    }

    public static void saveProportions(String title, int amount, String percents, VBox mainContainer, File file) throws IOException {
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        PDPageContentStream content = getContent(doc, page, title, amount, percents);

        for (Node n : mainContainer.getChildren()) {
            HBox el = (HBox) n;
            content.newLine();

            if(el.lookup(".infoInput") != null)
                content.showText(((TextField)el.lookup(".infoInput")).getText());
            else {
                int first = (int) ((Spinner)el.lookup(".amountSpinner")).getValue();
                int gcd = MathTools.gcd(first, amount);
                content.showText((first/gcd) + "/" + (amount/gcd) + "   ");
                content.showText(((TextField)el.lookup(".nameInput")).getText() + "    ");
                if(el.lookup(".percentsSpinner") != null)
                    content.showText(((Spinner)el.lookup(".percentsSpinner")).getValue() + " %");
            }
        }

        content.endText();
        content.close();
        doc.save(file);
        doc.close();
    }
}
