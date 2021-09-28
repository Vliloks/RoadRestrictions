import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {
    public static List<Restriction> parseFile(String path) {
        List<Restriction> restrictions = new ArrayList<>();
        //Получаем файл
        Path file = Paths.get(path);

        try {
            Scanner scanner = new Scanner(file);
            scanner.nextLine();
            //Полученый файл читаем построчно
            while(scanner.hasNextLine()){
                //Каждую строку парсим и добавляем в список
                restrictions.add(parseRestriction(scanner.nextLine()));
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return restrictions;
    }

    private static Restriction parseRestriction(String line) {
        Scanner scanner = new Scanner(line);
        //Разделяем строку по запятым
        scanner.useDelimiter(",");

        //Переходим к примерному месторасположению колонки, содержащей дату начала работы
        for (int i = 0; i < 10; i++) {
            scanner.next();
        }

        //Так-как знак разделения колонок "," в столбце адреса может использоваться,
        //как обычный знак препинания, нахожу ячейку нужного типа
        String start;
        String end;
        while (true) {
            if (scanner.hasNextInt()) {
                start = scanner.next();
                end = scanner.next();
                break;
            } else scanner.next();
        }
        scanner.close();

        //Перевожу получиные значения в тип LocalDate
        DateTimeFormatter dtf= DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate startDate = LocalDate.parse(start, dtf);
        LocalDate endDate = LocalDate.parse(end, dtf);

        return new Restriction(startDate, endDate);
    }
}
