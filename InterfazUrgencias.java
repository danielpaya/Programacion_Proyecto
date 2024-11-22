import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class InterfazUrgencias extends JFrame {
    private JTable tabla1;
    private JTable tabla2;

    public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new InterfazUrgencias());
    }

    public InterfazUrgencias() {

        JFrame frame = new JFrame("Urgencias");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ImageIcon icono = new ImageIcon("Cruz_Roja.png");
        frame.setIconImage(icono.getImage());

        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new GridBagLayout());
        GridBagConstraints gbcIzquierdo = new GridBagConstraints();

        JLabel tituloTabla1 = new JLabel("Pacientes Identificados", SwingConstants.CENTER);
        tituloTabla1.setFont(new Font("Arial", Font.BOLD, 16));
        gbcIzquierdo.gridx = 0;
        gbcIzquierdo.gridy = 0;
        gbcIzquierdo.weightx = 1.0;
        gbcIzquierdo.fill = GridBagConstraints.HORIZONTAL;
        panelIzquierdo.add(tituloTabla1, gbcIzquierdo);

        String[] columnasTabla1 = {"Tipo ID", "ID", "Nombre", "Apellido", "Edad", "EPS", "Síntoma", "Prioridad", "Fecha", "Hora"};
        tabla1 = new JTable(new DefaultTableModel(null, columnasTabla1));
        JScrollPane scrollTabla1 = new JScrollPane(tabla1);
        gbcIzquierdo.gridy = 1;
        gbcIzquierdo.weighty = 0.5;
        gbcIzquierdo.fill = GridBagConstraints.BOTH;
        panelIzquierdo.add(scrollTabla1, gbcIzquierdo);

        JLabel tituloTabla2 = new JLabel("Pacientes no Identificados", SwingConstants.CENTER);
        tituloTabla2.setFont(new Font("Arial", Font.BOLD, 16));
        gbcIzquierdo.gridy = 2;
        gbcIzquierdo.weighty = 0;
        gbcIzquierdo.fill = GridBagConstraints.HORIZONTAL;
        panelIzquierdo.add(tituloTabla2, gbcIzquierdo);
        
        String[] columnasTabla2 = {"Tipo ID", "Num ID", "Motivo", "Prioridad", "Fecha", "Hora"};
        tabla2 = new JTable(new DefaultTableModel(null, columnasTabla2));
        JScrollPane scrollTabla2 = new JScrollPane(tabla2);
        gbcIzquierdo.gridy = 3;
        gbcIzquierdo.weighty = 0.5;
        gbcIzquierdo.fill = GridBagConstraints.BOTH;
        panelIzquierdo.add(scrollTabla2, gbcIzquierdo);

        JPanel panelDerecho = new JPanel(new BorderLayout());

        JPanel panelBotones = new JPanel(new GridLayout(1, 4, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JButton btnConciente = new JButton("Conciente");
        JButton btnInconciente = new JButton("Inconciente");
        JButton btnVerDatos = new JButton("Ver Signos");
        JButton btnVerTiempo = new JButton("Ver tiempo");

        btnConciente.setPreferredSize(new Dimension(120, 40));
        btnInconciente.setPreferredSize(new Dimension(120, 40));
        btnVerDatos.setPreferredSize(new Dimension(120, 40));
        btnVerTiempo.setPreferredSize(new Dimension(120, 40));

        Font font = new Font("Arial", Font.BOLD, 16);
        btnConciente.setFont(font);
        btnInconciente.setFont(font);
        btnVerDatos.setFont(font);
        btnVerTiempo.setFont(font);

        panelBotones.add(btnConciente);
        panelBotones.add(btnInconciente);
        panelBotones.add(btnVerDatos);
        panelBotones.add(btnVerTiempo);

        JPanel panelEspacioVacio = new JPanel();
        panelEspacioVacio.setLayout(new BorderLayout());

        panelDerecho.add(panelBotones, BorderLayout.NORTH);
        panelDerecho.add(panelEspacioVacio, BorderLayout.CENTER);

        btnConciente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel formulario = new JPanel(new GridLayout(14, 2, 10, 10));
                formulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                formulario.add(new JLabel("Tipo de Identificación:"));
                String[] identificacionTipo = {"C.C.", "Documento de Identidad", "Pasaporte"};
                JComboBox<String> comboIdentificacionTipo = new JComboBox<>(identificacionTipo);
                formulario.add(comboIdentificacionTipo);

                formulario.add(new JLabel("Numero de Identificación:"));
                JTextField numeroIDField = new JTextField();
                formulario.add(numeroIDField);

                formulario.add(new JLabel("Nombre:"));
                JTextField nombreField = new JTextField();
                formulario.add(nombreField);

                formulario.add(new JLabel("Apellido:"));
                JTextField apellidoField = new JTextField();
                formulario.add(apellidoField);

                formulario.add(new JLabel("Edad:"));
                JTextField edadField = new JTextField();
                filtroNumerico(edadField);
                formulario.add(edadField);

                formulario.add(new JLabel("EPS:"));
                JTextField epsField = new JTextField();
                formulario.add(epsField);

                formulario.add(new JLabel("Motivo de Consulta:"));
                String[] sintomas = {"Dolor Toracico", "Dificultad Respiratoria", "Dolor Abdominal", "Traumatismo o Fractura", "Fiebre Persistente", "Reaccion Alergica", "Hemorragia no Controlada", "Mareo o Desmayo", "Dolor Lumbar", "Diarrea o Vomito", "Convulsiones", "Picadura o Mordedura", "Intoxicacion Alimenticia", "Herida Profunda o Infeccion", "Crisis Asmatica", "Crisis Hipertensiva"};
                JComboBox<String> comboSintomas = new JComboBox<>(sintomas);
                formulario.add(comboSintomas);

                formulario.add(new JLabel("Escala de Dolor (1-10):"));
                JSpinner escalaDolor = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
                formulario.add(escalaDolor);

                formulario.add(new JLabel("Temperatura:"));
                JTextField temperaturaField = new JTextField();
                filtroNumerico2(temperaturaField);
                formulario.add(temperaturaField);

                formulario.add(new JLabel("Frecuencia Respiratoria:"));
                JTextField frecuenciaRespiratoriaField = new JTextField();
                filtroNumerico(frecuenciaRespiratoriaField);
                formulario.add(frecuenciaRespiratoriaField);

                formulario.add(new JLabel("Frecuencia Cardiaca:"));
                JTextField frecuenciaCardiacaField = new JTextField();
                filtroNumerico(frecuenciaCardiacaField);
                formulario.add(frecuenciaCardiacaField);

                formulario.add(new JLabel("Saturación:"));
                JTextField saturacionField = new JTextField();
                filtroNumerico(saturacionField);
                formulario.add(saturacionField);

                formulario.add(new JLabel("Presión Sistólica:"));
                JTextField presionSistolicaField = new JTextField();
                filtroNumerico(presionSistolicaField);
                formulario.add(presionSistolicaField);

                formulario.add(new JLabel("Presión Diastólica:"));
                JTextField presionDiastolicaField = new JTextField();
                filtroNumerico(presionDiastolicaField);
                formulario.add(presionDiastolicaField);

                JButton btnRegistrar = new JButton("Registrar Paciente");
                btnRegistrar.setPreferredSize(new Dimension(200, 50));
                JPanel panelBotonRegistrar = new JPanel(new FlowLayout(FlowLayout.CENTER));
                panelBotonRegistrar.add(btnRegistrar);

                panelEspacioVacio.removeAll();
                panelEspacioVacio.add(formulario, BorderLayout.CENTER);
                panelEspacioVacio.add(panelBotonRegistrar, BorderLayout.SOUTH);

                panelEspacioVacio.revalidate();
                panelEspacioVacio.repaint();

                btnRegistrar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String tipoID = comboIdentificacionTipo.getSelectedItem().toString();
                        String id = numeroIDField.getText();
                        String nombrePaciente = nombreField.getText();
                        String apellidoPaciente = apellidoField.getText();
                        String edadPaciente = edadField.getText();
                        String epsPaciente = epsField.getText();
                        String sintomaPaciente = comboSintomas.getSelectedItem().toString();
                        int escala = (int) escalaDolor.getValue();
                        String tempString = temperaturaField.getText();
                        float temperatura = tempString.isEmpty() ? 0 : Float.parseFloat(tempString);
                        int freqRespiratoria = frecuenciaRespiratoriaField.getText().isEmpty() ? 0 : Integer.parseInt(frecuenciaRespiratoriaField.getText());
                        int freqCardiaca = frecuenciaCardiacaField.getText().isEmpty() ? 0 : Integer.parseInt(frecuenciaCardiacaField.getText());
                        int saturacion = saturacionField.getText().isEmpty() ? 0 : Integer.parseInt(saturacionField.getText());
                        int presSistolica = presionSistolicaField.getText().isEmpty() ? 0 : Integer.parseInt(presionSistolicaField.getText());
                        int presDiastolica = presionDiastolicaField.getText().isEmpty() ? 0 : Integer.parseInt(presionDiastolicaField.getText());
                        int prioridad = calcularPrioridad1(sintomaPaciente, escala, temperatura, freqRespiratoria, freqCardiaca, saturacion, presSistolica, presDiastolica);
        
                        DefaultTableModel model = (DefaultTableModel) tabla1.getModel();
                        model.addRow(new Object[]{tipoID, id, nombrePaciente, apellidoPaciente, edadPaciente, epsPaciente, sintomaPaciente, prioridad, obtenerFechaActual(), obtenerHoraActual()});

                        guardarPaciente(tipoID, id, nombrePaciente, apellidoPaciente, edadPaciente, epsPaciente, sintomaPaciente, prioridad, temperatura, freqRespiratoria, freqCardiaca, saturacion, presSistolica, presDiastolica, true);
                        
                        comboIdentificacionTipo.setSelectedIndex(0);
                        numeroIDField.setText("");
                        nombreField.setText("");
                        apellidoField.setText("");
                        edadField.setText("");
                        epsField.setText("");
                        comboSintomas.setSelectedIndex(0);
                        escalaDolor.setValue(1);
                        temperaturaField.setText("");
                        frecuenciaRespiratoriaField.setText("");
                        frecuenciaCardiacaField.setText("");
                        saturacionField.setText("");
                        presionSistolicaField.setText("");
                        presionDiastolicaField.setText("");
                    }
                });
            }


            private int calcularPrioridad1(String sintomaPaciente, int escalaDolor, float temperatura, int freqRespiratoria, int freqCardiaca, int saturacion, int presSistolica, int presDiastolica) {
                int puntos =  0;

                switch (sintomaPaciente) {
                    case "Dolor Toracico":
                    case "Dificultad Respiratoria":
                    case "Convulsiones":
                    case "Hemorragia no Controlada":
                        puntos += 3;
                        break;
                    case "Dolor Abdominal":
                    case "Traumatismo o Fractura":
                    case "Crisis Asmatica":
                    case "Crisis Hipertensiva":
                        puntos += 2;
                        break;
                    default:
                        puntos += 1;
                        break;
                }

                if (escalaDolor >= 8) {
                    puntos += 3;
                } else if (escalaDolor >= 5) {
                    puntos += 2;
                } else {
                    puntos += 1;
                }

                if (temperatura < 37.5 && temperatura > 36.3) {
                    puntos += 0;
                } else if (temperatura < 38.5) {
                    puntos += 1;
                } else if (temperatura < 39.5) {
                    puntos += 2; 
                } else {
                    puntos += 3;
                }

                if (freqRespiratoria >= 12 && freqRespiratoria <= 20) {
                    puntos += 0;
                } else if ((freqRespiratoria > 20 && freqRespiratoria <= 24) || (freqRespiratoria >= 10 && freqRespiratoria < 12)) {
                    puntos += 1;
                } else if ((freqRespiratoria > 24 && freqRespiratoria <= 30) || (freqRespiratoria >= 8 && freqRespiratoria < 10)) {
                    puntos += 2;
                } else {
                    puntos += 3;
                }

                if (freqCardiaca >= 60 && freqCardiaca <= 100) {
                    puntos += 0;
                } else if ((freqCardiaca > 100 && freqCardiaca <= 110) || (freqCardiaca >= 50 && freqCardiaca < 60)) {
                    puntos += 1;
                } else if ((freqCardiaca > 110 && freqCardiaca <= 120) || (freqCardiaca >= 40 && freqCardiaca < 50)) {
                    puntos += 2;
                } else {
                    puntos += 3;
                }

                if (saturacion >= 95) {
                    puntos += 0;
                } else if (saturacion >= 90 && saturacion < 95) {
                    puntos += 1;
                } else if (saturacion >= 85 && saturacion < 90) {
                    puntos += 2;
                } else {
                    puntos += 3;
                }

                if (presSistolica >= 90 && presSistolica <= 120) {
                    puntos += 0;
                } else if ((presSistolica > 120 && presSistolica <= 140) || (presSistolica >= 80 && presSistolica < 90)) {
                    puntos += 1;
                } else if ((presSistolica > 140 && presSistolica <= 180) || (presSistolica >= 70 && presSistolica < 80)) {
                    puntos += 2;
                } else {
                    puntos += 3;
                }

                if (presDiastolica >= 60 && presDiastolica <= 80) {
                    puntos += 0;
                } else if ((presDiastolica > 80 && presDiastolica <= 90) || (presDiastolica >= 50 && presDiastolica < 60)) {
                    puntos += 1;
                } else if ((presDiastolica > 90 && presDiastolica <= 120) || (presDiastolica >= 40 && presDiastolica < 50)) {
                    puntos += 2;
                } else {
                    puntos += 3;
                }

                if (puntos >= 10) {
                    return 1;
                } else if (puntos >= 8) {
                    return 2;
                } else if (puntos >= 6) {
                    return 3;
                } else if (puntos >= 4) {
                    return 4;
                } else {
                    return 5;
                }
            }
        });

        

        btnInconciente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel formularioInconciente = new JPanel(new GridLayout(8, 2, 10, 10));
                formularioInconciente.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                formularioInconciente.add(new JLabel("Motivo:"));
                String[] motivo = {"Accidente de trafico", "Desmayo repentino", "Caida", "Accidente domestico", "Victima de agresion", "Otros", "No especificado"};
                JComboBox<String> motivoComboBox = new JComboBox<>(motivo);
                formularioInconciente.add(motivoComboBox);
                 
                formularioInconciente.add(new JLabel("Alertas Físicas Visibles:"));
                String[] alertas = {"Traumatismo", "Corte", "Quemadura", "Hemorragia", "Fractura", "Reaccion Alerjica", "Ninguno"};
                JComboBox<String> alertasComboBox = new JComboBox<>(alertas);
                formularioInconciente.add(alertasComboBox);
        
                formularioInconciente.add(new JLabel("Temperatura:"));
                JTextField temperaturaField = new JTextField();
                filtroNumerico2(temperaturaField);
                formularioInconciente.add(temperaturaField);
        
                formularioInconciente.add(new JLabel("Frecuencia Respiratoria:"));
                JTextField frecuenciaRespiratoriaField = new JTextField();
                filtroNumerico(frecuenciaRespiratoriaField);
                formularioInconciente.add(frecuenciaRespiratoriaField);
        
                formularioInconciente.add(new JLabel("Frecuencia Cardiaca:"));
                JTextField frecuenciaCardiacaField = new JTextField();
                filtroNumerico(frecuenciaCardiacaField);
                formularioInconciente.add(frecuenciaCardiacaField);
        
                formularioInconciente.add(new JLabel("Saturación:"));
                JTextField saturacionField = new JTextField();
                filtroNumerico(saturacionField);
                formularioInconciente.add(saturacionField);
        
                formularioInconciente.add(new JLabel("Presión Sistólica:"));
                JTextField presionSistolicaField = new JTextField();
                filtroNumerico(presionSistolicaField);
                formularioInconciente.add(presionSistolicaField);
        
                formularioInconciente.add(new JLabel("Presión Diastólica:"));
                JTextField presionDiastolicaField = new JTextField();
                filtroNumerico(presionDiastolicaField);
                formularioInconciente.add(presionDiastolicaField);
        
                JButton btnRegistrarInconciente = new JButton("Registrar Paciente");
                btnRegistrarInconciente.setPreferredSize(new Dimension(200, 50));
                JPanel panelBotonRegistrar = new JPanel(new FlowLayout(FlowLayout.CENTER));
                panelBotonRegistrar.add(btnRegistrarInconciente);
        
                panelEspacioVacio.removeAll();
                panelEspacioVacio.add(formularioInconciente, BorderLayout.CENTER);
                panelEspacioVacio.add(panelBotonRegistrar, BorderLayout.SOUTH);
        
                panelEspacioVacio.revalidate();
                panelEspacioVacio.repaint();

                btnRegistrarInconciente.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String idAleatorio = "NI-" + (new java.util.Random().nextInt(50000) + 1);
                        String motivo = motivoComboBox.getSelectedItem().toString();
                        String alerta = alertasComboBox.getSelectedItem().toString();
                        String tempString = temperaturaField.getText();
                        float temperatura = tempString.isEmpty() ? 0 : Float.parseFloat(tempString);
                        int freqRespiratoria = frecuenciaRespiratoriaField.getText().isEmpty() ? 0 : Integer.parseInt(frecuenciaRespiratoriaField.getText());
                        int freqCardiaca = frecuenciaCardiacaField.getText().isEmpty() ? 0 : Integer.parseInt(frecuenciaCardiacaField.getText());
                        int saturacion = saturacionField.getText().isEmpty() ? 0 : Integer.parseInt(saturacionField.getText());
                        int presSistolica = presionSistolicaField.getText().isEmpty() ? 0 : Integer.parseInt(presionSistolicaField.getText());
                        int presDiastolica = presionDiastolicaField.getText().isEmpty() ? 0 : Integer.parseInt(presionDiastolicaField.getText());
                        int prioridad = calcularPrioridad2(motivo, alerta, temperatura, freqRespiratoria, freqCardiaca, saturacion, presSistolica, presDiastolica);
        
                        DefaultTableModel model = (DefaultTableModel) tabla2.getModel();
                        model.addRow(new Object[]{"Temporal", idAleatorio, motivo, prioridad, obtenerFechaActual(), obtenerHoraActual()});

                        guardarPaciente("Temporal", idAleatorio, "N/A", "N/A", "N/A", "N/A", motivo, prioridad, temperatura, freqRespiratoria, freqCardiaca, saturacion, presSistolica, presDiastolica, false);
                        
                        motivoComboBox.setSelectedIndex(0);
                        alertasComboBox.setSelectedIndex(0);
                        temperaturaField.setText("");
                        frecuenciaRespiratoriaField.setText("");
                        frecuenciaCardiacaField.setText("");
                        saturacionField.setText("");
                        presionSistolicaField.setText("");
                        presionDiastolicaField.setText("");
                    }
                });
            }
            

            private int calcularPrioridad2(String motivo, String alerta, float temperatura, int freqRespiratoria, int freqCardiaca, int saturacion, int presSistolica, int presDiastolica) {
                int puntos = 5;
                
                switch (motivo) {
                    case "Accidente de trafico":
                        puntos += 4;
                        break;
                    case "Desmayo repentino":
                    case "Victima de agresion":
                    case "Accidente domestico":
                        puntos += 2;
                        break;
                    default:
                        puntos += 0;
                        break;
                }

                switch (alerta) {
                    case "Traumatismo":
                    case "Hemorragia":
                        puntos += 4;
                        break;
                    case "Corte":
                    case "Quemadura":
                    case "Fractura":
                        puntos += 2;
                        break;
                    case "Reaccion Alerjica":
                        puntos += 1;
                        break;
                    default:
                        puntos += 0;
                        break;
                }
                
                if (temperatura < 37.5 && temperatura > 36.3) {
                    puntos += 0;
                } else if (temperatura < 38.5) {
                    puntos += 1;
                } else if (temperatura < 39.5) {
                    puntos += 2; 
                } else {
                    puntos += 3;
                }
                
                if (freqRespiratoria >= 12 && freqRespiratoria <= 20) {
                    puntos += 0;
                } else if ((freqRespiratoria > 20 && freqRespiratoria <= 24) || (freqRespiratoria >= 10 && freqRespiratoria < 12)) {
                    puntos += 1;
                } else if ((freqRespiratoria > 24 && freqRespiratoria <= 30) || (freqRespiratoria >= 8 && freqRespiratoria < 10)) {
                    puntos += 2;
                } else {
                    puntos += 3;
                }
                
                if (freqCardiaca >= 60 && freqCardiaca <= 100) {
                    puntos += 0;
                } else if ((freqCardiaca > 100 && freqCardiaca <= 110) || (freqCardiaca >= 50 && freqCardiaca < 60)) {
                    puntos += 1;
                } else if ((freqCardiaca > 110 && freqCardiaca <= 120) || (freqCardiaca >= 40 && freqCardiaca < 50)) {
                    puntos += 2;
                } else {
                    puntos += 3;
                }
                
                if (saturacion >= 95) {
                    puntos += 0;
                } else if (saturacion >= 90 && saturacion < 95) {
                    puntos += 1;
                } else if (saturacion >= 85 && saturacion < 90) {
                    puntos += 2;
                } else {
                    puntos += 3;
                }
                
                if (presSistolica >= 90 && presSistolica <= 120) {
                    puntos += 0;
                } else if ((presSistolica > 120 && presSistolica <= 140) || (presSistolica >= 80 && presSistolica < 90)) {
                    puntos += 1;
                } else if ((presSistolica > 140 && presSistolica <= 180) || (presSistolica >= 70 && presSistolica < 80)) {
                    puntos += 2;
                } else {
                    puntos += 3;
                }
                
                if (presDiastolica >= 60 && presDiastolica <= 80) {
                    puntos += 0;
                } else if ((presDiastolica > 80 && presDiastolica <= 90) || (presDiastolica >= 50 && presDiastolica < 60)) {
                    puntos += 1;
                } else if ((presDiastolica > 90 && presDiastolica <= 120) || (presDiastolica >= 40 && presDiastolica < 50)) {
                    puntos += 2;
                } else {
                    puntos += 3;
                }
                
                if (puntos >= 10) {
                    return 1;
                } else if (puntos >= 8) {
                    return 2;
                } else if (puntos >= 6){
                    return 3;
                } else if (puntos >= 4) {
                    return 4;
                } else {
                    return 5;
                }
            }

            
        });

        btnVerDatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable tablaSeleccionada = (tabla1.getSelectedRow() != -1) ? tabla1 : tabla2;
                int filaSeleccionada = tablaSeleccionada.getSelectedRow();
        
                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un paciente de una tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }
        
                String idSeleccionado = tablaSeleccionada.getValueAt(filaSeleccionada, 1).toString();
                mostrarVentanaSignos(idSeleccionado);
            }
        });

        btnVerTiempo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable tablaSeleccionada;
                int filaSeleccionada;
        
                if (tabla1.getSelectedRow() != -1) {
                    tablaSeleccionada = tabla1;
                    filaSeleccionada = tabla1.getSelectedRow();
                } else if (tabla2.getSelectedRow() != -1) {
                    tablaSeleccionada = tabla2;
                    filaSeleccionada = tabla2.getSelectedRow();
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un paciente.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }
        
                try {
                    int colFecha = (tablaSeleccionada == tabla1) ? 8 : 4;
                    int colHora = (tablaSeleccionada == tabla1) ? 9 : 5;
                    int colPrioridad = (tablaSeleccionada == tabla1) ? 7 : 3;
                    int colID = 1;

                    String idPaciente = tablaSeleccionada.getValueAt(filaSeleccionada, colID).toString();
                    String fechaIngreso = tablaSeleccionada.getValueAt(filaSeleccionada, colFecha).toString();
                    String horaIngreso = tablaSeleccionada.getValueAt(filaSeleccionada, colHora).toString();
                    String prioridadActual = tablaSeleccionada.getValueAt(filaSeleccionada, colPrioridad).toString();

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime ingreso = LocalDateTime.parse(fechaIngreso + " " + horaIngreso, formatter);

                    Duration tiempoTranscurrido = Duration.between(ingreso, LocalDateTime.now());
                    long horas = tiempoTranscurrido.toHours();
                    long minutos = tiempoTranscurrido.toMinutes() % 60;
        
                    JFrame ventanaTiempo = new JFrame("Tiempo en Urgencias");
                    ventanaTiempo.setSize(400, 110);
                    ventanaTiempo.setLocationRelativeTo(null);
                    ventanaTiempo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    ImageIcon icono = new ImageIcon("Cruz_Roja.png");
                    ventanaTiempo.setIconImage(icono.getImage());
        
                    JPanel panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
                    panel.add(new JLabel("Tiempo en urgencias: " + horas + " horas y " + minutos + " minutos."));
                    panel.add(Box.createRigidArea(new Dimension(0, 10)));

                    JButton btnModificarPrioridad = new JButton("Modificar Prioridad");
                    btnModificarPrioridad.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String nuevaPrioridad = JOptionPane.showInputDialog(ventanaTiempo, "Ingrese la nueva prioridad (1-5):", prioridadActual);
        
                            if (nuevaPrioridad != null && !nuevaPrioridad.trim().isEmpty()) {
                                try {
                                    int prioridad = Integer.parseInt(nuevaPrioridad);
                                    if (prioridad >= 1 && prioridad <= 5) {
                                        tablaSeleccionada.setValueAt(prioridad, filaSeleccionada, colPrioridad);
                                        actualizarPrioridadEnCSV(idPaciente, prioridad);
                                        JOptionPane.showMessageDialog(ventanaTiempo, "Prioridad actualizada");
                                    } else {
                                        JOptionPane.showMessageDialog(ventanaTiempo, "Prioridad debe estar en el rango 1 - 5", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                } catch (NumberFormatException ex) {
                                    JOptionPane.showMessageDialog(ventanaTiempo, "Numero invalido, intente de nuevo", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    });
        
                    panel.add(btnModificarPrioridad);
                    ventanaTiempo.add(panel);
                    ventanaTiempo.setVisible(true);
        
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al procesar los datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        
        cargarDatosTablas();


        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelDerecho);
        splitPane.setDividerLocation(0.75);
        splitPane.setContinuousLayout(true);
        splitPane.setResizeWeight(0.75);

        frame.add(splitPane);
        frame.setVisible(true);

    }

    private void actualizarPrioridadEnCSV(String idPaciente, int nuevaPrioridad) {
        File archivoOriginal = new File("pacientes.csv");
        File archivoTemporal = new File("pacientes_temp.csv");

        try (BufferedReader br = new BufferedReader(new FileReader(archivoOriginal));
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivoTemporal))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",", -1);

                if (datos[1].equalsIgnoreCase(idPaciente)) {
                    datos[7] = String.valueOf(nuevaPrioridad);
                }

                bw.write(String.join(",", datos));
                bw.newLine();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar la prioridad en el archivo CSV: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!archivoOriginal.delete()) {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar el archivo original.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!archivoTemporal.renameTo(archivoOriginal)) {
            JOptionPane.showMessageDialog(this, "No se pudo renombrar el archivo temporal.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void guardarPaciente(String tipoID, String id, String nombre, String apellido, String edad, String eps, String sintoma, int prioridad, float temperatura, int freqRespiratoria, int freqCardiaca, int saturacion, int presSistolica, int presDiastolica, boolean esConciente) {
        try {
            // Construir el nombre del archivo de historial clínico
            String historialFileName = id + "_historial.csv";
            File historialFile = new File(historialFileName);
    
            // Obtener la fecha y hora actual
            String fechaHora = obtenerFechaActual() + " " + obtenerHoraActual();
            String lugar = "Urgencias";
            String observaciones = "Se le dio un triage de: " + prioridad;
    
            // Escribir en el archivo del historial clínico
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(historialFile, true))) {
                // Agregar encabezado si el archivo está vacío
                if (historialFile.length() == 0) {
                    writer.write("Fecha/Hora,Lugar,Observaciones\n");
                }
    
                // Agregar nueva entrada al historial
                writer.write(fechaHora + "," + lugar + "," + observaciones + "\n");
            }
    
            // También escribir en el archivo global de pacientes (pacientes.csv)
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("pacientes.csv", true))) {
                String fecha = obtenerFechaActual();
                String hora = obtenerHoraActual();
    
                // Agregar datos del paciente al archivo de pacientes
                writer.write(tipoID + "," + (id.isEmpty() ? "N/A" : id) + "," + (nombre.isEmpty() ? "N/A" : nombre) + ","
                    + (apellido.isEmpty() ? "N/A" : apellido) + "," + (edad.isEmpty() ? "N/A" : edad) + "," + (eps.isEmpty() ? "N/A" : eps) + ","
                    + (sintoma.isEmpty() ? "N/A" : sintoma) + "," + prioridad + "," + (temperatura == 0 ? "N/A" : temperatura) + ","
                    + (freqRespiratoria == 0 ? "N/A" : freqRespiratoria) + "," + (freqCardiaca == 0 ? "N/A" : freqCardiaca) + ","
                    + (saturacion == 0 ? "N/A" : saturacion) + "," + (presSistolica == 0 ? "N/A" : presSistolica) + ","
                    + (presDiastolica == 0 ? "N/A" : presDiastolica) + "," + fecha + "," + hora + "\n");
            }
    
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar el historial clínico: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizarHistorialPaciente(String idPaciente, String fecha, String hora, int prioridad) {
        String nombreArchivo = idPaciente + "_historial.csv"; // Formato del archivo del historial personal
        File archivoHistorial = new File(nombreArchivo);
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoHistorial, true))) {
            if (!archivoHistorial.exists() || archivoHistorial.length() == 0) {
                // Si el archivo no existe o está vacío, agregar encabezados
                writer.write("Fecha/Hora\tLugar\tObservaciones");
                writer.newLine();
            }
            // Combinar Fecha y Hora en un solo campo separado por un espacio
            String fechaHora = fecha + " " + hora;
            // Escribir la nueva entrada en el historial
            writer.write(fechaHora + "\tUrgencias\tSe le dio un triage de: " + prioridad);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el historial del paciente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private String obtenerFechaActual() {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return ahora.format(formatoFecha);
    }

    private String obtenerHoraActual() {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");
        return ahora.format(formatoHora);
    }

    
    private static void filtroNumerico(JTextField textField) {
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
           }
        });
    }

    private static void filtroNumerico2(JTextField textField) {
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '.') {
                    e.consume();
                }
            }
        });
    }

    private void mostrarVentanaSignos(String idSeleccionado) {
        try (BufferedReader br = new BufferedReader(new FileReader("pacientes.csv"))) {
            String linea;
            String[] datosPaciente = null;
    
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
    
                if (datos[1].equalsIgnoreCase(idSeleccionado)) {
                    datosPaciente = datos;
                    break;
                }
            }
    
            if (datosPaciente == null) {
                JOptionPane.showMessageDialog(this, "No se encontraron datos para el paciente seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            JFrame ventanaSignos = new JFrame("Datos del Paciente");
            ventanaSignos.setSize(300, 205);
            ventanaSignos.setLocationRelativeTo(this);
            ventanaSignos.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
            JPanel panelDatos = new JPanel();
            panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.Y_AXIS));
            panelDatos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
            panelDatos.add(new JLabel("ID del Paciente: " + datosPaciente[1]));
            panelDatos.add(Box.createRigidArea(new Dimension(0, 10)));
            ImageIcon icono = new ImageIcon("Cruz_Roja.png");
            
    
            String[] etiquetasSignos = {"Frecuencia Respiratoria:", "Frecuencia Cardiaca:", "Saturación:", "Presión Sistólica:", "Presión Diastólica:", "Fecha de registro:", "Hora:"};
    
            int[] indicesSignos = {9, 10, 11, 12, 13, 14, 15}; 
    
            for (int i = 0; i < etiquetasSignos.length; i++) {
                panelDatos.add(new JLabel(etiquetasSignos[i] + " " + datosPaciente[indicesSignos[i]]));
            }
            
            ventanaSignos.setIconImage(icono.getImage());
            ventanaSignos.add(panelDatos);
            ventanaSignos.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al leer los datos del paciente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
            
            

    private void cargarDatosTablas() {
        DefaultTableModel modeloIdentificados = (DefaultTableModel) tabla1.getModel();
        DefaultTableModel modeloNoIdentificados = (DefaultTableModel) tabla2.getModel();
    
        modeloIdentificados.setRowCount(0); 
        modeloNoIdentificados.setRowCount(0); // Limpiar datos previos
    
        try (BufferedReader br = new BufferedReader(new FileReader("pacientes.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
    
                if (datos.length >= 16) {
                    if ("Temporal".equalsIgnoreCase(datos[0])) {
                        modeloNoIdentificados.addRow(new Object[]{datos[0], datos[1], datos[6], datos[7], datos[14], datos[15]});
                    } else {
                        modeloIdentificados.addRow(new Object[]{datos[0], datos[1], datos[2], datos[3], datos[4], datos[5], datos[6], datos[7], datos[14], datos[15]});
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
