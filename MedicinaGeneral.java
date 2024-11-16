import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;

public class MedicinaGeneral extends JFrame {
    private JTextField idField, nombreField, apellidoField, documentoField, epsField;
    private JTextField frecuenciaField, sistolicaField, diastolicaField, temperaturaField, saturacionField, hospitalizacionField;
    private JTextArea motivoConsultaArea;
    private JTable triageTable;
    private DefaultTableModel triageTableModel;
    private List<String[]> pacientesList = new ArrayList<>();
    private Set<String> ordenesHospitalizacion = new HashSet<>();
    private JList<String> laboratoriosList;

    public MedicinaGeneral() {
        setTitle("Medicina General");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel izquierdo: Información del Paciente
        JPanel leftPanel = new JPanel(new GridLayout(12, 2, 5, 5));
        leftPanel.setBorder(BorderFactory.createTitledBorder("Detalles del Paciente Seleccionado"));

        idField = agregarCampo("ID Paciente:", leftPanel);
        nombreField = agregarCampo("Nombres:", leftPanel);
        apellidoField = agregarCampo("Apellidos:", leftPanel);
        documentoField = agregarCampo("Documento:", leftPanel);
        epsField = agregarCampo("EPS:", leftPanel);
        frecuenciaField = agregarCampo("Frecuencia Cardiaca:", leftPanel);
        sistolicaField = agregarCampo("Presión Sistólica:", leftPanel);
        diastolicaField = agregarCampo("Presión Diastólica:", leftPanel);
        temperaturaField = agregarCampo("Temperatura Corporal:", leftPanel);
        saturacionField = agregarCampo("Saturación de Oxígeno:", leftPanel);

        leftPanel.add(new JLabel("Motivo de Consulta:"));
        motivoConsultaArea = new JTextArea(3, 20);
        leftPanel.add(new JScrollPane(motivoConsultaArea));

        JButton finalizarConsultaButton = new JButton("Finalizar Consulta");
        JButton hospitalizacionButton = new JButton("Hospitalización");
        JButton laboratoriosButton = new JButton("Laboratorios");

        hospitalizacionField = agregarCampo("Días Hospitalización:", leftPanel);

        leftPanel.add(finalizarConsultaButton);
        leftPanel.add(hospitalizacionButton);
        leftPanel.add(laboratoriosButton);

        add(leftPanel, BorderLayout.WEST);

        // Panel derecho: Lista de Pacientes en Triage
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Orden de Prioridad - Triage"));

        triageTableModel = new DefaultTableModel(new String[]{"Triage"}, 0);
        triageTable = new JTable(triageTableModel);
        cargarPacientesDesdeCSV("pacientes.csv");

        JScrollPane triageScrollPane = new JScrollPane(triageTable);
        rightPanel.add(triageScrollPane, BorderLayout.CENTER);

        add(rightPanel, BorderLayout.CENTER);

        // Lista de laboratorios
        String[] laboratorios = {"Hemograma", "Radiografía de tórax", "Tomografía computarizada", "Uroanálisis"};
        laboratoriosList = new JList<>(laboratorios);
        laboratoriosList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // Eventos
        triageTable.getSelectionModel().addListSelectionListener(e -> mostrarDetallesPaciente());
        finalizarConsultaButton.addActionListener(e -> finalizarConsulta());
        hospitalizacionButton.addActionListener(e -> manejarHospitalizacion());
        laboratoriosButton.addActionListener(e -> manejarLaboratorios());
    }

    // Método auxiliar para agregar campos y etiquetas
    private JTextField agregarCampo(String etiqueta, JPanel panel) {
        panel.add(new JLabel(etiqueta));
        JTextField campo = new JTextField(10);
        panel.add(campo);
        return campo;
    }

    // Cargar pacientes desde el archivo CSV y ordenarlos por triage
    private void cargarPacientesDesdeCSV(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean primeraLinea = true;
            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue; // Saltar encabezado
                }
                String[] datos = linea.split(",");
                if (datos.length >= 6) {
                    pacientesList.add(datos);
                    triageTableModel.addRow(new Object[]{datos[5]}); // Solo triage
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Mostrar los detalles del paciente seleccionado
    private void mostrarDetallesPaciente() {
        int selectedRow = triageTable.getSelectedRow();
        if (selectedRow != -1) {
            String triageSeleccionado = triageTableModel.getValueAt(selectedRow, 0).toString();
            for (String[] paciente : pacientesList) {
                if (paciente[5].equals(triageSeleccionado)) {
                    idField.setText(paciente[0]);
                    nombreField.setText(paciente[1]);
                    apellidoField.setText(paciente[2]);
                    documentoField.setText(paciente[3]);
                    epsField.setText(paciente[4]);
                    frecuenciaField.setText(paciente[6]);
                    sistolicaField.setText(paciente[7]);
                    diastolicaField.setText(paciente[8]);
                    temperaturaField.setText(paciente[9]);
                    saturacionField.setText(paciente[10]);
                    motivoConsultaArea.setText(paciente[11]);
                    break;
                }
            }
        }
    }

    // Manejar hospitalización
    private void manejarHospitalizacion() {
        String dias = hospitalizacionField.getText();
        try {
            int diasHospitalizacion = Integer.parseInt(dias);
            if (diasHospitalizacion > 0) {
                String codigoOrden = generarCodigoUnico();
                guardarEnCSV("pacientes_hospitalizados.csv", codigoOrden, diasHospitalizacion);
                JOptionPane.showMessageDialog(this, "Paciente hospitalizado. Código de orden: " + codigoOrden);
            } else {
                JOptionPane.showMessageDialog(this, "Ingrese un número mayor a 0.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un número válido.");
        }
    }

    // Generar código único
    private String generarCodigoUnico() {
        String codigo;
        do {
            codigo = UUID.randomUUID().toString().substring(0, 8);
        } while (ordenesHospitalizacion.contains(codigo));
        ordenesHospitalizacion.add(codigo);
        return codigo;
    }

    // Manejar laboratorios
    private void manejarLaboratorios() {
        List<String> seleccionados = laboratoriosList.getSelectedValuesList();
        if (!seleccionados.isEmpty()) {
            guardarEnCSV("pacientes_laboratorios.csv", seleccionados.toArray(new String[0]));
            JOptionPane.showMessageDialog(this, "Exámenes de laboratorio registrados.");
        } else {
            JOptionPane.showMessageDialog(this, "No se seleccionaron laboratorios.");
        }
    }

    // Finalizar consulta
    private void finalizarConsulta() {
        int selectedRow = triageTable.getSelectedRow();
        if (selectedRow != -1) {
            triageTableModel.removeRow(selectedRow);
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Consulta finalizada.");
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un paciente.");
        }
    }

    // Guardar información en CSV
    private void guardarEnCSV(String archivo, Object... datos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(String.join(",", Arrays.stream(datos).map(Object::toString).toArray(String[]::new)));
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para limpiar los campos
    private void limpiarCampos() {
        idField.setText("");
        nombreField.setText("");
        apellidoField.setText("");
        documentoField.setText("");
        epsField.setText("");
        frecuenciaField.setText("");
        sistolicaField.setText("");
        diastolicaField.setText("");
        temperaturaField.setText("");
        saturacionField.setText("");
        motivoConsultaArea.setText("");
        hospitalizacionField.setText("");
    }

    // Método principal para iniciar la aplicación
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MedicinaGeneral app = new MedicinaGeneral();
            app.setVisible(true);
        });
    }
}
