import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
//KJKLJLKJ
public class MedicinaGeneral extends JFrame {
    private JTextField idField, nombreField, apellidoField, documentoField, epsField;
    private JTextField frecuenciaField, sistolicaField, diastolicaField, temperaturaField, saturacionField;
    private JTextArea motivoConsultaArea;
    private JTable triageTable;
    private DefaultTableModel triageTableModel;
    private List<String[]> pacientesList = new ArrayList<>();

    public MedicinaGeneral() {
        setTitle("Medicina General");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel izquierdo: Información del Paciente
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(12, 2, 5, 5));
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
        leftPanel.add(finalizarConsultaButton);

        add(leftPanel, BorderLayout.WEST);

        // Panel derecho: Lista de Pacientes en Triage (solo muestra triage)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Orden de Prioridad - Triage"));

        triageTableModel = new DefaultTableModel(new String[]{"Triage"}, 0);
        triageTable = new JTable(triageTableModel);
        cargarPacientesDesdeCSV("pacientes.csv");

        JScrollPane triageScrollPane = new JScrollPane(triageTable);
        rightPanel.add(triageScrollPane, BorderLayout.CENTER);

        add(rightPanel, BorderLayout.CENTER);

        // Eventos
        triageTable.getSelectionModel().addListSelectionListener(e -> mostrarDetallesPaciente());
        finalizarConsultaButton.addActionListener(e -> finalizarConsulta());
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
            List<String[]> listaTemporal = new ArrayList<>();
            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue; // Saltar encabezado
                }
                String[] datos = linea.split(","); // Separador en CSV
                if (datos.length >= 6) {
                    listaTemporal.add(datos); // Agregar a lista temporal para ordenar
                }
            }
            // Ordenar la lista temporal por triage
            listaTemporal.sort(Comparator.comparingInt(p -> Integer.parseInt(p[5])));

            // Agregar los datos ordenados al modelo de la tabla (solo el triage)
            for (String[] paciente : listaTemporal) {
                pacientesList.add(paciente); // Guardar en lista general
                triageTableModel.addRow(new Object[]{paciente[5]});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Mostrar los detalles del paciente seleccionado
    private void mostrarDetallesPaciente() {
        int selectedRow = triageTable.getSelectedRow();
        if (selectedRow != -1) {
            // Obtener el triage del paciente seleccionado
            String triageSeleccionado = triageTableModel.getValueAt(selectedRow, 0).toString();

            // Buscar y mostrar la información completa del paciente correspondiente en los campos
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

    // Finalizar consulta y eliminar el paciente de la lista de triage
    private void finalizarConsulta() {
        int selectedRow = triageTable.getSelectedRow();
        if (selectedRow != -1) {
            triageTableModel.removeRow(selectedRow);
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Consulta finalizada.");
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un paciente de la lista de triage.");
        }
    }

    // Método para limpiar los campos después de finalizar consulta
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
    }

    // Método principal para iniciar la aplicación
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MedicinaGeneral app = new MedicinaGeneral();
            app.setVisible(true);
        });
    }
}
