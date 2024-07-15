package com.utp.biblioteca.reports;

import com.utp.biblioteca.resources.modelo.Libro;
import com.utp.biblioteca.resources.modelo.Usuario;
import com.utp.biblioteca.resources.modelo.FrecuenciaPrestamo;
import com.utp.biblioteca.resources.modelo.UsuarioConLibro;
import com.utp.biblioteca.resources.modelo.dao.sp.StoredProcedureRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ReportTableUpdater {

    private final StoredProcedureRepository repository = new StoredProcedureRepository();

    public void updateLibrosSinStockTable(JTable table) {
        List<Libro> librosSinStock = repository.spLibrosSinStock();
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "ISBN", "Título", "Autor", "Imagen", "Descripción", "Stock"}, 0);
        for (Libro libro : librosSinStock) {
            model.addRow(new Object[]{libro.getLibro_id(), libro.getIsbn(), libro.getTitulo(), libro.getAutor().getNombre(), libro.getLink_imagen(), libro.getDescripcion(), libro.getStock()});
        }
        table.setModel(model);
    }

    public void updateUsuariosAtrasadosTable(JTable table) {
        List<Usuario> usuariosAtrasados = repository.spUsuariosAtrasados();
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Nombres", "Apellidos", "DNI", "Correo", "Rol", "Estado"}, 0);
        for (Usuario usuario : usuariosAtrasados) {
            model.addRow(new Object[]{usuario.getUsuario_id(), usuario.getNombres(), usuario.getApellidos(), usuario.getDni(), usuario.getCorreo(), usuario.getRol().getNombre(), usuario.isEstado()});
        }
        table.setModel(model);
    }

    public void updateFrecuenciaPrestamosTable(JTable table) {
        List<FrecuenciaPrestamo> frecuenciaPrestamos = repository.spFrecuenciaPrestamos();
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Título", "Frecuencia"}, 0);
        for (FrecuenciaPrestamo frecuenciaPrestamo : frecuenciaPrestamos) {
            model.addRow(new Object[]{frecuenciaPrestamo.getTitulo(), frecuenciaPrestamo.getFrecuencia()});
        }
        table.setModel(model);
    }

    public void updateQuienesTienenLibroTable(JTable table, int libroId) {
        List<UsuarioConLibro> usuariosConLibro = repository.spQuienesTienenLibro(libroId);
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Nombres", "Apellidos", "DNI", "Correo", "Título del Libro"}, 0);
        for (UsuarioConLibro usuario : usuariosConLibro) {
            model.addRow(new Object[]{usuario.getNombres(), usuario.getApellidos(), usuario.getDni(), usuario.getCorreo(), usuario.getTitulo()});
        }
        table.setModel(model);
    }
}
