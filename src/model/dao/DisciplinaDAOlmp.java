package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.entities.Disciplina;

public class DisciplinaDAOlmp implements DisciplinaDAO {

    private Connection conn;

    public DisciplinaDAOlmp(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Disciplina obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO disciplina (nomedisciplina, cargahoraria) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getNomeDisciplina());
            st.setInt(2, obj.getCargaHoraria());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setIdDisciplina(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Erro inesperado! Nenhuma linha afetada!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Disciplina obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE disciplina " +
                            "SET nomedisciplina = ?, cargahoraria = ? " +
                            "WHERE iddisciplina = ?");

            st.setString(1, obj.getNomeDisciplina());
            st.setInt(2, obj.getCargaHoraria());
            st.setInt(3, obj.getIdDisciplina());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM disciplina WHERE iddisciplina = ?");
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Disciplina findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM disciplina WHERE iddisciplina = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Disciplina obj = new Disciplina();
                obj.setIdDisciplina(rs.getInt("iddisciplina"));
                obj.setNomeDisciplina(rs.getString("nomedisciplina"));
                obj.setCargaHoraria(rs.getInt("cargahoraria"));
                return obj;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Disciplina> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM disciplina ORDER BY nomedisciplina");
            rs = st.executeQuery();

            List<Disciplina> list = new ArrayList<>();

            while (rs.next()) {
                Disciplina obj = new Disciplina();
                obj.setIdDisciplina(rs.getInt("iddisciplina"));
                obj.setNomeDisciplina(rs.getString("nomedisciplina"));
                obj.setCargaHoraria(rs.getInt("cargahoraria"));
                list.add(obj);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}