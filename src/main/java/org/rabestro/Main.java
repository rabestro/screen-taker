package org.rabestro;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws AWTException {
        System.out.println("Кофе-машина");
        final int moneyAmount = new Scanner(System.in).nextInt();

        final int[] drinkPrices = {150, 80, 20, 50};
        final String[] drinkNames = {"капучино", "эспрессо", "воду", "молоко"};

        var canBuyAnything = false;
        for(int i = 0; i < drinkNames.length; i++)
        {
            if(moneyAmount >= drinkPrices[i]) {
                System.out.println("Вы можете купить " + drinkNames[i]);
                canBuyAnything = true;
            }
        }
        if (!canBuyAnything) {
            System.out.println("Недостаточно средств :( Изучайте Java и зарабатывайте много!))");
        }


        final var dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        final var now = new Date();
        System.out.println(dateFormat.format(now));

        final var screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final var rectangle = new Rectangle(screenSize);
        final var robot = new Robot();
        final var image = robot.createScreenCapture(rectangle);
        System.out.println(image.getWidth() + "x" + image.getHeight());
    }
}