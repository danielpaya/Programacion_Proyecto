
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Hospital extends JFrame {
    private JTextField idField, nombreField, apellidoField, documentoField, laboratoriosField;
    private JTextArea observacionesArea;
    private JTable laboratorioTable;
    private DefaultTableModel laboratorioTableModel;
    private final String CSV_LABORATORIOS = "pacientes_laboratorios.csv";

    public Hospital() {
        setTitle("Hospital - Laboratorios");
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

        JLabel laboratoriosLabel = new JLabel("Laboratorios:");
        laboratoriosField = new JTextField();
        laboratoriosField.setEditable(false);

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
                .addComponent(laboratoriosLabel)
                .addComponent(observacionesLabel))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(idField)
                .addComponent(nombreField)
                .addComponent(apellidoField)
                .addComponent(documentoField)
                .addComponent(laboratoriosField)
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
                .addComponent(laboratoriosLabel)
                .addComponent(laboratoriosField))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(observacionesLabel)
                .addComponent(observacionesScroll)));

        add(leftPanel, BorderLayout.CENTER);

        // Panel derecho: Tabla de pacientes
        JPanel rightPanel = new JPanel();
        rightPanel.setBorder(BorderFactory.createTitledBorder("Pacientes en Laboratorios"));
        rightPanel.setLayout(new BorderLayout());

        laboratorioTableModel = new DefaultTableModel(new String[]{"Documento", "Nombre", "Apellido"}, 0);
        laboratorioTable = new JTable(laboratorioTableModel);
        laboratorioTable.getSelectionModel().addListSelectionListener(e -> mostrarDetallesPaciente());
        JScrollPane laboratorioScrollPane = new JScrollPane(laboratorioTable);
        rightPanel.add(laboratorioScrollPane, BorderLayout.CENTER);

        add(rightPanel, BorderLayout.EAST);

        // Panel inferior: Botón de finalizar consulta
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton finalizarConsultaButton = new JButton("Finalizar Consulta");
        finalizarConsultaButton.addActionListener(e -> finalizarConsulta());
        bottomPanel.add(finalizarConsultaButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Cargar datos desde el CSV
        cargarPacientesDesdeCSV();

        pack();
        setVisible(true);
    }

    private void cargarPacientesDesdeCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_LABORATORIOS))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(",");
                if (datos.length >= 3) { // Validar columnas necesarias
                    laboratorioTableModel.addRow(new Object[]{datos[0], datos[1], datos[2]});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los pacientes: " + e.getMessage());
        }
    }

    private void mostrarDetallesPaciente() {
        int selectedRow = laboratorioTable.getSelectedRow();
        if (selectedRow != -1) {
            String documento = (String) laboratorioTableModel.getValueAt(selectedRow, 0);
            try (BufferedReader br = new BufferedReader(new FileReader(CSV_LABORATORIOS))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] datos = line.split(",");
                    if (datos[0].equals(documento)) {
                        idField.setText(datos[0]);
                        nombreField.setText(datos[1]);
                        apellidoField.setText(datos[2]);
                        documentoField.setText(datos[0]);
                        laboratoriosField.setText(datos.length > 3 ? datos[3] : "No asignados");
                        observacionesArea.setText("");
                        break;
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al mostrar detalles: " + e.getMessage());
            }
        }
    }

    private void finalizarConsulta() {
        int selectedRow = laboratorioTable.getSelectedRow();
        if (selectedRow != -1) {
            String documento = (String) laboratorioTableModel.getValueAt(selectedRow, 0);

            // Eliminar del modelo de la tabla
            laboratorioTableModel.removeRow(selectedRow);

            // Actualizar el CSV
            try {
                File inputFile = new File(CSV_LABORATORIOS);
                File tempFile = new File("temp_" + CSV_LABORATORIOS);

                try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                     BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] datos = line.split(",");
                        if (!datos[0].equals(documento)) {
                            writer.write(line);
                            writer.newLine();
                        }
                    }
                }

                if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
                    throw new IOException("Error al actualizar el archivo CSV.");
                }

                // Actualizar historial del paciente
                File historialFile = new File(documento + "_historial.csv");
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(historialFile, true))) {
                    if (historialFile.length() == 0) {
                        writer.write("Fecha/Hora,Lugar,Observaciones");
                    }
                    String fechaHora = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    writer.write(fechaHora + ",Laboratorio," + observacionesArea.getText().replace(",", " ") + "\n");
                }

                JOptionPane.showMessageDialog(this, "Consulta finalizada. Historial actualizado.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al finalizar consulta: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un paciente para finalizar la consulta.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Hospital::new);
    }
}
