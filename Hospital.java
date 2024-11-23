import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Hospital {

    // Modelo de tabla para almacenar el historial
    private static DefaultTableModel historialModel = new DefaultTableModel(new String[]{"Nombre", "Examen", "Estado", "ID", "Resumen Doctor"}, 0);
    private static int pacienteSeleccionado = -1; // Variable para almacenar el índice del paciente seleccionado

    public static void main(String[] args) {
        // Leer pacientes desde un archivo CSV
        cargarPacientesDesdeCSV("pacientes_laboratorios.csv");
        
        // Iniciar la ventana principal de laboratorio
        openLaboratorioWindow();
    }

    // Método para cargar pacientes desde un archivo CSV
    private static void cargarPacientesDesdeCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true; // Variable para omitir la primera línea si es un encabezado
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Saltar la primera línea
                    continue;
                }
                // Dividir la línea en columnas usando coma como separador
                String[] data = line.split(",");
                if (data.length >= 3) { // Verificar que haya al menos 3 columnas
                    String id = data[0].trim();
                    String nombre = data[1].trim();
                    String examen = data[2].trim();
                    
                    // Agregar datos al modelo de tabla
                    historialModel.addRow(new Object[]{nombre, examen, "Pendiente", id, ""});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, 
                "Error al leer el archivo CSV. Verifica la ruta y el contenido del archivo.\nError: " + e.getMessage(),
                "Error de Lectura",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // Ventana principal de Laboratorio
    public static void openLaboratorioWindow() {
        JFrame laboratorioFrame = new JFrame("Laboratorio");
        laboratorioFrame.setSize(800, 600); // Ajustar tamaño para incluir imagen y botones
        laboratorioFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        laboratorioFrame.setLayout(new BorderLayout());
    
        laboratorioFrame.getContentPane().setBackground(new Color(240, 248, 255));
    
        // Cargar la imagen
        ImageIcon logoImage = new ImageIcon("logo.jpg"); // Ruta de la imagen
        Image scaledImage = logoImage.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH); // Escalar imagen
        JLabel logoLabel = new JLabel(new ImageIcon(scaledImage), SwingConstants.CENTER);
    
        // Panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10)); // Organizar los botones verticalmente
    
        // Botón Exámenes de Sangre
        JButton btnExamenesSangre = new JButton("Exámenes de Sangre");
        btnExamenesSangre.setPreferredSize(new Dimension(250, 60));
        btnExamenesSangre.setBackground(new Color(60, 179, 113));
        btnExamenesSangre.setForeground(Color.WHITE);
        btnExamenesSangre.setFont(new Font("Arial", Font.BOLD, 20));
        btnExamenesSangre.setFocusPainted(false);
        btnExamenesSangre.setBorder(BorderFactory.createRaisedBevelBorder());
    
        // Botón Historial
        JButton btnHistorial = new JButton("Historial");
        btnHistorial.setPreferredSize(new Dimension(250, 60));
        btnHistorial.setBackground(new Color(60, 179, 113));
        btnHistorial.setForeground(Color.WHITE);
        btnHistorial.setFont(new Font("Arial", Font.BOLD, 20));
        btnHistorial.setFocusPainted(false);
        btnHistorial.setBorder(BorderFactory.createRaisedBevelBorder());
    
        // Botón Radiografías
        JButton btnRadiografias = new JButton("Radiografías");
        btnRadiografias.setPreferredSize(new Dimension(250, 60));
        btnRadiografias.setBackground(new Color(60, 179, 113));
        btnRadiografias.setForeground(Color.WHITE);
        btnRadiografias.setFont(new Font("Arial", Font.BOLD, 20));
        btnRadiografias.setFocusPainted(false);
        btnRadiografias.setBorder(BorderFactory.createRaisedBevelBorder());
    
        // Botón Salir
        JButton btnSalir = new JButton("Salir");
        btnSalir.setPreferredSize(new Dimension(250, 60));
        btnSalir.setBackground(new Color(220, 20, 60));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFont(new Font("Arial", Font.BOLD, 20));
        btnSalir.setFocusPainted(false);
        btnSalir.setBorder(BorderFactory.createRaisedBevelBorder());
    
        // Agregar acciones a los botones
        btnExamenesSangre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                laboratorioFrame.dispose();
                openSeleccionarPacienteWindow();
            }
        });
    
        btnHistorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openHistorialWindow();
            }
        });
    
        btnRadiografias.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                laboratorioFrame.dispose();
                openSeleccionarPacienteParaRadiografias();
            }
        });
    
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(
                    laboratorioFrame,
                    "¿Estás seguro que deseas salir?",
                    "Confirmación de Salida",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );
                if (response == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    
        // Agregar botones al panel
        buttonPanel.add(btnExamenesSangre);
        buttonPanel.add(btnHistorial);
        buttonPanel.add(btnRadiografias);
        buttonPanel.add(btnSalir);
    
        // Agregar imagen y botones al marco
        laboratorioFrame.add(logoLabel, BorderLayout.NORTH); // Imagen en la parte superior
        laboratorioFrame.add(buttonPanel, BorderLayout.CENTER); // Botones en el centro
    
        laboratorioFrame.setVisible(true);
    }

    // Ventana para seleccionar un paciente de la lista del historial
    public static void openSeleccionarPacienteWindow() {
        JFrame seleccionFrame = new JFrame("Seleccionar Paciente");
        seleccionFrame.setSize(600, 400);
        seleccionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        seleccionFrame.setLayout(new BorderLayout());

        JTable pacientesTable = new JTable(historialModel);
        JScrollPane scrollPane = new JScrollPane(pacientesTable);
        seleccionFrame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton seleccionarButton = new JButton("Seleccionar");
        JButton cancelarButton = new JButton("Cancelar");

        // Acción para seleccionar el paciente
        seleccionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = pacientesTable.getSelectedRow();
                if (selectedRow != -1) {
                    pacienteSeleccionado = selectedRow;  // Guardar el índice del paciente seleccionado
                    String nombre = pacientesTable.getValueAt(selectedRow, 0).toString();
                    String id = pacientesTable.getValueAt(selectedRow, 3).toString();
                    String examen = pacientesTable.getValueAt(selectedRow, 1).toString();

                    seleccionFrame.dispose();
                    openEncuestaWindow(nombre, id, examen); // Llamar a la ventana de Encuesta con los datos del paciente
                } else {
                    JOptionPane.showMessageDialog(seleccionFrame, "Por favor, selecciona un paciente.");
                }
            }
        });

        // Acción para cancelar la selección
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionFrame.dispose();
                openLaboratorioWindow(); // Regresar a la ventana principal de laboratorio
            }
        });

        buttonPanel.add(seleccionarButton);
        buttonPanel.add(cancelarButton);
        seleccionFrame.add(buttonPanel, BorderLayout.SOUTH);

        seleccionFrame.setVisible(true);
    }

    // Ventana de Radiografías
    public static void openRadiografiasWindow() {
        JFrame radiografiasFrame = new JFrame("Radiografías");
        radiografiasFrame.setSize(600, 500);
        radiografiasFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        radiografiasFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        radiografiasFrame.getContentPane().setBackground(new Color(240, 248, 255));

        // Botón Piernas
        JButton btnPiernas = new JButton("Piernas");
        btnPiernas.setPreferredSize(new Dimension(250, 60));
        btnPiernas.setBackground(new Color(60, 179, 113));
        btnPiernas.setForeground(Color.WHITE);
        btnPiernas.setFont(new Font("Arial", Font.BOLD, 20));
        btnPiernas.setFocusPainted(false);
        btnPiernas.setBorder(BorderFactory.createRaisedBevelBorder());

        

        // Botón Brazos
        JButton btnBrazos = new JButton("Brazos");
        btnBrazos.setPreferredSize(new Dimension(250, 60));
        btnBrazos.setBackground(new Color(60, 179, 113));
        btnBrazos.setForeground(Color.WHITE);
        btnBrazos.setFont(new Font("Arial", Font.BOLD, 20));
        btnBrazos.setFocusPainted(false);
        btnBrazos.setBorder(BorderFactory.createRaisedBevelBorder());

        // Botón Torso
        JButton btnTorso = new JButton("Torso");
        btnTorso.setPreferredSize(new Dimension(250, 60));
        btnTorso.setBackground(new Color(60, 179, 113));
        btnTorso.setForeground(Color.WHITE);
        btnTorso.setFont(new Font("Arial", Font.BOLD, 20));
        btnTorso.setFocusPainted(false);
        btnTorso.setBorder(BorderFactory.createRaisedBevelBorder());

        // Botón Cabeza
        JButton btnCabeza = new JButton("Cabeza");
        btnCabeza.setPreferredSize(new Dimension(250, 60));
        btnCabeza.setBackground(new Color(60, 179, 113));
        btnCabeza.setForeground(Color.WHITE);
        btnCabeza.setFont(new Font("Arial", Font.BOLD, 20));
        btnCabeza.setFocusPainted(false);
        btnCabeza.setBorder(BorderFactory.createRaisedBevelBorder());

        // Acción para el botón de Piernas
        btnPiernas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radiografiasFrame.dispose(); // Cerrar la ventana de Radiografías
                openPatasWindow(); // Abrir la ventana de Piernas
            }
        });

        // Acción para el botón de Brazos
        btnBrazos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radiografiasFrame.dispose(); // Cerrar la ventana de Radiografías
                openManitosWindow(); // Abrir la ventana de Piernas
            }
        });

        // Acción para el botón de Torso
        btnTorso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radiografiasFrame.dispose(); // Cerrar la ventana de Radiografías
                openPeshitoWindow(); // Abrir la ventana de Torso
            }
        });

        // Acción para el botón de Cabeza
        btnCabeza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radiografiasFrame.dispose(); // Cerrar la ventana de Radiografías
                openCabecillaWindow(); // Abrir la ventana de Cabeza
            }
        });

        // Agregar los botones a la ventana
        radiografiasFrame.add(btnPiernas);
        radiografiasFrame.add(btnBrazos);
        radiografiasFrame.add(btnTorso);
        radiografiasFrame.add(btnCabeza);

        radiografiasFrame.setVisible(true);
    }

    // Ventana de Encuesta de Laboratorio
    public static void openEncuestaWindow(String nombre, String id, String examen) {
        JFrame encuestaFrame = new JFrame("Encuesta de Laboratorio");
        encuestaFrame.setSize(600, 800);  // Aumentar tamaño de la ventana
        encuestaFrame.setLayout(new BorderLayout());
        encuestaFrame.getContentPane().setBackground(new Color(240, 248, 255));

        JLabel titulo = new JLabel("Paciente: " + nombre + " | ID: " + id, SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        encuestaFrame.add(titulo, BorderLayout.NORTH);

        JPanel encuestaPanel = new JPanel();
        encuestaPanel.setLayout(new GridLayout(12, 2, 10, 10));  // Ajustar tamaño para agregar más filas

        JTextField[] fields = new JTextField[10];
        String[] labels = {
            "Dolor (1-11):", "Ubicación del dolor:", "Presión corporal:", "Temperatura:",
            "Saturación:", "Oxigenación:", "Nivel de Hemoglobina:", "Nivel de Proteína:",
            "Nivel de Plaquetas:", "Nivel de Glóbulos Rojos y Blancos:"
        };

        // Agregar campos para los datos clínicos
        for (int i = 0; i < labels.length; i++) {
            encuestaPanel.add(new JLabel(labels[i]));
            fields[i] = new JTextField();
            encuestaPanel.add(fields[i]);
        }

        // Agregar campo para "Resumen Doctor"
        encuestaPanel.add(new JLabel("Resumen Doctor:"));
        JTextArea resumenDoctorArea = new JTextArea(5, 40); // Ámbito de texto para el resumen del doctor
        resumenDoctorArea.setLineWrap(true);
        resumenDoctorArea.setWrapStyleWord(true);
        resumenDoctorArea.setFont(new Font("Arial", Font.PLAIN, 14));
        resumenDoctorArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JScrollPane resumenDoctorScroll = new JScrollPane(resumenDoctorArea);
        encuestaPanel.add(resumenDoctorScroll);

        encuestaFrame.add(encuestaPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton volverButton = new JButton("Volver");
        volverButton.setBackground(new Color(220, 20, 60));
        volverButton.setForeground(Color.WHITE);

        JButton siguienteButton = new JButton("Siguiente");
        siguienteButton.setBackground(new Color(60, 179, 113));
        siguienteButton.setForeground(Color.WHITE);

        buttonPanel.add(volverButton);
        buttonPanel.add(siguienteButton);

        encuestaFrame.add(buttonPanel, BorderLayout.SOUTH);

        // Acción del botón "Volver"
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encuestaFrame.dispose();  // Cerrar la ventana de encuesta
                openLaboratorioWindow();  // Regresar a la ventana de laboratorio
            }
        });

        // Acción del botón "Siguiente"
        siguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mostrar el resumen médico y actualizar la fila del paciente
                openResumenMedico(nombre, id, fields, resumenDoctorArea.getText());
            }
        });

        encuestaFrame.setVisible(true);
    }

    // Ventana de Resumen Médico
    public static void openResumenMedico(String nombre, String id, JTextField[] fields, String resumenDoctor) {
        // Actualizar el estado del paciente y agregar el resumen doctor
        if (pacienteSeleccionado != -1) {
            // Actualizar estado del paciente a "Completado"
            historialModel.setValueAt("Completado", pacienteSeleccionado, 2);
            // Actualizar "Resumen Doctor"
            historialModel.setValueAt(resumenDoctor, pacienteSeleccionado, 4);
        }

        // Crear una ventana para mostrar el resumen médico
        JFrame resumenFrame = new JFrame("Resumen Médico");
        resumenFrame.setSize(600, 600);
        resumenFrame.setLayout(new BorderLayout());
        resumenFrame.getContentPane().setBackground(new Color(240, 248, 255));

        StringBuilder resumenText = new StringBuilder("<html><b>Datos de la Encuesta</b><br><br>");
        String[] labels = {
            "Dolor (1-11):", "Ubicación del dolor:", "Presión corporal:", "Temperatura:",
            "Saturación:", "Oxigenación:", "Nivel de Hemoglobina:", "Nivel de Proteína:",
            "Nivel de Plaquetas:", "Nivel de Glóbulos Rojos y Blancos:"
        };

        // Aquí procesamos los datos ingresados en la encuesta y agregamos alertas
        for (int i = 0; i < fields.length; i++) {
            resumenText.append(labels[i]).append(" ").append(fields[i].getText()).append("<br>");
        }

        resumenText.append("<br><b>Paciente:</b> ").append(nombre).append("<br><b>ID:</b> ").append(id);
        resumenText.append("<br><br><b>Resumen Doctor:</b><br>").append(resumenDoctor).append("</html>");

        JLabel resumenLabel = new JLabel(resumenText.toString());
        resumenFrame.add(resumenLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton siguienteButton = new JButton("Siguiente");

        siguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resumenFrame.dispose();
                openLaboratorioWindow();
            }
        });

        buttonPanel.add(siguienteButton);
        resumenFrame.add(buttonPanel, BorderLayout.SOUTH);

        resumenFrame.setVisible(true);
    }

    // Ventana de Historial de Pacientes
    public static void openHistorialWindow() {
        JFrame historialFrame = new JFrame("Historial de Pacientes");
        historialFrame.setSize(600, 400);
        historialFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Crear una tabla con los datos del historial
        JTable historialTable = new JTable(historialModel) {
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que todas las celdas sean no editables
            }
        };

        // Agregar un botón en la última columna "Acción"
        JButton resumenButton = new JButton("Ver Resumen");
        resumenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = historialTable.getSelectedRow();
                if (selectedRow != -1) {
                    String resumen = (String) historialTable.getValueAt(selectedRow, 4); // Resumen Doctor
                    if (resumen.isEmpty()) {
                        JOptionPane.showMessageDialog(historialFrame, "Examen no realizado aún.", "Resumen Médico", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(historialFrame, resumen, "Resumen Médico", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(historialFrame, "Por favor, selecciona un paciente.");
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(historialTable);
        historialFrame.add(scrollPane, BorderLayout.CENTER);

        JButton volverButton = new JButton("Volver");
        volverButton.setBackground(new Color(220, 20, 60));
        volverButton.setForeground(Color.WHITE);
        volverButton.setFont(new Font("Arial", Font.BOLD, 16));

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                historialFrame.dispose();
                openLaboratorioWindow();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(volverButton);
        buttonPanel.add(resumenButton);

        historialFrame.add(buttonPanel, BorderLayout.SOUTH);
        historialFrame.setVisible(true);
    }

    public static void openPiernasWindow() {
        JFrame piernasFrame = new JFrame("Radiografías de Piernas");
        piernasFrame.setSize(800, 600);
        piernasFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        piernasFrame.setLayout(new BorderLayout());
    
        // Panel para contener las imágenes
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(1, 2, 10, 10));
    
        // Cargar las imágenes
        ImageIcon leftLegImage = new ImageIcon("Piernas(1).jpg"); // Ruta a la imagen de la pierna izquierda
        ImageIcon rightLegImage = new ImageIcon("Piernas(2).jpg"); // Ruta a la imagen de la pierna derecha
    
        // Escalar las imágenes para que se ajusten al tamaño de los JLabel
        Image leftImage = leftLegImage.getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH);
        Image rightImage = rightLegImage.getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH);
    
        // Crear JLabel para las imágenes
        JLabel leftImageLabel = new JLabel(new ImageIcon(leftImage));
        JLabel rightImageLabel = new JLabel(new ImageIcon(rightImage));
    
        // Añadir las imágenes al panel
        imagePanel.add(leftImageLabel);
        imagePanel.add(rightImageLabel);
    
        // Panel para el cuadro de texto
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        JTextArea commentsArea = new JTextArea(10, 40);
        commentsArea.setLineWrap(true);
        commentsArea.setWrapStyleWord(true);
        commentsArea.setBorder(BorderFactory.createTitledBorder("Comentarios del operador"));
        JScrollPane scrollPane = new JScrollPane(commentsArea);
    
        textPanel.add(scrollPane, BorderLayout.CENTER);
    
        // Panel para el botón "Volver"
        JPanel buttonPanel = new JPanel();
        JButton volverButton = new JButton("Volver");
        volverButton.setBackground(new Color(220, 20, 60));
        volverButton.setForeground(Color.WHITE);
        volverButton.setFont(new Font("Arial", Font.BOLD, 16));
    
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                piernasFrame.dispose(); // Cerrar la ventana actual
                openRadiografiasWindow(); // Volver a la ventana de Radiografías
            }
        });
    
        buttonPanel.add(volverButton);
    
        // Agregar los paneles al marco
        piernasFrame.add(imagePanel, BorderLayout.CENTER);
        piernasFrame.add(textPanel, BorderLayout.SOUTH);
        piernasFrame.add(buttonPanel, BorderLayout.NORTH);
    
        piernasFrame.setVisible(true);
    }
    public static void openPatasWindow() {
        JFrame piernasFrame = new JFrame("Radiografías de las Piernas");
        piernasFrame.setSize(800, 600);
        piernasFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        piernasFrame.setLayout(new BorderLayout());
    
        // Panel para contener las imágenes
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(1, 2, 10, 10));
    
        // Cargar las imágenes
        ImageIcon leftLegImage = new ImageIcon("Piernas(1).jpg"); // Ruta a la imagen de la pierna izquierda
        ImageIcon rightLegImage = new ImageIcon("Piernas(2).jpg"); // Ruta a la imagen de la pierna derecha
    
        // Escalar las imágenes para que se ajusten al tamaño de los JLabel
        Image leftImage = leftLegImage.getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH);
        Image rightImage = rightLegImage.getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH);
    
        // Crear JLabel para las imágenes
        JLabel leftImageLabel = new JLabel(new ImageIcon(leftImage));
        JLabel rightImageLabel = new JLabel(new ImageIcon(rightImage));
    
        // Añadir las imágenes al panel
        imagePanel.add(leftImageLabel);
        imagePanel.add(rightImageLabel);
    
        // Panel para el cuadro de texto
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        JTextArea commentsArea = new JTextArea(10, 40);
        commentsArea.setLineWrap(true);
        commentsArea.setWrapStyleWord(true);
        commentsArea.setBorder(BorderFactory.createTitledBorder("OBSERVACIONES DEL DOCTOR"));
        JScrollPane scrollPane = new JScrollPane(commentsArea);
    
        textPanel.add(scrollPane, BorderLayout.CENTER);
    
        // Panel para los botones
        JPanel buttonPanel = new JPanel();
    
        // Botón "Volver"
        JButton volverButton = new JButton("Volver");
        volverButton.setBackground(new Color(220, 20, 60));
        volverButton.setForeground(Color.WHITE);
        volverButton.setFont(new Font("Arial", Font.BOLD, 16));
    
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                piernasFrame.dispose(); // Cerrar la ventana actual
                openRadiografiasWindow(); // Volver a la ventana de Radiografías
            }
        });
    
        // Botón "Siguiente"
        JButton siguienteButton = new JButton("Siguiente");
        siguienteButton.setBackground(new Color(60, 179, 113));
        siguienteButton.setForeground(Color.WHITE);
        siguienteButton.setFont(new Font("Arial", Font.BOLD, 16));
    
        siguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pacienteSeleccionado != -1) {
                    String comentarios = commentsArea.getText().trim();
                    if (!comentarios.isEmpty()) {
                        // Guardar los comentarios en el historial
                        historialModel.setValueAt(comentarios, pacienteSeleccionado, 4); // Columna "Resumen Doctor"
                        historialModel.setValueAt("Completado", pacienteSeleccionado, 2); // Marcar como "Completado"
                    }
                }
                piernasFrame.dispose(); // Cerrar la ventana actual
                openLaboratorioWindow(); // Volver a la ventana principal
            }
        });
    
        buttonPanel.add(volverButton);
        buttonPanel.add(siguienteButton);
    
        // Agregar los paneles al marco
        piernasFrame.add(imagePanel, BorderLayout.CENTER);
        piernasFrame.add(textPanel, BorderLayout.SOUTH);
        piernasFrame.add(buttonPanel, BorderLayout.NORTH);
    
        piernasFrame.setVisible(true);
    }
    
    public static void openManitosWindow() {
        JFrame brazosFrame = new JFrame("Radiografías de los Brazos");
        brazosFrame.setSize(800, 600);
        brazosFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        brazosFrame.setLayout(new BorderLayout());
    
        // Panel para contener las imágenes
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(1, 2, 10, 10));
    
        // Cargar las imágenes
        ImageIcon leftArmImage = new ImageIcon("Mano(1).jpg"); // Ruta a la imagen del brazo izquierdo
        ImageIcon rightArmImage = new ImageIcon("Mano(2).jpg"); // Ruta a la imagen del brazo derecho
    
        // Escalar las imágenes para que se ajusten al tamaño de los JLabel
        Image leftImage = leftArmImage.getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH);
        Image rightImage = rightArmImage.getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH);
    
        // Crear JLabel para las imágenes
        JLabel leftImageLabel = new JLabel(new ImageIcon(leftImage));
        JLabel rightImageLabel = new JLabel(new ImageIcon(rightImage));
    
        // Añadir las imágenes al panel
        imagePanel.add(leftImageLabel);
        imagePanel.add(rightImageLabel);
    
        // Panel para el cuadro de texto
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        JTextArea commentsArea = new JTextArea(10, 40);
        commentsArea.setLineWrap(true);
        commentsArea.setWrapStyleWord(true);
        commentsArea.setBorder(BorderFactory.createTitledBorder("OBSERVACIONES DEL DOCTOR"));
        JScrollPane scrollPane = new JScrollPane(commentsArea);
    
        textPanel.add(scrollPane, BorderLayout.CENTER);
    
        // Panel para los botones
        JPanel buttonPanel = new JPanel();
    
        // Botón "Volver"
        JButton volverButton = new JButton("Volver");
        volverButton.setBackground(new Color(220, 20, 60));
        volverButton.setForeground(Color.WHITE);
        volverButton.setFont(new Font("Arial", Font.BOLD, 16));
    
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                brazosFrame.dispose(); // Cerrar la ventana actual
                openRadiografiasWindow(); // Volver a la ventana de Radiografías
            }
        });
    
        // Botón "Siguiente"
        JButton siguienteButton = new JButton("Siguiente");
        siguienteButton.setBackground(new Color(60, 179, 113));
        siguienteButton.setForeground(Color.WHITE);
        siguienteButton.setFont(new Font("Arial", Font.BOLD, 16));
    
        siguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pacienteSeleccionado != -1) {
                    String comentarios = commentsArea.getText().trim();
                    if (!comentarios.isEmpty()) {
                        // Guardar los comentarios en el historial
                        historialModel.setValueAt(comentarios, pacienteSeleccionado, 4); // Columna "Resumen Doctor"
                        historialModel.setValueAt("Completado", pacienteSeleccionado, 2); // Marcar como "Completado"
                    }
                }
                brazosFrame.dispose(); // Cerrar la ventana actual
                openLaboratorioWindow(); // Volver a la ventana principal
            }
        });
    
        buttonPanel.add(volverButton);
        buttonPanel.add(siguienteButton);
    
        // Agregar los paneles al marco
        brazosFrame.add(imagePanel, BorderLayout.CENTER);
        brazosFrame.add(textPanel, BorderLayout.SOUTH);
        brazosFrame.add(buttonPanel, BorderLayout.NORTH);
    
        brazosFrame.setVisible(true);
    }
    
    public static void openPeshitoWindow() {
        JFrame torsoFrame = new JFrame("Radiografías del Torso");
        torsoFrame.setSize(800, 600);
        torsoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        torsoFrame.setLayout(new BorderLayout());
    
        // Panel para contener las imágenes
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(1, 2, 10, 10));
    
        // Cargar las imágenes
        ImageIcon frontTorsoImage = new ImageIcon("Torax(1).jpg"); // Ruta a la imagen del torso frontal
        ImageIcon backTorsoImage = new ImageIcon("Torax(2).jpg"); // Ruta a la imagen del torso dorsal
    
        // Escalar las imágenes para que se ajusten al tamaño de los JLabel
        Image frontImage = frontTorsoImage.getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH);
        Image backImage = backTorsoImage.getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH);
    
        // Crear JLabel para las imágenes
        JLabel frontImageLabel = new JLabel(new ImageIcon(frontImage));
        JLabel backImageLabel = new JLabel(new ImageIcon(backImage));
    
        // Añadir las imágenes al panel
        imagePanel.add(frontImageLabel);
        imagePanel.add(backImageLabel);
    
        // Panel para el cuadro de texto
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        JTextArea commentsArea = new JTextArea(10, 40);
        commentsArea.setLineWrap(true);
        commentsArea.setWrapStyleWord(true);
        commentsArea.setBorder(BorderFactory.createTitledBorder("OBSERVACIONES DEL DOCTOR"));
        JScrollPane scrollPane = new JScrollPane(commentsArea);
    
        textPanel.add(scrollPane, BorderLayout.CENTER);
    
        // Panel para los botones
        JPanel buttonPanel = new JPanel();
    
        // Botón "Volver"
        JButton volverButton = new JButton("Volver");
        volverButton.setBackground(new Color(220, 20, 60));
        volverButton.setForeground(Color.WHITE);
        volverButton.setFont(new Font("Arial", Font.BOLD, 16));
    
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                torsoFrame.dispose(); // Cerrar la ventana actual
                openRadiografiasWindow(); // Volver a la ventana de Radiografías
            }
        });
    
        // Botón "Siguiente"
        JButton siguienteButton = new JButton("Siguiente");
        siguienteButton.setBackground(new Color(60, 179, 113));
        siguienteButton.setForeground(Color.WHITE);
        siguienteButton.setFont(new Font("Arial", Font.BOLD, 16));
    
        siguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pacienteSeleccionado != -1) {
                    String comentarios = commentsArea.getText().trim();
                    if (!comentarios.isEmpty()) {
                        // Guardar los comentarios en el historial
                        historialModel.setValueAt(comentarios, pacienteSeleccionado, 4); // Columna "Resumen Doctor"
                        historialModel.setValueAt("Completado", pacienteSeleccionado, 2); // Marcar como "Completado"
                    }
                }
                torsoFrame.dispose(); // Cerrar la ventana actual
                openLaboratorioWindow(); // Volver a la ventana principal
            }
        });
    
        buttonPanel.add(volverButton);
        buttonPanel.add(siguienteButton);
    
        // Agregar los paneles al marco
        torsoFrame.add(imagePanel, BorderLayout.CENTER);
        torsoFrame.add(textPanel, BorderLayout.SOUTH);
        torsoFrame.add(buttonPanel, BorderLayout.NORTH);
    
        torsoFrame.setVisible(true);
    }
    

    public static void openCabecillaWindow() {
        JFrame cabezaFrame = new JFrame("Radiografías de la Cabeza");
        cabezaFrame.setSize(800, 600);
        cabezaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cabezaFrame.setLayout(new BorderLayout());
    
        // Panel para contener las imágenes
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(1, 2, 10, 10));
    
        // Cargar las imágenes
        ImageIcon frontHeadImage = new ImageIcon("Cabeza(1).jpg"); // Ruta a la imagen de la cabeza frontal
        ImageIcon sideHeadImage = new ImageIcon("Cabeza(2).jpg"); // Ruta a la imagen de la cabeza lateral
    
        // Escalar las imágenes para que se ajusten al tamaño de los JLabel
        Image frontImage = frontHeadImage.getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH);
        Image sideImage = sideHeadImage.getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH);
    
        // Crear JLabel para las imágenes
        JLabel frontImageLabel = new JLabel(new ImageIcon(frontImage));
        JLabel sideImageLabel = new JLabel(new ImageIcon(sideImage));
    
        // Añadir las imágenes al panel
        imagePanel.add(frontImageLabel);
        imagePanel.add(sideImageLabel);
    
        // Panel para el cuadro de texto
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        JTextArea commentsArea = new JTextArea(10, 40);
        commentsArea.setLineWrap(true);
        commentsArea.setWrapStyleWord(true);
        commentsArea.setBorder(BorderFactory.createTitledBorder("OBSERVACION DEL DOCTOR"));
        JScrollPane scrollPane = new JScrollPane(commentsArea);
    
        textPanel.add(scrollPane, BorderLayout.CENTER);
    
        // Panel para los botones
        JPanel buttonPanel = new JPanel();
    
        // Botón "Volver"
        JButton volverButton = new JButton("Volver");
        volverButton.setBackground(new Color(220, 20, 60));
        volverButton.setForeground(Color.WHITE);
        volverButton.setFont(new Font("Arial", Font.BOLD, 16));
    
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cabezaFrame.dispose(); // Cerrar la ventana actual
                openRadiografiasWindow(); // Volver a la ventana de Radiografías
            }
        });
    
        // Botón "Siguiente"
        JButton siguienteButton = new JButton("Siguiente");
        siguienteButton.setBackground(new Color(60, 179, 113));
        siguienteButton.setForeground(Color.WHITE);
        siguienteButton.setFont(new Font("Arial", Font.BOLD, 16));
    
        siguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pacienteSeleccionado != -1) {
                    String comentarios = commentsArea.getText().trim();
                    if (!comentarios.isEmpty()) {
                        // Guardar los comentarios en el historial
                        historialModel.setValueAt(comentarios, pacienteSeleccionado, 4); // Columna "Resumen Doctor"
                        historialModel.setValueAt("Completado", pacienteSeleccionado, 2); // Marcar como "Completado"
                    }
                }
                cabezaFrame.dispose(); // Cerrar la ventana actual
                openLaboratorioWindow(); // Volver a la ventana principal
            }
        });
    
        buttonPanel.add(volverButton);
        buttonPanel.add(siguienteButton);
    
        // Agregar los paneles al marco
        cabezaFrame.add(imagePanel, BorderLayout.CENTER);
        cabezaFrame.add(textPanel, BorderLayout.SOUTH);
        cabezaFrame.add(buttonPanel, BorderLayout.NORTH);
    
        cabezaFrame.setVisible(true);
    }
    


public static void openSeleccionarPacienteParaRadiografias() {
    JFrame seleccionFrame = new JFrame("Seleccionar Paciente para Radiografías");
    seleccionFrame.setSize(600, 400);
    seleccionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    seleccionFrame.setLayout(new BorderLayout());

    JTable pacientesTable = new JTable(historialModel);
    JScrollPane scrollPane = new JScrollPane(pacientesTable);
    seleccionFrame.add(scrollPane, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
    JButton seleccionarButton = new JButton("Seleccionar");
    JButton cancelarButton = new JButton("Cancelar");

    // Acción para seleccionar el paciente
    seleccionarButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = pacientesTable.getSelectedRow();
            if (selectedRow != -1) {
                pacienteSeleccionado = selectedRow; // Guardar el índice del paciente seleccionado
                seleccionFrame.dispose(); // Cerrar la ventana de selección
                openRadiografiasWindow(); // Abrir la ventana de radiografías
            } else {
                JOptionPane.showMessageDialog(seleccionFrame, "Por favor, selecciona un paciente.");
            }
        }
    });

    // Acción para cancelar la selección
    cancelarButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            seleccionFrame.dispose();
            openLaboratorioWindow(); // Regresar a la ventana principal
        }
    });

    buttonPanel.add(seleccionarButton);
    buttonPanel.add(cancelarButton);
    seleccionFrame.add(buttonPanel, BorderLayout.SOUTH);

    seleccionFrame.setVisible(true);
}}

