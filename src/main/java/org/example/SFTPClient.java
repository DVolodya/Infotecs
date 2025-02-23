package org.example;


import com.jcraft.jsch.*;
import java.io.*;
import java.net.InetAddress;
import java.util.*;

public class SFTPClient {

    private String host;
    private int port;
    private String username;
    private String password;
    private Session session;
    private ChannelSftp channelSftp;
    private final String filePath = "domains.txt"; // Файл для хранения пар 'домен - адрес'

    public SFTPClient(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public void connect() throws JSchException {
        JSch jsch = new JSch();
        session = jsch.getSession(username, host, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        channelSftp = (ChannelSftp) session.openChannel("sftp");
        channelSftp.connect();
    }

    public void disconnect() {
        if (channelSftp != null) {
            channelSftp.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
    }

    public void start() {
        try {
            connect();
            System.out.println("Подключение успешно!");

            Scanner scanner = new Scanner(System.in);
            while (true) {
                displayMenu();
                int choice = getUserChoice(scanner);

                switch (choice) {
                    case 1: getDomains(); break;
                    case 2: getIPByDomain(scanner); break;
                    case 3: getDomainByIP(scanner); break;
                    case 4: addDomain(scanner); break;
                    case 5: removeDomain(scanner); break;
                    case 6: disconnect(); System.out.println("Выход..."); return;
                    default: System.out.println("Неверный выбор. Попробуйте снова.");
                }
            }
        } catch (JSchException e) {
            System.out.println("Ошибка подключения: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка ввода/вывода: " + e.getMessage());
        }
    }

    private void displayMenu() {
        System.out.println("\nВыберите действие:");
        System.out.println("1. Получение списка пар 'домен – адрес'");
        System.out.println("2. Получение IP-адреса по доменному имени");
        System.out.println("3. Получение доменного имени по IP-адресу");
        System.out.println("4. Добавление новой пары 'домен – адрес'");
        System.out.println("5. Удаление пары 'домен – адрес'");
        System.out.println("6. Завершение работы");
    }

    private int getUserChoice(Scanner scanner) {
        System.out.print("Ваш выбор: ");
        return scanner.nextInt();
    }

    private void getDomains() throws IOException {
        List<String> domains = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                domains.add(line);
            }
        }
        Collections.sort(domains);
        if (domains.isEmpty()) {
            System.out.println("Список доменов пуст.");
        } else {
            System.out.println("Список доменов:");
            domains.forEach(System.out::println);
        }
    }

    private void getIPByDomain(Scanner scanner) {
        System.out.print("Введите доменное имя: ");
        String domain = scanner.next();
        try {
            InetAddress ip = InetAddress.getByName(domain);
            System.out.println("IP-адрес: " + ip.getHostAddress());
        } catch (Exception e) {
            System.out.println("Не удалось получить IP-адрес для " + domain + ": " + e.getMessage());
        }
    }

    private void getDomainByIP(Scanner scanner) {
        System.out.print("Введите IP-адрес: ");
        String ipAddress = scanner.next();
        try {
            InetAddress inet = InetAddress.getByName(ipAddress);
            System.out.println("Доменное имя: " + inet.getCanonicalHostName());
        } catch (Exception e) {
            System.out.println("Не удалось получить доменное имя для " + ipAddress + ": " + e.getMessage());
        }
    }

    protected void addDomain(Scanner scanner) throws IOException {
        System.out.print("Введите доменное имя: ");
        String domain = scanner.next();
        System.out.print("Введите IP-адрес: ");
        String ipAddress = scanner.next();

        if (!isValidIP(ipAddress)) {
            System.out.println("Некорректный IP-адрес.");
            return;
        }

        String entry = domain + " - " + ipAddress;
        if (isDomainExists(entry)) {
            System.out.println("Эта пара уже существует.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(entry);
            bw.newLine();
            System.out.println("Пара добавлена.");
        }
    }

    protected void removeDomain(Scanner scanner) throws IOException {
        System.out.print("Введите доменное имя или IP-адрес для удаления: ");
        String input = scanner.next();
        List<String> domainList = new ArrayList<>();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(input)) {
                    found = true; // Совпадение найдено
                } else {
                    domainList.add(line); // Сохраняем, если не совпадает
                }
            }
        }

        if (found) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                for (String domain : domainList) {
                    bw.write(domain);
                    bw.newLine();
                }
                System.out.println("Пара удалена.");
            }
        } else {
            System.out.println("Пара не найдена.");
        }
    }

    protected boolean isValidIP(String ip) {
        String ipPattern =
                "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
                        + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
                        + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
                        + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        return ip.matches(ipPattern);
    }

    protected boolean isDomainExists(String entry) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals(entry)) {
                    return true; // Пара существует
                }
            }
        }
        return false; // Пара не найдена
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите адрес SFTP-сервера: ");
        String host = scanner.nextLine();
        System.out.print("Введите порт SFTP-сервера: ");
        int port = Integer.parseInt(scanner.nextLine());
        System.out.print("Введите логин: ");
        String username = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        SFTPClient client = new SFTPClient(host, port, username, password);
        client.start();
        scanner.close();
    }
}
