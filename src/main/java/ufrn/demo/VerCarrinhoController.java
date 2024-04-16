package ufrn.demo;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ufrn.demo.Classes.Produto;
import ufrn.demo.Dao.ProdutoDao;

import java.io.IOException;
import java.util.List;

@Controller
public class VerCarrinhoController {
    @RequestMapping("/carrinho")
    public void exibirCarrinho(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var writer = response.getWriter();
        Cookie[] cookies = request.getCookies();
        ProdutoDao produtoDao = new ProdutoDao();
        List<Produto> produtos = produtoDao.listarTodosProduto();
        int quantidade = 0;
        int id=0;
        writer.println("<html> <head> <title>Carrinho</title> <link rel=\"stylesheet\" href=\"table.css\"> </head> <body> <table>");
        writer.println("<h1>Carrinho</h1>\n" +
                "    <h2>Produtos</h2>\n" +
                "    <a href=\"/logout\">\n" +
                "        <button>Logout</button>\n" +
                "    </a> </br>");
        writer.println("<thead>");
        writer.println("<tr>");
        writer.println("<th>Nome</th>");
        writer.println("<th>Descrição</th>");
        writer.println("<th>Preço</th>");
        writer.println("<th>Quantidade</th>");
        writer.println("<th>Remover</th>");
        writer.println("</thead>");
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (!c.getName().equals("JSESSIONID")) {
                    for (int i = 0; i < c.getValue().length(); i++) {
                        String verificar = c.getValue();
                        if (verificar.charAt(i) == '|') {
                            id = Integer.parseInt(verificar.substring(0,i));
                            quantidade = Integer.parseInt(verificar.substring(i+1));
                        }
                    }
                }
                if (produtoDao.getProdutoById(id) != null) {
                    Produto p = produtoDao.getProdutoById(id);
                    if(quantidade>p.getEstoque()){
                        quantidade=p.getEstoque();
                        Cookie c1 = new Cookie(c.getName(),id+"|"+quantidade);
                        c1.setMaxAge(48*60*60);
                        response.addCookie(c1);
                    }
                    if(quantidade>0){
                        writer.println("<tr>");
                        writer.println("<td>" + p.getNome() + "</td>");
                        writer.println("<td>" + p.getDescricao() + "</td>");
                        writer.println("<td>" + p.getPreco() + "</td>");
                        writer.println("<td>" + quantidade + "</td>");
                        writer.println("<td><a href=\"/tratarCarrinho?id=" + p.getId() + "&comando=remove\">Remover</a></td>");
                        writer.println("</tr>");
                    }
                }
            }
        }
        writer.println("</table><a href=\"/homeCliente\">Ver Produtos</a> </br>" +
                "<a href=\"/finalizar\">Finalizar compra<a/>" +
                "</body> </html>");
    }
    @RequestMapping("/finalizar")
    public void finalizarCompra(HttpServletRequest request, HttpServletResponse response) throws IOException{
        var writer = response.getWriter();
        Cookie[] cookies = request.getCookies();
        ProdutoDao produtoDao = new ProdutoDao();
        int quantidade = 0;
        float totalCompra=0;
        int id=0;
        writer.println("<html> <head> <title>Carrinho</title> <link rel=\"stylesheet\" href=\"table.css\"> </head> <body> <table>");
        writer.println("<h1>Carrinho</h1>\n" +
                "    <h2>Produtos</h2>\n");
        writer.println("<thead>");
        writer.println("<tr>");
        writer.println("<th>Nome</th>");
        writer.println("<th>Descrição</th>");
        writer.println("<th>Preço</th>");
        writer.println("<th>Quantidade</th>");
        writer.println("</thead>");
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (!c.getName().equals("JSESSIONID")) {
                    for (int i = 0; i < c.getValue().length(); i++) {
                        String verificar = c.getValue();
                        if (verificar.charAt(i) == '|') {
                            id = Integer.parseInt(verificar.substring(0,i));
                            quantidade = Integer.parseInt(verificar.substring(i+1));
                        }
                    }
                }
                if (produtoDao.getProdutoById(id) != null) {
                    Produto p = produtoDao.getProdutoById(id);
                    totalCompra+=(p.getPreco()*quantidade);
                    produtoDao.UpdateEstoqueProduto((p.getEstoque()-quantidade),id);
                    writer.println("<tr>");
                    writer.println("<td>" + p.getNome() + "</td>");
                    writer.println("<td>" + p.getDescricao() + "</td>");
                    writer.println("<td>" + p.getPreco() + "</td>");
                    writer.println("<td>" + quantidade + "</td>");
                    writer.println("</tr>");
                }
            }
            writer.println("<table><tbody><tr>Total: "+totalCompra+"<tr></tbody></table>");
            writer.println("</table><a href=\"/homeCliente\">Ver Produtos</a> </br></body> </html>");
            for(Cookie c:cookies){
                if(!c.getName().equals("JSESSIONID")){
                    Cookie c1 = new Cookie(c.getName(), c.getValue());
                    c1.setMaxAge(0);
                    response.addCookie(c1);
                }
            }
        }
    }

}

