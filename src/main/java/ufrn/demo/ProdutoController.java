package ufrn.demo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ufrn.demo.Classes.Produto;
import ufrn.demo.Dao.ProdutoDao;

import java.io.IOException;
import java.util.List;

@Controller
public class ProdutoController {
    @RequestMapping(value = "/cadastrarProdutos",method = RequestMethod.POST)
    public void cadastrarProdutos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int preco = Integer.parseInt(request.getParameter("precoProduto"));
        String nome = request.getParameter("nomeProduto");
        String descricao = request.getParameter("descricaoProduto");
        System.out.println(descricao);
        int estoque = Integer.parseInt(request.getParameter("estoqueProduto"));

        Produto produto = new Produto();
        produto.setPreco(preco);
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setEstoque(estoque);

        ProdutoDao produtoDao = new ProdutoDao();
        produtoDao.cadastrarProduto(produto);
        response.sendRedirect("/homeLojista");
    }
}
