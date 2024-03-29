package com.facturas.Facturas_Electronicas.Facturacion.Facturas.exportRelated;

import com.facturas.Facturas_Electronicas.Facturacion.DTO.FacturaConDetallesDTO;
import com.facturas.Facturas_Electronicas.Facturacion.Detalles.model.DetalleFacturaEntity;
import com.facturas.Facturas_Electronicas.Facturacion.Facturas.model.FacturaEntity;

import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
public class InvoiceExporter {

    public byte[] exportToPDF(FacturaConDetallesDTO facturaConDetallesDTO) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();

        try {
            com.itextpdf.text.pdf.PdfWriter.getInstance(document, outputStream);
            document.open();

            // Fuente y tamaño para el título
            com.itextpdf.text.Font titleFont = com.itextpdf.text.FontFactory.getFont(com.itextpdf.text.FontFactory.HELVETICA_BOLD, 18, com.itextpdf.text.BaseColor.BLACK);

            // Fuente y tamaño para la información de la factura
            com.itextpdf.text.Font infoFont = com.itextpdf.text.FontFactory.getFont(com.itextpdf.text.FontFactory.HELVETICA, 12, com.itextpdf.text.BaseColor.BLACK);

            FacturaEntity factura = facturaConDetallesDTO.getFactura();
            List<DetalleFacturaEntity> detalles = facturaConDetallesDTO.getDetalles();

            // Título de la factura
            com.itextpdf.text.Paragraph title = new com.itextpdf.text.Paragraph("Factura", titleFont);
            title.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            document.add(title);

            // Espacio entre el título y la información de la factura
            document.add(new com.itextpdf.text.Paragraph("\n"));

            // Detalles de la factura
            document.add(new com.itextpdf.text.Paragraph("Detalles de la Factura:", infoFont));
            document.add(new com.itextpdf.text.Paragraph("ID de Factura: " + factura.getIdFactura(), infoFont));
            document.add(new com.itextpdf.text.Paragraph("Fecha de Emisión: " + factura.getFechaEmision(), infoFont));
            document.add(new com.itextpdf.text.Paragraph("ID de Proveedor: " + factura.getIdProveedor(), infoFont));
            document.add(new com.itextpdf.text.Paragraph("ID de Cliente: " + factura.getIdCliente(), infoFont));
            document.add(new com.itextpdf.text.Paragraph("Subtotal: " + factura.getSubtotal(), infoFont));
            document.add(new com.itextpdf.text.Paragraph("Impuesto: " + factura.getImpuesto(), infoFont));
            document.add(new com.itextpdf.text.Paragraph("Total: " + factura.getTotal(), infoFont));

            // Espacio entre la información de la factura y los detalles de los productos
            document.add(new com.itextpdf.text.Paragraph("\n"));

            // Detalles de los productos
            com.itextpdf.text.pdf.PdfPTable table = new com.itextpdf.text.pdf.PdfPTable(4); // 4 columnas para ID Producto, Cantidad, Precio Unitario, Total
            table.setWidthPercentage(100);
            table.addCell(new com.itextpdf.text.pdf.PdfPCell(new com.itextpdf.text.Phrase("ID Producto", infoFont)));
            table.addCell(new com.itextpdf.text.pdf.PdfPCell(new com.itextpdf.text.Phrase("Cantidad", infoFont)));
            table.addCell(new com.itextpdf.text.pdf.PdfPCell(new com.itextpdf.text.Phrase("Precio Unitario", infoFont)));
            table.addCell(new com.itextpdf.text.pdf.PdfPCell(new com.itextpdf.text.Phrase("Total", infoFont)));

            for (DetalleFacturaEntity detalle : detalles) {
                table.addCell(new com.itextpdf.text.pdf.PdfPCell(new com.itextpdf.text.Phrase(String.valueOf(detalle.getIdProducto()), infoFont)));
                table.addCell(new com.itextpdf.text.pdf.PdfPCell(new com.itextpdf.text.Phrase(String.valueOf(detalle.getCantidad()), infoFont)));
                table.addCell(new com.itextpdf.text.pdf.PdfPCell(new com.itextpdf.text.Phrase(String.valueOf(detalle.getPrecioUnitario()), infoFont)));
                table.addCell(new com.itextpdf.text.pdf.PdfPCell(new com.itextpdf.text.Phrase(String.valueOf(detalle.getTotal()), infoFont)));
            }
            table.addCell(new com.itextpdf.text.pdf.PdfPCell(new com.itextpdf.text.Phrase("Subtotal", infoFont)));
            table.addCell(new com.itextpdf.text.pdf.PdfPCell(new com.itextpdf.text.Phrase("", infoFont)));
            table.addCell(new com.itextpdf.text.pdf.PdfPCell(new com.itextpdf.text.Phrase("", infoFont)));
            table.addCell(new com.itextpdf.text.pdf.PdfPCell(new com.itextpdf.text.Phrase(String.valueOf(factura.getSubtotal()), infoFont)));

            document.add(table);

            document.close();
        } catch (com.itextpdf.text.DocumentException e) {
            throw new RuntimeException("Error al exportar la factura a PDF: " + e.getMessage());
        }

        return outputStream.toByteArray();
    }

    public byte[] exportToXML(FacturaConDetallesDTO facturaConDetallesDTO) {
        try {
            org.w3c.dom.Document document = javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            org.w3c.dom.Element rootElement = document.createElement("facturaConDetallesDTO");
            document.appendChild(rootElement);

            // Factura entity
            FacturaEntity factura = facturaConDetallesDTO.getFactura();
            org.w3c.dom.Element facturaElement = document.createElement("factura");
            rootElement.appendChild(facturaElement);

            org.w3c.dom.Element idFacturaElement = document.createElement("idFactura");
            idFacturaElement.setTextContent(String.valueOf(factura.getIdFactura()));
            facturaElement.appendChild(idFacturaElement);

            org.w3c.dom.Element fechaEmisionElement = document.createElement("fechaEmision");
            fechaEmisionElement.setTextContent(factura.getFechaEmision().toString());
            facturaElement.appendChild(fechaEmisionElement);

            org.w3c.dom.Element idProveedorElement = document.createElement("idProveedor");
            idProveedorElement.setTextContent(String.valueOf(factura.getIdProveedor()));
            facturaElement.appendChild(idProveedorElement);

            org.w3c.dom.Element idClienteElement = document.createElement("idCliente");
            idClienteElement.setTextContent(String.valueOf(factura.getIdCliente()));
            facturaElement.appendChild(idClienteElement);

            org.w3c.dom.Element subtotalElement = document.createElement("subtotal");
            subtotalElement.setTextContent(String.valueOf(factura.getSubtotal()));
            facturaElement.appendChild(subtotalElement);

            org.w3c.dom.Element impuestoElement = document.createElement("impuesto");
            impuestoElement.setTextContent(String.valueOf(factura.getImpuesto()));
            facturaElement.appendChild(impuestoElement);

            org.w3c.dom.Element totalElement = document.createElement("total");
            totalElement.setTextContent(String.valueOf(factura.getTotal()));
            facturaElement.appendChild(totalElement);

            // Detalles entity
            org.w3c.dom.Element detallesElement = document.createElement("detalles");
            rootElement.appendChild(detallesElement);

            for (DetalleFacturaEntity detalle : facturaConDetallesDTO.getDetalles()) {
                org.w3c.dom.Element detalleElement = document.createElement("detalle");
                detallesElement.appendChild(detalleElement);

                org.w3c.dom.Element idProductoElement = document.createElement("idProducto");
                idProductoElement.setTextContent(String.valueOf(detalle.getIdProducto()));
                detalleElement.appendChild(idProductoElement);

                org.w3c.dom.Element cantidadElement = document.createElement("cantidad");
                cantidadElement.setTextContent(String.valueOf(detalle.getCantidad()));
                detalleElement.appendChild(cantidadElement);

                org.w3c.dom.Element precioUnitarioElement = document.createElement("precioUnitario");
                precioUnitarioElement.setTextContent(String.valueOf(detalle.getPrecioUnitario()));
                detalleElement.appendChild(precioUnitarioElement);

                org.w3c.dom.Element totalDetalleElement = document.createElement("total");
                totalDetalleElement.setTextContent(String.valueOf(detalle.getTotal()));
                detalleElement.appendChild(totalDetalleElement);
            }

            // Convertir el documento a bytes
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            javax.xml.transform.Transformer transformer = javax.xml.transform.TransformerFactory.newInstance().newTransformer();
            transformer.transform(new javax.xml.transform.dom.DOMSource(document), new javax.xml.transform.stream.StreamResult(outputStream));

            return outputStream.toByteArray();
        } catch (javax.xml.parsers.ParserConfigurationException | javax.xml.transform.TransformerException e) {
            throw new RuntimeException("Error al exportar la factura a XML: " + e.getMessage());
        }
    }

}
