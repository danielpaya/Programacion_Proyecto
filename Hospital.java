import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//777788888
public class Hospital {
    // Modelo de tabla compartido entre ORDEN y Historial
    private static DefaultTableModel historialModel = new DefaultTableModel(new String[]{"Nombre", "Examen", "Estado", "ID"}, 0);

    public static void main(String[] args) {
        openMainWindow();
    }

    public static void openMainWindow() {
        JFrame frame = new JFrame("Hospital");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        JButton btnLaboratorio = new JButton("Laboratorio");
        JButton btnHospitalizacion = new JButton("Hospitalización");
        JButton btnUrgencias = new JButton("Urgencias");
        JButton btnMedicinaGeneral = new JButton("Medicina General");
        JButton btnRegistro = new JButton("Registro");

        int buttonWidth = 160;
        int buttonHeight = 50;

        int centerX = (frameWidth - buttonWidth) / 2;
        int centerY = (frameHeight - buttonHeight) / 2;
        btnRegistro.setBounds(centerX, centerY, buttonWidth, buttonHeight);

        btnLaboratorio.setBounds(centerX - 180, centerY - 60, buttonWidth, buttonHeight);
        btnHospitalizacion.setBounds(centerX - 180, centerY + 60, buttonWidth, buttonHeight);
        btnUrgencias.setBounds(centerX + 180, centerY - 60, buttonWidth, buttonHeight);
        btnMedicinaGeneral.setBounds(centerX + 180, centerY + 60, buttonWidth, buttonHeight);

        frame.add(btnLaboratorio);
        frame.add(btnHospitalizacion);
        frame.add(btnUrgencias);
        frame.add(btnMedicinaGeneral);
        frame.add(btnRegistro);

        btnLaboratorio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                openLaboratorioWindow();
            }
        });

        frame.setVisible(true);
    }

    public static void openLaboratorioWindow() {
        JFrame laboratorioFrame = new JFrame("Laboratorio");
        laboratorioFrame.setSize(300, 200);
        laboratorioFrame.setLayout(new GridLayout(2, 2, 10, 10));

        JButton btnExamenes = new JButton("Exámenes");
        JButton btnPersonal = new JButton("Personal");
        JButton btnHistorial = new JButton("Historial");
        JButton btnVolver = new JButton("Volver");

        btnExamenes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                laboratorioFrame.dispose();
                openOrdenWindow();
            }
        });

        btnHistorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                laboratorioFrame.dispose();
                openHistorialWindow();
            }
        });

        btnPersonal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                laboratorioFrame.dispose();
                openMainWindow();
            }
        });

        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                laboratorioFrame.dispose();
                openMainWindow();
            }
        });

        laboratorioFrame.add(btnExamenes);
        laboratorioFrame.add(btnPersonal);
        laboratorioFrame.add(btnHistorial);
        laboratorioFrame.add(btnVolver);
        laboratorioFrame.setVisible(true);
    }

    public static void openOrdenWindow() {
        JFrame ordenFrame = new JFrame("ORDEN");
        ordenFrame.setSize(300, 350);
        ordenFrame.setLayout(new BorderLayout());

        JPanel ordenPanel = new JPanel();
        JLabel ordenLabel = new JLabel("ORDEN");
        ordenPanel.add(ordenLabel);

        JTextField idField = new JTextField(15);
        JTextField nameField = new JTextField(15);
        JTextField examField = new JTextField(15);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Nombre:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Examen a realizar:"));
        inputPanel.add(examField);

        JButton buscarButton = new JButton("Buscar");
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String name = nameField.getText();
                String exam = examField.getText();
                
                boolean idAndExamMatch = false;
                Object[] datos = new Object[4];

                for (int i = 0; i < historialModel.getRowCount(); i++) {
                    if (historialModel.getValueAt(i, 3).equals(id)) {
                        if (historialModel.getValueAt(i, 1).equals(exam)) {
                            idAndExamMatch = true;
                            datos[0] = historialModel.getValueAt(i, 0);  // Nombre
                            datos[1] = i + 1;  // Orden
                            datos[2] = historialModel.getValueAt(i, 1);  // Examen
                            datos[3] = historialModel.getValueAt(i, 2);  // Estado
                            break;
                        }
                    }
                }
                
                if (idAndExamMatch) {
                    ordenFrame.dispose();
                    openConfirmacionWindow(datos); // Abrir ventana de confirmación con los datos
                } else {
                    if (!id.isEmpty() && !name.isEmpty() && !exam.isEmpty()) {
                        historialModel.addRow(new Object[]{name, exam, "Pendiente", id});
                        JOptionPane.showMessageDialog(ordenFrame, "ID agregado al historial con el nuevo examen.");
                        idField.setText("");
                        nameField.setText("");
                        examField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(ordenFrame, "Por favor, complete todos los campos.");
                    }
                }
            }
        });

        JButton volverButton = new JButton("Volver");
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ordenFrame.dispose();
                openLaboratorioWindow();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(buscarButton);
        buttonPanel.add(volverButton);

        ordenFrame.add(ordenPanel, BorderLayout.NORTH);
        ordenFrame.add(inputPanel, BorderLayout.CENTER);
        ordenFrame.add(buttonPanel, BorderLayout.SOUTH);

        ordenFrame.setVisible(true);
    }

    public static void openConfirmacionWindow(Object[] datos) {
        JFrame confirmacionFrame = new JFrame("Confirmación de Datos");
        confirmacionFrame.setSize(300, 200);
        confirmacionFrame.setLayout(new GridLayout(6, 1, 10, 10));

        confirmacionFrame.add(new JLabel("Nombre: " + datos[0]));
        confirmacionFrame.add(new JLabel("Orden: " + datos[1]));
        confirmacionFrame.add(new JLabel("Examen a realizar: " + datos[2]));
        confirmacionFrame.add(new JLabel("Estado del examen: " + datos[3]));

        JButton hacerExamenButton = new JButton("Hacer examen");
        JButton reagendarButton = new JButton("Reagendar cita");

        hacerExamenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmacionFrame.dispose();
                openEncuestaWindow(datos[0].toString(), datos[3].toString(), datos[2].toString()); // Pasa el nombre, ID y examen a la ventana de encuesta
            }
        });

        reagendarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmacionFrame.dispose();
                openOrdenWindow();
            }
        });

        confirmacionFrame.add(hacerExamenButton);
        confirmacionFrame.add(reagendarButton);

        confirmacionFrame.setVisible(true);
    }

    public static void openEncuestaWindow(String nombre, String id, String examen) {
        JFrame encuestaFrame = new JFrame("Encuesta de Laboratorio");
        encuestaFrame.setSize(500, 600);
        encuestaFrame.setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Paciente: " + nombre + " | ID: " + id, SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        encuestaFrame.add(titulo, BorderLayout.NORTH);

        JPanel encuestaPanel = new JPanel();
        encuestaPanel.setLayout(new GridLayout(10, 2, 10, 10));

        JTextField[] fields = new JTextField[10];
        String[] labels = {
            "Dolor (1-11):", "Ubicación del dolor:", "Presión corporal:", "Temperatura:", 
            "Saturación:", "Oxigenación:", "Nivel de Hemoglobina:", "Nivel de Proteína:", 
            "Nivel de Plaquetas:", "Nivel de Glóbulos Rojos y Blancos:"
        };

        for (int i = 0; i < labels.length; i++) {
            encuestaPanel.add(new JLabel(labels[i]));
            fields[i] = new JTextField();
            encuestaPanel.add(fields[i]);
        }

        encuestaFrame.add(encuestaPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton volverButton = new JButton("Volver");
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encuestaFrame.dispose();
                openLaboratorioWindow();
            }
        });

        JButton finalizarButton = new JButton("Finalizar");
        finalizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encuestaFrame.dispose();
                openResumenMedico(nombre, id, fields);
            }
        });

        buttonPanel.add(volverButton);
        buttonPanel.add(finalizarButton);

        encuestaFrame.add(buttonPanel, BorderLayout.SOUTH);
        encuestaFrame.setVisible(true);
    }

    public static void openResumenMedico(String nombre, String id, JTextField[] fields) {
        JFrame resumenFrame = new JFrame("Resumen Médico");
        resumenFrame.setSize(500, 600);
        resumenFrame.setLayout(new BorderLayout());

        StringBuilder resumenText = new StringBuilder("<html><b>Datos de la Encuesta</b><br><br>");
        String[] labels = {
            "Dolor (1-11):", "Ubicación del dolor:", "Presión corporal:", "Temperatura:", 
            "Saturación:", "Oxigenación:", "Nivel de Hemoglobina:", "Nivel de Proteína:", 
            "Nivel de Plaquetas:", "Nivel de Glóbulos Rojos y Blancos:"
        };

        StringBuilder alertas = new StringBuilder();

        try {
            String presion = fields[2].getText();
            if (presion.matches("\\d+/\\d+")) {
                int sistolica = Integer.parseInt(presion.split("/")[0]);
                if (sistolica > 120) alertas.append("Hipertensión detectada.<br>");
            }
            resumenText.append(labels[2]).append(" ").append(presion).append("<br>");

            double temperatura = Double.parseDouble(fields[3].getText());
            resumenText.append(labels[3]).append(" ").append(temperatura);
            if (temperatura < 36) alertas.append("Hipotermia detectada.<br>");
            else if (temperatura > 38) alertas.append("Hipertermia detectada.<br>");
            resumenText.append("<br>");

            double saturacion = Double.parseDouble(fields[4].getText());
            resumenText.append(labels[4]).append(" ").append(saturacion);
            if (saturacion < 88) alertas.append("Atención médica urgente para saturación.<br>");
            resumenText.append("<br>");

            double oxigenacion = Double.parseDouble(fields[5].getText());
            resumenText.append(labels[5]).append(" ").append(oxigenacion);
            if (oxigenacion < 88) alertas.append("Atención médica urgente para oxigenación.<br>");
            resumenText.append("<br>");

            double hemoglobina = Double.parseDouble(fields[6].getText());
            resumenText.append(labels[6]).append(" ").append(hemoglobina);
            if (hemoglobina < 11) alertas.append("Anemia detectada.<br>");
            else if (hemoglobina > 16) alertas.append("Revisión médica urgente para hemoglobina.<br>");
            resumenText.append("<br>");

            double proteina = Double.parseDouble(fields[7].getText());
            resumenText.append(labels[7]).append(" ").append(proteina);
            if (proteina < 60) alertas.append("Se recomienda mayor consumo de proteínas.<br>");
            resumenText.append("<br>");

            double plaquetas = Double.parseDouble(fields[8].getText());
            resumenText.append(labels[8]).append(" ").append(plaquetas);
            if (plaquetas < 150 || plaquetas > 400) alertas.append("Revisión médica urgente para nivel de plaquetas.<br>");
            resumenText.append("<br>");

            double globulos = Double.parseDouble(fields[9].getText());
            resumenText.append(labels[9]).append(" ").append(globulos);
            if (globulos < 4.35 || globulos > 5.65) alertas.append("Revisión médica urgente para nivel de glóbulos.<br>");
            resumenText.append("<br>");

        } catch (NumberFormatException e) {
            alertas.append("Error en el ingreso de datos numéricos.<br>");
        }

        resumenText.append("<br><b>Paciente:</b> ").append(nombre).append("<br><b>ID:</b> ").append(id);
        resumenText.append("<br><br><b>Análisis:</b><br>").append(alertas.toString());
        resumenText.append("</html>");

        JLabel resumenLabel = new JLabel(resumenText.toString());
        resumenFrame.add(resumenLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton siguienteButton = new JButton("Siguiente");

        siguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarEstadoExamen(id);
                resumenFrame.dispose();
                openLaboratorioWindow();
            }
        });

        buttonPanel.add(siguienteButton);
        resumenFrame.add(buttonPanel, BorderLayout.SOUTH);

        resumenFrame.setVisible(true);
    }

    private static void actualizarEstadoExamen(String id) {
        for (int i = 0; i < historialModel.getRowCount(); i++) {
            if (historialModel.getValueAt(i, 3).equals(id) && historialModel.getValueAt(i, 2).equals("Pendiente")) {
                historialModel.setValueAt("Realizado", i, 2);
                break;
            }
        }
    }

    public static void openHistorialWindow() {
        JFrame historialFrame = new JFrame("Historial");
        historialFrame.setSize(500, 300);
        historialFrame.setLayout(new BorderLayout());

        JTable table = new JTable(historialModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton volverButton = new JButton("Volver");
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                historialFrame.dispose();
                openLaboratorioWindow();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(volverButton);

        historialFrame.add(scrollPane, BorderLayout.CENTER);
        historialFrame.add(buttonPanel, BorderLayout.SOUTH);

        historialFrame.setVisible(true);
    }
}
