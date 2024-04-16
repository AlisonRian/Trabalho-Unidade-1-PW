package ufrn.demo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ufrn.demo.Classes.Produto;
import ufrn.demo.Dao.ProdutoDao;

import java.io.IOException;
import java.util.List;
@Controller
public class ListaProdutosController {
    ProdutoDao produtoDao = new ProdutoDao();
    List<Produto> produtos =  produtoDao.listarTodosProduto();
    @RequestMapping(value="/homeCliente")
    public void listarProdutosCliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var writer = response.getWriter();
        produtos = produtoDao.listarTodosProduto();
        writer.println("<html> <head> <title>Home</title> <link rel=\"stylesheet\" href=\"table.css\"> </head> <body> <table>");
        writer.println("<h1>Home Cliente</h1>\n" +
                "    <h2>Produtos</h2>\n" +
                "    <a href=\"/logout\">\n" +
                "        <button>Logout</button>\n" +
                "    </a> </br>");
        writer.println("<thead>");
        writer.println("<tr>");
        writer.println("<th>Nome</th>");
        writer.println("<th>Descrição</th>");
        writer.println("<th>Preço</th>");
        writer.println("<th>Estoque</th>");
        writer.println("<th>Carrinho</th>");
        writer.println("</thead>");
        for (Produto p : produtos){
            writer.println("<tr>");
            writer.println("<td>"+p.getNome()+"</td>");
            writer.println("<td>"+p.getDescricao()+"</td>");
            writer.println("<td>"+p.getPreco()+"</td>");
            writer.println("<td>"+p.getEstoque()+"</td>");
            if(p.getEstoque()>0){
                writer.println("<td><a href=\"/tratarCarrinho?id="+p.getId()+"&comando=add\">Adicionar</a></td>");
            }else{
                writer.println("<td>Sem estoque</td>");
            }
            writer.println("</tr>");
        }
        writer.println("</table><a href=\"/carrinho\">Ver carrinho</a></body> </html>");
    }
    @RequestMapping(value="/homeLojista")
    public void exibirHomeLojista(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var writer = response.getWriter();
        writer.println("<html> <head> <title>Home</title> </head> " +
                "<body> <h1>Home Lojista</h1> <h2>Produtos</h2>\n" +
                " <a href=\"/logout\">\n" +
                "    <button>Logout</button>\n" +
                " </a>  " +
                "<a href=\"/listar\">" +
                "   <button>Listar produtos</button> " +
                "</a>" +
                "<a href=\"/cadastrar\">" +
                "   <button>Cadastrar produtos</button>"+
                "</a>" +
                "</body> </html>");
    }
    @RequestMapping(value="/cadastrar")
    public void cadastrarProdutosLojista(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var writer = response.getWriter();
        writer.println("<h1>Home Lojista</h1>\n" +
                "<h2>Cadastrar produtos</h2>\n" +
                " <a href=\\\"/logout\\\">\n" +
                "    <button>Logout</button>\n" +
                " </a>\n" +
                "<a href=\"/listar\">" +
                "   <button>Listar produtos</button> " +
                "</a>" +
                "<form action=\"/cadastrarProdutos\" method=\"post\">\n" +
                "    <input type=\"text\" placeholder=\"Nome\" name=\"nomeProduto\"> </br>\n" +
                "    <input type=\"number\" placeholder=Preço name=\"precoProduto\"> </br>\n" +
                "    <input type=\"text\" placeholder=\"Descrição\" name=\"descricaoProduto\"> </br>\n" +
                "    <input type=\"number\" placeholder=\"Estoque\" name=\"estoqueProduto\"> </br>\n" +
                "    <button type=\"submit\">Cadastrar</button> </br>\n" +
                "</form>");

    }
    @RequestMapping(value="/listar")
    public void listarProdutos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var writer = response.getWriter();
        produtos = produtoDao.listarTodosProduto();
        writer.println("<html> <head> <title>Home</title> <link rel=\"stylesheet\" href=\"table.css\"> </head> <body> <table>");
        writer.println("<h1>Home Lojista</h1>\n" +
                "    <h2>Produtos</h2>\n" +
                "    <a href=\"/logout\">\n" +
                "        <button>Logout</button>\n" +
                "    </a>");
        writer.println("<thead>");
        writer.println("<tr>");
        writer.println("<th>Nome</th>");
        writer.println("<th>Descrição</th>");
        writer.println("<th>Preço</th>");
        writer.println("<th>Estoque</th>");
        writer.println("</thead>");
        for (Produto p : produtos){
            writer.println("<tr>");
            writer.println("<td>"+p.getNome()+"</td>");
            writer.println("<td>"+p.getDescricao()+"</td>");
            writer.println("<td>"+p.getPreco()+"</td>");
            writer.println("<td>"+p.getEstoque()+"</td>");
            writer.println("</tr>");
        }
        writer.println("</table></body> </html>");
    }
}
