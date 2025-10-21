package ReqResAPI;

import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordUtils {

    private static final List<WordEntry> entries = new ArrayList<>();
    private static final String FILE_NAME = "ReqRes_API_Documentation.docx";

    // Add a request-response entry
    public static void addRequestResponse(String testName, String request, String response, int statusCode) {
        entries.add(new WordEntry(testName, request, response, statusCode));
    }

    // Save all entries into a Word document
    public static void saveWordFile() {
        try (XWPFDocument document = new XWPFDocument()) {

            XWPFParagraph title = document.createParagraph();
            title.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun runTitle = title.createRun();
            runTitle.setText("ReqRes API Documentation");
            runTitle.setBold(true);
            runTitle.setFontSize(20);

            for (WordEntry entry : entries) {
                XWPFParagraph para = document.createParagraph();
                XWPFRun run = para.createRun();
                run.setBold(true);
                run.setText("Test: " + entry.testName);

                XWPFParagraph reqPara = document.createParagraph();
                XWPFRun reqRun = reqPara.createRun();
                reqRun.setBold(true);
                reqRun.setText("Request: ");
                reqRun.addBreak();
                reqRun.setText(entry.request);

                XWPFParagraph respPara = document.createParagraph();
                XWPFRun respRun = respPara.createRun();
                respRun.setBold(true);
                respRun.setText("Response (Status: " + entry.statusCode + "): ");
                respRun.addBreak();
                respRun.setText(entry.response);

                XWPFParagraph separator = document.createParagraph();
                XWPFRun sepRun = separator.createRun();
                sepRun.addBreak();
                sepRun.setText("-----------------------------------------------------");
                sepRun.addBreak();
            }

            try (FileOutputStream out = new FileOutputStream(FILE_NAME)) {
                document.write(out);
                System.out.println("✅ Word file generated: " + FILE_NAME);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Inner class to hold test data
    private static class WordEntry {
        String testName;
        String request;
        String response;
        int statusCode;

        WordEntry(String testName, String request, String response, int statusCode) {
            this.testName = testName;
            this.request = request;
            this.response = response;
            this.statusCode = statusCode;
        }
    }
}
