package org.example;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.*;
import java.util.Scanner;

public class SFTPClientTest {

    private final String testFilePath = "test_domains.txt";

    @Test
    public void testValidIP() {
        SFTPClient client = new SFTPClient("test.com", 22, "user", "pass");
        Assert.assertTrue(client.isValidIP("192.168.1.1"), "IP должен быть корректным");
        Assert.assertFalse(client.isValidIP("999.999.999.999"), "IP не должен быть корректным");
    }

    @Test
    public void testAddDomain() throws IOException {
        SFTPClient client = new SFTPClient("test.com", 22, "user", "pass");
        client.addDomain(new Scanner(new StringReader("example.com\n192.168.1.1")));

        // Проверить, добавлена ли пара
        boolean exists = client.isDomainExists("example.com - 192.168.1.1");
        Assert.assertTrue(exists, "Пара должна быть добавлена.");

        // Добавляем дубликат
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        client.addDomain(new Scanner(new StringReader("example.com\n192.168.1.1")));
        System.setOut(originalOut);

        Assert.assertTrue(outContent.toString().contains("Эта пара уже существует."), "Дубликат должен быть обнаружен");
    }

    @Test
    public void testRemoveDomain() throws IOException {
        SFTPClient client = new SFTPClient("test.com", 22, "user", "pass");

        // Добавляем домен для дальнейшего удаления
        client.addDomain(new Scanner(new StringReader("remove.com\n192.168.1.2")));

        // Удаляем домен
        client.removeDomain(new Scanner(new StringReader("remove.com")));

        // Проверяем, что домена больше нет
        boolean exists = client.isDomainExists("remove.com - 192.168.1.2");
        Assert.assertFalse(exists, "Пара должна быть удалена.");
    }
}
