import java.io.*;
import java.net.Socket;

public class SimpleFileClient {

    public static void main(String[] args) {
        new SimpleFileClient();
    }

    //Конструктор файл-клиента:
    public SimpleFileClient(){
        System.out.println("FILE CLIENT IS STARTED...");//сообщение в консоль, что файл-клиент запущен

        //создаю клиентский сокет с адресом сервера и номером порта на сервере (такой же, какой указали на сервере):
        try(Socket socket = new Socket("localhost", 2000)){

            //создаю поток чтения данных с сервера dis - обработчик входящего потока данных:
            DataInputStream dis = new DataInputStream(socket.getInputStream());

            //поток записи данных в файл studInput.ser:
            FileOutputStream fos = new FileOutputStream("studInput.ser");

            //использую поток-буферный_массив для ускорения обработки данных:
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();//для формирования буферного массива байт из входящего потока

            //теперь обработаю этот входящий поток:

            //чтение данных из потока:
            int x;
            while ((x = dis.read()) != -1){
                buffer.write(x);//Запись данных в поток-буферный_массив
            }

            //Записываю сформированный буфер данных в файл (конечно, преобразуя его в массив):
            fos.write(buffer.toByteArray());

            //теперь десериализую полученный файл с помощью своего метода, который у меня есть в классе Main:
            Student desStudInput = (Student)Main.deserializeObject("studInput.ser");//выполню явное приведение типа восстановленного объекта
            desStudInput.info();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
