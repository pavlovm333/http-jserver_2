package ru.otus.http.jserver;


public class Application {

    // Домашнее задание:
    // 1. Добавить логирование вместо sout
    // 2. Доделать DELETE для продуктов. DELETE без ид удаляет все продукты, DELETE c ид удаляет конкретный продукт
    // 3. * Доделать PUT для продуктов. По id из тела запроса находим соответствующий продукт и обновляем его поля
    public static void main(String[] args) {new HttpServer(8189).start();}
}
