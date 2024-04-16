package ufrn.demo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ufrn.demo.Dao.ClienteDao;
import ufrn.demo.Dao.LojistaDao;

import java.io.IOException;

@Controller
public class LoginController {
    @RequestMapping(value="/login",method = RequestMethod.POST)
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var login = request.getParameter("nomeLogin");
        var senha = request.getParameter("senhaLogin");

        ClienteDao clienteDao = new ClienteDao();
        LojistaDao lojistaDao = new LojistaDao();

        if(lojistaDao.verificarLogin(login,senha)){
            HttpSession session = request.getSession();
            session.setAttribute("logado", true);
            session.setAttribute("lojista",true);
            response.sendRedirect("/homeLojista");
        }else if(clienteDao.verificarLogin(login,senha)){
            HttpSession session = request.getSession();
            session.setAttribute("logado", true);
            session.setAttribute("cliente",true);
            response.sendRedirect("/homeCliente");
        }else{
            response.sendRedirect("index.html?msg=Login falhou");
        }
    }

    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("index.html?msg=Usuario deslogado");
    }
}

