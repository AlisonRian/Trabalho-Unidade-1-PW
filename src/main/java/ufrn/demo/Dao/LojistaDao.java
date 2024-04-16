package ufrn.demo.Dao;

import ufrn.demo.Classes.Cliente;
import ufrn.demo.Classes.Lojista;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LojistaDao {
    public void UpdateLojista(Lojista lojista){
        Connection connection = null;
        PreparedStatement pt = null;
        try{
            connection = Conexao.getConnection();

            pt = connection.prepareStatement("update Lojista set nome=?,email=?,senha=? where id=?");
            pt.setString(1,lojista.getEmail());
            pt.setString(2,lojista.getEmail());
            pt.setString(3,lojista.getSenha());
            pt.setInt(4,lojista.getId());
            pt.executeUpdate();
            connection.close();
        }catch(SQLException | URISyntaxException ex){
            System.out.println("Connection Failed! Check output console" + ex.getMessage());
        }
    }
    public Lojista getLojistaById(Integer id) {
        Connection connection = null;
        PreparedStatement pt = null;
        ResultSet rs = null;
        Lojista lojista = null;

        try {
            connection = Conexao.getConnection();

            pt = connection.prepareStatement("select * from Lojista where id = ?");
            pt.setInt(1, id);

            rs = pt.executeQuery();

            while (rs.next()) {
                lojista = new Lojista(rs.getInt("id"),rs.getString("nome"),rs.getString(" email"),rs.getString("senha"));
            }
            connection.close();

        } catch (SQLException | URISyntaxException ex) {
            System.out.println("Connection Failed! Check output console" + ex.getMessage());
        }
        return lojista;
    }

    public List<Lojista> listarTodosLojistas() {

        Connection connection = null;
        PreparedStatement pt = null;
        ResultSet rs = null;
        List<Lojista> listaDeLojista = new ArrayList<>();

        try {
            connection = Conexao.getConnection();

            pt = connection.prepareStatement("select * from Lojista");
            rs = pt.executeQuery();

            while (rs.next()) {
                Lojista lojista = new Lojista(rs.getInt("id"),rs.getString("nome"),rs.getString(" email"),rs.getString("senha"));
                listaDeLojista.add(lojista);
            }
            connection.close();

        } catch (SQLException | URISyntaxException ex) {
            System.out.println("Connection Failed! Check output console" + ex.getMessage());
        }
        return listaDeLojista;
    }

    public boolean verificarLogin(String email, String senha){
        Connection connection = null;
        PreparedStatement pt = null;
        ResultSet rs = null;
        boolean encontrado = false;

        try{
            connection = Conexao.getConnection();
            pt = connection.prepareStatement("select * from Lojista where email = ? and senha = ?");
            pt.setString(1,email);
            pt.setString(2,senha);
            rs = pt.executeQuery();
            if(rs.next()){
                encontrado = true;
            }
            connection.close();
        }catch(SQLException | URISyntaxException ex){
            System.out.println("Connection Failed! Check output console" + ex.getMessage());
        }
        return encontrado;
    }
    public void cadastrarLojista(Lojista lojista) {

        Connection connection = null;
        PreparedStatement pt = null;

        try {
            connection = Conexao.getConnection();

            pt = connection.prepareStatement(
                    "insert into Lojista (id, nome, email, senha) values (?, ?,?,?)");

            pt.setInt(1, lojista.getId());
            pt.setString(2, lojista.getNome());
            pt.setString(3, lojista.getEmail());
            pt.setString(4, lojista.getSenha());

            pt.executeUpdate();
            connection.close();

        } catch (SQLException | URISyntaxException ex) {
            System.out.println("Connection Failed! Check output console" + ex.getMessage());
        }
    }
}
