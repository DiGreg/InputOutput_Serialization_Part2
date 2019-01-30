import java.net.*;//в ббк. находятся 2 класса ServerSocket и Socket
import java.io.*;//ббк., чтобы взять классы потоков для потоков м/у сервером и клиентом

public class SimpleFileServer {

    public static void main(String[] args) {
        new SimpleFileServer();
    }

    //Конструктор сервера:
    public SimpleFileServer(){
        System.out.println("FILE SERVER IS STARTED...");//сообщение в консоль, что файл-сервер запущен

        //создаю сокет сервера прямо в try() с ресурсами, адрес порта сервера выбираю произвольно:
        try(ServerSocket server = new ServerSocket(2000)) {

            //запускаем бесконечный цикл обработки:
            while(true){
                Socket clientSocket = server.accept();//инициализация клиентского сокета на сервере - ожидает подключения клиента

                //создаю поток вывода данных на сторону клиента dos - обработчик исходящего потока данных:
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

                //создаю поток чтения данных из файла сериализации stud.ser:
                FileInputStream fis = new FileInputStream("stud.ser");

                //использую поток-буферный_массив для ускорения передачи данных:
                ByteArrayOutputStream out = new ByteArrayOutputStream();//для формирования буферного массива байт из файла сериализации

                int x;//переменная для хранения считанного байта данных
                while ((x = fis.read()) != -1) {//внутри условия происходит чтение данных из файла
                    out.write(x);//Запись данных в поток-буферный_массив
                }

                //теперь записываю в исходящий поток полученный массив байт из файла
                dos.write(out.toByteArray());//записываю в исходящий поток массив байт,полученных из файла

                //закрываю ресурсы:
                fis.close();
                dos.close(); //закрываю поток вывода данных

            }
        }catch (IOException ex){
            ex.printStackTrace();
        }

    }
}
