package ca.sheridancollege.vuhi.database;

import ca.sheridancollege.vuhi.beans.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DatabaseAccess {

    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    public void insertTeam(Team team){
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "insert into Teams (TeamName, Continent, Played, Won, Drawn, Lost) " +
                "values (:teamName, :continent, :played, :won, :drawn, :lost) ";
        namedParameters.addValue("teamName",team.getTeamName());
        namedParameters.addValue("continent",team.getContinent());
        namedParameters.addValue("played",team.getPlayed());
        namedParameters.addValue("won",team.getWon());
        namedParameters.addValue("drawn",team.getDrawn());
        namedParameters.addValue("lost",team.getLost());
        int rowsAffected = jdbc.update(query,namedParameters);
        if (rowsAffected > 0){
            System.out.println("Inserted team into database!");
        }
    }

    public void deleteTeamById(int id){
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "delete from Teams where TeamID = :id";
        namedParameters.addValue("id",id);
        int rowsAffected = jdbc.update(query,namedParameters);
        if (rowsAffected > 0){
            System.out.println("Deleted team with ID: "+id+" from database!");
        }
    }

    public List<Team> selectAllTeams(){
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "select * from Teams";
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Team>(Team.class));
    }

    public List<Team> selectTeamsByCondition(Team team){
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "select * from Teams where TeamName like concat('%',:teamName,'%') or Continent like concat('%',:continent,'%')";
        namedParameters.addValue("teamName",team.getTeamName());
        namedParameters.addValue("continent",team.getTeamName());
        return  jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Team>(Team.class));
    }

    public List<Team> sortTeam (Team team){
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        if(team.getTeamName()=="Point"){
            String query = "select TeamName, Continent, Played, Won, Drawn, Lost from " +
                    "(select TeamName, Continent, Played, Won, Drawn, Lost, Won*3 + Drawn as Point from Teams order by Point desc)";
            return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Team>(Team.class));
        }
        else {
            String query = "select * from Teams order by " + team.getTeamName();
            return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Team>(Team.class));

        }
    }

    public List<Team> selectTeamById(int teamID){
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "select * from Teams where TeamID = :teamID ";
        namedParameters.addValue("teamID",teamID);
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Team>(Team.class));
    }

    public void updateTeam(Team team){
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "update Teams set TeamName = :teamName, Continent = :continent, Played = :played, Won = :won, Drawn = :drawn, Lost = :lost where TeamID = :id";
        namedParameters.addValue("id", team.getTeamID());
        namedParameters.addValue("continent",team.getContinent());
        namedParameters.addValue("played",team.getPlayed());
        namedParameters.addValue("won",team.getWon());
        namedParameters.addValue("drawn",team.getDrawn());
        namedParameters.addValue("lost",team.getLost());
        namedParameters.addValue("teamName", team.getTeamName());

        int rowsAffected = jdbc.update(query,namedParameters);
        if (rowsAffected > 0){
            System.out.println("Updated team with id " +team.getTeamID() + " in database!");
        }
    }
}
