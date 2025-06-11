package model.dao;

import db.DB;

public class DaoFactory {

    public static DisciplinaDAO createDisciplinaDAO() {
        return new DisciplinaDAOlmp(DB.getConnection());
    }
}