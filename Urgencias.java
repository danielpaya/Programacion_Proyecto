import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Urgencias extends JFrame {
    private ArrayList<Urgencias> Tipo;
    private JComboBox<String> TipoBox;
    private ArrayList<Urgencias> Sintomas;
    private JComboBox<String> SintomasBox;
    private ArrayList<Urgencias> Alertas;
    private JComboBox<String> AlertasBox;
    public JTextField numIdentificacionField, epsField, tempField, frecuenciaCardiacaField, frecuenciaRespiratoriaField, saturacionField, sistolicaField, diastolicaField;
    public DefaultTableModel tableModel;
    private JTable table;
    private ArrayList<Urgencias> Paciente;

    //Primera ventana, Triage y registro
    public Urgencias (){

        setTitle("Urgencias");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));
        setLocationRelativeTo(null);

        JButton btnTriage = new JButton("Crear Triage");
        btnTriage.addActionListener(e -> {
        abrirVentanaTriage();
        dispose();
    });
        add(btnTriage);

        JButton btnRegistro = new JButton("Registro Temporal");
        //btnRegistro.addActionListener(e -> abrirVentanaRegistro());
        add(btnRegistro);
    }

    //Estado de conciencia del paciente
    private void abrirVentanaTriage (){

        JFrame ventanaTriage = new JFrame("Urgencias");
        ventanaTriage.setSize(300, 200);
        ventanaTriage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaTriage.setLayout(new GridLayout(2, 1));
        ventanaTriage.setLocationRelativeTo(null);

        ventanaTriage.add(new JLabel("¿El paciente se encuentra consciente?", SwingConstants.CENTER));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton btnSi = new JButton("Si");
        btnSi.setPreferredSize(new Dimension(80, 40));
        btnSi.addActionListener(e -> {
            abrirVentanaSi();
            ventanaTriage.dispose();
        });
        buttonPanel.add(btnSi);

        JButton btnNo = new JButton("No");
        btnNo.setPreferredSize(new Dimension(80, 40));
        btnNo.addActionListener(e -> {
            abrirVentanaDatos2();
            ventanaTriage.dispose();
        });
        buttonPanel.add(btnNo);

        ventanaTriage.add(buttonPanel);
        
        ventanaTriage.setVisible(true);
    }

    //Si esta conciente, Identificacion y eps
    private void abrirVentanaSi (){
        Tipo = new ArrayList<>();

        JFrame ventanaSi = new JFrame("Urgencias");
        ventanaSi.setSize(400, 200);
        ventanaSi.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventanaSi.setLocationRelativeTo(null);
        ventanaSi.setLayout(new GridLayout(4, 2));

        JTextField txtNumIdentificacion = new JTextField();
        JTextField txtEPS = new JTextField();

        ventanaSi.add(new JLabel("Tipo de Identificacion:"));
        String[] Tipo = {"C.C.", "Documento de Identidad", "Pasaporte"};
        TipoBox = new JComboBox<>(Tipo);
        ventanaSi.add(TipoBox);
        ventanaSi.add(new JLabel("Numero de Identificación:"));
        numIdentificacionField = new JTextField();
        ventanaSi.add(numIdentificacionField);
        ventanaSi.add(new JLabel("EPS:"));
        epsField = new JTextField();
        ventanaSi.add(epsField);

        JButton btnSiguiente1 = new JButton("Siguiente");
        btnSiguiente1.addActionListener(e -> {
            abrirVentanaDatos1();
            ventanaSi.dispose();
        });
        ventanaSi.add(new JLabel());
        ventanaSi.add(btnSiguiente1);

        ventanaSi.setVisible(true);
    }

    //Toma de signos vitales y mas datos
    private void abrirVentanaDatos1 (){

        Paciente = new ArrayList<>();

        JFrame ventanaDatos1 = new JFrame("Urgencias");
        ventanaDatos1.setSize(1500, 500);
        ventanaDatos1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventanaDatos1.setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        
        inputPanel.add(new JLabel("Sintomas:"));
        String[] Sintomas = {"Dolor Toracico", "Dificultad Respiratoria", "Dolor Abdominal", "Traumatismo o Fractura", "Fiebre Persistente", "Reaccion Alergica", "Hemorragia no Controlada", "Mareo o Desmayo", "Dolor Lumbar", "Diarrea o Vomito", "Convulsiones", "Picadura o Mordedura", "Intoxicacion Alimenticia", "Herida Profunda o Infeccion", "Crisis Asmatica", "Crisis Hipertensiva"};
        SintomasBox = new JComboBox<>(Sintomas);
        inputPanel.add(SintomasBox);

        inputPanel.add(new JLabel("Escala de dolor (1-10):"));
        JSpinner spinnerEscalaDolor = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        inputPanel.add(spinnerEscalaDolor);

        inputPanel.add(new JLabel("Temperatura:"));
        tempField = new JTextField();
        inputPanel.add(tempField);

        inputPanel.add(new JLabel("Frecuencia Respiratoria:"));
        frecuenciaRespiratoriaField = new JTextField();
        inputPanel.add(frecuenciaRespiratoriaField);

        inputPanel.add(new JLabel("Frecuencia Cardiaca:"));
        frecuenciaCardiacaField = new JTextField();
        inputPanel.add(frecuenciaCardiacaField);

        inputPanel.add(new JLabel("Saturacion de oxigeno:"));
        saturacionField = new JTextField();
        inputPanel.add(saturacionField);

        inputPanel.add(new JLabel("Presion Sistolica:"));
        sistolicaField = new JTextField();
        inputPanel.add(sistolicaField);

        inputPanel.add(new JLabel("Presion Diastolica:"));
        diastolicaField = new JTextField();
        inputPanel.add(diastolicaField);

        JButton btnGenerarTriage = new JButton("Generar Triage");
        inputPanel.add(new JLabel());
        inputPanel.add(new JLabel());
        inputPanel.add(new JLabel());
        inputPanel.add(btnGenerarTriage);

        ventanaDatos1.add(inputPanel, BorderLayout.NORTH);

        String[] columnNames = {"T.ID.", "ID", "EPS", "Motivo de consulta", "Temperatura", "Frecuencia Respiratoria", "Frecuencia Cardiaca", "Saturacion de oxigeno", "Presion Sistolica", "Presion Diastolica", "Triage"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        ventanaDatos1.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton btnAgregarOtroPaciente = new JButton("Agregar a otro paciente");
        buttonPanel.add(btnAgregarOtroPaciente);
        btnAgregarOtroPaciente.addActionListener(e -> {
            abrirVentanaSi();
            ventanaDatos1.dispose();
        });

        JButton btnVolverInicio = new JButton("Volver al inicio");
        btnVolverInicio.addActionListener(e -> {
            ventanaDatos1.dispose();
        
            Urgencias ventana = new Urgencias();
            ventana.setVisible(true);
        });
        
        buttonPanel.add(btnVolverInicio);

        ventanaDatos1.add(buttonPanel, BorderLayout.SOUTH);

        btnGenerarTriage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipoIdentificacion;
                tipoIdentificacion = (String) TipoBox.getSelectedItem();
                String Identificacion = numIdentificacionField.getText();
                String Eps = epsField.getText();
                String tempString = tempField.getText();
                String Sintomas;
                Sintomas = (String) SintomasBox.getSelectedItem();
                float Temperatura = Float.parseFloat(tempString);
                String frecuenciaRespiratoriaString = frecuenciaRespiratoriaField.getText();
                int FrecuenciaRespiratoria = Integer.parseInt(frecuenciaRespiratoriaString);
                String frecuenciaCardiacaString = frecuenciaCardiacaField.getText();
                int FrecuenciaCardiaca = Integer.parseInt(frecuenciaCardiacaString);
                String saturacionString = saturacionField.getText();
                int Saturacion = Integer.parseInt(saturacionString);
                
                String sistolicaString = sistolicaField.getText();
                int Sistolica = Integer.parseInt(sistolicaString);

                String diastolicaString = diastolicaField.getText();
                int Diastolica = Integer.parseInt(diastolicaString);

                int Prioridad = calcularPrioridad1(Sintomas, (int) spinnerEscalaDolor.getValue(), Temperatura, FrecuenciaRespiratoria, FrecuenciaCardiaca, Saturacion, Sistolica, Diastolica);

                tableModel.addRow(new Object[]{tipoIdentificacion, Identificacion, Eps, Sintomas, Temperatura, FrecuenciaRespiratoria, FrecuenciaCardiaca, Saturacion, Sistolica, Diastolica, Prioridad});
            }
        });

        ventanaDatos1.setVisible(true);
    }

    //Calcula la prioridad
    private int calcularPrioridad1(String sintoma, int dolor, float temperatura, int frecuenciaRespiratoria, int frecuenciaCardiaca, int saturacionOxigeno, int sistolica, int diastolica) {
        int puntos = 0;
    
        // Asignación de puntos según el síntoma
        switch (sintoma) {
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
    
        // Puntos por nivel de dolor
        if (dolor >= 8) {
            puntos += 3;
        } else if (dolor >= 5) {
            puntos += 2;
        } else {
            puntos += 1;
        }
    
        // Evaluación de temperatura
        if (temperatura < 37.5 && temperatura > 36.3) {
            puntos += 0; // Normal
        } else if (temperatura < 38.5) {
            puntos += 1; // Levemente alta
        } else if (temperatura < 39.5) {
            puntos += 2; // Muy alta
        } else {
            puntos += 3; // Grave
        }
    
        // Evaluación de frecuencia respiratoria
        if (frecuenciaRespiratoria >= 12 && frecuenciaRespiratoria <= 20) {
            puntos += 0; // Normal
        } else if ((frecuenciaRespiratoria > 20 && frecuenciaRespiratoria <= 24) || (frecuenciaRespiratoria >= 10 && frecuenciaRespiratoria < 12)) {
            puntos += 1; // Levemente alterada
        } else if ((frecuenciaRespiratoria > 24 && frecuenciaRespiratoria <= 30) || (frecuenciaRespiratoria >= 8 && frecuenciaRespiratoria < 10)) {
            puntos += 2; // Muy alterada
        } else {
            puntos += 3; // Grave
        }
    
        // Evaluación de frecuencia cardíaca
        if (frecuenciaCardiaca >= 60 && frecuenciaCardiaca <= 100) {
            puntos += 0; // Normal
        } else if ((frecuenciaCardiaca > 100 && frecuenciaCardiaca <= 110) || (frecuenciaCardiaca >= 50 && frecuenciaCardiaca < 60)) {
            puntos += 1; // Levemente alterada
        } else if ((frecuenciaCardiaca > 110 && frecuenciaCardiaca <= 120) || (frecuenciaCardiaca >= 40 && frecuenciaCardiaca < 50)) {
            puntos += 2; // Muy alterada
        } else {
            puntos += 3; // Grave
        }
    
        // Evaluación de saturación de oxígeno
        if (saturacionOxigeno >= 95) {
            puntos += 0; // Normal
        } else if (saturacionOxigeno >= 90 && saturacionOxigeno < 95) {
            puntos += 1; // Levemente baja
        } else if (saturacionOxigeno >= 85 && saturacionOxigeno < 90) {
            puntos += 2; // Muy baja
        } else {
            puntos += 3; // Grave
        }
    
        // Evaluación de presión arterial sistólica
        if (sistolica >= 90 && sistolica <= 120) {
            puntos += 0; // Normal
        } else if ((sistolica > 120 && sistolica <= 140) || (sistolica >= 80 && sistolica < 90)) {
            puntos += 1; // Levemente alterada
        } else if ((sistolica > 140 && sistolica <= 180) || (sistolica >= 70 && sistolica < 80)) {
            puntos += 2; // Muy alterada
        } else {
            puntos += 3; // Grave
        }
    
        // Evaluación de presión arterial diastólica
        if (diastolica >= 60 && diastolica <= 80) {
            puntos += 0; // Normal
        } else if ((diastolica > 80 && diastolica <= 90) || (diastolica >= 50 && diastolica < 60)) {
            puntos += 1; // Levemente alterada
        } else if ((diastolica > 90 && diastolica <= 120) || (diastolica >= 40 && diastolica < 50)) {
            puntos += 2; // Muy alterada
        } else {
            puntos += 3; // Grave
        }
    
        // Determinación de la prioridad
        if (puntos >= 10) {
            return 1; // Atención inmediata
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
    

    //No esta conciente
    private void abrirVentanaDatos2 (){

        JFrame ventanaDatos1 = new JFrame("Urgencias");
        ventanaDatos1.setSize(1500, 500);
        ventanaDatos1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventanaDatos1.setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        
        inputPanel.add(new JLabel("Alertas Fisicas Visibles:"));
        String[] Alertas = {"Traumatismo", "Corte", "Quemadura", "Hemorragia", "Fractura", "Reaccion Alerjica"};
        AlertasBox = new JComboBox<>(Alertas);
        inputPanel.add(AlertasBox);

        inputPanel.add(new JLabel("Temperatura:"));
        tempField = new JTextField();
        inputPanel.add(tempField);

        inputPanel.add(new JLabel("Frecuencia Respiratoria:"));
        frecuenciaRespiratoriaField = new JTextField();
        inputPanel.add(frecuenciaRespiratoriaField);

        inputPanel.add(new JLabel("Frecuencia Cardiaca:"));
        frecuenciaCardiacaField = new JTextField();
        inputPanel.add(frecuenciaCardiacaField);

        inputPanel.add(new JLabel("Saturacion de oxigeno:"));
        saturacionField = new JTextField();
        inputPanel.add(saturacionField);

        inputPanel.add(new JLabel("Presion Sistolica:"));
        sistolicaField = new JTextField();
        inputPanel.add(sistolicaField);

        inputPanel.add(new JLabel("Presion Diastolica:"));
        diastolicaField = new JTextField();
        inputPanel.add(diastolicaField);

        JButton btnGenerarTriage2 = new JButton("Generar Triage");
        inputPanel.add(new JLabel());
        inputPanel.add(btnGenerarTriage2);

        ventanaDatos1.add(inputPanel, BorderLayout.NORTH);

        String[] columnNames = {"Identificacion Temporal", "Alertas Fisicas Visibles", "Temperatura", "Frecuencia Respiratoria", "Frecuencia Cardiaca", "Saturacion de oxigeno", "Presion Sistolica", "Presion Diastolica", "Prioridad de atencion"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        ventanaDatos1.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton btnVolverInicio = new JButton("Volver al inicio");
        btnVolverInicio.addActionListener(e -> {
            ventanaDatos1.dispose();
        
            Urgencias ventana = new Urgencias();
            ventana.setVisible(true);
        });
        
        buttonPanel.add(btnVolverInicio);

        ventanaDatos1.add(buttonPanel, BorderLayout.SOUTH);

        btnGenerarTriage2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random random = new Random();
                int identificacionTemporal = 1 + random.nextInt(50000);
                String identificacionTemporalString = String.format("%05d", identificacionTemporal);
                String tempString = tempField.getText();
                String AlertasFisicas;
                AlertasFisicas = (String) AlertasBox.getSelectedItem();
                float Temperatura = Float.parseFloat(tempString);
                String frecuenciaRespiratoriaString = frecuenciaRespiratoriaField.getText();
                int FrecuenciaRespiratoria = Integer.parseInt(frecuenciaRespiratoriaString);
                String frecuenciaCardiacaString = frecuenciaCardiacaField.getText();
                int FrecuenciaCardiaca = Integer.parseInt(frecuenciaCardiacaString);
                String saturacionString = saturacionField.getText();
                int Saturacion = Integer.parseInt(saturacionString);
                String sistolicaString = sistolicaField.getText();
                int Sistolica = Integer.parseInt(sistolicaString);
                String diastolicaString = diastolicaField.getText();
                int Diastolica = Integer.parseInt(diastolicaString);

                int Prioridad2 = calcularPrioridad2(AlertasFisicas, Temperatura, FrecuenciaRespiratoria, FrecuenciaCardiaca, Saturacion, Sistolica, Diastolica);
                
        
                tableModel.addRow(new Object[]{identificacionTemporalString, AlertasFisicas, Temperatura, FrecuenciaRespiratoria, FrecuenciaCardiaca, Saturacion, Sistolica, Diastolica, Prioridad2});
            }
        });

        ventanaDatos1.setVisible(true);
    }

    private int calcularPrioridad2(String alertaFisica, float temperatura, int frecuenciaRespiratoria, int frecuenciaCardiaca, int saturacionOxigeno, int sistolica, int diastolica) {
    int puntos = 2; // Puntos iniciales por la inconsciencia del paciente

    // Asignación de puntos según la alerta física
    switch (alertaFisica) {
        case "Traumatismo":
        case "Hemorragia no Controlada":
            puntos += 3; // Caso grave
            break;
        case "Corte":
        case "Quemadura":
        case "Fractura":
            puntos += 2; // Leve
            break;
        case "Reaccion Alerjica":
            puntos += 1; // Leve
            break;
        default:
            puntos += 0; // Sin alerta visible
            break;
    }

    // Evaluación de la temperatura
    if (temperatura < 37.5 && temperatura >= 36.3) {
        puntos += 0; // Normal
    } else if (temperatura >= 38.5 && temperatura < 39.5) {
        puntos += 2; // Alta
    } else if (temperatura >= 39.5) {
        puntos += 3; // Muy alta
    } else if (temperatura < 36.3) {
        puntos += 1; // Baja
    }

    // Evaluación de la frecuencia respiratoria
    if (frecuenciaRespiratoria >= 12 && frecuenciaRespiratoria <= 20) {
        puntos += 0; // Normal
    } else if (frecuenciaRespiratoria > 20 && frecuenciaRespiratoria <= 30) {
        puntos += 2; // Muy alterada
    } else if (frecuenciaRespiratoria < 12 || frecuenciaRespiratoria > 30) {
        puntos += 3; // Grave
    }

    // Evaluación de la frecuencia cardíaca
    if (frecuenciaCardiaca >= 60 && frecuenciaCardiaca <= 100) {
        puntos += 0; // Normal
    } else if (frecuenciaCardiaca > 100 && frecuenciaCardiaca <= 120) {
        puntos += 1; // Levemente alterada
    } else if (frecuenciaCardiaca < 60 || frecuenciaCardiaca > 120) {
        puntos += 2; // Muy alterada
    }

    // Evaluación de la saturación de oxígeno
    if (saturacionOxigeno >= 95) {
        puntos += 0; // Normal
    } else if (saturacionOxigeno >= 90 && saturacionOxigeno < 95) {
        puntos += 1; // Levemente baja
    } else if (saturacionOxigeno >= 85 && saturacionOxigeno < 90) {
        puntos += 2; // Muy baja
    } else if (saturacionOxigeno < 85) {
        puntos += 3; // Grave
    }

    // Evaluación de presión arterial sistólica
    if (sistolica >= 90 && sistolica <= 120) {
        puntos += 0; // Normal
    } else if (sistolica > 120 && sistolica <= 140) {
        puntos += 1; // Levemente alta
    } else if (sistolica > 140 && sistolica <= 180) {
        puntos += 2; // Muy alta
    } else {
        puntos += 3; // Grave
    }

    // Evaluación de presión arterial diastólica
    if (diastolica >= 60 && diastolica <= 80) {
        puntos += 0; // Normal
    } else if (diastolica > 80 && diastolica <= 90) {
        puntos += 1; // Levemente alta
    } else if (diastolica > 90 && diastolica <= 120) {
        puntos += 2; // Muy alta
    } else {
        puntos += 3; // Grave
    }

    // Determinación de la prioridad de atención
    if (puntos >= 10) {
        return 1; // Prioridad inmediata
    } else if (puntos >= 8) {
        return 2; // Alta
    } else if (puntos >= 6) {
        return 3; // Moderada
    } else if (puntos >= 4) {
        return 4; // Baja
    } else {
        return 5; // Prioridad mínima
    }
}
    

    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Urgencias ventana = new Urgencias();
            ventana.setVisible(true);
        });
    }
}