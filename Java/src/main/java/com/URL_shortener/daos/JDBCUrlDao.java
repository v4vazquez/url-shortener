package com.URL_shortener.daos;

import com.URL_shortener.models.URL;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class JDBCUrlDao implements UrlDao{

    private JdbcTemplate jdbcTemplate;

    public JDBCUrlDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void  createShortenedUrl(URL url){
        url.setCreatedTime(Timestamp.valueOf(LocalDateTime.now()));
        String sql = "INSERT INTO url (long_url, short_url, created_time, expiration_time) VALUES(?,?,?,?) ";

        jdbcTemplate.update(sql,url.getLongUrl(),url.getShortUrl(),url.getCreatedTime(),url.getExpirationTime());
     }

    @Override
    public URL getShortenedUrl(String shortUrl) {
        String sql ="SELECT * FROM url where short_url = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, shortUrl);
        if(results.next()) {
            URL url = mapRowToUrl(results);
            if (url.getExpirationTime().before(Timestamp.valueOf(LocalDateTime.now()))) {
                String deletedSqlQuery = "DELETE FROM url WHERE short_url = ?";
                jdbcTemplate.update(deletedSqlQuery, shortUrl);
                return null;
            }
            return url;
        }

            return null;
        }


    @Scheduled(fixedRate = 300000, initialDelay = 10000 )
    public void deleteExpiredUrls(){
        String sql = "DELETE FROM  url WHERE expiration_time <= CURRENT_TIMESTAMP";
        jdbcTemplate.update(sql);
    }


    @Override
    public List<URL> getAllUrls() {
        List<URL> allUrls = new ArrayList<>();
        String sql = "SELECT * FROM URL ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()){
            allUrls.add(mapRowToUrl(results));
        }
        return allUrls;
    }


    private URL mapRowToUrl(SqlRowSet rs){
        URL url = new URL();
        url.setUrlId(rs.getInt("url_id"));
        url.setLongUrl(rs.getString("long_url"));
        url.setShortUrl(rs.getString("short_url"));
        url.setCreatedTime(rs.getTimestamp("created_time"));
        url.setExpirationTime(rs.getTimestamp("expiration_time"));

       return url;
    }


}
