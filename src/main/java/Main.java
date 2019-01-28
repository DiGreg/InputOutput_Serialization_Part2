/*
Java 3. Средства ввода-вывода. Сериализация объектов
Работа с файлами.

Гришин Дмитрий
 */
import java.io.*; //импорт библиотеки ввода/вывода
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //Вывод страницы (1 стр. условно = 1800 лат.символов) из большого файла в консоль:
        //printPage("BigTxtFile.txt");

        //Создам студента:
        Student stud1 = new Student(123, "John", 80);

        //Сериализую студента stud1 в файл stud.ser:
        //serializeObject(stud1,"stud.ser");

        //Десериализую объект (восстановлю):
        Student desStud1 = (Student)deserializeObject("stud.ser");//и выполню явное приведение типа восстановленного объекта
        desStud1.info();
        desStud1.setScore(55); //поменяю поле очков
        desStud1.info();
    }

    //Метод чтения файла постранично (условно 1 стр. = 1800 символов)
    public static void printPage(String fileWay){
        final int PAGE_SIZE = 1800;
        Scanner scan = new Scanner(System.in);//сканер для считывания введённого номера страницы
        //Объект RandomAccessFile помещаю в try c ресурсами, т.к. он Closeable
        try(RandomAccessFile raf = new RandomAccessFile(fileWay, "r")){
            byte[] bArray = new byte[PAGE_SIZE];//массив для заполнения считанными байтами. Его длина укажет одной из перегруженной версий метода read(), сколько считывать позиций
            int p = scan.nextInt() - 1;//формирую номер страницы

            long t1 = System.currentTimeMillis();//засекаю время старта
            raf.seek(p * PAGE_SIZE);//перемещаю курсор на позицию с индексом (p * PAGE_SIZE);
            raf.read(bArray);//считываю 1800 позиций (одну страницу номера p) в байт-массив
            System.out.println(new String(bArray));//вывод стр. на консоль
            //System.out.println();
            System.out.println(System.currentTimeMillis() - t1);//проверка времени выполнения

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scan.close();
        }
    }

    //Метод сериализации объекта в файл:
    public static void serializeObject(Object obj, String fileName){
        //Использую try с ресурсами, чтобы не писать oos.close();
        //oos - поток для сериализации объекта. Используя его, запишу объект в файл, имя которого укажу при вызове метода.
        //Внутрь конструктора ObjectOutputStream передаю объект класса FileOutputStream, используемого для записи данных в файл.
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(obj);//пишу объект obj (сериализую) в файл, указанный выше
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Метод десериализации объекта из файла:
    public static Object deserializeObject(String fileName){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null; //на случай, если не сработал основной код
    }
}
