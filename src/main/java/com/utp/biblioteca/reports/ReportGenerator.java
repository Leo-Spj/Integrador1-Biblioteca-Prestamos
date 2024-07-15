package com.utp.biblioteca.reports;

import com.utp.biblioteca.resources.configuracion.Conexion;
import com.utp.biblioteca.resources.modelo.FrecuenciaPrestamo;
import com.utp.biblioteca.resources.modelo.Libro;
import com.utp.biblioteca.resources.modelo.Usuario;
import com.utp.biblioteca.resources.modelo.UsuarioConLibro;
import com.utp.biblioteca.resources.modelo.dao.UsuarioDao;
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


    public void generateReport(String jrxmlFilePath, String outputFileName, Map<String, Object> parameters, JRBeanCollectionDataSource dataSource) {
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

            // Cargar y compilar el archivo JRXML desde la ruta absoluta
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFilePath);

            // Rellenar el reporte con los datos y parámetros
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
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ReportTitle", "Libros Sin Stock");

        generateReport(jrxmlFilePath, "LibrosSinStockReport.pdf", parameters, dataSource);
    }

    public void generateUsuariosAtrasadosReport(List<Usuario> usuarios) {
        String jrxmlFilePath = "/Users/brandonluismenesessolorzano/Desktop/Integrador1-Biblioteca-Prestamos/src/main/resources/reports/usuariosAtrasadosReport.jrxml";

        // Convertir los objetos a un formato adecuado para el reporte
        List<Map<String, Object>> reportData = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            Map<String, Object> item = new HashMap<>();
            item.put("usuario_id", usuario.getUsuario_id());
            item.put("nombres", usuario.getNombres());
            item.put("apellidos", usuario.getApellidos());
            item.put("dni", usuario.getDni());
            item.put("correo", usuario.getCorreo());
            item.put("estado", usuario.isEstado());
            reportData.add(item);
        }

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportData);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ReportTitle", "Usuarios Atrasados");

        generateReport(jrxmlFilePath, "UsuariosAtrasadosReport.pdf", parameters, dataSource);
    }


    public void generateFrecuenciaPrestamosReport(List<FrecuenciaPrestamo> frecuenciaPrestamos) {
        String jrxmlFilePath = "/Users/brandonluismenesessolorzano/Desktop/Integrador1-Biblioteca-Prestamos/src/main/resources/reports/frecuenciaPrestamosReport.jrxml";

        // Convertir los objetos a un formato adecuado para el reporte
        List<Map<String, Object>> reportData = new ArrayList<>();
        for (FrecuenciaPrestamo frecuenciaPrestamo : frecuenciaPrestamos) {
            Map<String, Object> item = new HashMap<>();
            item.put("titulo", frecuenciaPrestamo.getTitulo());
            item.put("frecuencia", frecuenciaPrestamo.getFrecuencia());
            reportData.add(item);
        }

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportData);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ReportTitle", "Frecuencia de Préstamos");

        generateReport(jrxmlFilePath, "FrecuenciaPrestamosReport.pdf", parameters, dataSource);
    }

    public void generateQuienesTienenLibroReport(List<UsuarioConLibro> usuarios, int libroId) {
        String jrxmlFilePath = "/Users/brandonluismenesessolorzano/Desktop/Integrador1-Biblioteca-Prestamos/src/main/resources/reports/quienesTienenLibroReport.jrxml";

        // Convertir los objetos a un formato adecuado para el reporte
        List<Map<String, Object>> reportData = new ArrayList<>();
        for (UsuarioConLibro usuario : usuarios) {
            Map<String, Object> item = new HashMap<>();
            item.put("nombres", usuario.getNombres());
            item.put("apellidos", usuario.getApellidos());
            item.put("dni", usuario.getDni());
            item.put("correo", usuario.getCorreo());
            item.put("titulo", usuario.getTitulo());
            reportData.add(item);
        }

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportData);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ReportTitle", "Usuarios con el Libro: " + libroId);

        generateReport(jrxmlFilePath, "QuienesTienenLibroReport.pdf", parameters, dataSource);
    }


}