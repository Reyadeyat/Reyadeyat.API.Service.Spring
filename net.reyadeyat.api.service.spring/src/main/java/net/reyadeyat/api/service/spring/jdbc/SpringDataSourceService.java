package net.reyadeyat.api.service.spring.jdbc;

import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import net.reyadeyat.api.library.data.source.DataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class SpringDataSourceService extends DataSourceService {
    private JdbcTemplate jdbc;
    
    @Autowired
    public SpringDataSourceService(List<DataSource> datasources_list, Map<String,DataSource> datasources_map, JdbcTemplate jdbc) {
        super(datasources_list, datasources_map, jdbc.getDataSource());
    }
}
