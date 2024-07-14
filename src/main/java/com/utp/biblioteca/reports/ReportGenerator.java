package com.utp.biblioteca.reports;

import com.utp.biblioteca.resources.configuracion.Conexion;
import com.utp.biblioteca.resources.modelo.FrecuenciaPrestamo;
import com.utp.biblioteca.resources.modelo.Libro;
import com.utp.biblioteca.resources.modelo.Usuario;
import com.utp.biblioteca.resources.modelo.dao.sp.StoredProcedureRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReportGenerator {

    private final StoredProcedureRepository repository = new StoredProcedureRepository();


    public void generateReport(String jrxmlFilePath, String outputFileName, List<Libro> libros) {
        try {

            // Verificar si el archivo existe
            if (!Files.exists(Paths.get(jrxmlFilePath))) {
                JOptionPane.showMessageDialog(null, "El archivo JRXML no se encontró en la ruta especificada.");
                return;
            }

            // Verificar si el archivo es legible
            if (!Files.isReadable(Paths.get(jrxmlFilePath))) {
                JOptionPane.showMessageDialog(null, "El archivo JRXML no es legible.");
                return;
            }

            // Convertir los objetos a un formato adecuado para el reporte
            List<Map<String, Object>> reportData = new ArrayList<>();
            for (Libro libro : libros) {
                Map<String, Object> item = new HashMap<>();
                item.put("isbn", libro.getIsbn());
                item.put("titulo", libro.getTitulo());
                item.put("autor", libro.getAutor().getNombre());
                item.put("stock", libro.getStock());
                reportData.add(item);
            }

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportData);

            // Cargar y compilar el archivo JRXML desde la ruta absoluta
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFilePath);

            // Rellenar el reporte con los datos y parámetros
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Obtener la ruta de la carpeta de descargas del usuario
            String userHome = System.getProperty("user.home");
            String downloadsDir = Paths.get(userHome, "Downloads").toString();
            String outputFilePath = Paths.get(downloadsDir, outputFileName).toString();

            // Exportar el reporte a un archivo PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputFilePath);

            JOptionPane.showMessageDialog(null, "Reporte generado exitosamente: " + outputFilePath);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + e.getMessage());
        }
    }

    public void generateLibrosSinStockReport(List<Libro> libros) {
        String jrxmlFilePath = "/Users/brandonluismenesessolorzano/Desktop/Integrador1-Biblioteca-Prestamos/src/main/resources/reports/librosSinStockReport.jrxml";
        generateReport(jrxmlFilePath, "LibrosSinStockReport.pdf", libros);
    }

    /*public void generateUsuariosAtrasadosReport(List<Usuario> usuarios) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ReportTitle", "Usuarios Atrasados");
        parameters.put("usuarios", new JRBeanCollectionDataSource(usuarios));
        generateReport("path/to/usuariosAtrasadosReport.jrxml", "path/to/output/UsuariosAtrasados.pdf", parameters);
    }

    public void generateFrecuenciaPrestamosReport(List<FrecuenciaPrestamo> frecuenciaPrestamos) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("frecuenciaPrestamos", new JRBeanCollectionDataSource(frecuenciaPrestamos));
        generateReport("path/to/FrecuenciaPrestamos.jrxml", "path/to/output/FrecuenciaPrestamos.pdf", parameters);
    }

    public void generateQuienesTienenLibroReport(int libroId, List<Usuario> usuariosConLibro) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("usuariosConLibro", new JRBeanCollectionDataSource(usuariosConLibro));
        generateReport("path/to/QuienesTienenLibro.jrxml", "path/to/output/QuienesTienenLibro.pdf", parameters);
    }*/


}