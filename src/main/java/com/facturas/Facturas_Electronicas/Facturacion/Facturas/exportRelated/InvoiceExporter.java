package com.facturas.Facturas_Electronicas.Facturacion.Facturas.exportRelated;

import com.facturas.Facturas_Electronicas.Facturacion.DTO.FacturaConDetallesDTO;
import com.facturas.Facturas_Electronicas.Facturacion.Detalles.model.DetalleFacturaEntity;
import com.facturas.Facturas_Electronicas.Facturacion.Facturas.model.FacturaEntity;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.List;

@Component
public class InvoiceExporter {

    public byte[] exportToPDF(FacturaConDetallesDTO facturaConDetallesDTO) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Fuente y tamaño para el título
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);

            // Fuente y tamaño para la información de la factura
            Font infoFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

            FacturaEntity factura = facturaConDetallesDTO.getFactura();
            List<DetalleFacturaEntity> detalles = facturaConDetallesDTO.getDetalles();

            // Título de la factura
            Paragraph title = new Paragraph("Factura", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Espacio entre el título y la información de la factura
            document.add(new Paragraph("\n"));

            // Detalles de la factura
            document.add(new Paragraph("Detalles de la Factura:", infoFont));
            document.add(new Paragraph("ID de Factura: " + factura.getIdFactura(), infoFont));
            document.add(new Paragraph("Fecha de Emisión: " + factura.getFechaEmision(), infoFont));
            document.add(new Paragraph("ID de Proveedor: " + factura.getIdProveedor(), infoFont));
            document.add(new Paragraph("ID de Cliente: " + factura.getIdCliente(), infoFont));
            document.add(new Paragraph("Subtotal: " + factura.getSubtotal(), infoFont));
            document.add(new Paragraph("Impuesto: " + factura.getImpuesto(), infoFont));
            document.add(new Paragraph("Total: " + factura.getTotal(), infoFont));

            // Espacio entre la información de la factura y los detalles de los productos
            document.add(new Paragraph("\n"));

            // Detalles de los productos
            PdfPTable table = new PdfPTable(4); // 4 columnas para ID Producto, Cantidad, Precio Unitario, Total
            table.setWidthPercentage(100);
            table.addCell(new PdfPCell(new Phrase("ID Producto", infoFont)));
            table.addCell(new PdfPCell(new Phrase("Cantidad", infoFont)));
            table.addCell(new PdfPCell(new Phrase("Precio Unitario", infoFont)));
            table.addCell(new PdfPCell(new Phrase("Total", infoFont)));

            for (DetalleFacturaEntity detalle : detalles) {
                table.addCell(new PdfPCell(new Phrase(String.valueOf(detalle.getIdProducto()), infoFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(detalle.getCantidad()), infoFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(detalle.getPrecioUnitario()), infoFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(detalle.getTotal()), infoFont)));
            }
            table.addCell(new PdfPCell(new Phrase("Subtotal", infoFont)));
            table.addCell(new PdfPCell(new Phrase("", infoFont)));
            table.addCell(new PdfPCell(new Phrase("", infoFont)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(factura.getSubtotal()), infoFont)));

            document.add(table);

            document.close();
        } catch (DocumentException e) {
            throw new RuntimeException("Error al exportar la factura a PDF: " + e.getMessage());
        }

        return outputStream.toByteArray();
    }

}
