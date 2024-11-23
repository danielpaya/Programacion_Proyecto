public class Habitaciones {
    boolean ocupada = false;
    String numhabitacion=null;
    String orden= null;
    String cedulaStr = null;
    String paciente = null;
    String ingreso = null;
    double fcardiacai = 0;
    double frespiratoriai = 0;
    double presioni = 0;
    double temperaturai = 0;
    double saturacioni = 0;
    double fcardiacaf = 0;
    double frespiratoriaf = 0;
    double presionf = 0;
    double temperaturaf = 0;
    double saturacionf = 0;
    String ultrevision = null;
    String patologia = null;
    String salida = null;
    String tiempo = null;

    // Constructor sin parámetros
    public Habitaciones() {
        // Usa valores predeterminados
    }

    // Setters y Getters

    // Getter y Setter para ocupada
    public String getOrden(){
        return orden;
    }
    public void setOrden(String orden){
        this.orden=orden;
    }
    public boolean getOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    // Getter y Setter para cedula
    public String getCedula() {
        return cedulaStr; // Ahora devuelve la cédula como String
    }
    
    public void setCedula(String cedulaStr) {
        this.cedulaStr = cedulaStr; // Permite asignar valores alfanuméricos
    }
    
    // Getter y Setter para paciente
    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    // Getter y Setter para ingreso
    public String getIngreso() {
        return ingreso;
    }

    public void setIngreso(String ingreso) {
        this.ingreso = ingreso;
    }

    // Getter y Setter para frecuencia cardiaca
    public double getFcardiacai() {
        return fcardiacai;
    }

    public void setFcardiacai(double fcardiacai) {
        this.fcardiacai = fcardiacai;
    }

    // Getter y Setter para frecuencia respiratoria
    public double getFrespiratoriai() {
        return frespiratoriai;
    }

    public void setFrespiratoriai(double frespiratoriai) {
        this.frespiratoriai = frespiratoriai;
    }

    // Getter y Setter para presión
    public double getPresioni() {
        return presioni;
    }

    public void setPresioni(double presioni) {
        this.presioni = presioni;
    }

    // Getter y Setter para temperatura
    public double getTemperaturai() {
        return temperaturai;
    }

    public void setTemperaturai(double temperaturai) {
        this.temperaturai = temperaturai;
    }

    // Getter y Setter para saturación
    public double getSaturacioni() {
        return saturacioni;
    }

    public void setSaturacioni(double saturacioni) {
        this.saturacioni = saturacioni;
    }

    public double getFcardiacaf() {
        return fcardiacaf;
    }

    public void setFcardiacaf(double fcardiacaf) {
        this.fcardiacaf = fcardiacaf;
    }

    // Getter y Setter para frecuencia respiratoria
    public double getFrespiratoriaf() {
        return frespiratoriaf;
    }

    public void setFrespiratoriaf(double frespiratoriaf) {
        this.frespiratoriaf = frespiratoriaf;
    }

    // Getter y Setter para presión
    public double getPresionf() {
        return presionf;
    }

    public void setPresionf(double presionf) {
        this.presionf = presionf;
    }

    // Getter y Setter para temperatura
    public double getTemperaturaf() {
        return temperaturaf;
    }

    public void setTemperaturaf(double temperaturaf) {
        this.temperaturaf = temperaturaf;
    }

    // Getter y Setter para saturación
    public double getSaturacionf() {
        return saturacionf;
    }

    public void setSaturacionf(double saturacionf) {
        this.saturacionf = saturacionf;
    }
    public void setUltrevision(String ultrevision){
        this.ultrevision=ultrevision;
    }
    public String getUltrevision()
    {
        return ultrevision;
    } 
    // Getter y Setter para patología
    public String getPatologia() {
        return patologia;
    }

    public void setPatologia(String patologia) {
        this.patologia = patologia;
    }

    // Getter y Setter para salida
    public String getSalida() {
        return salida;
    }

    public void setSalida(String salida) {
        this.salida = salida;
    }

    // Método para imprimir los datos
    public String imprimirDatos() {
        return "Orden: " + orden + "\n" +
               "Cédula: " + cedulaStr + "\n" +
               "Paciente: " + paciente + "\n" +
               "Fecha de Ingreso: " + ingreso + "\n" +
               "Frecuencia Cardiaca inicial: " + fcardiacai + " lpm\n" +
               "Frecuencia Respiratoria inicial: " + frespiratoriai + " rpm\n" +
               "Presión inicial: " + presioni + " mmHg\n" +
               "Temperatura inicial: " + temperaturai + " °C\n" +
               "Saturación inicial: " + saturacioni + " %\n" +
               "Patología: " + patologia;
    }
    
    // Método para imprimir los datos finales
public String imprimirDatosFinales() {
    return 
           "Frecuencia Cardiaca Final: " + fcardiacaf + " lpm"+ "\n" +
           "Frecuencia Respiratoria Final: " + frespiratoriaf + " rpm" + "\n" +
           "Presión Final: " + presionf + " mmHg" + "\n" +
           "Temperatura Final: " + temperaturaf + " °C" + "\n" +
           "Saturación Final: " + saturacionf + " %" +"\n" +
           "Última Revisión: " + ultrevision + "\n";
}

    public void Vaciar(){
        this.numhabitacion=null;
        this.orden=null;
        this.ocupada = false;
        this.cedulaStr = null;
        this.paciente =null ;
        this.ingreso = null;
        this.fcardiacai = 0;
        this.frespiratoriai = 0;
        this.presioni = 0;
        this.temperaturai = 0;
        this.saturacioni = 0;
        this.patologia = null;
        
    }
    public void Llenar(String[] datos) {
        this.orden = datos[0]; // Orden
        this.cedulaStr = datos[1]; // La cédula ahora es String, puede manejar valores alfanuméricos como "N/A"
        this.paciente = datos[2].isEmpty() ? null : datos[2];
        this.fcardiacai = parseDoubleOrDefault(datos[3], 0.0);
        this.frespiratoriai = parseDoubleOrDefault(datos[4], 0.0);
        this.presioni = parseDoubleOrDefault(datos[5], 0.0);
        this.temperaturai = parseDoubleOrDefault(datos[6], 0.0);
        this.saturacioni = parseDoubleOrDefault(datos[7], 0.0);
        this.patologia = datos[8].isEmpty() ? null : datos[8];
        this.fcardiacaf = fcardiacai;
        this.frespiratoriaf = frespiratoriai;
        this.presionf = presioni;
        this.saturacionf = saturacioni;
        this.ultrevision = "Aun no se ha realizado una nueva revision";
    }
    
    private double parseDoubleOrDefault(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException | NullPointerException e) {
            return defaultValue; // Si falla la conversión, devuelve el valor por defecto
        }
    }
    
}
