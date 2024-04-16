package ufrn.demo.Dao;
import ufrn.demo.Classes.Cliente;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {
    public void UpdateCliente(Cliente cliente){
        Connection connection = null;
        PreparedStatement pt = null;
        try{
            connection = Conexao.getConnection();

            pt = connection.prepareStatement("update Cliente set nome=?,email=?,senha=? where id=?");
            pt.setString(1,cliente.getEmail());
            pt.setString(2,cliente.getEmail());
            pt.setString(3,cliente.getSenha());
            pt.setInt(4,cliente.getId());
            pt.executeUpdate();
            connection.close();
        }catch(SQLException | URISyntaxException ex){
            System.out.println("Connection Failed! Check output console" + ex.getMessage());
        }
    }
    public Cliente getClienteById(Integer id) {
        Connection connection = null;
        PreparedStatement pt = null;
        ResultSet rs = null;
        Cliente cliente = null;

        try {
            connection = Conexao.getConnection();

            pt = connection.prepareStatement("select * from Cliente where id = ?");
            pt.setInt(1, id);

            rs = pt.executeQuery();

            while (rs.next()) {
                cliente = new Cliente(rs.getInt("id"),rs.getString("nome"),rs.getString(" email"),rs.getString("senha"));
            }
            connection.close();

        } catch (SQLException | URISyntaxException ex) {
            System.out.println("Connection Failed! Check output console" + ex.getMessage());
        }
        return cliente;
    }

    public List<Cliente> listarTodosClientes() {

        Connection connection = null;
        PreparedStatement pt = null;
        ResultSet rs = null;
        List<Cliente> listaDeTarefas = new ArrayList<>();

        try {
            connection = Conexao.getConnection();

            pt = connection.prepareStatement("select * from Clientes");
            rs = pt.executeQuery();

            while (rs.next()) {
                Cliente clientes = new Cliente(rs.getInt("id"),rs.getString("nome"),rs.getString(" email"),rs.getString("senha"));
                listaDeTarefas.add(clientes);
            }
            connection.close();

        } catch (SQLException | URISyntaxException ex) {
            System.out.println("Connection Failed! Check output console" + ex.getMessage());
        }
        return listaDeTarefas;
    }
    public void cadastrarCliente(Cliente cliente) {

        Connection connection = null;
        PreparedStatement pt = null;

        try {
            connection = Conexao.getConnection();

            pt = connection.prepareStatement(
                    "insert into Cliente (nome, email, senha) values (?,?,?)");

            pt.setString(1, cliente.getNome());
            pt.setString(2, cliente.getEmail());
            pt.setString(3, cliente.getSenha());

            pt.executeUpdate();
            connection.close();

        } catch (SQLException | URISyntaxException ex) {
            System.out.println("Connection Failed! Check output console" + ex.getMessage());
        }
    }

    public boolean verificarLogin(String email, String senha){
        Connection connection = null;
        PreparedStatement pt = null;
        ResultSet rs = null;
        boolean encontrado = false;

        try{
            connection = Conexao.getConnection();
            pt = connection.prepareStatement("select * from Cliente where email = ? and senha = ?");
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
}
