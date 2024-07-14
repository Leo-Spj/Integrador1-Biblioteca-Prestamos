package com.utp.biblioteca.reports;

import com.utp.biblioteca.resources.configuracion.Conexion;
import com.utp.biblioteca.resources.modelo.FrecuenciaPrestamo;
import com.utp.biblioteca.resources.modelo.Libro;
import com.utp.biblioteca.resources.modelo.Usuario;
import com.utp.biblioteca.resources.modelo.dao.sp.StoredProcedureRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportGenerator {

    private final StoredProcedureRepository repository = new StoredProcedureRepository();



    public void generateLibrosSinStockReport(List<Libro> libros) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ReportTitle", "Libros sin Stock");
        parameters.put("libros", new JRBeanCollectionDataSource(libros));
        generateReport("path/to/librosSinStockReport.jrxml", "path/to/output/LibrosSinStock.pdf", parameters);
    }

    public void generateUsuariosAtrasadosReport(List<Usuario> usuarios) {
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
    }

    public void generateReport(String jrxmlPath, String outputPdfPath, Map<String, Object> parameters) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlPath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputPdfPath);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}