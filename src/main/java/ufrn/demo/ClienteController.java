package ufrn.demo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ufrn.demo.Classes.Cliente;
import ufrn.demo.Dao.ClienteDao;

import java.io.IOException;


@Controller
public class ClienteController {

    @RequestMapping(value = "/cadastroCliente", method = RequestMethod.POST)
    public void CadastrarCliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setEmail(email);
        cliente.setSenha(senha);

        ClienteDao clienteDao = new ClienteDao();
        clienteDao.cadastrarCliente(cliente);

        response.sendRedirect("index.html");
    }


}
