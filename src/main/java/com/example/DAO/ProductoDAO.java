package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.Model.Categoria;
import com.example.Model.Producto;

@SuppressWarnings("CallToPrintStackTrace")

public class ProductoDAO {

    private final CategoriaDAO catDAO=new CategoriaDAO();

    public boolean insert(Producto p) {
        String sql="insert into productos(nombre, id_categoria, precio, stock) values (?,?,?,?)";
        
        try (Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNombre());
            ps.setInt(2, p.getCategoria().getIdCategoria());
            ps.setDouble(3, p.getPrecio());
            ps.setInt(4, p.getStock());
            
            int affectedRows=ps.executeUpdate();
            if (affectedRows>0) {
                ResultSet rs=ps.getGeneratedKeys();
                if (rs.next()) {
                    p.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Producto searchId(int id) {
        String sql="select * from productos where id_producto=?";
        
        try (Connection con=DBConnection.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToProducto(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Producto searchNombre(String nombre) {
        String sql="select * from productos where nombre=?";

        try (Connection con=DBConnection.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToProducto(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Producto> searchByCategoria(int idCategoria) {
        List<Producto> lista=new ArrayList<>();
        String sql="select * from productos where id_categoria=? order by nombre";

        try (Connection con=DBConnection.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, idCategoria);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                lista.add(mapResultSetToProducto(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Producto> list() {
        List<Producto> lista=new ArrayList<>();
        String sql="select * from productos order by nombre";

        try (Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapResultSetToProducto(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean update(Producto p) {
        String sql="update productos set nombre=?, id_categoria=?, precio=?, stock=? where id_producto=?";
        
        try (Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setInt(2, p.getCategoria().getIdCategoria());
            ps.setDouble(3, p.getPrecio());
            ps.setInt(4, p.getStock());
            ps.setInt(5, p.getIdProducto());
            return ps.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateStock(int idProducto, int nuevoStock) {
        String sql="update productos set stock=? where id_producto=?";

        try (Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, nuevoStock);
            ps.setInt(2, idProducto);
            return ps.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int idProducto) {
        String sql="delete from productos where id_producto=?";

        try (Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            return ps.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Producto mapResultSetToProducto(ResultSet rs) throws SQLException {
        Categoria c=catDAO.searchId(rs.getInt("id_categoria"));
        Producto p=new Producto(rs.getString("nombre"), c, rs.getDouble("precio"), rs.getInt("stock"));
        p.setId(rs.getInt("id_producto"));
        return p;
    }
}