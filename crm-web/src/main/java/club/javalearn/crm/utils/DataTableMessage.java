package club.javalearn.crm.utils;

import lombok.Data;

import java.util.List;

/**
 * DataTable消息封装类
 *
 * @author king-pan
 * @date  2017-12-09
 **/
@Data

public class DataTableMessage<T> {
    private List<T> data;
    private Integer start;
    private Integer length;
    private Long recordsTotal;
    private Long recordsFiltered;
}

