import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Raffle {

    private static ArrayList<Toy> toys = new ArrayList<>();
    private static PriorityQueue<Toy> prizes = new PriorityQueue<>();

    private static int idCounter = 0;

    public void addToy() {
        Scanner scan = new Scanner(System.in);
        String title;
        int frequency;
        while (true) {
            System.out.print("Добавить название игрушки: ");
            title = scan.nextLine();
            if (title.isEmpty()) {
                System.out.println("В названии не может быть пустая строка. Попробуйте снова");
                break;
            }
            System.out.print("Добавьте вероятность выпадения для игрушки: ");
            String value = scan.nextLine();
            if (isDigit(value)) {
                frequency = Integer.parseInt(value);
                if (frequency <= 0) {
                    System.out.println("Вес не может быть отрицательным. Попробуйте снова");
                } else {
                    Toy toy = new Toy(idCounter, title, frequency);
                    if (!toys.contains(toy) || toys.size() == 0) {
                        idCounter++;
                        toys.add(toy);
                        System.out.println("Новая игрушка была добавлена");
                    } else {
                        System.out.println("Такая игрушка уже есть в базе.");
                    }
                }
            } else {
                System.out.println("Вес состоит только из цифр. Попробуйте снова.");
            }
            break;
        }
    }

    public void setFrequency() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Введите ID игрушки: ");
        String value = scan.nextLine();
        if (isDigit(value)) {
            int selectedId = Integer.parseInt(value);
            if (selectedId >= 0 && selectedId < toys.size()) {
                System.out.println("Игрушка " + toys.get(selectedId).getToyTitle() +
                        " имеет вероятность выпадения " + toys.get(selectedId).getToyVictoryFrequency());
                System.out.print("Введите новую вероятность выпадения: ");
                value = scan.nextLine();
                if (isDigit(value)) {
                    int newFrequency = Integer.parseInt(value);
                    toys.get(selectedId).setToyVictoryFrequency(newFrequency);
                    System.out.println("Вероятность выпадения изменена.");
                } else {
                    System.out.println("Вес состоит только из цифр. Попробуйте снова.");
                }
            } else {
                System.out.println("В базе не существует игрушки с таким ID");
            }
        } else {
            System.out.println("ID состоит только из цифр. Попробуйте снова.");
        }
    }

    private static boolean isDigit(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Toy getPrize() {
        if (prizes.size() == 0) {
            Random rnd = new Random();
            for (Toy toy : toys) {
                for (int i = 0; i < toy.getToyVictoryFrequency(); i++) {
                    Toy temp = new Toy(toy.getToyId(), toy.getToyTitle(), rnd.nextInt(1, 10));
                    prizes.add(temp);
                }
            }
        }
        return prizes.poll();
    }

    public void raffle() {
        if (toys.size() >= 2) {
            Toy prize = getPrize();
            System.out.println("Prize: " + prize.getToyTitle());
            saveResult(prize.getInfo());
        } else {
            System.out.println("В базе слишком мало игрушек");
        }
    }

    private void saveResult(String text) {
        File file = new File("Result.txt");
        try {
            file.createNewFile();
        } catch (Exception ignored) {
            throw new RuntimeException();
        }
        try (FileWriter fw = new FileWriter("Result.txt", true)) {
            fw.write(text + "\n");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }    
}
