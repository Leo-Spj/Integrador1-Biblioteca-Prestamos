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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReportGenerator {

    private final StoredProcedureRepository repository = new StoredProcedureRepository();


    public void generateReport(String jrxmlFilePath, String outputFileName, Map<String, Object> parameters, JRBeanCollectionDataSource dataSource) {
        try (InputStream jrxmlInputStream = getClass().getResourceAsStream(jrxmlFilePath)) {
            if (jrxmlInputStream == null) {
                JOptionPane.showMessageDialog(null, "El archivo JRXML no se encontró en la ruta especificada.");
                return;
            }

            // Cargar y compilar el archivo JRXML desde el stream
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlInputStream);

            // Rellenar el reporte con los datos y parámetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Obtener la ruta de la carpeta de descargas del usuario
            String userHome = System.getProperty("user.home");
            String downloadsDir = Paths.get(userHome, "Downloads").toString();

            // Obtener la fecha y hora actual
            String timeStamp = new SimpleDateFormat("dd-MM-yyyy_HH.mm.ss").format(new Date());
            String uniqueOutputFileName = outputFileName.replace(".pdf", "_" + timeStamp + ".pdf");

            String outputFilePath = Paths.get(downloadsDir, uniqueOutputFileName).toString();

            // Exportar el reporte a un archivo PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputFilePath);

            JOptionPane.showMessageDialog(null, "Reporte generado exitosamente: " + outputFilePath);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage());
        }
    }
    public void generateLibrosSinStockReport(List<Libro> libros) {
        String jrxmlFilePath = "/reports/librosSinStockReport.jrxml";

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
        String jrxmlFilePath = "/reports/usuariosAtrasadosReport.jrxml";

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
        String jrxmlFilePath = "/reports/frecuenciaPrestamosReport.jrxml";

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
        String jrxmlFilePath = "/reports/quienesTienenLibroReport.jrxml";

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