import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Servidor {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        System.out.println("Ingrese el puerto a escuchar: ");
        int puerto = teclado.nextInt();
        try {
            ServerSocket serverSocket = new ServerSocket(puerto);
            Socket cliente = serverSocket.accept(); //aca el programa para hasta que un cliente se conecte
            System.out.println("Se conecto un Cliente");

            DataInputStream dataInputStream = new DataInputStream(cliente.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(cliente.getOutputStream());
            String mensajeCliente = "";

            while (!mensajeCliente.equalsIgnoreCase("exit")){
                mensajeCliente = dataInputStream.readUTF();
                switch (mensajeCliente){
                    case "1":
                        File file = new File("src/main/java/archivoServidor");
                        dataOutputStream.writeUTF(mostrarContenido(file)+
                          "\n Que deseas realizar? \n 1.Ver Listado de Archivos \n 2. Duplicar Archivo \n 3. Eliminar Archivos" );
                        break;
                    case "2":
                        file = new File("src/main/java/archivoServidor");
                        dataOutputStream.writeUTF("Ingrese el nombre del archivo a duplicar:");
                        String respuesta = dataInputStream.readUTF();
                        String[] split = respuesta.split("\\.");
                        File newfile = new File(file,split[0]+"(copy)."+split[1]);
                        newfile.createNewFile();
                        leerArchivo(respuesta,newfile);
                        dataOutputStream.writeUTF("Que deseas realizar? \n 1.Ver Listado de Archivos \n 2. Duplicar Archivo \n 3. Eliminar Archivos");
                        break;
                    case "3":
                        file = new File("src/main/java/archivoServidor");
                        dataOutputStream.writeUTF("Ingrese el nombre del archivo a eliminar");
                        respuesta = dataInputStream.readUTF();
                        newfile = new File(file,respuesta);
                        newfile.deleteOnExit();
                        dataOutputStream.writeUTF("Que deseas realizar? \n 1.Ver Listado de Archivos \n 2. Duplicar Archivo \n 3. Eliminar Archivos");
                        break;

                    default:dataOutputStream.writeUTF("Que deseas realizar? \n 1.Ver Listado de Archivos \n 2. Duplicar Archivo \n 3. Eliminar Archivos");

                }
            }
            dataInputStream.close();
            dataOutputStream.close();
            cliente.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static String mostrarContenido(File file){
        String[] contenido = file.list();
        String archivos = "";
        for (int i=0; i<contenido.length; i++)
            archivos = archivos +"\n"+contenido[i];
        return archivos;
    }

    public static void leerArchivo(String archivoLeer, File newFile){
        File archivo = new File("src/main/java/archivoServidor/"+archivoLeer);
        FileReader fileReader = null;
        BufferedReader bufferedReader;
        try {
            fileReader = new FileReader(archivo);
            bufferedReader = new BufferedReader(fileReader);
            String linea;
            while ((linea = bufferedReader.readLine()) != null){
               duplicarContenido(linea,newFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static boolean duplicarContenido(String msg, File archivo) {
        try {
            FileWriter fichero = new FileWriter(archivo,true);
            BufferedWriter bw = new BufferedWriter(fichero);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(msg);
            bw.close();
            pw.close();
            return true;
        }catch (IOException e){
            System.out.println("Error en escritura del archivo");
            return false;
        }

    }

}
