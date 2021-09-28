import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class TrafficBlocks {
    public static void main(String[] args) {
        //Получаем аргументы из командной строки (путь к файлу и дату)
        String path = args[0];
        String date = args[1];
        //Парсим файл находящийся по заданному пути
        List<Restriction> restrictions = Parser.parseFile(path);

        dateComparison(date, restrictions);
    }

    static void dateComparison(String date, List<Restriction> restrictions) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate userDate = LocalDate.parse(date, dtf);
        //Подсчитываю колличество ограничений на дату, введеную пользователем
        int counter = 0;
        for (Restriction restriction : restrictions) {
            if ((userDate.isAfter(restriction.getStartDate()))
                    && (userDate.isBefore(restriction.getEndDate()))
                    || (userDate.isEqual(restriction.getStartDate()))
                    || (userDate.isEqual(restriction.getEndDate()))) {
                counter++;
            }
        }
        System.out.println("The number of traffic restrictions in force as of " + date + " = " + counter);
    }
}
