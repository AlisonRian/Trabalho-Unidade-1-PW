package ufrn.demo.Dao;

import ufrn.demo.Classes.Produto;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class ProdutoDao {
    public void UpdateEstoqueProduto(int estoque,int id){
        Connection connection = null;
        PreparedStatement pt = null;
        try{
            connection = Conexao.getConnection();

            pt = connection.prepareStatement("update Produto set estoque=? where id=?");
            pt.setInt(1,estoque);
            pt.setInt(2,id);
            pt.executeUpdate();
            connection.close();
        }catch(SQLException | URISyntaxException ex){
            System.out.println("Connection Failed! Check output console" + ex.getMessage());
        }
    }
    public Produto getProdutoById(Integer id) {
        Connection connection = null;
        PreparedStatement pt = null;
        ResultSet rs = null;
        Produto produto = null;

        try {
            connection = Conexao.getConnection();

            pt = connection.prepareStatement("select * from Produto where id = ?");
            pt.setInt(1, id);

            rs = pt.executeQuery();

            while (rs.next()) {
                produto = new Produto(rs.getInt("id"),rs.getInt("preco"),rs.getString("nome"),rs.getString("descricao"),rs.getInt("estoque"));
            }
            connection.close();

        } catch (SQLException | URISyntaxException ex) {
            System.out.println("Connection Failed! Check output console" + ex.getMessage());
        }
        return produto;
    }

    public List<Produto> listarTodosProduto() {

        Connection connection = null;
        PreparedStatement pt = null;
        ResultSet rs = null;
        List<Produto> listaDeProdutos = new ArrayList<>();

        try {
            connection = Conexao.getConnection();

            pt = connection.prepareStatement("select * from Produto");
            rs = pt.executeQuery();

            while (rs.next()) {
                Produto produtos = new Produto(rs.getInt("id"),rs.getInt("preco"),rs.getString("nome"),rs.getString("descricao"),rs.getInt("estoque"));
                listaDeProdutos.add(produtos);
            }
            connection.close();

        } catch (SQLException | URISyntaxException ex) {
            System.out.println("Connection Failed! Check output console" + ex.getMessage());
        }
        return listaDeProdutos;
    }
    public void cadastrarProduto(Produto produto) {

        Connection connection = null;
        PreparedStatement pt = null;

        try {
            connection = Conexao.getConnection();

            pt = connection.prepareStatement(
                    "insert into Produto (preco ,nome, descricao, estoque) values (?,?,?,?)");

            pt.setInt(1,produto.getPreco());
            pt.setString(2, produto.getNome());
            pt.setString(3, produto.getDescricao());
            pt.setInt(4,produto.getEstoque());

            pt.executeUpdate();
            connection.close();

        } catch (SQLException | URISyntaxException ex) {
            System.out.println("Connection Failed! Check output console" + ex.getMessage());
        }
    }
}
