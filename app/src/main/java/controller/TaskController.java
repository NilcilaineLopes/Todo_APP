
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Task;
import util.ConnectionFactory;

/**
 *
 * @author nilci
 */
public class TaskController {
    
    public void save(Task task) {
        
        String sql = "INSERT INTO tasks ("
                + "idProject,"
                + " name,"
                + " description,"
                + "completed,"
                + "notes,"
                + "deadline,"
                + "createdAt,"
                + "updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
           connection = ConnectionFactory.getConnection();
           statement = connection.prepareStatement(sql);
           statement.setInt(1, task.getIdProject());
           statement.setString(2, task.getName());
           statement.setString(3, task.getDescription());
           statement.setBoolean(4, task.isIsCompleted());
           statement.setString(5, task.getNotes());
           statement.setDate(6, new Date(task.getDeadline().getTime()));
           statement.setDate(7, new Date(task.getCreatedAt().getTime()));
           statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
           statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao salvar a tarefa" 
                    + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
            
        }
    }
    
    public void upadate(Task task) {      
        String sql = "UPDATE tasks SET "
                + "idProject = ?, "
                + "name = ?, "
                + "description = ?, "
                + "completed = ?, "
                + "notes = ?, "
                + "deadline = ?, "
                + "createdAt = ?, "
                + "updatedAt = ? "
                + "WHERE id = ?"; 
        
        Connection connection = null;
        PreparedStatement statement = null; 
        
        try {
             //Estabelecendo conex�o com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //Preparando a query
            statement = connection.prepareStatement(sql);
            
            //Setando os valores do statement
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));          
            statement.setInt(9, task.getId());
            
            //Executando a query 
            statement.execute();  

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar tarefa " + ex.getMessage(), ex);
        }
    }    
    
    public void removeById(int taskId){
        String sql = "DELETE FROM tasks WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try{
            //Cria��o da conex�o com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //Preparando a query
            statement = connection.prepareStatement(sql);
            
            //Setando os valores
            statement.setInt(1, taskId);
            
            //Executando a query
            statement.execute();
        }catch (Exception e) {
            throw new RuntimeException("Erro ao deletar a tarefa");
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public List<Task> getAll(int idProject) {
        String sql =  "SELECT * FROM tasks WHERE idProject = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        //Lista de tarefas que ser� devolvida quando a chamada do m�todo acontecer
        List<Task> tasks = new ArrayList<Task>();
        
        try{
            //Cria��o da conex�o
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            
            //Setando um valor que corresponde ao filtro de busca 
            statement.setInt(1, idProject);
            
            //Valor retornado pela execu��o da query
            resultSet = statement.executeQuery();
            
            //Enquanto houvorem valores a serem percorridos no meu resultSet
            while (resultSet.next()){
                
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setIsCompleted(resultSet.getBoolean("completed"));
                task.setNotes(resultSet.getString("notes"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setUpdatedAt(resultSet.getDate("updatedAt"));
                
                tasks.add(task);
         }
            
        }catch(Exception ex){
            throw new RuntimeException("Erro ao inserir a tarefa" + ex.getMessage(), ex);
        
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        
        //Lista de tarefas que foi criada e carregada do banco de dados
        return tasks;
    }
    
}
