package com.utp.biblioteca.reports;

import com.utp.biblioteca.resources.configuracion.Conexion;
import com.utp.biblioteca.resources.modelo.FrecuenciaPrestamo;
import com.utp.biblioteca.resources.modelo.Libro;
import com.utp.biblioteca.resources.modelo.Usuario;
import com.utp.biblioteca.resources.modelo.dao.sp.StoredProcedureRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportGenerator {

    private final StoredProcedureRepository repository = new StoredProcedureRepository();

    public void generateReport(String reportPath, String outputPath, Map<String, Object> parameters) {
        try (Connection connection = Conexion.getConnection()) {
            JasperReport jasperReport;
            if (reportPath.endsWith(".jrxml")) {
                jasperReport = JasperCompileManager.compileReport(reportPath);
            } else {
                jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportPath);
            }

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);
            System.out.println("Report generated successfully: " + outputPath);

        } catch (SQLException | JRException e) {
            e.printStackTrace();
        }
    }

    public void generateLibrosSinStockReport() {
        List<Libro> librosSinStock = repository.spLibrosSinStock();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("librosSinStock", new JRBeanCollectionDataSource(librosSinStock));
        generateReport("path/to/LibrosSinStock.jrxml", "path/to/output/LibrosSinStock.pdf", parameters);
    }

    public void generateUsuariosAtrasadosReport() {
        List<Usuario> usuariosAtrasados = repository.spUsuariosAtrasados();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("usuariosAtrasados", new JRBeanCollectionDataSource(usuariosAtrasados));
        generateReport("path/to/UsuariosAtrasados.jrxml", "path/to/output/UsuariosAtrasados.pdf", parameters);
    }

    public void generateFrecuenciaPrestamosReport() {
        List<FrecuenciaPrestamo> frecuenciaPrestamos = repository.spFrecuenciaPrestamos();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("frecuenciaPrestamos", new JRBeanCollectionDataSource(frecuenciaPrestamos));
        generateReport("path/to/FrecuenciaPrestamos.jrxml", "path/to/output/FrecuenciaPrestamos.pdf", parameters);
    }

    public void generateQuienesTienenLibroReport(int libroId) {
        List<Usuario> usuariosConLibro = repository.spQuienesTienenLibro(libroId);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("usuariosConLibro", new JRBeanCollectionDataSource(usuariosConLibro));
        generateReport("path/to/QuienesTienenLibro.jrxml", "path/to/output/QuienesTienenLibro.pdf", parameters);
    }
}