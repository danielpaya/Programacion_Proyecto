import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MedicinaGeneral extends JFrame {
    private JTextField idField, nombreField, apellidoField, documentoField, epsField;
    private JTextField frecuenciaField, sistolicaField, diastolicaField, temperaturaField, saturacionField;
    private JTextArea motivoConsultaArea, observacionesArea;
    private JTable triageTable, consultaTable;
    private DefaultTableModel triageTableModel, consultaTableModel;
    private final String CSV_TRIAGE = "pacientes.csv";
    private final String CSV_CONSULTA = "pacientes_consulta.csv";
    private final String CSV_LABORATORIOS = "pacientes_laboratorios.csv";
    private final String CSV_HOSPITALIZACION = "pacientes_hospitalizacion.csv";

    private final String[] EPS_LIST = {
        "Aliansalud", "Cafam EPS", "Capital Salud EPS", "Capresoca", "Colsubsidio",
        "COMFANDI", "Compensar", "Coomeva", "Coosalud", "EPS Sanitas",
        "EPS Sura", "Famisanar", "Medimás", "Mutual SER", "Nueva EPS",
        "Salud Total", "Savia Salud EPS"
    };

    private final String[] LABORATORIOS = {
        "Radiografía de Tórax", "TAC", "Ultrasonido Abdominal", "Ecografía Doppler",
        "Ecocardiograma", "Oximetría de Pulso", "Prueba de Función Pulmonar", "Exámenes de Sangre"
    };

    public MedicinaGeneral() {
        setTitle("Medicina General");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

    // Panel izquierdo: Información del Paciente
        JPanel leftPanel = new JPanel();
        leftPanel.setBorder(BorderFactory.createTitledBorder("Detalles del Paciente"));

        GroupLayout layout = new GroupLayout(leftPanel);
        leftPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel idLabel = new JLabel("ID Paciente:");
        idField = new JTextField();

        JLabel nombreLabel = new JLabel("Nombres:");
        nombreField = new JTextField();

        JLabel apellidoLabel = new JLabel("Apellidos:");
        apellidoField = new JTextField();

        JLabel documentoLabel = new JLabel("Documento:");
        documentoField = new JTextField();

        JLabel epsLabel = new JLabel("EPS:");
        epsField = new JTextField();

        JLabel frecuenciaLabel = new JLabel("Frecuencia Cardiaca:");
        frecuenciaField = new JTextField();

        JLabel sistolicaLabel = new JLabel("Presión Sistólica:");
        sistolicaField = new JTextField();

        JLabel diastolicaLabel = new JLabel("Presión Diastólica:");
        diastolicaField = new JTextField();

        JLabel temperaturaLabel = new JLabel("Temperatura Corporal:");
        temperaturaField = new JTextField();

        JLabel saturacionLabel = new JLabel("Saturación de Oxígeno:");
        saturacionField = new JTextField();

        JLabel motivoLabel = new JLabel("Motivo de Consulta:");
        motivoConsultaArea = new JTextArea();
        motivoConsultaArea.setLineWrap(true);
        motivoConsultaArea.setWrapStyleWord(true);
        JScrollPane motivoScroll = new JScrollPane(motivoConsultaArea);

        JLabel observacionesLabel = new JLabel("Observaciones:");
        observacionesArea = new JTextArea();
        observacionesArea.setLineWrap(true);
        observacionesArea.setWrapStyleWord(true);
        JScrollPane observacionesScroll = new JScrollPane(observacionesArea);

        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(idLabel)
                .addComponent(nombreLabel)
                .addComponent(apellidoLabel)
                .addComponent(documentoLabel)
                .addComponent(epsLabel)
                .addComponent(frecuenciaLabel)
                .addComponent(sistolicaLabel)
                .addComponent(diastolicaLabel)
                .addComponent(temperaturaLabel)
                .addComponent(saturacionLabel)
                .addComponent(motivoLabel)
                .addComponent(observacionesLabel))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(idField)
                .addComponent(nombreField)
                .addComponent(apellidoField)
                .addComponent(documentoField)
                .addComponent(epsField)
                .addComponent(frecuenciaField)
                .addComponent(sistolicaField)
                .addComponent(diastolicaField)
                .addComponent(temperaturaField)
                .addComponent(saturacionField)
                .addComponent(motivoScroll)
                .addComponent(observacionesScroll)));

        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(idLabel)
                .addComponent(idField))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(nombreLabel)
                .addComponent(nombreField))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(apellidoLabel)
                .addComponent(apellidoField))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(documentoLabel)
                .addComponent(documentoField))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(epsLabel)
                .addComponent(epsField))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(frecuenciaLabel)
                .addComponent(frecuenciaField))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(sistolicaLabel)
                .addComponent(sistolicaField))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(diastolicaLabel)
                .addComponent(diastolicaField))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(temperaturaLabel)
                .addComponent(temperaturaField))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(saturacionLabel)
                .addComponent(saturacionField))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(motivoLabel)
                .addComponent(motivoScroll))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(observacionesLabel)
                .addComponent(observacionesScroll)));

        add(leftPanel, BorderLayout.CENTER);

    // Panel derecho: Tablas
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        
    // Tabla de urgencias
        JPanel triagePanel = new JPanel(new BorderLayout());
        triagePanel.setBorder(BorderFactory.createTitledBorder("Orden de Prioridad - Triage"));
        triageTableModel = new DefaultTableModel(new String[]{"ID", "Prioridad"}, 0);
        triageTable = new JTable(triageTableModel);
        triageTable.getSelectionModel().addListSelectionListener(e -> mostrarDetallesPaciente());
        JScrollPane triageScrollPane = new JScrollPane(triageTable);
        triagePanel.add(triageScrollPane, BorderLayout.CENTER);
        rightPanel.add(triagePanel);

    // Tabla de pacientes que vienen por consulta
        JPanel consultaPanel = new JPanel(new BorderLayout());
        consultaPanel.setBorder(BorderFactory.createTitledBorder("Pacientes que vienen a consulta Consulta"));
        consultaTableModel = new DefaultTableModel(new String[]{"Documento", "Nombre", "Apellido"}, 0);
        consultaTable = new JTable(consultaTableModel);
    //Traer datos del csv
        consultaTable.getSelectionModel().addListSelectionListener(e -> mostrarDetallesConsulta());
        JScrollPane consultaScrollPane = new JScrollPane(consultaTable);
        consultaPanel.add(consultaScrollPane, BorderLayout.CENTER);
        rightPanel.add(consultaPanel);

        add(rightPanel, BorderLayout.EAST);

     // Panel inferior: Botones
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton finalizarConsultaButton = new JButton("Finalizar Consulta");
        JButton agendarCitaButton = new JButton("Agendar Cita");
        JButton laboratoriosButton = new JButton("Laboratorios");
        JButton hospitalizacionButton = new JButton("Hospitalización");
        JButton historialButton = new JButton("Historial Clínico");

    // Agregar funcionalidad a los botones
        finalizarConsultaButton.addActionListener(e-> finalizarConsulta());
        agendarCitaButton.addActionListener(e -> mostrarVentanaAgendarCita());
        laboratoriosButton.addActionListener(e -> mostrarVentanaLaboratorios());
        hospitalizacionButton.addActionListener(e -> mostrarVentanaHospitalizacion());
        historialButton.addActionListener(e -> mostrarVentanaHistorialClinico());

        bottomPanel.add(finalizarConsultaButton);
        bottomPanel.add(agendarCitaButton);
        bottomPanel.add(laboratoriosButton);
        bottomPanel.add(hospitalizacionButton);
        bottomPanel.add(historialButton);

        add(bottomPanel, BorderLayout.SOUTH);

     //Metodos para cargar los pacientes desde los csv
        cargarPacientesDesdeCSV();
        cargarPacientesConsultaDesdeCSV();
        pack();
        setVisible(true);
    }

// Metodo del boton De Finalizar Consulta
    private void finalizarConsulta() {
        // Verificar si se seleccionó un paciente en la tabla de urgencias
        int selectedRowTriage = triageTable.getSelectedRow();
        int selectedRowConsulta = consultaTable.getSelectedRow();

        if (selectedRowTriage != -1) {
        // Paciente de la tabla de urgencias
            String id = (String) triageTableModel.getValueAt(selectedRowTriage, 0);

        // Eliminar del modelo de la tabla
            triageTableModel.removeRow(selectedRowTriage);

        // Eliminar del archivo CSV_TRIAGE
            try {
                File inputFile = new File(CSV_TRIAGE);
                File tempFile = new File("temp_" + CSV_TRIAGE);

                try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] datos = line.split(",");
                        // Escribir solo las filas cuyo ID no coincida con el seleccionado
                        if (!datos[1].equals(id)) {
                            writer.write(line);
                            writer.newLine();
                        }
                    }
                }

            // Reemplazar el archivo original por el temporal
                if (!inputFile.delete()) {
                    throw new IOException("No se pudo eliminar el archivo original.");
                }
                if (!tempFile.renameTo(inputFile)) {
                    throw new IOException("No se pudo renombrar el archivo temporal.");
                }

                JOptionPane.showMessageDialog(this, "Consulta finalizada. El paciente ha sido eliminado correctamente de urgencias.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar el paciente: " + ex.getMessage());
            }
        } else if (selectedRowConsulta != -1) {
        // Paciente de la tabla de consultas
            String documento = (String) consultaTableModel.getValueAt(selectedRowConsulta, 0);

        // Eliminar del modelo de la tabla
            consultaTableModel.removeRow(selectedRowConsulta);

         // Eliminar del archivo CSV_CONSULTA
            try {
                File inputFile = new File(CSV_CONSULTA);
                File tempFile = new File("temp_" + CSV_CONSULTA);

                try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] datos = line.split(",");
                    // Escribir solo las filas cuyo documento no coincida con el seleccionado
                        if (!datos[1].equals(documento)) {
                            writer.write(line);
                            writer.newLine();
                        }
                    }
                }

            // Reemplazar el archivo original por el temporal
                if (!inputFile.delete()) {
                    throw new IOException("No se pudo eliminar el archivo original.");
                }
                if (!tempFile.renameTo(inputFile)) {
                    throw new IOException("No se pudo renombrar el archivo temporal.");
                }

                JOptionPane.showMessageDialog(this, "Consulta finalizada. El paciente ha sido eliminado correctamente de la tabla de consultas.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar el paciente: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un paciente para finalizar la consulta.");
        }
    }

    
// Metodo del boton de Agendar Cita
    private void mostrarVentanaAgendarCita() {
        JDialog dialog = new JDialog(this, "Agendar Cita", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Componentes
        JTextField nombreField = new JTextField();
        JTextField apellidoField = new JTextField();
        JTextField documentoField = new JTextField();
        JComboBox<String> epsCombo = new JComboBox<>(EPS_LIST);
        JTextField motivoField = new JTextField();
        JComboBox<String> tipoIdCombo = new JComboBox<>(new String[]{"C.C", "Documento de Identidad"});

        // Fila 1: Tipo ID
        gbc.gridx = 0; // Columna 0
        gbc.gridy = 0; // Fila 0
        dialog.add(new JLabel("Tipo ID:"), gbc);

        gbc.gridx = 1; // Columna 1
        gbc.gridwidth = 2; // Ocupa dos columnas para mejor alineación
        dialog.add(tipoIdCombo, gbc);
        gbc.gridwidth = 1; // Restaurar ancho

        // Fila 2: Documento
        gbc.gridx = 0;
        gbc.gridy = 1; // Fila 1
        dialog.add(new JLabel("Documento:"), gbc);

        gbc.gridx = 1; // Columna 1
        gbc.gridwidth = 2; // Ocupa dos columnas
        dialog.add(documentoField, gbc);
        gbc.gridwidth = 1;

        // Fila 3: Nombre
        gbc.gridx = 0;
        gbc.gridy = 2; // Fila 2
        dialog.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3; // Combinar 3 columnas para que el campo sea más ancho
        dialog.add(nombreField, gbc);
        gbc.gridwidth = 1; // Restaurar ancho

        // Fila 4: Apellido
        gbc.gridx = 0;
        gbc.gridy = 3; // Fila 3
        dialog.add(new JLabel("Apellido:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        dialog.add(apellidoField, gbc);
        gbc.gridwidth = 1;

        // Fila 5: EPS
        gbc.gridx = 0;
        gbc.gridy = 4;
        dialog.add(new JLabel("EPS:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        dialog.add(epsCombo, gbc);
        gbc.gridwidth = 1;

        // Fila 6: Motivo de Consulta
        gbc.gridx = 0;
        gbc.gridy = 5;
        dialog.add(new JLabel("Motivo de Consulta:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        dialog.add(motivoField, gbc);
        gbc.gridwidth = 1;

        // Fila 7: Botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton guardarButton = new JButton("Guardar");
        JButton cancelarButton = new JButton("Cancelar");
        buttonPanel.add(guardarButton);
        buttonPanel.add(cancelarButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 4;
        dialog.add(buttonPanel, gbc);

        // Acción del botón Guardar
        guardarButton.addActionListener(e -> {
            try (FileWriter writer = new FileWriter(CSV_CONSULTA, true)) {
                // Escribir en el archivo CSV
                writer.write(tipoIdCombo.getSelectedItem() + "," + documentoField.getText() + "," +
                        nombreField.getText() + "," + apellidoField.getText() + "," +
                        epsCombo.getSelectedItem() + "," + motivoField.getText() + "\n");

                // Mostrar mensaje de éxito
                JOptionPane.showMessageDialog(this, "Cita guardada correctamente.");

                // Agregar a la tabla de consultas
                consultaTableModel.addRow(new Object[]{
                        documentoField.getText(),
                        nombreField.getText(),
                        apellidoField.getText()
                });

                dialog.dispose();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar la cita: " + ex.getMessage());
            }
        });

        // Acción del botón Cancelar
        cancelarButton.addActionListener(e -> dialog.dispose());

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    
    

// Ventana para Laboratorios
    private void mostrarVentanaLaboratorios() {
        JDialog dialog = new JDialog(this, "Laboratorios", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new BorderLayout());

        JPanel labPanel = new JPanel(new GridLayout(LABORATORIOS.length, 1));
        JCheckBox[] labCheckBoxes = new JCheckBox[LABORATORIOS.length];
        for (int i = 0; i < LABORATORIOS.length; i++) {
            labCheckBoxes[i] = new JCheckBox(LABORATORIOS[i]);
            labPanel.add(labCheckBoxes[i]);
        }

        JButton guardarButton = new JButton("Guardar");
        guardarButton.addActionListener(e -> {
            StringBuilder labsSeleccionados = new StringBuilder();
            for (JCheckBox checkBox : labCheckBoxes) {
                if (checkBox.isSelected()) {
                    labsSeleccionados.append(checkBox.getText()).append(";");
                }
            }

            if (labsSeleccionados.length() > 0) {
                try (FileWriter writer = new FileWriter(CSV_LABORATORIOS, true)) {
                    // Escribir datos sin la primera columna
                    writer.write(documentoField.getText() + "," + 
                            nombreField.getText() + "," + apellidoField.getText() + "," +
                            labsSeleccionados.toString() + "\n");
                    JOptionPane.showMessageDialog(this, "Laboratorios registrados correctamente.");
                    dialog.dispose();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error al guardar los laboratorios: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Debes seleccionar al menos un laboratorio.");
            }
        });

        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(guardarButton);
        buttonPanel.add(cancelarButton);

        dialog.add(new JScrollPane(labPanel), BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }


// Ventana para Hospitalización
    private void mostrarVentanaHospitalizacion() {
        JDialog dialog = new JDialog(this, "Hospitalización", true);
        dialog.setSize(400, 200);
        dialog.setLayout(new GridLayout(3, 2));

        JTextField diasField = new JTextField();
        dialog.add(new JLabel("Días de hospitalización:"));
        dialog.add(diasField);

        JButton guardarButton = new JButton("Guardar");
        guardarButton.addActionListener(e -> {
            try {
                int dias = Integer.parseInt(diasField.getText());
                if (dias > 0) {
                    // Abrir el archivo para escritura
                    File file = new File("pacientes_hospitalizacion.csv");
                    int nextId = 1; // ID inicial

                    // Calcular el siguiente ID directamente en este método
                    if (file.exists()) {
                        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                String[] datos = line.split(",");
                                if (datos.length > 0) {
                                    try {
                                        int currentId = Integer.parseInt(datos[0].trim());
                                        if (currentId >= nextId) {
                                            nextId = currentId + 1;
                                        }
                                    } catch (NumberFormatException ex) {
                                        // Ignorar líneas con IDs no válidos
                                    }
                                }
                            }
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(this, "Error al leer el archivo de hospitalización: " + ex.getMessage());
                            return;
                        }
                    }

                    try (FileWriter writer = new FileWriter(file, true)) {
                        if (triageTable.getSelectedRow() != -1) {
                            // Paciente de urgencias
                            String documento = documentoField.getText();
                            String nombre = nombreField.getText();
                            String apellido = apellidoField.getText();
                            String eps = epsField.getText();
                            String motivoConsulta = motivoConsultaArea.getText();
                            String frecuencia = frecuenciaField.getText();
                            String sistolica = sistolicaField.getText();
                            String diastolica = diastolicaField.getText();
                            String temperatura = temperaturaField.getText();
                            String saturacion = saturacionField.getText();
                            String observaciones = observacionesArea.getText();

                            // Escribir en el archivo CSV
                            writer.write(nextId + "," + documento + "," + nombre + "," + apellido + "," +
                                    eps + "," + motivoConsulta + "," + frecuencia + "," +
                                    sistolica + "," + diastolica + "," + temperatura + "," +
                                    saturacion + "," + observaciones + "," + dias + "\n");
                            JOptionPane.showMessageDialog(this, "Paciente de urgencias registrado para hospitalización.");
                        } else if (consultaTable.getSelectedRow() != -1) {
                            // Paciente de consulta
                            int selectedRow = consultaTable.getSelectedRow();
                            String documento = (String) consultaTableModel.getValueAt(selectedRow, 0);
                            String nombre = (String) consultaTableModel.getValueAt(selectedRow, 1);
                            String apellido = (String) consultaTableModel.getValueAt(selectedRow, 2);

                            // Obtener datos del panel izquierdo
                            String eps = epsField.getText();
                            String motivoConsulta = motivoConsultaArea.getText();
                            String frecuencia = frecuenciaField.getText();
                            String sistolica = sistolicaField.getText();
                            String diastolica = diastolicaField.getText();
                            String temperatura = temperaturaField.getText();
                            String saturacion = saturacionField.getText();
                            String observaciones = observacionesArea.getText();

                            // Escribir en el archivo CSV
                            writer.write(nextId + "," + documento + "," + nombre + "," + apellido + "," +
                                    eps + "," + motivoConsulta + "," + frecuencia + "," +
                                    sistolica + "," + diastolica + "," + temperatura + "," +
                                    saturacion + "," + observaciones + "," + dias + "\n");
                            JOptionPane.showMessageDialog(this, "Paciente de consulta registrado para hospitalización.");
                        } else {
                            JOptionPane.showMessageDialog(this, "No se ha seleccionado un paciente.");
                            return;
                        }

                        dialog.dispose();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "Error al guardar en el archivo CSV: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Debes ingresar al menos 1 día de hospitalización.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingresa un número válido para los días.");
            }
        });

        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(e -> dialog.dispose());

        dialog.add(guardarButton);
        dialog.add(cancelarButton);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }



// Ventana para Historial Clínico
private void mostrarVentanaHistorialClinico() {
    // Verificar si hay un paciente seleccionado
    String documento = documentoField.getText();
    String nombre = nombreField.getText();
    String apellido = apellidoField.getText();

    if (documento.isEmpty() || nombre.isEmpty() || apellido.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, selecciona un paciente para ver o actualizar su historial clínico.");
        return;
    }

    // Archivo del historial clínico del paciente
    File historialFile = new File(documento + "_historial.csv");

    if (!historialFile.exists()) {
        // Caso 1: No existe historial clínico, se muestra la ventana para crear uno nuevo
        JDialog dialog = new JDialog(this, "Crear Historial Clínico", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(5, 2));

        dialog.add(new JLabel("Documento paciente:"));
        JTextField documentoFieldDialog = new JTextField(documento);
        documentoFieldDialog.setEditable(false);
        dialog.add(documentoFieldDialog);

        dialog.add(new JLabel("Nombre:"));
        JTextField nombreFieldDialog = new JTextField(nombre);
        nombreFieldDialog.setEditable(false);
        dialog.add(nombreFieldDialog);

        dialog.add(new JLabel("Apellido:"));
        JTextField apellidoFieldDialog = new JTextField(apellido);
        apellidoFieldDialog.setEditable(false);
        dialog.add(apellidoFieldDialog);

        dialog.add(new JLabel("Observaciones:"));
        JTextArea observacionesAreaDialog = new JTextArea();
        JScrollPane observacionesScroll = new JScrollPane(observacionesAreaDialog);
        dialog.add(observacionesScroll);

        JButton crearButton = new JButton("Crear Historial");
        JButton cancelarButton = new JButton("Cancelar");

        crearButton.addActionListener(e -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(historialFile, true))) {
                String fechaHora = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                String lugar = "Medicina General"; // Lugar de atención definido estáticamente
                String observaciones = observacionesAreaDialog.getText().replace("\n", " ").replace("\r", " ");

                // Escribir encabezado si es un archivo nuevo
                if (historialFile.length() == 0) {
                    writer.write("Fecha/Hora,Lugar,Observaciones\n");
                }
                // Escribir la nueva entrada
                writer.write(fechaHora + "," + lugar + "," + observaciones + "\n");

                JOptionPane.showMessageDialog(this, "Historial clínico creado correctamente.");
                dialog.dispose();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al crear el historial clínico: " + ex.getMessage());
            }
        });

        cancelarButton.addActionListener(e -> dialog.dispose());

        dialog.add(crearButton);
        dialog.add(cancelarButton);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    } else {
        // Caso 2: Ya existe historial clínico, se muestra la ventana para actualizar o ver historial
        JDialog dialog = new JDialog(this, "Actualizar o Ver Historial Clínico", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(6, 2));

        dialog.add(new JLabel("Documento paciente:"));
        JTextField documentoFieldDialog = new JTextField(documento);
        documentoFieldDialog.setEditable(false);
        dialog.add(documentoFieldDialog);

        dialog.add(new JLabel("Nombre:"));
        JTextField nombreFieldDialog = new JTextField(nombre);
        nombreFieldDialog.setEditable(false);
        dialog.add(nombreFieldDialog);

        dialog.add(new JLabel("Apellido:"));
        JTextField apellidoFieldDialog = new JTextField(apellido);
        apellidoFieldDialog.setEditable(false);
        dialog.add(apellidoFieldDialog);

        dialog.add(new JLabel("Observaciones:"));
        JTextArea observacionesAreaDialog = new JTextArea();
        JScrollPane observacionesScroll = new JScrollPane(observacionesAreaDialog);
        dialog.add(observacionesScroll);

        JButton actualizarButton = new JButton("Actualizar Historial");
        JButton verHistorialButton = new JButton("Ver Historial Clínico");
        JButton cancelarButton = new JButton("Cancelar");

        actualizarButton.addActionListener(e -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(historialFile, true))) {
                String fechaHora = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                String lugar = "Medicina General"; // Lugar de atención definido estáticamente
                String observaciones = observacionesAreaDialog.getText().replace("\n", " ").replace("\r", " ");

                // Agregar nueva entrada al historial
                writer.write(fechaHora + "," + lugar + "," + observaciones + "\n");

                JOptionPane.showMessageDialog(this, "Historial clínico actualizado correctamente.");
                dialog.dispose();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar el historial clínico: " + ex.getMessage());
            }
        });

        verHistorialButton.addActionListener(e -> {
            // Mostrar historial clínico en una tabla
            JDialog verDialog = new JDialog(dialog, "Historial Clínico del Paciente", true);
            verDialog.setSize(600, 400);
            verDialog.setLayout(new BorderLayout());

            // Modelo y tabla para mostrar el historial
            DefaultTableModel historialTableModel = new DefaultTableModel(new String[]{"Fecha/Hora", "Lugar", "Observaciones"}, 0);
            JTable historialTable = new JTable(historialTableModel);

            try (BufferedReader reader = new BufferedReader(new FileReader(historialFile))) {
                String line;
                boolean isFirstLine = true; // Saltar encabezado
                while ((line = reader.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue;
                    }
                    String[] datos = line.split(",");
                    if (datos.length >= 3) {
                        historialTableModel.addRow(datos);
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al leer el historial clínico: " + ex.getMessage());
            }

            verDialog.add(new JScrollPane(historialTable), BorderLayout.CENTER);

            JButton cerrarButton = new JButton("Cerrar");
            cerrarButton.addActionListener(event -> verDialog.dispose());
            verDialog.add(cerrarButton, BorderLayout.SOUTH);

            verDialog.setLocationRelativeTo(dialog);
            verDialog.setVisible(true);
        });

        cancelarButton.addActionListener(e -> dialog.dispose());

        dialog.add(actualizarButton);
        dialog.add(verHistorialButton);
        dialog.add(cancelarButton);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}


// FIN HISTORIAL

// Metodo que lee los pacientes que vienen de urgencias
    private void cargarPacientesDesdeCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_TRIAGE))) {
            List<String[]> pacientes = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(",");
                if (datos.length >= 9) { // Validar que haya suficiente información
                    pacientes.add(datos);
                }
            }

            // Ordenar pacientes por triage (columna 7)
            pacientes.sort(Comparator.comparingInt(p -> Integer.parseInt(p[7])));

            // Cargar datos en la tabla
            for (String[] paciente : pacientes) {
                triageTableModel.addRow(new Object[]{paciente[1], paciente[7]});
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al leer el archivo CSV: " + e.getMessage());
        }
    }

    // Metodo para poner a los pacientes que vienen de consulta en la tabla
    private void cargarPacientesConsultaDesdeCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_CONSULTA))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(",");
                // Asegurar que el registro tenga todas las columnas necesarias
                if (datos.length >= 3) {
                    String documento = datos[1];
                    String nombre = datos[2];
                    String apellido = datos[3];
                    consultaTableModel.addRow(new Object[]{documento, nombre, apellido});
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los pacientes de consulta: " + ex.getMessage());
        }
    }
    // FIN
// Mostrar detaller de paceintes que vienen por consulta
    private void mostrarDetallesConsulta() {
        int selectedRow = consultaTable.getSelectedRow();
        if (selectedRow != -1) {
            // Obtener los datos de la fila seleccionada
            String documento = (String) consultaTableModel.getValueAt(selectedRow, 0);
            String nombre = (String) consultaTableModel.getValueAt(selectedRow, 1);
            String apellido = (String) consultaTableModel.getValueAt(selectedRow, 2);

            // Buscar el registro completo en el CSV_CONSULTA para obtener EPS y Motivo de Consulta
            try (BufferedReader reader = new BufferedReader(new FileReader(CSV_CONSULTA))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] datos = line.split(",");
                    if (datos.length >= 6 && datos[1].equals(documento)) { // Comparar Documento
                        String eps = datos[4]; // Columna EPS
                        String motivoConsulta = datos[5]; // Columna Motivo de Consulta

                        // Mostrar los datos en los campos del panel izquierdo
                        documentoField.setText(documento);
                        nombreField.setText(nombre);
                        apellidoField.setText(apellido);
                        epsField.setText(eps);
                        motivoConsultaArea.setText(motivoConsulta);

                        // Limpiar otros campos que no tienen información
                        frecuenciaField.setText("");
                        sistolicaField.setText("");
                        diastolicaField.setText("");
                        temperaturaField.setText("");
                        saturacionField.setText("");
                        observacionesArea.setText("");
                        break;
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al leer los datos del paciente: " + ex.getMessage());
            }
        }
    }



// Mostrar detalles de pacientes que vienen de urgencias
    private void mostrarDetallesPaciente() {
        int selectedRow = triageTable.getSelectedRow();
        if (selectedRow != -1) {
            String id = (String) triageTableModel.getValueAt(selectedRow, 0);
            try (BufferedReader br = new BufferedReader(new FileReader(CSV_TRIAGE))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] datos = line.split(",");
                    if (datos[1].equals(id)) {
                        idField.setText(datos[1]);
                        nombreField.setText(datos[2]);
                        apellidoField.setText(datos[3]);
                        documentoField.setText(datos[1]);
                        epsField.setText(datos[5]);
                        frecuenciaField.setText(datos[10]);
                        sistolicaField.setText(datos[11]);
                        diastolicaField.setText(datos[12]);
                        temperaturaField.setText(datos[8]);
                        saturacionField.setText(datos[9]);
                        motivoConsultaArea.setText(datos[6]);
                        observacionesArea.setText("No se hicieron observaciones");
                        break;
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al buscar detalles del paciente: " + e.getMessage());
            }
        }
    }
    // Metodo para que se actualice el historial en la tabla
    private void cargarHistorialEnTabla(File historialFile, JTable historialTable) {
        DefaultTableModel modeloTabla = (DefaultTableModel) historialTable.getModel();
        modeloTabla.setRowCount(0); // Limpiar cualquier dato previo en la tabla
    
        try (BufferedReader reader = new BufferedReader(new FileReader(historialFile))) {
            String linea;
            boolean isFirstLine = true;
    
            while ((linea = reader.readLine()) != null) {
                if (isFirstLine) {
                    // Ignorar encabezado en el archivo CSV si existe
                    isFirstLine = false;
                    continue;
                }
                String[] datos = linea.split(","); // Dividir por comas
                if (datos.length >= 3) {
                    modeloTabla.addRow(new Object[]{datos[0], datos[1], datos[2]}); // Agregar fila a la tabla
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar el historial clínico: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MedicinaGeneral::new);
    }
}  