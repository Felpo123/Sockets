import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        System.out.println("Ingrese la dirección ip a conectar: ");
        String ipDestino = teclado.nextLine();
        System.out.println("Ingrese el puerto: ");
        int puertoDestino = teclado.nextInt();
        System.out.println("Conectando....");

        try {  //try y catch para controlar la ejecución del subprograma

            Socket socketCliente = new Socket(ipDestino,puertoDestino);  // objeto socket para realizar la comunicación  con la ip de destino y el
                                                                         // puerto que vamos a utilizar para la comunicación.

            System.out.println("Conexion establecida");
            DataOutputStream dataOutputStream = new DataOutputStream(socketCliente.getOutputStream()); // objeto DataOutputStream para ayudarnos a enviar datos al servidor.
            DataInputStream dataInputStream = new DataInputStream(socketCliente.getInputStream());     // objeto DataOutputStream para ayudarnos a leer datos al servidor.
            String mensaje = "";

            while (!mensaje.equalsIgnoreCase("EXIT"))
            {
                mensaje = teclado.nextLine();
                dataOutputStream.writeUTF(mensaje);
                socketCliente.setKeepAlive(true);               //Mantenemos el socket activo
                System.out.println(dataInputStream.readUTF());  //Imprimimos
            }
            socketCliente.close();
        }catch (ConnectException e){
            System.err.println("Error: Conexion rechazada");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    }

