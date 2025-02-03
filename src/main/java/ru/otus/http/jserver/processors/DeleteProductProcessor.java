package ru.otus.http.jserver.processors;

import com.google.gson.Gson;
import ru.otus.http.jserver.HttpRequest;
import ru.otus.http.jserver.application.Product;
import ru.otus.http.jserver.application.ProductsService;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DeleteProductProcessor implements RequestProcessor {
    private ProductsService productsService;

    public DeleteProductProcessor(ProductsService productsService) {
        this.productsService = productsService;
    }

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        String jsonResult = null;
        String code = "200 Ok";
        String type = "application/json";
        Gson gson = new Gson();
        if (request.containsParameter("id")) {
            Long id = Long.parseLong(request.getParameter("id"));
            Product product = productsService.deleteProductById(id);
            if (product != null) {
                jsonResult = gson.toJson(product);
            } else {
                code = "404 Not Found";
                type = "text/html";
            }
        } else {
            List<Product> products = productsService.deleteAllProducts();
            jsonResult = gson.toJson(products);
        }
        String response = "" +
                "HTTP/1.1 " + code + "\r\n" +
                "Content-Type: "+ type + "\r\n" +
                "\r\n" +
                jsonResult;
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }





}
