package util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Task;

/**
 *
 * @author nilci
 */
public class TaskTableModel extends AbstractTableModel{

    String [] columns = {"Nome", "Descri��o", "Prazo", "Tarefa Conclu�da", "Editar", "Excluir"};
    List<Task> tasks = new ArrayList();

    //Retorna o numero de tarefas
    @Override
    public int getRowCount() {
        return tasks.size();
    }

    //Retorna numero de linhas e colunas
    @Override
    public int getColumnCount() {
        return columns.length;
    }
    
    @Override
    public String getColumnName(int columnIndex){
        return columns[columnIndex];
    }

    //Retorna uma informa��o correspondente a uma linha e a uma coluna especifica
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0: 
                return tasks.get(rowIndex).getName();
            case 1: 
                return tasks.get(rowIndex).getDescription();
            case 2: 
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                return dateFormat.format(tasks.get(rowIndex).getDeadline());
            case 3: 
                return tasks.get(rowIndex).isIsCompleted();
            case 4: 
               return ""; 
            case 5: 
               return "";                
            default:
                return "Dados n�o foram encontrados";
        }
    }

    public String[] getColumns() {
        return columns;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }  
}
