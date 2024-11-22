import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.time.Duration;

public class Interfaz extends JFrame {
    public static String entrada = "pacientes_hospitalizacion.csv";
    public static String salida = "C:/PERSONAL/UNIVERSIDAD/SEGUNDO SEMESTRE/POO/TERCER CORTE/PROYECTO FINAL HOSPITALIZACION/INFORME DE PERSONAS HOSPITALIZADA.csv";

    public static void tiempohosp(Habitaciones habitacion) {    
        // Formateador para convertir las cadenas en LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Convertir las cadenas a objetos LocalDateTime
        LocalDateTime fechaInicial = LocalDateTime.parse(habitacion.ingreso, formatter);
        LocalDateTime fechaFinal = LocalDateTime.parse(habitacion.salida, formatter);

        // Calcular la duración entre las dos fechas
        Duration duracion = Duration.between(fechaInicial, fechaFinal);

        // Extraer días, horas, minutos y segundos
        long dias = duracion.toDays();
        long horas = duracion.toHours() % 24;
        long minutos = duracion.toMinutes() % 60;
        long segundos = duracion.getSeconds() % 60;

        // Mostrar el tiempo transcurrido
        habitacion.tiempo=(
                dias + " dias, " +
                horas + " horas, " +
                minutos + " minutos, " +
                segundos + " segundos.");
    }

    public static void actualizarCSVsalida(String archivo, Habitaciones habitacion) {
        if (habitacion.ocupada) {
            ArrayList<String> lineas = new ArrayList<>();
            boolean ordenEncontrada = false;
    
            // Leer el contenido actual del archivo CSV
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    // Dividir la línea por ";" para verificar el atributo "orden"
                    String[] datos = linea.split(";");
                    if (datos.length > 18 && datos[18].equals(habitacion.orden)) {
                        // Sobrescribir la línea si la orden coincide
                        linea = habitacion.cedula + ";" +
                                habitacion.paciente + ";" +
                                habitacion.fcardiacai + ";" +
                                habitacion.frespiratoriai + ";" +
                                habitacion.presioni + ";" +
                                habitacion.temperaturai + ";" +
                                habitacion.saturacioni + ";" +
                                habitacion.patologia + ";" +
                                habitacion.fcardiacaf + ";" +
                                habitacion.frespiratoriaf + ";" +
                                habitacion.presionf + ";" +
                                habitacion.temperaturaf + ";" +
                                habitacion.saturacionf + ";" +
                                habitacion.ingreso + ";" +
                                habitacion.ultrevision + ";" +
                                habitacion.salida + ";" +
                                habitacion.tiempo + ";" +
                                habitacion.numhabitacion + ";" +
                                habitacion.orden;
                        ordenEncontrada = true;
                    }
                    lineas.add(linea);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    
            // Si la orden no se encontró, agregar una nueva línea
            if (!ordenEncontrada) {
                lineas.add(
                    habitacion.cedula + ";" +
                    habitacion.paciente + ";" +
                    habitacion.fcardiacai + ";" +
                    habitacion.frespiratoriai + ";" +
                    habitacion.presioni + ";" +
                    habitacion.temperaturai + ";" +
                    habitacion.saturacioni + ";" +
                    habitacion.patologia + ";" +
                    habitacion.fcardiacaf + ";" +
                    habitacion.frespiratoriaf + ";" +
                    habitacion.presionf + ";" +
                    habitacion.temperaturaf + ";" +
                    habitacion.saturacionf + ";" +
                    habitacion.ingreso + ";" +
                    habitacion.ultrevision + ";" +
                    habitacion.salida + ";" +
                    habitacion.tiempo + ";" +
                    habitacion.numhabitacion + ";" +
                    habitacion.orden
                );
            }
    
            // Escribir el contenido actualizado de vuelta al archivo
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, false))) {
                for (String linea : lineas) {
                    writer.write(linea);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
       

    // Método para generar un valor aleatorio dentro de un rango dado
    public static double generarValor(double limiteInferior, double limiteSuperior) {
        // Generar el número aleatorio y redondearlo a dos decimales
        double valor = ThreadLocalRandom.current().nextDouble(limiteInferior, limiteSuperior);
        return Math.round(valor * 100.0) / 100.0;
    }

    public static void Revision(Habitaciones habitacion) {
        if (habitacion.ocupada) {
            // Frecuencia Cardíaca
            habitacion.fcardiacaf = generarValor(50, 110); // Rango extendido: 60-100 -> 50-110
            // Frecuencia Respiratoria
            habitacion.frespiratoriaf = generarValor(2, 30); // Rango extendido: 12-20 -> 2-30
            // Presión Arterial (para simplificar, usamos solo sistólica)
            habitacion.presionf = generarValor(70, 130); // Rango extendido: 90-120 -> 70-130

            // Temperatura Corporal
            habitacion.temperaturaf = generarValor(26.1, 47.2); // Rango extendido: 36.1-37.2 -> 26.1-47.2

            // Saturación de Oxígeno
            habitacion.saturacionf = generarValor(85, 110); // Rango extendido: 95-100 -> 85-110

            LocalDateTime fechaActual = LocalDateTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fechaHora = fechaActual.format(formato);
            habitacion.setUltrevision(fechaHora);
            actualizarCSVsalida(salida,habitacion);

        }
    }

    public boolean buscarOrdenEnCSV(String ordenBuscada, String rutaArchivo) {
    try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
        String linea;
        boolean isFirstLine = true; // Ignorar encabezados
        while ((linea = reader.readLine()) != null) {
            if (isFirstLine) {
                isFirstLine = false;
                continue;
            }
            String[] columnas = linea.split(";"); // Cambiar el delimitador si es necesario
            if (columnas[0].trim().equals(ordenBuscada.trim())) {
                return true;
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return false;
}

 public static void Cambiarorden(String archivoCsv, String valorOrden, String caracterAgregar) throws IOException {
    // Leer todas las líneas del archivo CSV usando una codificación explícita
    ArrayList<String> lineas = new ArrayList<>(Files.readAllLines(Paths.get(archivoCsv), Charset.forName("ISO-8859-1")));

    // Crear una lista para almacenar las líneas modificadas
    ArrayList<String> lineasActualizadas = new ArrayList<>();

    // Iterar por cada línea del CSV
    for (String linea : lineas) {
        // Validar si la línea no está vacía antes de dividirla
        if (linea.trim().isEmpty()) {
            lineasActualizadas.add(linea); // Mantener líneas vacías sin cambios
            continue;
        }

        // Dividir la línea en columnas usando el punto y coma como delimitador
        String[] columnas = linea.split(";");

        // Verificar si la columna que contiene la orden (por ejemplo, la segunda columna) es igual al valor que buscamos
        if (columnas.length > 1 && columnas[0].trim().equals(valorOrden.trim())) {
            // Modificar el valor de la orden, agregando el carácter al número de la orden
            columnas[0] = columnas[0] + caracterAgregar; // Agregar el carácter (por ejemplo, 'u')
        }

        // Volver a juntar las columnas modificadas usando punto y coma como delimitador
        lineasActualizadas.add(String.join(";", columnas));
    }

    // Sobrescribir el archivo original con las líneas actualizadas usando la misma codificación
    Files.write(Paths.get(archivoCsv), lineasActualizadas, Charset.forName("ISO-8859-1"));
}

    private Map<String, String> ordenesAsignadas = new HashMap<>();

    private void actualizarEstadoHabitacion(String numhabitacion, String orden, JButton boton, Habitaciones habitacion) {
        // Obtener la ventana contenedora del botón
        Window ventanaActual = SwingUtilities.getWindowAncestor(boton);

        // Verificar si la orden ya está asociada a una habitación diferente
        if (ordenesAsignadas.containsKey(orden)) {
            String habitacionAsociada = ordenesAsignadas.get(orden);
            if (!habitacionAsociada.equals(numhabitacion)) {
                JOptionPane.showMessageDialog(ventanaActual, 
                    "La orden ya está asignada a otra habitación (" + habitacionAsociada + ").");
                return;
            }
        }

        if (!habitacion.ocupada) {
            // Leer el archivo CSV para buscar la orden
            String rutaArchivo =entrada;
            String[] datosOrden = null;

            try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
                String linea;
                boolean isFirstLine = true; // Ignorar encabezados

                while ((linea = reader.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue;
                    }

                    // Separar columnas por delimitador punto y coma
                    String[] columnas = linea.split(",");
                    if (columnas[0].trim().equals(orden.trim())) {
                        for (int i = 0; i < columnas.length; i++) {
                            columnas[i] = columnas[i].replace(",", "."); // Reemplazar comas por puntos
                        }
                        datosOrden = columnas;
                        break;
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(ventanaActual, "Error al leer el archivo: " + e.getMessage());
                e.printStackTrace();
                return;
            }

            if (datosOrden != null) {
                habitacion.Llenar(datosOrden);
                habitacion.numhabitacion = numhabitacion;

                // Registrar la orden asociada a esta habitación
                ordenesAsignadas.put(orden, numhabitacion);

                // Cambiar el estado y mostrar éxito
                boton.setBackground(Color.RED);
                JOptionPane.showMessageDialog(ventanaActual, "HOSPITALIZACIÓN EXITOSA");
                habitacion.ocupada = true;
                habitacion.orden = orden;

                // Registrar fecha de ingreso
                LocalDateTime fechaActual = LocalDateTime.now();
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String fechaHora = fechaActual.format(formato);
                habitacion.setIngreso(fechaHora);
                actualizarCSVsalida(salida, habitacion);
                if (ventanaActual != null) {
                    ventanaActual.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(ventanaActual, "Orden no encontrada en el archivo.");
            }
        } else {
            if (habitacion.orden.equals(orden)) {
                int option = JOptionPane.showConfirmDialog(
                    ventanaActual, "¿Está seguro de realizar la salida?", 
                    "Confirmar salida", JOptionPane.YES_NO_OPTION
                );

                if (option == JOptionPane.YES_OPTION) {
                    boton.setBackground(Color.GREEN);
                    JOptionPane.showMessageDialog(ventanaActual, "SALIDA EXITOSA");

                    // Liberar habitación
                    

                    try {
                        Cambiarorden(entrada, orden, "usada");
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(ventanaActual, 
                            "Error al actualizar la orden en el archivo: " + e.getMessage());
                        e.printStackTrace();
                        return;
                    }

                    // Registrar fecha de salida
                    LocalDateTime fechaActual = LocalDateTime.now();
                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String fechaHora = fechaActual.format(formato);
                    habitacion.setSalida(fechaHora);
                    tiempohosp(habitacion);
                    actualizarCSVsalida(salida, habitacion);
                    habitacion.Vaciar();
                    habitacion.ocupada = false;
                    // Eliminar la asociación de la orden
                    ordenesAsignadas.remove(orden);

                    if (ventanaActual != null) {
                        ventanaActual.dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(ventanaActual, "Operación cancelada.");
                }
            } else {
                JOptionPane.showMessageDialog(ventanaActual, 
                    "Con esta orden no puede deshospitalizar a este paciente.");
            }
        }
    }


    
    
    

    private void Colorhabitacion(JButton boton, Habitaciones habitacion) {
        // Cambiar el color del botón y mostrar mensaje según el estado de 'ocupada'
        if (habitacion.ocupada) {
            boton.setBackground(Color.BLUE);  // Si está ocupada, se pone azul
            // Obtener los datos de la habitación usando el método imprimirDatos()
        } else {
            boton.setBackground(Color.WHITE); // Si está vacía, se pone blanco
        }
    }
    private void Mostrarhabitacion(JButton boton, Habitaciones habitacion) {
        // Crear un área de texto para mostrar el mensaje en la ventana
        JTextArea areaTexto = new JTextArea(15, 50);
        areaTexto.setEditable(false); // Hacer que el área de texto sea solo de lectura
    
        // Cambiar el contenido según el estado de la habitación
        if (habitacion.ocupada) {
            // Crear un StringBuilder para concatenar los datos en un formato ordenado
            StringBuilder contenido = new StringBuilder();
    
            // Agregar los datos iniciales con formato de dos columnas
            contenido.append("*********************Datos Iniciales*********************\n");
            contenido.append(habitacion.imprimirDatos());
            
            contenido.append("\n\n");  // Separación entre los datos iniciales y finales
    
            // Agregar los datos finales con formato de dos columnas
            contenido.append("*********************Datos Finales*********************\n");
            contenido.append(habitacion.imprimirDatosFinales());
    
            // Asignar el contenido generado al área de texto
            areaTexto.setText(contenido.toString());
        } else {
            // Mensaje para habitación vacía
            areaTexto.setText("La habitación está vacía.");
        }
    
        // Mostrar la ventana con el área de texto
        JOptionPane.showMessageDialog(
            null, 
            new JScrollPane(areaTexto), 
            "INFORMACIÓN DE LA HABITACIÓN", 
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    
    
    public Interfaz() {
        
        Habitaciones habitacion1 = new Habitaciones();
        Habitaciones habitacion2 = new Habitaciones();
        Habitaciones habitacion3 = new Habitaciones();
        Habitaciones habitacion4 = new Habitaciones();
        Habitaciones habitacion5 = new Habitaciones();
        Habitaciones habitacion6 = new Habitaciones();
        Habitaciones habitacion7 = new Habitaciones();
        Habitaciones habitacion8 = new Habitaciones();
        Habitaciones habitacion9 = new Habitaciones();
        Habitaciones habitacion10 = new Habitaciones();
        // Configuración de la ventana principal
        setTitle("HOSPITALIZACION");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel principal con fondo blanco
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE); // Fondo blanco para el panel principal

        // Crear un espacio para la imagen en la parte superior
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setBackground(Color.WHITE); // Fondo blanco para el label de la imagen
        imageLabel.setOpaque(true); // Necesario para que el fondo blanco sea visible

        // Cargar la imagen (ajusta la ruta a tu archivo de imagen)
        ImageIcon icon = new ImageIcon("C:/PERSONAL/UNIVERSIDAD/SEGUNDO SEMESTRE/POO/TERCER CORTE/PROYECTO FINAL HOSPITALIZACION/LOGO.jpg");
        if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            // Escalar la imagen para ajustarla al tamaño deseado
            Image scaledImage = icon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
        } else {
            // Si no se encuentra la imagen, muestra un texto de error
            imageLabel.setText("Imagen no encontrada");
        }

        // Agregar la imagen al panel superior
        mainPanel.add(imageLabel, BorderLayout.NORTH);

        // Panel para los botones en la mitad de la ventana con fondo blanco
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 100));
        buttonPanel.setBackground(Color.WHITE); // Fondo blanco para el panel de botones

        // Botón 1
        JButton button1 = new JButton("HOSPITALIZARSE");
        button1.setPreferredSize(new Dimension(200, 100)); // Establecer tamaño preferido
        buttonPanel.add(button1);

        // Botón 2
        JButton button2 = new JButton("CONSULTAR");
        button2.setPreferredSize(new Dimension(200, 100)); // Establecer tamaño preferido
        buttonPanel.add(button2);

        // Agregar el panel de botones al centro de la ventana
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Configuración de acciones para los botones
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crear un JTextField para que el usuario ingrese un número
                JTextField textField = new JTextField(10); // límite de 10 caracteres

                // Mostrar un cuadro de diálogo con el JTextField
                int option = JOptionPane.showConfirmDialog(Interfaz.this, textField,
                        "Ingrese un número de orden", JOptionPane.OK_CANCEL_OPTION);

                // Comprobar si el usuario hizo clic en OK
                if (option == JOptionPane.OK_OPTION) {
                    String input = textField.getText();

                    // Validar que la entrada sea solo números y no más de 10 dígitos
                    if (input.matches("\\d{1,10}") && buscarOrdenEnCSV(input, entrada)==true){ // Lógica si la entrada es válida y se encuentra en el archivo
                        // Crear una nueva ventana con el logo si la validación es exitosa
                        JOptionPane.showMessageDialog(null, "Ey parcero si tiene una orden bien pueda siga elija salita");
                        JFrame newWindow = new JFrame("HABITACIONES");
                        newWindow.setSize(700, 600);
                        newWindow.setLocationRelativeTo(null);
                    
                        // Panel para la nueva ventana con el logo en la parte superior
                        JPanel newMainPanel = new JPanel();
                        newMainPanel.setLayout(new BorderLayout());
                        newMainPanel.setBackground(Color.WHITE);
                    
                        // Agregar la imagen en la nueva ventana
                        JLabel newImageLabel = new JLabel();
                        newImageLabel.setHorizontalAlignment(JLabel.CENTER);
                        newImageLabel.setBackground(Color.WHITE);
                        newImageLabel.setOpaque(true);
                    
                        // Usar la misma imagen que la ventana original
                        if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                            Image scaledImage = icon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
                            newImageLabel.setIcon(new ImageIcon(scaledImage));
                        } else {
                            newImageLabel.setText("Imagen no encontrada");
                        }   
                    
                        // Agregar la imagen al panel superior de la nueva ventana
                        newMainPanel.add(newImageLabel, BorderLayout.NORTH);
                    
                        // Crear un panel para la rejilla con 5 filas y 6 columnas
                        JPanel gridPanel = new JPanel();
                        gridPanel.setLayout(new GridLayout(5, 6));  // 5 filas y 6 columnas
                        gridPanel.setBackground(Color.WHITE);
                        // Crea las instancias de las habitaciones fuera de los ActionListeners
                                                
                                                    // Habitación 1
                            JButton hab1 = new JButton("HABITACION 1");
                            hab1.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    actualizarEstadoHabitacion("1",input, hab1, habitacion1);
                                }
                            });
                            hab1.setBackground(habitacion1.ocupada ? Color.RED : Color.GREEN); // Color inicial
                            gridPanel.add(hab1);

                            // Habitación 2
                            JButton hab2 = new JButton("HABITACION 2");
                            hab2.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    actualizarEstadoHabitacion("2",input,hab2, habitacion2);
                                }
                            });
                            hab2.setBackground(habitacion2.ocupada ? Color.RED : Color.GREEN); // Color inicial
                            gridPanel.add(hab2);

                            // Habitación 3
                            JButton hab3 = new JButton("HABITACION 3");
                            hab3.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    actualizarEstadoHabitacion("3",input,hab3, habitacion3);
                                }
                            });
                            hab3.setBackground(habitacion3.ocupada ? Color.RED : Color.GREEN); // Color inicial
                            gridPanel.add(hab3);

                            // Habitación 4
                            JButton hab4 = new JButton("HABITACION 4");
                            hab4.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    actualizarEstadoHabitacion("4",input,hab4, habitacion4);
                                }
                            });
                            hab4.setBackground(habitacion4.ocupada ? Color.RED : Color.GREEN); // Color inicial
                            gridPanel.add(hab4);

                            // Habitación 5
                            JButton hab5 = new JButton("HABITACION 5");
                            hab5.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    actualizarEstadoHabitacion("5",input,hab5, habitacion5);
                                }
                            });
                            hab5.setBackground(habitacion5.ocupada ? Color.RED : Color.GREEN); // Color inicial
                            gridPanel.add(hab5);

                            // Habitación 6
                            JButton hab6 = new JButton("HABITACION 6");
                            hab6.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    actualizarEstadoHabitacion("6",input,hab6, habitacion6);
                                }
                            });
                            hab6.setBackground(habitacion6.ocupada ? Color.RED : Color.GREEN); // Color inicial
                            gridPanel.add(hab6);

                            // Habitación 7
                            JButton hab7 = new JButton("HABITACION 7");
                            hab7.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    actualizarEstadoHabitacion("7",input,hab7, habitacion7);
                                }
                            });
                            hab7.setBackground(habitacion7.ocupada ? Color.RED : Color.GREEN); // Color inicial
                            gridPanel.add(hab7);

                            // Habitación 8
                            JButton hab8 = new JButton("HABITACION 8");
                            hab8.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    actualizarEstadoHabitacion("8",input,hab8, habitacion8);
                                }
                            });
                            hab8.setBackground(habitacion8.ocupada ? Color.RED : Color.GREEN); // Color inicial
                            gridPanel.add(hab8);

                            // Habitación 9
                            JButton hab9 = new JButton("HABITACION 9");
                            hab9.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    actualizarEstadoHabitacion("9",input,hab9, habitacion9);
                                }
                            });
                            hab9.setBackground(habitacion9.ocupada ? Color.RED : Color.GREEN); // Color inicial
                            gridPanel.add(hab9);

                            // Habitación 10
                            JButton hab10 = new JButton("HABITACION 10");
                            hab10.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    actualizarEstadoHabitacion("10",input,hab10, habitacion10);
                                }
                            });
                            hab10.setBackground(habitacion10.ocupada ? Color.RED : Color.GREEN); // Color inicial
                            gridPanel.add(hab10);


                    
                        
                        // Agregar la rejilla al panel principal (debajo del logo)
                        newMainPanel.add(gridPanel, BorderLayout.CENTER);
                    
                        // Hacer visible la nueva ventana
                        newWindow.add(newMainPanel);
                        newWindow.setVisible(true);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Ey bro revisate esa orden porque no esta en el sistema");
                    }
                }
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame newWindow = new JFrame("HABITACIONES");
                        newWindow.setSize(700, 600);
                        newWindow.setLocationRelativeTo(null);
                    
                        // Panel para la nueva ventana con el logo en la parte superior
                        JPanel newMainPanel = new JPanel();
                        newMainPanel.setLayout(new BorderLayout());
                        newMainPanel.setBackground(Color.WHITE);
                    
                        // Agregar la imagen en la nueva ventana
                        JLabel newImageLabel = new JLabel();
                        newImageLabel.setHorizontalAlignment(JLabel.CENTER);
                        newImageLabel.setBackground(Color.WHITE);
                        newImageLabel.setOpaque(true);
                    
                        // Usar la misma imagen que la ventana original
                        if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                            Image scaledImage = icon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
                            newImageLabel.setIcon(new ImageIcon(scaledImage));
                        }
                    
                        // Agregar la imagen al panel superior de la nueva ventana
                        newMainPanel.add(newImageLabel, BorderLayout.NORTH);
                    
                        // Crear un panel para la rejilla con 5 filas y 6 columnas
                        JPanel gridPanel = new JPanel();
                        gridPanel.setLayout(new GridLayout(5, 6));  // 5 filas y 6 columnas
                        gridPanel.setBackground(Color.WHITE);

                        

                        JButton hab1 = new JButton("HABITACION 1");
                        Colorhabitacion(hab1,habitacion1);
                        hab1.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Mostrarhabitacion(hab1, habitacion1);
                                 ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                                 // Programar la ejecución de la función cada minuto
                                 scheduler.scheduleAtFixedRate(() -> Revision(habitacion1), 0, 1, TimeUnit.MINUTES);
                            }
                        });
                        gridPanel.add(hab1);
                        
                        JButton hab2 = new JButton("HABITACION 2");
                        Colorhabitacion(hab2,habitacion2);
                        hab2.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Mostrarhabitacion(hab2, habitacion2);
                                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                                // Programar la ejecución de la función cada minuto
                                scheduler.scheduleAtFixedRate(() -> Revision(habitacion2), 0, 1, TimeUnit.MINUTES);
                            }
                        });
                        gridPanel.add(hab2);
                        
                        JButton hab3 = new JButton("HABITACION 3");
                        Colorhabitacion(hab3,habitacion3);
                        hab3.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Mostrarhabitacion(hab3, habitacion3);
                                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                                // Programar la ejecución de la función cada minuto
                                scheduler.scheduleAtFixedRate(() -> Revision(habitacion3), 0, 1, TimeUnit.MINUTES);
                            }
                        });
                        gridPanel.add(hab3);
                        
                        JButton hab4 = new JButton("HABITACION 4");
                        Colorhabitacion(hab4,habitacion4);
                        hab4.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Mostrarhabitacion(hab4, habitacion4);
                                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                                // Programar la ejecución de la función cada minuto
                                scheduler.scheduleAtFixedRate(() -> Revision(habitacion4), 0, 1, TimeUnit.MINUTES);
                            }
                        });
                        gridPanel.add(hab4);
                        
                        JButton hab5 = new JButton("HABITACION 5");
                        Colorhabitacion(hab5,habitacion5);
                        hab5.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Mostrarhabitacion(hab5, habitacion5);
                                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                                // Programar la ejecución de la función cada minuto
                                scheduler.scheduleAtFixedRate(() -> Revision(habitacion5), 0, 1, TimeUnit.MINUTES);
                            }
                        });
                        gridPanel.add(hab5);
                        
                        JButton hab6 = new JButton("HABITACION 6");
                        Colorhabitacion(hab6,habitacion6);
                        hab6.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Mostrarhabitacion(hab6, habitacion6);
                                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                                // Programar la ejecución de la función cada minuto
                                scheduler.scheduleAtFixedRate(() -> Revision(habitacion6), 0, 1, TimeUnit.MINUTES);
                            }
                        });
                        gridPanel.add(hab6);
                        
                        JButton hab7 = new JButton("HABITACION 7");
                        Colorhabitacion(hab7,habitacion7);
                        hab7.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Mostrarhabitacion(hab7, habitacion7);
                                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                                // Programar la ejecución de la función cada minuto
                                scheduler.scheduleAtFixedRate(() -> Revision(habitacion7), 0, 1, TimeUnit.MINUTES);
                            }
                        });
                        gridPanel.add(hab7);
                        
                        JButton hab8 = new JButton("HABITACION 8");
                        Colorhabitacion(hab8,habitacion8);
                        hab8.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Mostrarhabitacion(hab8, habitacion8);
                                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                                // Programar la ejecución de la función cada minuto
                                scheduler.scheduleAtFixedRate(() -> Revision(habitacion8), 0, 1, TimeUnit.MINUTES);
                            }
                        });
                        gridPanel.add(hab8);
                        
                        JButton hab9 = new JButton("HABITACION 9");
                        Colorhabitacion(hab9,habitacion9);
                        hab9.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Mostrarhabitacion(hab9, habitacion9);
                                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                                // Programar la ejecución de la función cada minuto
                                scheduler.scheduleAtFixedRate(() -> Revision(habitacion9), 0, 1, TimeUnit.MINUTES);
                            }
                        });
                        gridPanel.add(hab9);
                        
                        JButton hab10 = new JButton("HABITACION 10");
                        Colorhabitacion(hab10,habitacion10);
                        hab10.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Mostrarhabitacion(hab10, habitacion10);
                                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                                // Programar la ejecución de la función cada minuto
                                scheduler.scheduleAtFixedRate(() -> Revision(habitacion10), 0, 1, TimeUnit.MINUTES);
                            }
                        });
                        gridPanel.add(hab10);
                        
     


                        
                        // Agregar la rejilla al panel principal (debajo del logo)
                        newMainPanel.add(gridPanel, BorderLayout.CENTER);
                    
                        // Hacer visible la nueva ventana
                        newWindow.add(newMainPanel);
                        newWindow.setVisible(true);
            }
        });

        // Agregar el panel principal a la ventana
        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Interfaz().setVisible(true);
            }
        });
    }
}