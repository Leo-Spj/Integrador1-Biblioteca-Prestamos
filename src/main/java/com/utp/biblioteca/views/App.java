/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.utp.biblioteca.views;

import com.utp.biblioteca.reports.ReportGenerator;
import com.utp.biblioteca.reports.ReportTableUpdater;
import com.utp.biblioteca.resources.modelo.Libro;
import com.utp.biblioteca.resources.modelo.Prestamo;
import com.utp.biblioteca.resources.modelo.Usuario;
import com.utp.biblioteca.resources.modelo.FrecuenciaPrestamo;
import com.utp.biblioteca.resources.modelo.dao.FrecuenciaPrestamoDao;
import com.utp.biblioteca.resources.modelo.dao.LibroDao;
import com.utp.biblioteca.resources.modelo.dao.PrestamoDao;
import com.utp.biblioteca.resources.modelo.dao.RolDao;
import com.utp.biblioteca.resources.modelo.dao.UsuarioDao;
import com.utp.biblioteca.resources.modelo.dao.sp.StoredProcedureRepository;
import com.utp.biblioteca.resources.servicio.AvisosEmail;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 *
 * @author Leo
 */
public class App extends javax.swing.JFrame {

    private DefaultTableModel model_librosPrestamos;
    private DefaultTableModel model_librosSinStock;
    private DefaultTableModel model_usuariosAtrasados;
    private DefaultTableModel model_frecuenciaPrestamos;
    private DefaultTableModel model_quienesTienenElLibro;
    /**
     * Creates new form NewJFrame
     */
    public App() {
        initComponents();
        iniciandoTablaPrestamos();
        cargarTablasReportes();
    }
    private final ReportTableUpdater reportTableUpdater = new ReportTableUpdater();
    private final StoredProcedureRepository repository = new StoredProcedureRepository();
    private final ReportGenerator reportGenerator = new ReportGenerator();

    public void iniciandoTablaPrestamos(){
        model_librosPrestamos = (DefaultTableModel) tbl_librosBusqueda_prestamos.getModel();
        LibroDao libroDao = new LibroDao();
        List<Libro> libros = libroDao.buscarTodos();
        model_librosPrestamos.setRowCount(0);
        for (Libro libro : libros) {
            Object[] row = new Object[]{
                    libro.getLibro_id(),
                    libro.getIsbn(),
                    libro.getTitulo(),
                    libro.getAutor().getNombre(),
                    libro.getStock()
            };
            model_librosPrestamos.addRow(row);
        }
    }
    
    private void cargarTablasReportes() {
        reportTableUpdater.updateLibrosSinStockTable(tbl_librosSinStock_reportes);
        reportTableUpdater.updateUsuariosAtrasadosTable(tbl_usuariosAtrasados_reportes);
        reportTableUpdater.updateFrecuenciaPrestamosTable(tbl_recuenciaPrestamos_reportes);
        int libroIdPredeterminado = 1;
        reportTableUpdater.updateQuienesTienenLibroTable(tbl_quienesTienenElLibro_reportes1, libroIdPredeterminado);
    }
    public void updateLibrosSinStockTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        LibroDao libroDao = new LibroDao();
        List<Libro> librosSinStock = libroDao.buscarLibrosSinStock();
        model.setRowCount(0);
        for (Libro libro : librosSinStock) {
            Object[] row = new Object[]{
                libro.getLibro_id(),
                libro.getIsbn(),
                libro.getTitulo(),
                libro.getAutor().getNombre(),
                libro.getStock()
            };
            model.addRow(row);
        }
        // Llamada para generar el reporte
        reportGenerator.generateLibrosSinStockReport(librosSinStock);
    }
    public void updateUsuariosAtrasadosTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        UsuarioDao usuarioDao = new UsuarioDao();
        List<Usuario> usuariosAtrasados = usuarioDao.buscarUsuariosAtrasados();
        model.setRowCount(0);
        for (Usuario usuario : usuariosAtrasados) {
            Object[] row = new Object[]{
                usuario.getUsuario_id(),
                usuario.getNombres(),
                usuario.getApellidos(),
                usuario.getDni(),
                usuario.isEstado()
            };
            model.addRow(row);
        }
        reportGenerator.generateUsuariosAtrasadosReport(usuariosAtrasados);
    }

    public void updateFrecuenciaPrestamosTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        FrecuenciaPrestamoDao frecuenciaPrestamoDao = new FrecuenciaPrestamoDao();
        List<FrecuenciaPrestamo> frecuenciaPrestamos = frecuenciaPrestamoDao.spFrecuenciaPrestamos();
        model.setRowCount(0);
        for (FrecuenciaPrestamo fp : frecuenciaPrestamos) {
            Object[] row = new Object[]{
                fp.getTitulo(),
                fp.getFrecuencia()
            };
            model.addRow(row);
        }
        //reportGenerator.generateFrecuenciaPrestamosReport(frecuenciaPrestamos);
    }

    public void updateQuienesTienenLibroTable(JTable table, int libroId) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        UsuarioDao usuarioDao = new UsuarioDao();
        List<Usuario> usuariosConLibro = usuarioDao.spQuienesTienenLibro(libroId);
        model.setRowCount(0);
        for (Usuario usuario : usuariosConLibro) {
            Object[] row = new Object[]{
                usuario.getUsuario_id(),
                usuario.getNombres(),
                usuario.getApellidos(),
                usuario.getDni()
            };
            model.addRow(row);
        }
        //reportGenerator.generateQuienesTienenLibroReport(libroId, usuariosConLibro);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pestañas = new javax.swing.JTabbedPane();
        panel_prestamos = new javax.swing.JPanel();
        panelLibros = new javax.swing.JPanel();
        Lab_Libros = new javax.swing.JLabel();
        txtF_barraBusquedaLibros_prestamos = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_librosBusqueda_prestamos = new javax.swing.JTable();
        cbx_buscarAtributo = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtF_buscarDNI_prestamo = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btn_realizarPrestamo = new javax.swing.JButton();
        txtF_idLibro_prestamo = new javax.swing.JTextField();
        txtF_dni_prestamo = new javax.swing.JTextField();
        txtF_diasAprestar_prestamo = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_usuarioEncontrado_prestamo = new javax.swing.JTable();
        panel_devoluciones = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtF_buscarDNI_devolucion = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_usuarioEncontrado_devolucion = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_historialUsuario_devolucion = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        panel_registarUsuario = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtF_dni_registroUsuario = new javax.swing.JTextField();
        txtF_nombre_registroUsuario = new javax.swing.JTextField();
        txtxF_apellido_registroUsuario = new javax.swing.JTextField();
        txtF_corro_registroUsuario = new javax.swing.JTextField();
        btn_crearUsuario_registroUsuario = new javax.swing.JButton();
        panel_reportes = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbl_librosSinStock_reportes = new javax.swing.JTable();
        btn_usuariosAtrasados_reportes = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbl_usuariosAtrasados_reportes = new javax.swing.JTable();
        btn_descargarLibrosSinStock_reportes = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tbl_recuenciaPrestamos_reportes = new javax.swing.JTable();
        btn_descargarFrecuenciaPrestamos_reportes = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        tbl_quienesTienenElLibro_reportes1 = new javax.swing.JTable();
        btn_quienesTienenElLibro_reportes1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Lab_Libros.setText("Libros");

        txtF_barraBusquedaLibros_prestamos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtF_barraBusquedaLibros_prestamosKeyPressed(evt);
            }
        });

        tbl_librosBusqueda_prestamos.setAutoCreateRowSorter(true);
        tbl_librosBusqueda_prestamos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "ISBN", "Titulo", "Autor", "Stock"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_librosBusqueda_prestamos.getTableHeader().setReorderingAllowed(false);
        tbl_librosBusqueda_prestamos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_librosBusqueda_prestamosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_librosBusqueda_prestamos);
        if (tbl_librosBusqueda_prestamos.getColumnModel().getColumnCount() > 0) {
            tbl_librosBusqueda_prestamos.getColumnModel().getColumn(0).setPreferredWidth(16);
            tbl_librosBusqueda_prestamos.getColumnModel().getColumn(4).setPreferredWidth(36);
            tbl_librosBusqueda_prestamos.getColumnModel().getColumn(4).setMaxWidth(36);
        }

        cbx_buscarAtributo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Titulo", "Autor", "ISBN" }));

        javax.swing.GroupLayout panelLibrosLayout = new javax.swing.GroupLayout(panelLibros);
        panelLibros.setLayout(panelLibrosLayout);
        panelLibrosLayout.setHorizontalGroup(
            panelLibrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLibrosLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(panelLibrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelLibrosLayout.createSequentialGroup()
                        .addComponent(cbx_buscarAtributo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtF_barraBusquedaLibros_prestamos))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Lab_Libros, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        panelLibrosLayout.setVerticalGroup(
            panelLibrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLibrosLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(Lab_Libros)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLibrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtF_barraBusquedaLibros_prestamos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_buscarAtributo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jLabel2.setText("Usuario DNI");

        txtF_buscarDNI_prestamo.setToolTipText("");
        txtF_buscarDNI_prestamo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtF_buscarDNI_prestamoKeyPressed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setText("Prestamo");

        jLabel5.setText("ID Libro");

        jLabel6.setText("DNI");

        jLabel8.setText("Días");

        btn_realizarPrestamo.setText("Realizar Prestamo");
        btn_realizarPrestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_realizarPrestamoActionPerformed(evt);
            }
        });

        txtF_diasAprestar_prestamo.setText("7");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btn_realizarPrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6)
                                .addComponent(jLabel8))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtF_idLibro_prestamo)
                                .addComponent(txtF_dni_prestamo)
                                .addComponent(txtF_diasAprestar_prestamo, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(25, 25, 25)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtF_idLibro_prestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtF_dni_prestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtF_diasAprestar_prestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(29, 29, 29)
                .addComponent(btn_realizarPrestamo)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        tbl_usuarioEncontrado_prestamo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "DNI", "Nombre", "Apellidos", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_usuarioEncontrado_prestamo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_usuarioEncontrado_prestamoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_usuarioEncontrado_prestamo);
        if (tbl_usuarioEncontrado_prestamo.getColumnModel().getColumnCount() > 0) {
            tbl_usuarioEncontrado_prestamo.getColumnModel().getColumn(3).setPreferredWidth(64);
            tbl_usuarioEncontrado_prestamo.getColumnModel().getColumn(3).setMaxWidth(64);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtF_buscarDNI_prestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(15, 15, 15))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(160, 160, 160))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtF_buscarDNI_prestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_prestamosLayout = new javax.swing.GroupLayout(panel_prestamos);
        panel_prestamos.setLayout(panel_prestamosLayout);
        panel_prestamosLayout.setHorizontalGroup(
            panel_prestamosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_prestamosLayout.createSequentialGroup()
                .addComponent(panelLibros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_prestamosLayout.setVerticalGroup(
            panel_prestamosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelLibros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pestañas.addTab("Prestamos", panel_prestamos);

        jLabel9.setText("Usuario");

        jLabel10.setText("DNI");

        txtF_buscarDNI_devolucion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtF_buscarDNI_devolucionKeyPressed(evt);
            }
        });

        tbl_usuarioEncontrado_devolucion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "DNI", "Nombre", "Apellido", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_usuarioEncontrado_devolucion.setPreferredSize(new java.awt.Dimension(273, 80));
        tbl_usuarioEncontrado_devolucion.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(tbl_usuarioEncontrado_devolucion);
        if (tbl_usuarioEncontrado_devolucion.getColumnModel().getColumnCount() > 0) {
            tbl_usuarioEncontrado_devolucion.getColumnModel().getColumn(3).setPreferredWidth(64);
            tbl_usuarioEncontrado_devolucion.getColumnModel().getColumn(3).setMaxWidth(64);
        }

        tbl_historialUsuario_devolucion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID ", "Titulo", "Limite", "Devolucion", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_historialUsuario_devolucion.getTableHeader().setReorderingAllowed(false);
        tbl_historialUsuario_devolucion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_historialUsuario_devolucionMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbl_historialUsuario_devolucion);
        if (tbl_historialUsuario_devolucion.getColumnModel().getColumnCount() > 0) {
            tbl_historialUsuario_devolucion.getColumnModel().getColumn(0).setPreferredWidth(24);
            tbl_historialUsuario_devolucion.getColumnModel().getColumn(0).setMaxWidth(24);
            tbl_historialUsuario_devolucion.getColumnModel().getColumn(4).setPreferredWidth(64);
            tbl_historialUsuario_devolucion.getColumnModel().getColumn(4).setMaxWidth(64);
        }

        jLabel11.setText("Historial del usuario");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(33, 33, 33)
                        .addComponent(txtF_buscarDNI_devolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4)
                    .addComponent(jScrollPane3))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtF_buscarDNI_devolucion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_devolucionesLayout = new javax.swing.GroupLayout(panel_devoluciones);
        panel_devoluciones.setLayout(panel_devolucionesLayout);
        panel_devolucionesLayout.setHorizontalGroup(
            panel_devolucionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_devolucionesLayout.createSequentialGroup()
                .addGap(250, 250, 250)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(258, Short.MAX_VALUE))
        );
        panel_devolucionesLayout.setVerticalGroup(
            panel_devolucionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pestañas.addTab("Devoluciones", panel_devoluciones);

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel16.setText("Registrar Usuario");

        jLabel17.setText("DNI");

        jLabel18.setText("Nombre");

        jLabel19.setText("Apellido");

        jLabel20.setText("Correo");

        btn_crearUsuario_registroUsuario.setText("Crear Usuario");
        btn_crearUsuario_registroUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crearUsuario_registroUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                    .addGap(76, 76, 76)
                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(76, 76, 76)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtF_dni_registroUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btn_crearUsuario_registroUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                                .addComponent(txtF_nombre_registroUsuario)
                                .addComponent(txtxF_apellido_registroUsuario)
                                .addComponent(txtF_corro_registroUsuario))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(127, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addGap(35, 35, 35)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtF_dni_registroUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtF_nombre_registroUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtxF_apellido_registroUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtF_corro_registroUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(btn_crearUsuario_registroUsuario)
                .addGap(26, 26, 26))
        );

        javax.swing.GroupLayout panel_registarUsuarioLayout = new javax.swing.GroupLayout(panel_registarUsuario);
        panel_registarUsuario.setLayout(panel_registarUsuarioLayout);
        panel_registarUsuarioLayout.setHorizontalGroup(
            panel_registarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_registarUsuarioLayout.createSequentialGroup()
                .addContainerGap(285, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(258, 258, 258))
        );
        panel_registarUsuarioLayout.setVerticalGroup(
            panel_registarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_registarUsuarioLayout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(121, Short.MAX_VALUE))
        );

        pestañas.addTab("Registrar Usuario", panel_registarUsuario);

        jLabel21.setText("Libros sin Stock");

        tbl_librosSinStock_reportes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(tbl_librosSinStock_reportes);

        btn_usuariosAtrasados_reportes.setText("Descargar");
        btn_usuariosAtrasados_reportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_usuariosAtrasados_reportesActionPerformed(evt);
            }
        });

        jLabel22.setText("Usuarios atrasados:");

        tbl_usuariosAtrasados_reportes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(tbl_usuariosAtrasados_reportes);

        btn_descargarLibrosSinStock_reportes.setText("Descargar");
        btn_descargarLibrosSinStock_reportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_descargarLibrosSinStock_reportesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane5)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(184, 184, 184)
                        .addComponent(btn_descargarLibrosSinStock_reportes, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(182, 182, 182)
                        .addComponent(btn_usuariosAtrasados_reportes, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_descargarLibrosSinStock_reportes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_usuariosAtrasados_reportes)
                .addGap(33, 33, 33))
        );

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel23.setText("Frecuencia de prestamos");

        tbl_recuenciaPrestamos_reportes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane7.setViewportView(tbl_recuenciaPrestamos_reportes);

        btn_descargarFrecuenciaPrestamos_reportes.setText("Descargar");
        btn_descargarFrecuenciaPrestamos_reportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_descargarFrecuenciaPrestamos_reportesActionPerformed(evt);
            }
        });

        jLabel1.setText("Quienes tiene el libro?:");

        tbl_quienesTienenElLibro_reportes1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane8.setViewportView(tbl_quienesTienenElLibro_reportes1);

        btn_quienesTienenElLibro_reportes1.setText("Descargar");
        btn_quienesTienenElLibro_reportes1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_quienesTienenElLibro_reportes1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                                        .addGap(192, 192, 192)
                                        .addComponent(btn_descargarFrecuenciaPrestamos_reportes)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_quienesTienenElLibro_reportes1)
                        .addGap(197, 197, 197))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_descargarFrecuenciaPrestamos_reportes)
                .addGap(34, 34, 34)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_quienesTienenElLibro_reportes1)
                .addGap(39, 39, 39))
        );

        javax.swing.GroupLayout panel_reportesLayout = new javax.swing.GroupLayout(panel_reportes);
        panel_reportes.setLayout(panel_reportesLayout);
        panel_reportesLayout.setHorizontalGroup(
            panel_reportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_reportesLayout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panel_reportesLayout.setVerticalGroup(
            panel_reportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pestañas.addTab("Reportes", panel_reportes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pestañas, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pestañas, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtF_barraBusquedaLibros_prestamosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtF_barraBusquedaLibros_prestamosKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            String seleccionCbx = cbx_buscarAtributo.getSelectedItem().toString();
            if ("Titulo".equals(seleccionCbx)) {
                String tituloBusqueda = txtF_barraBusquedaLibros_prestamos.getText();
                LibroDao libroDao = new LibroDao();
                List<Libro> libros = libroDao.buscarPorTitulo(tituloBusqueda);

                model_librosPrestamos.setRowCount(0);
                for (Libro libro : libros) {
                    Object[] row = new Object[]{
                        libro.getLibro_id(),
                        libro.getIsbn(),
                        libro.getTitulo(),
                        libro.getAutor().getNombre(),
                        libro.getStock()
                    };
                    model_librosPrestamos.addRow(row);
                }
            }
        }
    }//GEN-LAST:event_txtF_barraBusquedaLibros_prestamosKeyPressed

    private void txtF_buscarDNI_prestamoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtF_buscarDNI_prestamoKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            String dni = txtF_buscarDNI_prestamo.getText();

            //envio dni al panel de prestamos para ver el historial
            txtF_buscarDNI_devolucion.setText(dni + "");
            actualizarTablas_devoluciones();

            if (dni.isEmpty()) {
                return;
            }
            UsuarioDao usuarioDao = new UsuarioDao();
            Usuario usuario = usuarioDao.buscarPorDni(Integer.parseInt(dni));

            DefaultTableModel model = (DefaultTableModel) tbl_usuarioEncontrado_prestamo.getModel();
            model.setRowCount(0);
            Object[] row = new Object[]{
                usuario.getDni(),
                usuario.getNombres(),
                usuario.getApellidos(),
                usuario.isEstado()
            };
            model.addRow(row);

            //si no se encuentra el usuario alertar
            if (usuario.getDni() == 0) {
                JOptionPane.showMessageDialog(null, "Usuario no encontrado");
                return;
            }

            if(usuario.isEstado()){
                txtF_dni_prestamo.setText(dni);
            } else {
                txtF_dni_prestamo.setText("");
                JOptionPane.showMessageDialog(null, "El usuario no puede realizar prestamos");
            }

            if (usuario.getDni() == 0) {
                model.setRowCount(0);
                txtF_dni_prestamo.setText("");
            }
        }
    }//GEN-LAST:event_txtF_buscarDNI_prestamoKeyPressed

    private void tbl_usuarioEncontrado_prestamoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_usuarioEncontrado_prestamoMouseClicked
        int row = tbl_usuarioEncontrado_prestamo.getSelectedRow();
        int dni = (int) tbl_usuarioEncontrado_prestamo.getValueAt(row, 0);
        boolean estado = (boolean) tbl_usuarioEncontrado_prestamo.getValueAt(row, 3);
        if(!estado){
            JOptionPane.showMessageDialog(null, "El usuario no puede realizar prestamos");
            return;
        }
        txtF_dni_prestamo.setText(dni + "");
    }//GEN-LAST:event_tbl_usuarioEncontrado_prestamoMouseClicked

    private void tbl_librosBusqueda_prestamosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_librosBusqueda_prestamosMouseClicked
        int row = tbl_librosBusqueda_prestamos.getSelectedRow();
        txtF_idLibro_prestamo.setText(tbl_librosBusqueda_prestamos.getValueAt(row, 0).toString());
    }//GEN-LAST:event_tbl_librosBusqueda_prestamosMouseClicked

    private void txtF_buscarDNI_devolucionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtF_buscarDNI_devolucionKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            String dni = txtF_buscarDNI_devolucion.getText();

            DefaultTableModel tablaUsuario = (DefaultTableModel) tbl_usuarioEncontrado_devolucion.getModel();
            DefaultTableModel tablaHistorial = (DefaultTableModel) tbl_historialUsuario_devolucion.getModel();

            if (dni.isEmpty()) {
                tablaUsuario.setRowCount(0);
                tablaHistorial.setRowCount(0);
                return;
            }

            UsuarioDao usuarioDao = new UsuarioDao();
            Usuario usuario = usuarioDao.buscarPorDni(Integer.parseInt(dni));

            if (usuario.getDni() == 0) {
                tablaUsuario.setRowCount(0);
                tablaHistorial.setRowCount(0);
                return;
            }

            actualizarTablas_devoluciones();

        }

    }//GEN-LAST:event_txtF_buscarDNI_devolucionKeyPressed

    private void btn_crearUsuario_registroUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crearUsuario_registroUsuarioActionPerformed

        int dni = Integer.parseInt(txtF_dni_registroUsuario.getText());
        String nombre = txtF_nombre_registroUsuario.getText();
        String apellido = txtxF_apellido_registroUsuario.getText();
        String correo = txtF_corro_registroUsuario.getText();

        Usuario usuario = new Usuario();
        usuario.setDni(dni);
        usuario.setNombres(nombre);
        usuario.setApellidos(apellido);
        usuario.setCorreo(correo);
        usuario.setEstado(true);

        RolDao rol = new RolDao();
        usuario.setRol(rol.buscarUno(2)); // por defecto será Usuario


        UsuarioDao usuarioDao = new UsuarioDao();
        try {
            usuarioDao.crear(usuario);
            JOptionPane.showMessageDialog(null, "Usuario creado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al crear el usuario: " + e.getMessage());
        }
    }//GEN-LAST:event_btn_crearUsuario_registroUsuarioActionPerformed

    private void btn_descargarLibrosSinStock_reportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_descargarLibrosSinStock_reportesActionPerformed
        List<Libro> librosSinStock = repository.spLibrosSinStock();
        reportTableUpdater.updateLibrosSinStockTable(tbl_librosSinStock_reportes);
        reportGenerator.generateLibrosSinStockReport(librosSinStock);
        JOptionPane.showMessageDialog(this, "Reporte de Libros sin Stock generado y guardado en output/LibrosSinStock.pdf.");
    }//GEN-LAST:event_btn_descargarLibrosSinStock_reportesActionPerformed

    private void btn_usuariosAtrasados_reportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_usuariosAtrasados_reportesActionPerformed
        List<Usuario> usuariosAtrasados = repository.spUsuariosAtrasados();
        reportTableUpdater.updateUsuariosAtrasadosTable(tbl_usuariosAtrasados_reportes);
        reportGenerator.generateUsuariosAtrasadosReport(usuariosAtrasados);
        JOptionPane.showMessageDialog(this, "Reporte de Usuarios Atrasados generado.");
    }//GEN-LAST:event_btn_usuariosAtrasados_reportesActionPerformed

    private void btn_descargarFrecuenciaPrestamos_reportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_descargarFrecuenciaPrestamos_reportesActionPerformed
        reportTableUpdater.updateFrecuenciaPrestamosTable(tbl_recuenciaPrestamos_reportes);
        JOptionPane.showMessageDialog(this, "Reporte de Frecuencia de Préstamos generado.");
    }//GEN-LAST:event_btn_descargarFrecuenciaPrestamos_reportesActionPerformed

    private void btn_quienesTienenElLibro_reportes1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_quienesTienenElLibro_reportes1ActionPerformed
        int libroId = Integer.parseInt(jTextField1.getText());
        reportTableUpdater.updateQuienesTienenLibroTable(tbl_quienesTienenElLibro_reportes1, libroId);
        JOptionPane.showMessageDialog(this, "Reporte de Quiénes Tienen el Libro generado.");
    }//GEN-LAST:event_btn_quienesTienenElLibro_reportes1ActionPerformed

    private void tbl_historialUsuario_devolucionMouseClicked(java.awt.event.MouseEvent evt) {
        int row = tbl_historialUsuario_devolucion.getSelectedRow();
        int prestamoId = (int) tbl_historialUsuario_devolucion.getValueAt(row, 0);
        boolean estado = (boolean) tbl_historialUsuario_devolucion.getValueAt(row, 4);
        if (estado) {
            JOptionPane.showMessageDialog(null, "El libro ya fue devuelto");
            return;
        }
        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Desea confirmar la devolución del libro?");
        if (confirmacion == 0) {
            try {
                StoredProcedureRepository sp = new StoredProcedureRepository();
                sp.spDevolverLibro(prestamoId);
                JOptionPane.showMessageDialog(null, "Libro devuelto");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al devolver el libro: " + e.getMessage());
            } finally {
                iniciandoTablaPrestamos();
                actualizarTablas_devoluciones();
            }
        }
    }

    private void actualizarTablas_devoluciones() {
        String dni = txtF_buscarDNI_devolucion.getText();

        DefaultTableModel tablaHistorial = (DefaultTableModel) tbl_historialUsuario_devolucion.getModel();
        DefaultTableModel tablaUsuario = (DefaultTableModel) tbl_usuarioEncontrado_devolucion.getModel();
        if (!dni.isEmpty()) {

            try {
                //usuario:
                UsuarioDao usuarioDao = new UsuarioDao();
                Usuario usuario = usuarioDao.buscarPorDni(Integer.parseInt(dni));
                tablaUsuario.setRowCount(0);
                Object[] row = new Object[]{
                        usuario.getDni(),
                        usuario.getNombres(),
                        usuario.getApellidos(),
                        usuario.isEstado()
                };
                tablaUsuario.addRow(row);

                //historial:
                tablaHistorial.setRowCount(0);
                List<Prestamo> prestamos = new PrestamoDao().historialUsuarioDni(Integer.parseInt(dni));
                for (Prestamo prestamo : prestamos) {
                    Object[] rowPrestamo = new Object[]{
                        prestamo.getPrestamo_id(),
                        prestamo.getLibro().getTitulo(),
                        prestamo.getFecha_limite(),
                        prestamo.getFecha_devolucion(),
                        prestamo.isDevuelto()
                    };
                    tablaHistorial.addRow(rowPrestamo);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "DNI inválido: " + dni);
            }

        }
    }                                                            

    private void btn_realizarPrestamoActionPerformed(java.awt.event.ActionEvent evt) {
        StoredProcedureRepository sp = new StoredProcedureRepository();

        int dni = Integer.parseInt(txtF_dni_prestamo.getText());
        int libroId = Integer.parseInt(txtF_idLibro_prestamo.getText());
        int dias = Integer.parseInt(txtF_diasAprestar_prestamo.getText());

        String mensaje = sp.spRealizarPrestamo(dni, libroId, dias);
        if (mensaje.startsWith("Préstamo")) {

            CompletableFuture.supplyAsync(() -> {
                System.out.println("Entró al hilo - Intentando enviar correo...");

                PrestamoDao prestamoDao = new PrestamoDao();
                Prestamo prestamo = prestamoDao.ultimoPrestamoUsuarioDni(dni);

                AvisosEmail avisosEmail = new AvisosEmail();
                String cuerpoCorreo = "<html>" +
                        "<body>" +
                        "<h1>Prestamo Realizado en Biblioteca Pueblo Libre</h1>" +
                        "<p>Se ha realizado un préstamo del libro: <strong>" + prestamo.getLibro().getTitulo() + "</strong></p>" +
                        "<p>No olvide devolverlo antes de la fecha límite: <strong>" + prestamo.getFecha_limite() + "</strong></p>" +
                        "</body>" +
                        "</html>";

                // coincidencia de correo regex
                if (prestamo.getUsuario().getCorreo() != null && prestamo.getUsuario().getCorreo().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {

                    avisosEmail.enviarCorreo(prestamo.getUsuario().getCorreo(), "Prestamo Realizado en Biblioteca Pueblo Libre", cuerpoCorreo);
                } else {
                    /*SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            JOptionPane.showMessageDialog(null, "El usuario no tiene un correo válido");
                        }
                    });*/
                    System.out.println("El usuario no tiene un correo válido");
                }
                iniciandoTablaPrestamos();
                return null;
            });

            JOptionPane.showMessageDialog(this, mensaje);

        } else {
            JOptionPane.showMessageDialog(this, mensaje);
        }

        txtF_dni_prestamo.setText("");
        txtF_idLibro_prestamo.setText("");
        txtF_diasAprestar_prestamo.setText("");
    }                                                    

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new App().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Lab_Libros;
    private javax.swing.JButton btn_crearUsuario_registroUsuario;
    private javax.swing.JButton btn_descargarFrecuenciaPrestamos_reportes;
    private javax.swing.JButton btn_descargarLibrosSinStock_reportes;
    private javax.swing.JButton btn_quienesTienenElLibro_reportes1;
    private javax.swing.JButton btn_realizarPrestamo;
    private javax.swing.JButton btn_usuariosAtrasados_reportes;
    private javax.swing.JComboBox<String> cbx_buscarAtributo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel panelLibros;
    private javax.swing.JPanel panel_devoluciones;
    private javax.swing.JPanel panel_prestamos;
    private javax.swing.JPanel panel_registarUsuario;
    private javax.swing.JPanel panel_reportes;
    private javax.swing.JTabbedPane pestañas;
    private javax.swing.JTable tbl_historialUsuario_devolucion;
    private javax.swing.JTable tbl_librosBusqueda_prestamos;
    private javax.swing.JTable tbl_librosSinStock_reportes;
    private javax.swing.JTable tbl_quienesTienenElLibro_reportes1;
    private javax.swing.JTable tbl_recuenciaPrestamos_reportes;
    private javax.swing.JTable tbl_usuarioEncontrado_devolucion;
    private javax.swing.JTable tbl_usuarioEncontrado_prestamo;
    private javax.swing.JTable tbl_usuariosAtrasados_reportes;
    private javax.swing.JTextField txtF_barraBusquedaLibros_prestamos;
    private javax.swing.JTextField txtF_buscarDNI_devolucion;
    private javax.swing.JTextField txtF_buscarDNI_prestamo;
    private javax.swing.JTextField txtF_corro_registroUsuario;
    private javax.swing.JTextField txtF_diasAprestar_prestamo;
    private javax.swing.JTextField txtF_dni_prestamo;
    private javax.swing.JTextField txtF_dni_registroUsuario;
    private javax.swing.JTextField txtF_idLibro_prestamo;
    private javax.swing.JTextField txtF_nombre_registroUsuario;
    private javax.swing.JTextField txtxF_apellido_registroUsuario;
    // End of variables declaration//GEN-END:variables
}
