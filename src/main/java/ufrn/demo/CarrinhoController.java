package ufrn.demo;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ufrn.demo.Classes.Carrinho;
import ufrn.demo.Dao.ProdutoDao;
import ufrn.demo.Classes.Produto;

import java.io.IOException;

import java.util.List;

@Controller
public class CarrinhoController {
    @RequestMapping(value="/tratarCarrinho")
    public void tratarRequisicao(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String command = request.getParameter("comando");
        Cookie[] cookies = request.getCookies();
        Carrinho carrinho = new Carrinho();
        ProdutoDao produtoDao = new ProdutoDao();
        String nomeCookie = "id"+id;
        int quantidade=1;
        int capturaId=-1;
        int capturaQtd=0;

        for(Cookie c1:cookies){
            if(c1.getName().equals(nomeCookie)){
                for(int i=0;i<c1.getValue().length();i++){
                    String verificar = c1.getValue();
                    if(verificar.charAt(i)=='|'){
                        quantidade = Integer.parseInt(verificar.substring(i+1));
                        quantidade++;
                    }
                }
            }
        }

        if(command.equals("add")){
            Cookie c = new Cookie(nomeCookie,request.getParameter("id")+"|"+quantidade);
            c.setMaxAge(48*60*60);
            response.addCookie(c);
            response.sendRedirect("/homeCliente");
        }else if(command.equals("remove")){
            for(Cookie c1 : cookies) {
                if (!c1.getName().equals("JSESSIONID")){
                    for (int i = 0; i < c1.getValue().length(); i++) {
                        String verificar = c1.getValue();
                        if (verificar.charAt(i) == '|') {
                            capturaId = Integer.parseInt(verificar.substring(0,i));
                            capturaQtd = Integer.parseInt(verificar.substring(i+1));
                            capturaQtd--;
                        }
                    }
                        if (capturaId == id) {
                            Cookie c = new Cookie(nomeCookie, capturaId+"|"+capturaQtd);
                            if(capturaQtd>0){
                                c.setMaxAge(48*60*60);
                            }else{
                                c.setMaxAge(0);

                            }
                            response.addCookie(c);
                        }
                 }
            }
            response.sendRedirect("/carrinho");
        }
    }
}
