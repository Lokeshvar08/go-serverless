package com.restaurant.server.restaurantservermanager.service;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.restaurant.server.restaurantservermanager.service.forms.kitchen.Order;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service

public class CreatePDF {

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLACK);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("s.no", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("food name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("quantity", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("total", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table, List<Order> orders) {
        int i = 1;
        Double sum = 0.0;
        for( Order order: orders ) {
            table.addCell(String.valueOf(i));
            table.addCell(order.getFood());
            table.addCell(String.valueOf(order.getQuantity()));
            table.addCell(String.valueOf(order.getTotal()));
            sum = sum + order.getTotal();
        }
        table.addCell("");
        table.addCell("");
        table.addCell("GRAND TOTAL");
        table.addCell(String.valueOf(sum));
    }

    public OutputStream export(OutputStream out,
                               List<Order> orders,
                               String name) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, out);

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);

        Paragraph p = new Paragraph("Your Transaction in " + name, font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table, orders);

        document.add(table);

        document.close();
        return out;
    }
}
