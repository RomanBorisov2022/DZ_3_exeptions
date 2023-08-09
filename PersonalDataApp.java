import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PersonalDataApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Введите данные (Фамилия Имя Отчество ДатаРождения НомерТелефона Пол):");
            String input = scanner.nextLine();
            String[] inputData = input.split(" ");

            if (inputData.length != 6) {
                throw new InputMismatchException("Количество данных не соответствует требованиям.");
            }

            String lastName = inputData[0];
            String firstName = inputData[1];
            String middleName = inputData[2];
            String birthDate = inputData[3];
            long phoneNumber = Long.parseLong(inputData[4]);
            char gender = inputData[5].charAt(0);

            if (gender != 'f' && gender != 'm') {
                throw new IllegalArgumentException("Некорректный пол. Используйте 'f' или 'm'.");
            }

            // Проверка формата даты рождения (dd.mm.yyyy)
            if (!birthDate.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
                throw new IllegalArgumentException("Некорректный формат даты рождения. Используйте dd.mm.yyyy.");
            }

            // Проверка номера телефона (10 цифр)
            if (String.valueOf(phoneNumber).length() != 10) {
                throw new IllegalArgumentException("Некорректный номер телефона. Используйте 10 цифр.");
            }

            String filename = lastName + ".txt";
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8))) {
                String formattedData = String.format("%s %s %s %s %d %c", lastName, firstName, middleName, birthDate, phoneNumber, gender);
                writer.write(formattedData);
            } catch (IOException e) {
                System.err.println("Ошибка при чтении/записи файла: " + e.getMessage());
                e.printStackTrace();
            }

            System.out.println("Данные успешно записаны в файл " + filename);
        } catch (InputMismatchException | IllegalArgumentException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
