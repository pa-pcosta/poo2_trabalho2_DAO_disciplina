package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DisciplinaDAO;
import model.entities.Disciplina;

public class Program {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        DisciplinaDAO disciplinaDAO = DaoFactory.createDisciplinaDAO();

        System.out.println("=== TESTE 1: disciplina findById ===");
        Disciplina disciplina = disciplinaDAO.findById(1);
        System.out.println(disciplina);

        System.out.println("\n=== TESTE 2: disciplina findAll ===");
        List<Disciplina> list = disciplinaDAO.findAll();
        for (Disciplina d : list) {
            System.out.println(d);
        }

        System.out.println("\n=== TESTE 3: disciplina insert ===");
        Disciplina newDisciplina = new Disciplina(null, "Engenharia de Software", 72);
        disciplinaDAO.insert(newDisciplina);
        System.out.println("Inserido! Novo id = " + newDisciplina.getIdDisciplina());

        System.out.println("\n=== TESTE 4: disciplina update ===");
        disciplina = disciplinaDAO.findById(1);
        disciplina.setCargaHoraria(60);
        disciplinaDAO.update(disciplina);
        System.out.println("Update completo");

        System.out.println("\n=== TESTE 5: disciplina delete ===");
        System.out.print("Entre com o id para deletar: ");
        int id = sc.nextInt();
        disciplinaDAO.deleteById(id);
        System.out.println("Delete completo");

        sc.close();
    }
}